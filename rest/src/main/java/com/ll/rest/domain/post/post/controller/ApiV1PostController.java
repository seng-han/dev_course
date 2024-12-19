package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.post.post.dto.PostDto;
import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import com.ll.rest.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    public RsData deleteItem(
            @PathVariable long id
    ) {
        Post post = postService.findById(id).get();

        postService.delete(post);

        return new RsData(
                "200-1",
                "%d번 글을 삭제하였습니다".formatted(id)
        );
    }


    record PostModifyReqBody( //record는 그냥 class임 단지 코드 길이 줄임
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
    public RsData modifyItem(
            @PathVariable long id,
            @RequestBody @Valid PostModifyReqBody reqBody //requestBody는 수정과 생성해만 있음 다른데에서는 필요 없으니깐 없음
    ) {
        Post post = postService.findById(id).get();

        postService.modify(post, reqBody.title, reqBody.content);

        return new RsData(
                "200-1",
                "%d번 글이 수정되었습니다".formatted(id)
        );
    }

    record PostWriteReqBody(
            @NotBlank
            @Length(min = 2)
            String title,
            @NotBlank
            @Length(min = 2)
            String content
    ) {
    }

    @PostMapping
    public RsData writeItem(
            @RequestBody @Valid PostWriteReqBody reqBody //@Valid를 여기에 써서 검증 대상임을 명시해줌
    ) {
        Post post = postService.write(reqBody.title(), reqBody.content());

        return new RsData(
                "200-1",
                "%d번 글이 등록되었습니다".formatted(post.getId())
        );
    }
}




