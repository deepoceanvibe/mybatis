package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")      // 만약 클래스명과 테이블명이 '다르게' 매칭되기를 원하면 사용하는 어노테이션,
                            // USER는 MySQL의 예약어이기 때문에 user로 테이블명 하겠다.
@Getter @Setter @NoArgsConstructor

// UserDetails의 구현체만 스프링시큐리티 적용가능ㅇㅇ
public class User implements UserDetails {

    // 필드 멤버변수
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)     // 변경 불가 옵션
    private Long id;     // 대문자 L로 주는 이유 : null 체크 대비


    // PK는 아니지만 유일키로 사용 (ex. 번호, 메일, 아이디)
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String loginId;

    // 비밀번호는 null 허용, 카카오같은 소셜로그인(OAuth2.0)은 비번입력이 없기 때문
    private String password;

    // 생성자에는 인증정보(auth)를 요구함
    @Builder
    public User(String email, String password, String loginId, String auth) {
        this.email = email;
        this.password = password;
        this.loginId = loginId;
    }


    // [운영자, 정회원, 준회원] 권한을 다 줘야되기 때문에 List로 저장함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    // 로그인 중이냐 ? Yes -> 로그인 유지
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 밴 안당했는지 ? Yes -> 밴안당함
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
