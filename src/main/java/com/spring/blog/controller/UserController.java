package com.spring.blog.controller;

import com.spring.blog.config.jwt.TokenProvider;
import com.spring.blog.dto.AccessTokenResponseDTO;
import com.spring.blog.entity.User;
import com.spring.blog.service.UsersService;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;

@Controller
public class UserController {

    private final UsersService usersService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserController(UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenProvider tokenProvider) {
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "user/login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp() {
        return "user/signup";
    }


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(User user) {
        usersService.save(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    @ResponseBody       // REST 컨트롤러 아닌데 REST 형식으로 리턴하고 싶을 때 쓰는 자료형
    public ResponseEntity<?> authenticate(User user) {

        // 폼에서 입력한 로그인 아이디를 이용해 DB에 저장된 전체 정보 얻어오기
        User userInfo = usersService.getByCredentials(user.getLoginId());

        // 먼저 비번을 암호화 구문으로 바꾼다음, form에서 날린 거 / db에 들어있던 거 맞는지 안맞는지 체크
        if (bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword())) {

            // 맞으면, 2시간 동안 유효한 액세스 토큰 생성
            String token = tokenProvider.generateToken(userInfo, Duration.ofHours(2));

            // json으로 리턴하려면 클래스 요소를 리턴해야 하므로, AccessTokenResponseDTO의 accessToken 발급 완료
            AccessTokenResponseDTO tokenDTO = new AccessTokenResponseDTO(token);
            return ResponseEntity.ok(tokenDTO);
        } else {
            return ResponseEntity.badRequest().body("login failed");
        }
    }
}