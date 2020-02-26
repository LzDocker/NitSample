package com.docker.common.common.utils.ait.v1;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.docker.common.common.command.ReplyCommand;
import com.docker.common.common.config.Constant;

import java.util.List;

/**
 * Created by hzchenkang on 2017/7/10.
 */

public class MyAitManager implements TextWatcher {

    private Context context;

    private ReplyCommand replyCommand;

    private MyAitContactsModel aitContactsModel;

    private int curPos;

    private boolean ignoreTextChange = false;

    private MyAitTextChangeListener listener;

    public MyAitManager(Context context, ReplyCommand replyCommand) {
        this.context = context;
        this.replyCommand = replyCommand;
        aitContactsModel = new MyAitContactsModel();
    }

    public void setTextChangeListener(MyAitTextChangeListener listener) {
        this.listener = listener;
    }

    public List<String> getAitTeamMember() {
        return aitContactsModel.getAitTeamMember();
    }

    public String getAitRobot() {
        return aitContactsModel.getFirstAitRobot();
    }

    public String removeRobotAitString(String text, String robotAccount) {
        MyAitBlock block = aitContactsModel.getAitBlock(robotAccount);
        if (block != null) {
            return text.replaceAll(block.text, "");
        } else {
            return text;
        }
    }

    public void reset() {
        aitContactsModel.reset();
        ignoreTextChange = false;
        curPos = 0;
    }

    /**
     * ------------------------------ 增加@成员 --------------------------------------
     */

    public void handResult(AitVo aitVo) {
        String account = "";
        String name = "";
        account = aitVo.aitId;
        name = aitVo.aitname;
        insertAitMemberInner(account, name, MyAitContactType.TEAM_MEMBER, curPos, false);
    }

    private void insertAitMemberInner(String account, String name, int type, int start, boolean needInsertAitInText) {
        name = name + " ";
        String content = needInsertAitInText ? "@" + name : name;
        if (listener != null) {
            // 关闭监听
            ignoreTextChange = true;
            // insert 文本到editText
            listener.onTextAdd(content, start, content.length());
            // 开启监听
            ignoreTextChange = false;
        }

        // update 已有的 aitBlock
        aitContactsModel.onInsertText(start, content);

        int index = needInsertAitInText ? start : start - 1;
        // 添加当前到 aitBlock
        aitContactsModel.addAitMember(account, name, type, index);
    }

    /**
     * ------------------------------ editText 监听 --------------------------------------
     */

    // 当删除尾部空格时，删除一整个segment,包含界面上也删除
    private boolean deleteSegment(int start, int count) {
        if (count != 1) {
            return false;
        }
        boolean result = false;
        MyAitSegment segment = aitContactsModel.findAitSegmentByEndPos(start);
        if (segment != null) {
            int length = start - segment.start;
            if (listener != null) {
                ignoreTextChange = true;
                listener.onTextDelete(segment.start, length);
                ignoreTextChange = false;
            }
            aitContactsModel.onDeleteText(start, length);
            result = true;
        }
        return result;
    }

    /**
     * @param editable 变化后的Editable
     * @param start    text 变化区块的起始index
     * @param count    text 变化区块的大小
     * @param delete   是否是删除
     */
    private void afterTextChanged(Editable editable, int start, int count, boolean delete) {
        curPos = delete ? start : count + start;
        if (ignoreTextChange) {
            return;
        }
        if (delete) {
            int before = start + count;
            if (deleteSegment(before, count)) {
                return;
            }
            aitContactsModel.onDeleteText(before, count);

        } else {
            if (count <= 0 || editable.length() < start + count) {
                return;
            }
            CharSequence s = editable.subSequence(start, start + count);
            if (s == null) {
                return;
            }
            if (s.toString().equals(Constant.ACTIVE_CONTACT_FALG)) {
                if (aitContactsModel.getAitTeamMember() == null || aitContactsModel.getAitTeamMember().size() == 0) {
                    replyCommand.exectue();
                }

//                if (!TextUtils.isEmpty(tid) || robot) {
//                    AitContactSelectorActivity.start(context, tid, robot);
//
//                }
            }
            aitContactsModel.onInsertText(start, s.toString());
        }
    }

    private int editTextStart;
    private int editTextCount;
    private int editTextBefore;
    private boolean delete;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        delete = count > after;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.editTextStart = start;
        this.editTextCount = count;
        this.editTextBefore = before;
    }

    @Override
    public void afterTextChanged(Editable s) {
        afterTextChanged(s, editTextStart, delete ? editTextBefore : editTextCount, delete);
    }
}
