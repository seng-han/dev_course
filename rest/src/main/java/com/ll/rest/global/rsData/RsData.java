package com.ll.rest.global.rsData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;


@JsonInclude(JsonInclude.Include.NON_NULL)//json화 할 때 null이 포함되지 않게함
@AllArgsConstructor
@Getter
public class RsData {
    private String resultCode;
    private String msg;
    private Object data; //object로 변경

    public RsData(String resultCode, String msg){
        this(resultCode, msg, null);
    }
}
//RsData 인자 두개받는 생성자, 세개받는 생성자 둘 다 만듦