package com.company;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {
        System.out.println("main start");
        CompletableFuture.runAsync(() -> {
            CompletableFuture<Void> t1 = CompletableFuture.runAsync(() -> {
                System.out.println("t1 start");
                try {
                    Thread.sleep(new Random().nextInt(2000));
                } catch (InterruptedException e) {

                }
                System.out.println("t1 end");
            });
            CompletableFuture<Void> t2 = CompletableFuture.runAsync(() -> {
                System.out.println("t2 start");
                try {
                    Thread.sleep(new Random().nextInt(2000));
                } catch (InterruptedException e) {

                }
                System.out.println("t2 end");
            });
            CompletableFuture.allOf(t1, t2).join();
        });
        System.out.println("main deploy end");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {

        }
        System.out.println("main end");
    }
}
