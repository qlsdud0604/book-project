package com.example.book.web;

import com.example.book.domain.Book;
import com.example.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void save_테스트() throws Exception {
        log.info("=========================save_테스트() 시작=========================");

        // given (테스트를 하기 위한 준비)
        Book book = new Book(null, "스프링 따라하기", "임빈영");
        String content = new ObjectMapper().writeValueAsString(book);   // Object를 Json으로 바꾸는 함수
        when(bookService.registerBook(book)).thenReturn(new Book(1L, "스프링 따라하기", "임빈영"));  // 임의로 함수의 결과를 지정

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

// 단위테스트 (Controller 관련 로직만 띄우고 테스트)
// "@WebMvcTest"는 Controller, Filter, ControllerAdvice를 메모리에 등록시켜줌
// "@MockBean"은 해당 객체를 가짜로 IoC환경에 bean으로 등록함