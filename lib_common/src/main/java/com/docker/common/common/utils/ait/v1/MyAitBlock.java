package com.docker.common.common.utils.ait.v1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyAitBlock {
    /**
     * text = "@" + name
     */
    public String text;

    /**
     * 类型，群成员/机器人
     */
    public int aitType;

    /**
     * 在文本中的位置
     */
    public List<MyAitSegment> segments = new ArrayList<>();

    public MyAitBlock(String name, int aitType) {
        this.text = "@" + name;
        this.aitType = aitType;
    }

    // 新增 segment
    public MyAitSegment addSegment(int start) {
        int end = start + text.length() - 1;
        MyAitSegment segment = new MyAitSegment(start, end);
        segments.add(segment);
        return segment;
    }

    /**
     * @param start      起始光标位置
     * @param changeText 插入文本
     */
    public void moveRight(int start, String changeText) {
        if (changeText == null) {
            return;
        }
        int length = changeText.length();
        Iterator<MyAitSegment> iterator = segments.iterator();
        while (iterator.hasNext()) {
            MyAitSegment segment = iterator.next();
            // 从已有的一个@块中插入
            if (start > segment.start && start <= segment.end) {
                segment.end += length;
                segment.broken = true;
            } else if (start <= segment.start) {
                segment.start += length;
                segment.end += length;
            }
        }
    }

    /**
     * @param start  删除前光标位置
     * @param length 删除块的长度
     */
    public void moveLeft(int start, int length) {
        int after = start - length;
        Iterator<MyAitSegment> iterator = segments.iterator();

        while (iterator.hasNext()) {
            MyAitSegment segment = iterator.next();
            // 从已有@块中删除
            if (start > segment.start) {
                // @被删除掉
                if (after <= segment.start) {
                    iterator.remove();
                } else if (after <= segment.end) {
                    segment.broken = true;
                    segment.end -= length;
                }
            } else if (start <= segment.start) {
                segment.start -= length;
                segment.end -= length;
            }
        }
    }

    // 获取该账号所有有效的@块最靠前的start
    public int getFirstSegmentStart() {
        int start = -1;
        for (MyAitSegment segment : segments) {
            if (segment.broken) {
                continue;
            }
            if (start == -1 || segment.start < start) {
                start = segment.start;
            }
        }
        return start;
    }

    public MyAitSegment findLastSegmentByEnd(int end) {
        int pos = end - 1;
        for (MyAitSegment segment : segments) {
            if (!segment.broken && segment.end == pos) {
                return segment;
            }
        }
        return null;
    }

    public boolean valid() {
        if (segments.size() == 0) {
            return false;
        }
        for (MyAitSegment segment : segments) {
            if (!segment.broken) {
                return true;
            }
        }
        return false;
    }

}