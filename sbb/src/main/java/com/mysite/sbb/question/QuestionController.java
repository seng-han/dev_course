package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@RequestMapping("/question")//URL의 프리픽스가 모두 /question으로 시작하니까 그냥 위로 빼줌
@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }



    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {// 숫자 2처럼 변하는 id값을 얻을 때에는 @pathVariable 사용
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);//addAttribute는 model객체에 데이터 추가
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")//로그인한 경우에만 실행됨
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify (QuestionForm questionForm, @PathVariable("id") Integer id,
                                  Principal principal){
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");}
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(questionForm.getContent());
        return "question_form";

        }
}
