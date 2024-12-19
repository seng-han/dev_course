package com.ll.rest.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
    private String resultCode;
    private String msg;
    private long data; //생성할 때만 data가 보이게 해야함

    public RsData(String resultCode, String msg){
        //지금 rsdata에 세개인데 두개만 받는 것들을 위해서 생성자를 따로 만들어줌
        this.resultCode = resultCode;
        this.msg = msg;
    }
}
//RsData 인자 두개받는 생성자, 세개받는 생성자 둘 다 만듦