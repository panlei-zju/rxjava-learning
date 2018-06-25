package com.rhaegar.base;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * <p>
 *     from可接收三类数据，Iterable，Future，Array。
 *     1. Iterable和Array都好理解，逐个发射集中的数据，简化了遍历过程。
 *     2. Future是发送Future.get方法返回的单个数据，基于事件。
 * </p>
 *
 * @author rhaegar(lei_pan1989@163.com)
 * @date 2018/6/25
 */
public class CreateFromDemo {

    public static void callArray() {

        final Integer[] values = new Integer[] {1,3,5,7,9};

        Observable.from(values).forEach(new Action1<Integer>() {
            public void call(Integer integer) {
                System.out.println("accept : " + integer);
            }
        });

    }

    public static void callIterable() {

        List<String> values = Arrays.asList("Hello,","I'm Rhaegar.","Nice to meet you.");

        Observable.from(values).subscribe(new Subscriber<String>() {
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }


            public void onError(Throwable throwable) {

            }

            public void onNext(String item) {
                System.out.println("accepte : " + item);
            }
        });

    }

    /**
     * 举生活的小例子
     */
    public static void callFuture() throws Exception{
        long time1 = System.currentTimeMillis();
        Callable<Integer> onlineShopping = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("第一步：将青菜泡入淘米水中");
                Thread.sleep(5000);  // 等待青菜的农药被泡掉
                System.out.println("浸泡完毕，将青菜打捞洗净并烹饪");
                return 1;
            }

        };

        FutureTask<Integer> task = new FutureTask<Integer>(onlineShopping);
        new Thread(task).start();

        System.out.println("第二步：炒个肉");
        // 第二步 切肉或处理其他无需清洗的食材，并烹饪
        Thread.sleep(2000);  // 模拟食材处理和烹饪

        System.out.println("第二步：肉炒完了");

        // 第三步，下面两种方式对比
        //past(task);
        now(task);

        System.out.println("开饭！");
        long time2 = System.currentTimeMillis();
        System.out.println("做饭花了"+(time2-time1)+"ms");

    }

    private static void now(FutureTask<Integer> task) {
        Observable.from(task).subscribe(new Subscriber<Integer>() {
            public void onCompleted() {

            }

            public void onError(Throwable throwable) {

            }

            public void onNext(Integer integer) {

            }
        });
    }

    private static void past(FutureTask<Integer> task) throws InterruptedException {

        int hungry = 0;
        while (!task.isDone()) {  // 看看青菜是否浸泡完毕
            System.out.println("第三步：青菜还没泡好");
            if(hungry++ > 6) {
                System.out.println("等了这么久，实在太饿了，不等了");
                break;
            }
            System.out.println("给我一首歌的时间,再等等吧，安全第一");
            Thread.sleep(1000);
        }
    }

}
