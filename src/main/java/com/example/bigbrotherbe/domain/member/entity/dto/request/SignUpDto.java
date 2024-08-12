package com.example.bigbrotherbe.domain.member.entity.dto.request;

import com.example.bigbrotherbe.domain.member.entity.Member;


import com.example.bigbrotherbe.global.exception.BusinessException;
import com.example.bigbrotherbe.global.exception.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "MEMBER_REQ_01 : 회원가입 요청 DTO")
public class SignUpDto {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @NotBlank
    @Size(min = 3,max = 10)
    @jakarta.validation.constraints.Pattern(
        regexp = "^[a-zA-Z가-힣]+$",
        message = "이름에는 영어, 한글만 올 수 있습니다."
    )
    @Schema(description = "사용자 이름", example = "테스터훈")
    private String username;

    @NotNull
    @jakarta.validation.constraints.Pattern(
        regexp = "^(?=.*[a-z])(?=.*[0-9])(?=.*\\W).{8,20}$",
        message = "비밀번호는 소문자와 숫자, 특수문자가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다."
    )
    @Schema(description = "비밀번호", example = "test1234!")
    private String password;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "사용자 이메일을 입력해주세요.")
    @Schema(description = "사용자 이메일", example = "testtest@mju.ac.kr")
    private String email;
    private String is_active;
    private String createAt;
    private String updateAt;
    private String role;
    private String affiliation;

    public Member toEntity(SignUpDto signUpDto, String password) {
        if(!isVaildEmail(signUpDto.getEmail())){
            throw new BusinessException(ErrorCode.INVALID_EMAIL_FORMAT);
        }
        return Member.builder()
            .username(signUpDto.getUsername())
            .password(password)
            .email(signUpDto.getEmail())
            .is_active("true")
            .createAt(LocalDateTime.now())
            .updateAt(LocalDateTime.now())
            .build();
    }

    private boolean isVaildEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
