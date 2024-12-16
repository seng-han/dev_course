package com.mysite.sbb.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3 , max= 25)
    @NotEmpty(message="사용자 id는 필수항목입니다.")
    private String username;

    @NotEmpty(message="비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message="비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email//이메일은 형식 검증이 필요 하기 때문에 이 부분을 써줌
    private String email;

}
