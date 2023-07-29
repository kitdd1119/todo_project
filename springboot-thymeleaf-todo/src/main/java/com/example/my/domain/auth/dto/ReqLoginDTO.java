package com.example.my.domain.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReqLoginDTO {

    @Valid
    @NotNull
    private User user;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class User {

        @NotBlank(message = "아이디는 공백일수없습니다.") //구글이나 gpt한테 스프링부트 validation 검색 후 나오는 거 사용
        @Size(min = 4, message = "아이디는 4자이상 입력해주세요.")
        private String id;

        @NotBlank(message = "비밀번호가 비워져있습니다.")
        private String password;
    }

}
