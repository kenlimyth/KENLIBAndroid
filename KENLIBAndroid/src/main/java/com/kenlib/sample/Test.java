package com.kenlib.sample;

/**
 * 测试java类
 */
public class Test {


    static abstract class aa {
        void aff() {
            System.out.println("aff aa");

        }

        abstract void test();
    }

    static class a extends aa {
        @Override
        void aff() {
            super.aff();
            System.out.println("aff a");
        }

        @Override
        void test() {
            System.out.println("test a");
        }
    }

    static class b extends a {
        @Override
        void aff() {
            System.out.println("aff b");
            super.aff();
            test();
        }


    }

    public static void main(String[] args) {
        b b = new b();
        b.aff();
//        b.test();
    }

}
