package com.spring.blog.controller;

import com.spring.blog.dto.BmiDTO;
import com.spring.blog.dto.PersonDTO;
import org.apache.coyote.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Controller
// @ResponseBody   // REST 형식으로 변환
@RestController // 위 어노테이션 2개를 한번에 처리
@RequestMapping("/resttest")
public class RestApiController {

    // REST 컨트롤러는 크게 json을 리턴하거나, String을 리턴하게 할 수 있다.
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "안녕하세요";
    }

    // 문자 배열도 리턴 가능
    @RequestMapping(value = "/foods", method = RequestMethod.GET)
    public List<String> food() {
        List<String> foodList = List.of("탕수육", "똠양꿍", "돈카츠");
        return foodList;
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public PersonDTO psrson() {
        PersonDTO p = PersonDTO.builder().id(1L).name("좋코더").age(20).build();
        return p;
    }

    // "상태코드까지"(헤더,본문 등 HTTP응답을) 함께 리턴할 수 있는 리턴자료형 ResponseEntity<>
    @GetMapping("/person-list")
    public ResponseEntity<?> personList() {
        PersonDTO p = PersonDTO.builder().id(1L).name("까까").age(10).build();
        PersonDTO p2 = PersonDTO.builder().id(2L).name("무무").age(20).build();
        PersonDTO p3 = PersonDTO.builder().id(3L).name("나나").age(30).build();
        List<PersonDTO> personList = List.of(p, p2, p3);

        // .ok는 "200코드"를 반환하고, 뒤에 연달아 붙은 body()에 실제 리턴자료를 입력
        return ResponseEntity.ok().body(personList);
    }

    // @RequestBody, @ResponseBody : HTTP 요청,응답 -> 자바,json으로 변환 : 직렬화, 역직렬화
    @RequestMapping(value = "/bmi", method = RequestMethod.GET)
    public ResponseEntity<?> bmi(BmiDTO bmi) {     // 커맨드 객체

        // 예외처리
        if(bmi.getHeight() == 0) {
            return ResponseEntity
                    .badRequest()   // 400
                    .body("키 부분에 0을 넣어 계산이 안 됩니다.");
        }

        double result = bmi.getWeight() / ((bmi.getHeight()/100) * (bmi.getHeight()/100));

        // 헤더 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("lunch", "pizza");

        return ResponseEntity
                .ok()   // 200
                .headers(headers)   // 헤더 (필수는아님)
                .body(result);  // 사용자에게 보여질 데이터
    }

    // Postman 활용한 json을 파라미터로 전송한 요청
    @RequestMapping(value = "/bmi2", method = RequestMethod.GET)
    public ResponseEntity<?> bmi2(@RequestBody BmiDTO bmi) {     // 무조건 파라미터를 json으로 받을 거다

        // 예외처리
        if(bmi.getHeight() == 0) {
            return ResponseEntity
                    .badRequest()   // 400
                    .body("키 부분에 0을 넣어 계산이 안 됩니다.");
        }

        double result = bmi.getWeight() / ((bmi.getHeight()/100) * (bmi.getHeight()/100));

        // 헤더 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("lunch", "pizza");

        return ResponseEntity
                .ok()   // 200
                .headers(headers)   // 헤더 (필수는아님)
                .body(result);  // 사용자에게 보여질 데이터
    }












}
