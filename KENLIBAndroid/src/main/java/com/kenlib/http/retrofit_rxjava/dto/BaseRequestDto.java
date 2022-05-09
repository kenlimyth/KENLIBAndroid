package com.kenlib.http.retrofit_rxjava.dto;


public class BaseRequestDto<T> {
    private HeaderDto header;
    private T data;

    public HeaderDto getHeader() {
        return header;
    }

    public void setHeader(HeaderDto header) {
        this.header = header;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
