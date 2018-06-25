package com.rhaegar;

import com.rhaegar.base.CreateFromDemo;
import com.rhaegar.base.CreateJustDemo;
import rx.Observable;
import rx.functions.Action1;

public class HelloWorld {
    public static void main(String[] args) throws Exception{
        //hello("Rhaegar", "Tegarean", "World");

        //CreateJustDemo.call();
        //CreateFromDemo.callIterable();
        CreateFromDemo.callFuture();


    }


    public static void hello(String... names) {
        Observable.from(names).subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }

        });
    }

}
