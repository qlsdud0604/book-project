package com.example.book.web;

import com.example.book.domain.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void save_테스트() throws Exception {
        log.info("=========================save_테스트() 시작=========================");

        // given (테스트를 하기 위한 준비)
        Book book = new Book(null, "스프링 따라하기", "임빈영");
        String content = new ObjectMapper().writeValueAsString(book);   // Object를 Json으로 바꾸는 함수
        //when(bookService.registerBook(book)).thenReturn(new Book(1L, "스프링 따라하기", "임빈영"));  // 임의로 함수의 결과를 지정

        // when (테스트 실행)
        ResultActions resultActions = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then (검증)
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("스프링 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }
}

// 통합테스트 (모든 Bean들을 메모리에 올리고 테스트)
// WebEnvironment.MOCK는 실제 톰캣을 올리는게 아니라, 다른 톰캣으로 테스트
// WebEnvironment.RANDOM_POR는 실제 톰캣으로 테스트
// "@AutoConfigureMockMvc"는 MockMvc를 IoC에 등록해줌
// "@Transactional"은 각 테스트 함수가 종료될 때마다 트랜잭션을 rollback 해줌

