package com.kenlib.http.retrofitrxjava.dto;

public class BaseResponseDto<T> {
    public int code;
    public String message;
    public T data;


}