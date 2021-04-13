package com.setu.splitwise.exchange.response;

/**
 * Created by anketjain on 13/04/21.
 */
public class RestResponse<T> {
    private Integer status;
    private T data;
    private String error;
}
