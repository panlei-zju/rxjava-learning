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
 *     from�ɽ����������ݣ�Iterable��Future��Array��
 *     1. Iterable��Array������⣬������伯�е����ݣ����˱������̡�
 *     2. Future�Ƿ���Future.get�������صĵ������ݣ������¼���
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
     * �������С����
     */
    public static void callFuture() throws Exception{
        long time1 = System.currentTimeMillis();
        Callable<Integer> onlineShopping = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("��һ�����������������ˮ��");
                Thread.sleep(5000);  // �ȴ���˵�ũҩ���ݵ�
                System.out.println("������ϣ�����˴���ϴ�������");
                return 1;
            }

        };

        FutureTask<Integer> task = new FutureTask<Integer>(onlineShopping);
        new Thread(task).start();

        System.out.println("�ڶ�����������");
        // �ڶ��� �����������������ϴ��ʳ�ģ������
        Thread.sleep(2000);  // ģ��ʳ�Ĵ�������

        System.out.println("�ڶ������⳴����");

        // ���������������ַ�ʽ�Ա�
        //past(task);
        now(task);

        System.out.println("������");
        long time2 = System.currentTimeMillis();
        System.out.println("��������"+(time2-time1)+"ms");

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
        while (!task.isDone()) {  // ��������Ƿ�������
            System.out.println("����������˻�û�ݺ�");
            if(hungry++ > 6) {
                System.out.println("������ô�ã�ʵ��̫���ˣ�������");
                break;
            }
            System.out.println("����һ�׸��ʱ��,�ٵȵȰɣ���ȫ��һ");
            Thread.sleep(1000);
        }
    }

}
