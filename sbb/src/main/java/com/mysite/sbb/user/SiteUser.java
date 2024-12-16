package com.mysite.sbb.user;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // username과 email의 중복을 허용하지 않음을 의미
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    }
