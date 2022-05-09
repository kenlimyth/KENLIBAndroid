package com.kenlib.sample;

/**
 * 构造代码块 ，运行示例
 */
public class CodeBlock {

    static class t {

        {
            System.out.println("构造代码块");
        }

        t() {
            System.out.println("构造函数");
        }

        void ff() {
            System.out.println("方法执行进入");

            {
                System.out.println("方法中的构造代码块");
            }
            System.out.println("方法执行");

        }
    }

    public static void main(String[] args) {

        t t = new t();
        t.ff();
        t.ff();

    }


}
