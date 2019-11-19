package com.bfhd.evaluate.vm;

import android.arch.lifecycle.LiveData;

import com.alibaba.fastjson.JSON;
import com.bfhd.evaluate.api.EnStudyService;
import com.bfhd.evaluate.vo.BookListVo;
import com.bfhd.evaluate.vo.RadioLessonVo;
import com.bfhd.evaluate.vo.RadioMenuVo;
import com.dcbfhd.utilcode.utils.LogUtils;
import com.docker.common.common.vm.container.NitCommonContainerViewModel;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxindang on 2018/10/19.
 */

public class EnStudyRxViewModel extends NitCommonContainerViewModel {
    @Inject
    EnStudyService commonService;

    @Inject
    public EnStudyRxViewModel() {

    }
    //  RxJava 创建一个observable对象来做工作，然后使用各种操作符建立起来链式操作，
    // 把想要的数据一步步变换为最后所需要的数据，发射给subscriber处理。

    /**
     * 返回 {@link RadioLessonVo}
     *
     * @param apiurl
     * @param param
     * @return
     */
    @Override
    public LiveData<ApiResponse<BaseResponse>> getServicefun(String apiurl, HashMap param) {
        LiveData<ApiResponse<BaseResponse>> apiResponseLiveData = commonService.lessionRadioLessonList(param);
        return apiResponseLiveData;
    }

    /**
     * 依次concatMap执行
     *
     * @param memberid
     * @param uuid
     */
    public void rxConcatMap(String memberid, String uuid) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        commonService.tttt1(paramMap).doOnNext(new Consumer<BaseResponse<List<RadioMenuVo>>>() {
            @Override
            public void accept(BaseResponse<List<RadioMenuVo>> listBaseResponse) throws Exception {
                LogUtils.e(JSON.toJSONString(listBaseResponse.getRst().get(0)));
            }
        }).concatMap(new Function<BaseResponse<List<RadioMenuVo>>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(BaseResponse<List<RadioMenuVo>> listBaseResponse) throws Exception {
                RadioMenuVo vo = listBaseResponse.getRst().get(0);
                paramMap.put("lession_dubbing_id", vo.getId());
                return commonService.tttt2(paramMap);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
                .cast(BaseResponse.class).subscribe(
                new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse baseResponse) throws Exception {
                        List<RadioLessonVo> radioLessonVos = (List<RadioLessonVo>) baseResponse.getRst();
                        LogUtils.e(radioLessonVos.get(0).getName());
                        mContainerLiveData.setValue(radioLessonVos.get(0));
                    }
                });

    }

    /**
     * merge 合并数据源
     *
     * @param memberid
     * @param uuid
     */
    public void rxMerge(String memberid, String uuid) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberid", memberid);
        paramMap.put("uuid", uuid);
        Observable.merge(commonService.tttt1(paramMap), commonService.ttttt3(paramMap))
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<BaseResponse<? extends List<? extends Object>>>() {
                    @Override
                    public void accept(BaseResponse<? extends List<? extends Object>> baseResponse) throws Exception {
                        List d = baseResponse.getRst();
                        if (d.get(0) instanceof RadioMenuVo) {
                            List<RadioMenuVo> radioMenuVos = d;
                            LogUtils.e(JSON.toJSONString(radioMenuVos));
                        } else {
                            List<BookListVo> radioMenuVos = d;
                            LogUtils.e(JSON.toJSONString(radioMenuVos));

                        }
                        mContainerLiveData.setValue(d);

                    }
                });

    }
