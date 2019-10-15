package com.docker.common.common.router;
import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dcbfhd.utilcode.utils.ToastUtils;
import com.docker.common.api.OpenService;
import com.docker.common.common.command.ReplyCommand;
import com.docker.common.common.command.ReplyCommandParam;
import com.docker.common.common.config.Constant;
import com.docker.common.common.utils.ParamUtils;
import com.docker.common.common.utils.cache.DbCacheUtils;
import com.docker.core.di.module.cachemodule.CacheDatabase;
import com.docker.core.di.module.cachemodule.CacheEntity;
import com.docker.core.util.AppExecutors;
import com.docker.core.util.IOUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

/*
 * 路由控制
 *
 * 路由初始化-----》跳转控制
 *routerControl.Jump(new RouterParam.RouterBuilder(RouterInfo.CIRCLE_DETAIL).withParam(staDetailParam).create());
 * */
@Singleton
public class RouterControl {

    @Inject
    AppExecutors appExecutors;

    @Inject
    CacheDatabase cacheDatabase;

    @Inject
    OpenService openService;

    @Inject
    DbCacheUtils dbCacheUtils;

    String mtime;

    @Inject
    public RouterControl() {
    }

    private static HashMap<String, Router> routerMap = new HashMap<>();

    private RoutersServerVo routersServerVo = new RoutersServerVo();

    public void initRouterData(LifecycleOwner lifecycleOwner, ReplyCommandParam replyCommand) {
        dbCacheUtils.loadFromDb("router_version").observe(lifecycleOwner, o -> {
            mtime = (String) o;
            HashMap<String, String> param = new HashMap<>();
            if (TextUtils.isEmpty(mtime)) {
                param.put("mtime", "1");
            } else {
                param.put("mtime", mtime);
            }
            openService.fetchRouter(param).observe(lifecycleOwner, baseResponseApiResponse -> {
                if (baseResponseApiResponse.isSuccessful() && baseResponseApiResponse.body != null
                        && "0".equals(baseResponseApiResponse.body.getErrno())) {
                    RoutersServerVo routersServerVo = baseResponseApiResponse.body.getRst();
                    dbCacheUtils.save("router_db", routersServerVo, () -> {
                        dbCacheUtils.save("router_version", routersServerVo.mtime, null);
                        initData(() -> {
                            Log.d("sss", "onComplete:路由初始化成功");
                            if (replyCommand != null) {
                                replyCommand.exectue(true);
                            }
                        }, false);
                    });
                } else {
                    initData(() -> {
                        Log.d("sss", "onComplete:路由初始化成功");
                        if (replyCommand != null) {
                            replyCommand.exectue(true);
                        }
                    }, true);
                }
            });
        });
    }

    // 初始化路由数据
    public void initData(ReplyCommand replyCommand, boolean ischeck) {
        if (ischeck) {
            if (routerMap.size() > 0) {
                return;
            }
        }
        appExecutors.diskIO().execute(() -> {
            CacheEntity souce = cacheDatabase.cacheEntityDao().LoadCacheSync("router_db");
            if (souce != null && souce.getData() != null) {
                routersServerVo = (RoutersServerVo) IOUtils.toObject(souce.getData());
            }
            if (routersServerVo.routes == null || routersServerVo.routes.size() == 0) {
                AppRouter appRouter = new AppRouter();
                HashMap parammap = (HashMap<String, String>) ParamUtils.objectToMap(appRouter, false);
                Iterator iter = parammap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    Router routerEntry = new Router();
                    routerEntry.androidRoute = val;
                    routerEntry.isdefault = "0";
                    routerEntry.paramStr = null;
                    routerEntry.key = key;
                    routersServerVo.routes.add(routerEntry);
                }
            }
            for (int i = 0; i < routersServerVo.routes.size(); i++) {
                routerMap.put(routersServerVo.routes.get(i).key, routersServerVo.routes.get(i));
            }
            if (replyCommand != null) {
                appExecutors.mainThread().execute(() -> replyCommand.exectue());
            }
        });
    }


    // 更新内存中的 路由数据
    public void updateData(ReplyCommand replyCommand) {
        appExecutors.diskIO().execute(() -> {
            CacheEntity souce = cacheDatabase.cacheEntityDao().LoadCacheSync("router_db");
            if (souce != null) {
                routersServerVo = (RoutersServerVo) IOUtils.toObject(souce.getData());
            }
            if (routersServerVo.routes.size() == 0) {
                AppRouter appRouter = new AppRouter();
                HashMap parammap = (HashMap<String, String>) ParamUtils.objectToMap(appRouter, false);
                Iterator iter = parammap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    String val = (String) entry.getValue();
                    Router routerEntry = new Router();
                    routerEntry.androidRoute = val;
                    routerEntry.isdefault = "0";
                    routerEntry.paramStr = null;
                    routerEntry.key = key;
                    routersServerVo.routes.add(routerEntry);
                }
            }
            for (int i = 0; i < routersServerVo.routes.size(); i++) {
                routerMap.put(routersServerVo.routes.get(i).key, routersServerVo.routes.get(i));
            }
            if (replyCommand != null) {
                appExecutors.mainThread().execute(() -> replyCommand.exectue());
            }
        });
    }

