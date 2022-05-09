package com.kenlib.http.retrofit_rxjava.dto;

public class BaseResponseDto<T> {
    public int code;
    public String message;
    public T data;


}