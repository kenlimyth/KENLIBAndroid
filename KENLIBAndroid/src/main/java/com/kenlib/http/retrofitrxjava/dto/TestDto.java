package com.kenlib.http.retrofitrxjava.dto;

public class TestDto {

    private String from;
    private String to;
    private String vendor;
    private String out;
    private int errNo;

    //定义 输出返回数据 的方法
    public void show() {
        System.out.println("Rxjava翻译结果：" + from);
        System.out.println("Rxjava翻译结果：" + to);
        System.out.println("Rxjava翻译结果：" + vendor);
        System.out.println("Rxjava翻译结果：" + out);
        System.out.println("Rxjava翻译结果：" + errNo);
    }
}


