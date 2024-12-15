package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList(){
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        //Optional이란? null 방지를 위한 래퍼 클래스,값이 있을 수도 있고 없을 수도 있는 상황을 안전하게 처리
        if(question.isPresent()){
            return question.get();
        }else{
            throw new DataNotFoundException("question not found");
        }
    }
    public void create(String subject, String content){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
    public Page<Question> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));//Sort.Order.desc("createDate")로 작성일시를 desc로 조회
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));//page는 조회 할 페이지, 10은 한 페이지에 보여 줄 게시판의 개수
        return this.questionRepository.findAll(pageable);
    }
}
