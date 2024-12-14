package com.mysite.sbb;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sbb")
    @ResponseBody
    public String index(){
        return "안녕하세요 sbb에 오신 것을 환영";
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/question/list";//localhost:8080으로 접속하면 localhost:8080/question/list로 주소가 바뀌면서 연결됨
    }
}
