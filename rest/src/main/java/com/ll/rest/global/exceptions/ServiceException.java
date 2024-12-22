package com.ll.rest.global.exceptions;
import com.ll.rest.global.rsData.RsData;
public class ServiceException extends RuntimeException {
    private final String resultCode;
    private final String msg;
    public ServiceException(String resultCode, String msg) {//런타임예외에 생성자 두개니까 하나 지정해줘
        super(resultCode + " : " + msg);
        this.resultCode = resultCode;
        this.msg = msg;
    }
    public RsData<Void> getRsData() {
        return new RsData<>(resultCode, msg);
    }
}
