package com.mysite.sbb.question;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequestMapping("/question")//URL의 프리픽스가 모두 /question으로 시작하니까 그냥 위로 빼줌
@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model){
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";

    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){// 숫자 2처럼 변하는 id값을 얻을 때에는 @pathVariable 사용
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);//addAttribute는 model객체에 데이터 추가
        return"question_detail";
    }
}
