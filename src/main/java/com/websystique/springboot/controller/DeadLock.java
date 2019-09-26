package com.websystique.springboot.controller;

public class DeadLock {
    private static String a = "1";

    private static String b = "2";

    public static void methodA() {
        synchronized (a) {
            System.out.println("我是A方法中获得到了A锁");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (b) {
                System.out.println("我是A方法中获取到b锁");
            }
        }

    }

    public static void methodB() {
        synchronized (b) {
            System.out.println("我是B方法中获得到了b锁");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (a) {
                System.out.println("我是B方法中获取到a锁");
            }
        }

    }

    public static void main(String[] args) {

        new Thread(() -> {
            methodA();
        }).start();
        new Thread(() -> {
            methodB();
        }).start();


    }
}
