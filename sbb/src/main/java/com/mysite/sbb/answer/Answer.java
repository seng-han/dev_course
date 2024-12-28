package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @ManyToOne //answer엔티티의 question 속성과 question엔티티와 연결됨
    private Question question;

    @ManyToOne//jpa는 manytoone을 적용하면 자동으로 디비에 ~_id 로 생성됨
    private SiteUser author;

    @ManyToMany //manytomany는 db에 새로운 테이블을 만들어서 관리함
    Set<SiteUser> voter;
}
