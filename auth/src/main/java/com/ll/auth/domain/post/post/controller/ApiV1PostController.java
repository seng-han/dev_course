package com.ll.auth.domain.post.post.controller;

import com.ll.auth.domain.member.member.entity.Member;
import com.ll.auth.domain.member.member.service.MemberService;
import com.ll.auth.domain.post.post.dto.PostDto;
import com.ll.auth.domain.post.post.entity.Post;
import com.ll.auth.domain.post.post.service.PostService;
import com.ll.auth.global.exceptions.ServiceException;
import com.ll.auth.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;
    private final MemberService memberService;

    @GetMapping
    public List<PostDto> getItems() {
        return postService
                .findAllByOrderByIdDesc()
                .stream()
                .map(PostDto::new)
                .toList();
    }


    @GetMapping("/{id}")
    public PostDto getItem(
            @PathVariable long id
    ) {
        return postService.findById(id)
                .map(PostDto::new)
                .orElseThrow();
    }


    @DeleteMapping("/{id}")
    public RsData<Void> deleteItem(
            @PathVariable long id,
            @RequestHeader("actorId") long actorId,
            @RequestHeader("actorPassword") String actorPassword //인증정보를 url헤더에 넣어서 전달함
    ) {

        Member actor = memberService.findById(actorId).get();

        if (!actor.getPassword().equals(actorPassword))
            throw new ServiceException("401-1","비밀번호가 일치하지 않습니다");

        Post post = postService.findById(id).get(); //글을 불러옴

        if (!post.getAuthor().equals(actor))//글 작성자와 지금 너.
            throw new ServiceException("401-1","작성자만 글을 삭제 할 권한이 있습니다");

        postService.delete(post);

        return new RsData<>(
                "200-1",
                "%d번 글이 삭제되었습니다.".formatted(id)
        );
    }


    record PostModifyReqBody(
            @NotBlank
            @Length(min = 2)
            String title,
            @NotBlank
            @Length(min = 2)
            String content
    ) {
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<PostDto> modifyItem(
            @PathVariable long id,
            @RequestBody @Valid PostModifyReqBody reqBody,
            @RequestHeader long actorId,
            @RequestHeader String actorPassword

    ) {
        Member actor = memberService.findById(actorId).get();

        if (!actor.getPassword().equals(actorPassword))
            throw new ServiceException("401-1","비밀번호가 일치하지 않습니다");


        Post post = postService.findById(id).get();

        if (!post.getAuthor().equals(actor))
            throw new ServiceException("403-1","작성자만 글을 수정할 권한이 있습니다");// 401:인증실패, 403:권한부여 실패

        postService.modify(post, reqBody.title, reqBody.content);

        return new RsData<>(
                "200-1",
                "%d번 글이 수정되었습니다.".formatted(id),
                new PostDto(post)
        );
    }


    record PostWriteReqBody(
            @NotBlank
            @Length(min = 2)
            String title,
            @NotBlank
            @Length(min = 2)
            String content,
            @NotNull
            Long authorId,
            @NotNull
            @Length(min=4)
            String password
    ) {
    }

    @PostMapping
    public RsData<PostDto> writeItem(
            @RequestBody @Valid PostWriteReqBody reqBody,
            @RequestHeader long actorId,
            @RequestHeader String actorPassword
    ) {
        Member actor = memberService.findById(actorId).get();

        if (!actor.getPassword().equals(actorPassword))
            throw new ServiceException("401-1","비밀번호가 일치하지 않습니다");

        Post post = postService.write(actor, reqBody.title, reqBody.content);

        return new RsData<>(
                "201-1",
                "%d번 글이 작성되었습니다.".formatted(post.getId()),
                new PostDto(post)
        );
    }
}