package com.spring.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.repository.ReplyRepository;
import jdk.jshell.spi.ExecutionControlProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 컨트롤러 테스트는 '특정 주소'에 접속해서 메서드가 실행되는지 검증하기 때문에
// 컨트롤러 객체를 이용하는게 아니라, 브라우저 객체를 주입한다.
// 서버를 안켜고 하기 때문에 빠르고 좋다
@SpringBootTest
@AutoConfigureMockMvc   // 브라우저를 대체할 '가짜브라우저' 객체를 만들어 수행
class ReplyControllerTest {

    // 브라우저 가짜객체를 컨테이너에 생성 후 주입
    @Autowired
    private MockMvc mockMvc;
    // 웹 환경에서 스프링 빈들을 관리할 수 있는 거
    @Autowired
    private WebApplicationContext context;
    // 직렬화에 필요한 ObjectMapper
    @Autowired
    private ObjectMapper objectMapper;
    // (객체지향적으로 안좋음) 하나의 쿼리문을 호출하는게 좋기 때문에 service 대신 repository를 불러오는 게 좋다.
    @Autowired
    private ReplyRepository replyRepository;


    // 각 테스트 전에 설정하기 (생성된 객체 내부에 세팅하기)
    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    @DisplayName("2번 글의 전체댓글을 조회했을 때, 0번째 요소의 replyWriter는 댓글작성자, replyId는 1")
    public void findAllRepliesTest() throws Exception {    // mockMvc의 예외를 던져줄 Exception
        // given : fixture 세팅, 접속주소 세팅
        String replyWriter = "댓글작성자";
        long replyId = 1;

        String url = "/reply/2/all";            // 도메인주소 이후로 적으면 됨, 모르면 http부터 다 적자

        // when : 위의 url로 접속 후 **json 데이터 리턴받아**(fetch.then) ResultActions형 자료로 저장하기 & 예외처리

                                    // fetch(url, {method:`get`}).then(res => res.json()); 과 같음
        final ResultActions result = mockMvc.perform(get(url)
                                            .accept(MediaType.APPLICATION_JSON));

        // then
        // 200코드가 맞는가
        // 받아온 json의 replyWriter와 fixture의 replyWriter가 일치하는가
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter))
                .andExpect(jsonPath("$[0].replyId").value(replyId));
    }
    @Test
    @Transactional
    @DisplayName("replyId 2번 조회시 json의 replyWriter는 개소리하면짖는멍멍이, replyId는 2번")
    public void findByReplyIdTest() throws Exception{
        // given
        String replyWriter = "개소리하면짖는멍멍이";
        long replyId = 2;

        String url = "/reply/2";

        // when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.replyWriter").value(replyWriter))
                .andExpect(jsonPath("$.replyId").value(replyId));
    }
    @Test
    @Transactional
    @DisplayName("blogId 1번에 replyWriter는 냐옹, replyContent는 난냐옹이다냐옹 이다.")
    public void insertReplyTest() throws Exception{
        // given
        long blogId = 1;
        String replyWriter = "냐옹";
        String replyContent = "난냐옹이다냐옹";

        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        String url = "/reply";
        String url2 = "/reply/1/all";

        // 자바 객체 -> json 으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(replyInsertDTO);

        // when : 직렬화된 json 데이터를 이용해 post 방식으로 url에 요청
        mockMvc.perform(post(url)   // /reply주소에 post방식으로 요청을 넣고
                        .contentType(MediaType.APPLICATION_JSON)    // 전달 자료는 json이며
                        .content(requestBody)); // 위에서 직렬화한 requestBody 변수를 전달


        // then
        final ResultActions result = mockMvc.perform(get(url2)
                                    .accept(MediaType.APPLICATION_JSON));

        // url2에서 받아온 json데이터와, fixture->json직렬화한 데이터(requestBody)랑 일치하는지 비교?
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter))
                .andExpect(jsonPath("$[0].replyContent").value(replyContent));

    }

    @Test
    @Transactional
    @DisplayName("댓글번호 3번을 삭제할 경우, 글번호 2번의 댓글수는 3개, 해당댓글은 null이다.")
    public void deleteReplyTest() throws Exception {
        // given
        long replyId = 3;
        long blogId = 2;
        String url = "http://localhost:8080/reply/3";

        // when
        mockMvc.perform(delete(url).accept(MediaType.TEXT_PLAIN));

        // then
        List<ReplyFindByIdDTO> allReplies = replyRepository.findAllByBlogId(blogId);
        assertEquals(3, allReplies.size());
        assertNull(replyRepository.findByReplyId(replyId));
    }
}