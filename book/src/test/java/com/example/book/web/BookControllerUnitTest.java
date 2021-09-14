package com.example.book.web;

import com.example.book.domain.Book;
import com.example.book.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void findAll_테스트() throws Exception {
        log.info("=========================findAll_테스트() 시작=========================");

        // given (테스트를 하기 위한 준비)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링부트 따라하기", "임빈영"));
        books.add(new Book(2L, "리액트 따라하기", "임빈석"));
        when(bookService.getBookList()).thenReturn(books);

        // when (테스트 실행)
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_테스트() throws Exception {
        log.info("=========================findById_테스트() 시작=========================");

        // given (테스트를 하기 위한 준비)
        Long id = 1L;
        when(bookService.getBookDetail(id)).thenReturn(new Book(1L, "자바 공부하기", "이순신"));

        // when (테스트 실행)
        ResultActions resultActions = mockMvc.perform(get("/book/{id}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("자바 공부하기"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void update_테스트() throws Exception {
        log.info("=========================update_테스트() 시작=========================");

        // given (테스트를 하기 위한 준비)
        Long id = 1L;
        Book book = new Book(null, "C++ 따라하기", "임빈영");
        String content = new ObjectMapper().writeValueAsString(book);   // Object를 Json으로 바꾸는 함수
        when(bookService.updateBook(id, book)).thenReturn(new Book(1L, "C++ 따라하기", "임빈영"));  // 임의로 함수의 결과를 지정

        // when
        ResultActions resultActions = mockMvc.perform(put("/book/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("C++ 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delete_테스트() throws Exception {
        log.info("=========================update_테스트() 시작=========================");

        // given (테스트를 하기 위한 준비)
        Long id = 1L;
        when(bookService.deleteBook(id)).thenReturn("삭제되었습니다.");  // 임의로 함수의 결과를 지정

        // when
        ResultActions resultActions = mockMvc.perform(delete("/book/{id}", id)
                .accept(MediaType.TEXT_PLAIN));

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MvcResult requestResult = resultActions.andReturn();
        String result = requestResult.getResponse().getContentAsString();

        assertEquals("삭제되었습니다.", result);
    }
}

// 단위테스트 (Controller 관련 로직만 띄우고 테스트)
// "@WebMvcTest"는 Controller, Filter, ControllerAdvice를 메모리에 등록시켜줌
// "@MockBean"은 해당 객체를 가짜로 IoC환경에 bean으로 등록함