package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.post.post.dto.PostDto;
import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import com.ll.rest.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RsData<Void>> deleteItem(
            @PathVariable long id
    ) {
        // Post를 찾아서 삭제
        Post post = postService.findById(id).orElseThrow();
        postService.delete(post);

        // 204 No Content 응답 반환
        return ResponseEntity
                .status(HttpStatus.OK)//204(성공,바디없는)에서 200(성공,바디있는)으로 변경
                .body(new RsData<>(
                        "200-1","%d번 글이 삭제 되었습니다"
                ));
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
    public RsData<PostDto> modifyItem(
            @PathVariable long id,
            @RequestBody @Valid PostModifyReqBody reqBody //requestBody는 수정과 생성해만 있음 다른데에서는 필요 없으니깐 없음
    ) {
        Post post = postService.findById(id).get();

        postService.modify(post, reqBody.title, reqBody.content);

        return new RsData<>(
                "200-1",
                "%d번 글이 수정되었습니다".formatted(id),
                new PostDto(post) //수정됐을 때 수정된 객체를 보여주기 위해 추가.dto로 감싸서
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
    record PostWriteResBody(
            PostDto item,
            long totalCount
    ){

    }

    @PostMapping
    public ResponseEntity<RsData<PostWriteResBody>> writeItem(
            @RequestBody @Valid PostWriteReqBody reqBody //@Valid를 여기에 써서 검증 대상임을 명시해줌
    ) {
        Post post = postService.write(reqBody.title, reqBody.content);

        return ResponseEntity
                .status(HttpStatus.CREATED)// 바디 + 상태코드 까지 명시적으로
                .body(new RsData<>(
                        "200-1",
                        "%d번 글이 등록되었습니다".formatted(post.getId()),
                        new PostWriteResBody(
                                new PostDto(post),
                                postService.count()
                        )
                ));
    }
}




