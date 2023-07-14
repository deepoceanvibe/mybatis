package com.spring.blog.service;

import com.spring.blog.entity.User;
import com.spring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// UserService는 "인증"만 담당하고, 나머지 회원가입 등은 이걸로 처리함
@Service
public class UsersService {

    private final UserRepository userRepository;

    // 암호화 객체 (DB에 비번을 암호화해서 넣어야 하기 때문)
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(UserRepository userRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    // 폼에서 날려준 정보를 DB에 적재하되, 비밀번호는 암호화(인코딩)을 진행한 구문 insert함
    public void save(User user) {
        User newUser = User.builder()
                .email(user.getEmail())
                .loginId(user.getLoginId())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .build();

        userRepository.save(newUser);
    }
}
