package com.rhaegar.base;

import rx.Observable;
import rx.Subscriber;

/**
 * <p>just经常用于测试，mock测试数据非常方便</p>
 *
 * @author rhaegar(lei_pan1989@163.com)
 * @date 2018/6/25
 */

public class CreateJustDemo {

    public static void call() {

        Observable.just(1,2,3,4,5,6,7,8,9,10).subscribe(new Subscriber<Integer>() {
            public void onCompleted() {
                System.out.println("Sequence complete.");
            }


            public void onError(Throwable throwable) {

            }

            public void onNext(Integer item) {
                System.out.println("accepte : " + item);
            }
        });

    }

}

