package org.fsdcic.xjh.mongo.entity;

import java.io.Serializable;

/**
 * Created by ben on 2017-9-19.
 */
public class ServiceResult<T> implements Serializable {

    public static final int SUCCESS = 0;
    public static final int FAILURE = -1;
    public static final int EXCEPTION = 10000;

    private int code;
    private String message = "";
    private T data;

    public ServiceResult(int code, String message) {
        this(code, message, null);
    }

    public ServiceResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public boolean isSucc() {
        return this.code == SUCCESS;
    }
}
