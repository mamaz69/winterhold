package com.winterhold.dto.account;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class RequestTokenDTO {

    //username dan password di entry user
    @Getter @Setter private String username;
    @Getter @Setter private String password;

    //subject,secretKey, dan audience di inject oleh app client-side
    @Getter @Setter private String subject;
    @Getter @Setter private String secretKey;
    @Getter @Setter private String audience;
}
