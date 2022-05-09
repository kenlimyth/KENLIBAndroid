package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class BaseResponseDto1<T> {
    public int code;
    public String message;
    public T data;

    public List<FilesDto> Files;

}