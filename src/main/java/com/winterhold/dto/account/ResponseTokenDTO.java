package com.winterhold.dto.account;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ResponseTokenDTO {

    @Getter @Setter private String token;
    @Getter @Setter private String username;

}