//    public void jump(String key, ReplyCommandParam replyCommandParam) {
//        if (routerMap.size() == 0) {
//            initData(() -> {
//                if (routerMap.size() == 0) {
//                    return;
//                }
//                replyCommandParam.exectue(routerMap.get(key).path);
//            });
//        } else {
//            replyCommandParam.exectue(routerMap.get(key).path);
//        }
//    }

    /*
     * key  路由注册的key  对应 routerinfo 中的属性  必须参数
     *
     * param 跳转界面传递的参数 非必填  在到达的界面 使用  Constant.ParamTrans 作为key 来获取
     *
     * ispush 是否推送跳转 必传参数
     *
     * content code 非必须参数   传入则是 forResult 的跳转
     *
     * */
    public void Jump(RouterParam routerParam) {
        if (routerMap.size() == 0) {
            initData(() -> {
                if (routerMap.size() == 0) {
                    return;
                }
                if (routerMap.containsKey(routerParam.key)) {
                    processJump(routerParam);
                } else {
                    ToastUtils.showShort("功能暂未开放，敬请期待...");
                }
            }, true);
        } else {
            if (routerMap.containsKey(routerParam.key)) {
                processJump(routerParam);
            } else {
                ToastUtils.showShort("功能暂未开放，敬请期待...");
            }
        }
    }

    private void processJump(RouterParam routerParam) {
        Router routerEntry = routerMap.get(routerParam.key);
        Object param = routerParam.param;
        boolean isPush = routerParam.ispush;
        boolean isfromH5 = routerParam.isFormH5;
        Activity content = routerParam.context;
        int code = routerParam.code;
        Postcard postcard;
        if (isPush) {
            if (param != null) {
                postcard = ARouter.getInstance().build(routerEntry.androidRoute)
                        .withSerializable(Constant.ParamTrans, (Serializable) param);
            } else {
                postcard = ARouter.getInstance().build(routerEntry.androidRoute);
            }
            postcard.navigation();
            return;
        }
        if (isfromH5) {  // h5--->原生
            if (param != null) {
                postcard = ARouter.getInstance().build(routerEntry.androidRoute)
                        .withSerializable(Constant.ParamTrans, (Serializable) param);
            } else {
                postcard = ARouter.getInstance().build(routerEntry.androidRoute);
            }
            postcard.navigation();
            return;
        }

        if ("1".equals(routerEntry.type)) {  // app 原生跳转
            if ("0".equals(routerEntry.isdefault)) {   // 后端指定参数
                if (!TextUtils.isEmpty(routerEntry.paramStr)) {
                    HashMap<String, String> parammap = new HashMap<>();
                    String[] parmarr = routerEntry.paramStr.split("&");
                    for (int i = 0; i < parmarr.length; i++) {
                        String[] paramone = parmarr[i].split("=");
                        parammap.put(paramone[0], paramone[1]);
                    }
                    postcard = ARouter.getInstance().build(routerEntry.androidRoute)
                            .withSerializable(Constant.ParamTrans, parammap);
//                        .withSerializable(Constant.ParamTrans, (Serializable) ParamUtils.mapToObject(parammap, new Object(), false));
                } else {
                    postcard = ARouter.getInstance().build(routerEntry.androidRoute);
                }
                if (content != null && code > 0) {
                    postcard.navigation(content, code);
                } else {
                    postcard.navigation();
                }
            } else {
                if (param != null) {
                    postcard = ARouter.getInstance().build(routerEntry.androidRoute)
                            .withSerializable(Constant.ParamTrans, (Serializable) param);
                } else {
                    postcard = ARouter.getInstance().build(routerEntry.androidRoute);
                }
                if (content != null && code > 0) {
                    postcard.navigation(content, code);
                } else {
                    postcard.navigation();
                }
            }
        } else {
            ARouter.getInstance().build(AppRouter.COMMONH5).withString("weburl", routerEntry.httpurl).withString("title", routerEntry.name).navigation();
        }
    }

}
