package com.ll.tem.domain.post.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/posts")
public class PostController {
    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return """
                <form method="POST">
                    <input type="text" name="title" placeholder="제목">
                    <textarea name="content" placeholder="내용"></textarea>
                    <button type="submit">글쓰기</button>
                </form>
                """;
    }
    //포스트 방식으로 보내서 암호화됨, 없으면 기본적으로 GET임, action="/posts/write" 이 부분은 get과 post매핑의 주소가 같으면 생략가능

    @PostMapping("/write")//위에 /write를 통해서 포스트 방식으로 왓으니까 포스트맵핑으로 변경.
    @ResponseBody
    public String write(
            String title,
            String content
    ) {
        return """
                <h1>글쓰기 완료</h1>
                
                <div>
                    <h2>%s</h2>
                    <p>%s</p>
                </div>
                """.formatted(title, content);
    }
}