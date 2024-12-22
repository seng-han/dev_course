package com.ll.rest.domain.post.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.rest.domain.member.member.dto.MemberDto;
import com.ll.rest.domain.post.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
//Dto는 외부를 상대함

@Getter
public class PostDto {
    private long id;

    @JsonProperty("createdDatetime")
    private LocalDateTime createDate;

    @JsonProperty("modifiedDate")
    private LocalDateTime modifyDate;

    private MemberDto author;//member는 노출하면 안되니까 dto를 씀

    private String title;

    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifyDate();
        this.author = new MemberDto(post.getAuthor());
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}