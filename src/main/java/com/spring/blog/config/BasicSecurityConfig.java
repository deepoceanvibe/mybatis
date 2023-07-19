package com.spring.blog.config;

        import com.spring.blog.service.UserService;
        import jakarta.servlet.DispatcherType;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.authentication.AuthenticationManager;
        import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.web.SecurityFilterChain;

// 베이직 인증 방식 설정
@Configuration
public class BasicSecurityConfig {

    private final UserDetailsService userService;

    @Autowired
    public BasicSecurityConfig(UserDetailsService userService) {
        this.userService = userService;
    }


    // 정적 파일이나 .jsp 파일 등 스프링 시큐리티가 기본적으로 적용되지 않을 영역 설정하기
    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers("/static/**", "")
                .dispatcherTypeMatchers(DispatcherType.FORWARD);
    }


    // http 요청에 대한 웹 보안 구성 (로그인 외부로직)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests (authorizationConfig -> {
                    authorizationConfig
                            .requestMatchers("/login", "/signup", "/user")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .formLogin (formLoginConfig -> {
                    formLoginConfig
                            .loginPage("/login")
                            .defaultSuccessUrl("/blog/list");
                })
                .logout (logoutConfig -> {
                    logoutConfig
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true);
                })
                .csrf (csrfConfig -> {
                    csrfConfig
                            .disable();
                })
                .build();

//                .authorizeRequests()    // 인증, 인가 설정
//                .requestMatchers("/login", "/signup", "/user")
//                .permitAll()    // 위 경로들은 인증없이 접속허가
//                .anyRequest()   // 위에 적힌 경로 말고는
//                .authenticated()    // 로그인 필수임.
//                .and()
//                .formLogin()    // 로그인 설정
//                .loginPage("/login")    // 로그인 페이지로 지정할 주소
//                .defaultSuccessUrl("/blog/list")    // 로그인 하면 처음으로 보여질 페이지
//                .and()
//                .logout()   // 로그아웃 설정
//                .logoutSuccessUrl("/login")     // 로그아웃 성공하면 넘어갈 경로
//                .invalidateHttpSession(true)    // 로그아웃 후 접속시 로그인 풀려있게 함
//                .and()
//                .csrf()     // csrf 공격 방지용 토큰
//                .disable()  // 을 쓰지 않겠음.
//                .build();
    }


    // 위의 설정을 따라가는 인증은 어떤 서비스 클래스를 통해서 설정할 것인가? (로그인 내부로직)
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserService userService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)        // userService에 기술된 내용을 토대로 로그인 처리
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }


    // 암호화 모듈 임포트
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
