package com.ll.rest.domain.member.member.entity;

import com.ll.rest.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 생성
public class Member extends BaseTime {
    @Column(unique = true, length = 30)
    private String username;

    @Column(length = 50)
    private String password;

    @Column(length = 30)
    private String nickname;

    public String getName(){
        return this.nickname;
    }
}