//rx 创建

    private void t1() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                PrintLog(String.format("subscribe   Thread-name:%s", Thread.currentThread().getName()));
                e.onNext("send    ");
                e.onComplete();
            }
        }).observeOn(Schedulers.newThread()).subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Object>() {// Consumer---->Observable
                    @Override
                    public void accept(Object o) throws Exception {
                    }
                });/**
         * .subscribe(new Observer() { // Observer
         *
         * @Override public void onSubscribe(Disposable d) { // TODO Auto-generated
         *           method stub PrintLog(String.format("onSubscribe
         *           Thread-name:%s",Thread.currentThread().getName()));
         *
         *           }
         *
         * @Override public void onNext(Object t) { // TODO Auto-generated method stub
         *           PrintLog(String.format("onNext
         *           Thread-name:%s",Thread.currentThread().getName()));
         *
         *           }
         *
         * @Override public void onError(Throwable e) { // TODO Auto-generated method
         *           stub PrintLog(String.format("onError
         *           Thread-name:%s",Thread.currentThread().getName()));
         *
         *           }
         *
         * @Override public void onComplete() { // TODO Auto-generated method stub
         *           PrintLog(String.format("onComplete
         *           Thread-name:%s",Thread.currentThread().getName()));
         *
         *           } });
         **/

    }

    private void t2() {
        // interval 定时器
        Observable.interval(3, TimeUnit.MINUTES).subscribe(new Observer<Object>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Object t) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

//		.subscribe(new Consumer<Object>() {
//			@Override
//			public void accept(Object t) throws Exception {
//				PrintLog(t);
//			}
//		});

        // 循环//打印range 0-4
        Observable.range(0, 5).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object t) throws Exception {
                PrintLog("range---accept  t=" + t);
            }
        });
        // repeat 重复3次循环
        Observable.range(0, 5).repeat(3).subscribe(new Consumer<Object>() {

            @Override
            public void accept(Object t) throws Exception {
                PrintLog("range---accept  t=" + t);
            }
        });
    }

    // 倒计时
    private void t3() {
        Disposable disposable = Flowable.intervalRange(0, 200 + 1, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object t) throws Exception {
                        PrintLog("doOnNext-accept:" + t);
                    }
                }).doOnComplete(new Action() {

                    @Override
                    public void run() throws Exception {
                        PrintLog("doOnComplete-----");

                    }
                }).subscribe();

    }

    // 变换操作符 map,flatMap,cast ,concatMap,buffer
    private void t4() {
        // map
        Observable.just(System.currentTimeMillis()).map(new Function<Long, String>() {

            @Override
            public String apply(Long t) throws Exception {
                return toDate(t);// 单一转换
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String t) throws Exception {
                PrintLog(t);
            }
        });
//flatMap   任务会存在交叉现象，concatMap不会存在交叉现象
        Observable.just(ttt1(), ttt2()).flatMap(new Function<List<?>, Observable<?>>() {
            @Override
            public Observable<?> apply(List t) throws Exception {//
                return Observable.create(new ObservableOnSubscribe<List>() {

                    @Override
                    public void subscribe(ObservableEmitter<List> e) throws Exception {
                        if (t.size() == 5) {
                            Thread.sleep(1000);
                        }
                        e.onNext(t);
                        e.onComplete();
                    }
                });// 一或者多的转换
            }
        }).cast(List.class).subscribe(new Consumer<List>() {// cast 强制转换结果
            @Override
            public void accept(List t) throws Exception {
                String deString = JSON.toJSONString(t);

                PrintLog(deString);
            }
        });

        // 将原来的observable变换成新的 ，每次发射根据指定的条件发射
        Observable.just(1, 2, 3, 4).buffer(3).cast(List.class).subscribe(new Consumer<List>() {

            @Override
            public void accept(List t) throws Exception {
                PrintLog("buffer-begin");
                PrintLog(JSON.toJSONString(t));
                PrintLog("buffer-end");
            }
        });
    }

    // 过滤
    public void t5() {
        Observable.just(1, 20, 65, -5, 7, 19).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer >= 10;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                PrintLog(integer);
            }
        });
    }

    // 头部添加数据
    public void t6() {
        Observable.just(ttt1()).startWith(ttt1()).subscribe(new Consumer<List>() {
            @Override
            public void accept(List integer) throws Exception {
                PrintLog("startWith;" + JSON.toJSONString(integer));
            }
        });
        Observable o1 = Observable.just(ttt1());
        Observable o12 = Observable.just(ttt1());
        Observable o2 = Observable.just(ttt2());
        Observable.merge(o1, o12).subscribe(new Consumer<List>() {// 会让多个数据交错，contact不会交错
            @Override
            public void accept(List integer) throws Exception {
                PrintLog("merge:" + JSON.toJSONString(integer));
            }
        });
        //合并数据 zip
        Observable.zip(o1, o2, new BiFunction<List<TestEn1>, List<TestEn2>, List<TestEn1>>() {

            @Override
            public List<TestEn1> apply(List<TestEn1> testEn1s, List<TestEn2> testEn2s) throws Exception {
                for (int i = 0; i < testEn1s.size(); i++) {
                    TestEn1 testEn1 = testEn1s.get(i);
                    int in = testEn2s.indexOf(testEn1);
                    if (in > -1) {
                        TestEn2 testEn2 = testEn2s.get(in);
                        testEn1.age = testEn2.age;
                        testEn1.avater = testEn2.avater;
                    }
                }
                return testEn1s;
            }
        }).subscribe(new Consumer<List>() {
            @Override
            public void accept(List integer) throws Exception {
                PrintLog("zip:" + JSON.toJSONString(integer));
            }
        });

        //超时 c
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

                for (int i = 0; i < 5; i++) {
                    if (i == 3) {
                        Thread.sleep(i * 100);
                    }

                    e.onNext(i);
                }
                e.onComplete();

            }
        }).timeout(200, TimeUnit.MILLISECONDS, new ObservableSource<Integer>() {
            @Override
            public void subscribe(Observer<? super Integer> observer) {
                PrintLog("超时出现在" + observer);

            }
        }).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer t) throws Exception {
                PrintLog(t);

            }
        });
    }

    private List<TestEn1> ttt1() {
        List<TestEn1> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TestEn1 testEn1 = new TestEn1();
            testEn1.id = "73642" + i;
            testEn1.name1 = "du2id" + i;
            testEn1.time1 = System.currentTimeMillis();
            list.add(testEn1);
        }
        return list;
    }

    private List<TestEn2> ttt2() {
        List<TestEn2> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TestEn2 testEn1 = new TestEn2();
            testEn1.id = "73642" + i;
            testEn1.age = String.valueOf(i);
            testEn1.avater = "avatar " + i;
            list.add(testEn1);
        }
        return list;
    }

    public static class TestEn1 {
        public String id;
        public String name1;
        public Long time1;
        public String age;
        public String avater;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            TestEn2 other = (TestEn2) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
        }
    }

    public static class TestEn2 {
        public String id;
        public String age;
        public String avater;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;

            TestEn2 other = (TestEn2) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
        }

    }

    /**
     * 详情 https://www.jianshu.com/p/ff8167c1d191/
     * 背压 ->
     * 当上下游在不同的线程中，通过Observable发射，处理，响应数据流时，如果上游发射数据的速度 "快于" 下游接收处理数据的速度，
     * 这样对于那些没来得及处理的数据就会造成积压，这些数据既不会丢失，也不会被垃圾回收机制回收，而是存放在一个异步缓存池中，
     * 如果缓存池中的数据一直得不到处理，越积越多，最后就会造成内存溢出，这便是响应式编程中的背压（backpressure）问题
     */
    private void test1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                int i = 0;
                while (true) {
                    i++;
                    System.out.println("发射---->" + i);
                    e.onNext(i);
                }
            }
        })
//       .subscribeOn(Schedulers.newThread())
//       .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            Thread.sleep(50);
                            System.out.println("接收------>" + integer);
                        } catch (InterruptedException ignore) {
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("接收------>完成");
                    }
                });
    }

    /**
     * 背压
     */
    private void testFlowable() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                        int i = 0;
                        while (true) {
                            if (e.requested() == 0) continue;//此处添加代码，让flowable按需发送数据
                            System.out.println("发射---->" + i);
                            i++;
                            e.onNext(i);
                        }
                    }
                }, BackpressureStrategy.MISSING)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread()).blockingSubscribe(new Subscriber<Integer>() {
            private Subscription mSubscription;

            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);            //设置初始请求数据量为1
                mSubscription = s;
            }

            @Override
            public void onNext(Integer integer) {
                try {
                    Thread.sleep(50);
                    System.out.println("接收------>" + integer);
                    mSubscription.request(1);//每接收到一条数据增加一条请求量
                } catch (InterruptedException ignore) {
                }
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public static void PrintLog(Object s) {
        System.err.println(String.valueOf(s));
    }

    public static String toDate(Long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(date));
    }


}
