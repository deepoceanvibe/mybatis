package com.spring.blog.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component @ConfigurationProperties("jwt")      // properties 파일 중, jwt 프로퍼티에 속한 항목을 받아서 바인딩 해줌

public class JwtProperties {
    private String issuer;
    private String secretKey;
}
