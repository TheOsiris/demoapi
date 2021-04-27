package com.backend.demoapi.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;
    private String message;
    private Object data;

    public static Result success(int code, String message, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result success(Object data) {
        return success(200, "操作成功", data);
    }

    public static Result failed(int code, String message, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result failed(String message, Object data) {
        return failed(400, message, data);
    }

    public static Result failed(String message) {
        return failed(400, message, null);
    }
}
