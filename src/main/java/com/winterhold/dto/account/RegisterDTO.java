package com.winterhold.dto.account;

import com.winterhold.validation.Compare;
import com.winterhold.validation.UniqueUsername;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Compare(message = "confirm password doesn't match.",firstField = "password",secondField = "passwordConfirmation")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RegisterDTO {

    @UniqueUsername(message = "username does exist.")
    @Size(max = 20,message = "max character 20.")
    @NotBlank(message = "username harus diisi.")
    @Getter
    @Setter
    private String username;//unique username

    @Size(max = 20,message = "max character 20.")
    @NotBlank(message = "password harus diisi.")
    @Getter @Setter private String password;//compare dengan password confirmation

    @Size(max = 20,message = "max character 20.")
    @NotBlank(message = "password confirmation harus diisi.")
    @Getter @Setter private String passwordConfirmation;
}
