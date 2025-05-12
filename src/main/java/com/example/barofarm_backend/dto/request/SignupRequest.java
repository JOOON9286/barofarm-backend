package com.example.barofarm_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 1, max = 20, message = "이름은 1~20자 사이여야 합니다.")
    private String name;

    @Size(min = 11, max = 11, message = "전화번호는 11자리여야 합니다.")
    @Pattern(regexp = "^010\\d{8}$", message = "010으로 시작하는 11자리 숫자만 입력 가능합니다.")
    private String phone;

}
