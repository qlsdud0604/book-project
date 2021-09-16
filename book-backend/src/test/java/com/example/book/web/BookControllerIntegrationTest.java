package com.example.book.web;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

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

    @Test
    public void findAll_테스트() throws Exception {
        log.info("=========================findAll_테스트() 시작=========================");

        // given (테스트를 하기 위한 준비)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링부트 따라하기", "임빈영"));
        books.add(new Book(2L, "리액트 따라하기", "임빈석"));
        books.add(new Book(3L, "리액트 따라하기", "홍길동"));
        bookRepository.saveAll(books);

        // when (테스트 실행)
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[0].title").value("스프링부트 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_테스트() throws Exception {
        log.info("=========================findById_테스트() 시작=========================");

        // given (테스트를 하기 위한 준비)
        Long id = 1L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링부트 따라하기", "임빈영"));
        books.add(new Book(2L, "리액트 따라하기", "임빈석"));
        books.add(new Book(3L, "리액트 따라하기", "홍길동"));
        bookRepository.saveAll(books);

        // when (테스트 실행)
        ResultActions resultActions = mockMvc.perform(get("/book/{id}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then (검증)
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("스프링부트 따라하기"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void update_테스트() throws Exception {
        log.info("=========================update_테스트() 시작=========================");

        // given (테스트를 하기 위한 준비)
        Long id = 1L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링부트 따라하기", "임빈영"));
        books.add(new Book(2L, "리액트 따라하기", "임빈석"));
        books.add(new Book(3L, "리액트 따라하기", "홍길동"));
        bookRepository.saveAll(books);

        Book book = new Book(null, "C++ 따라하기", "임빈영");
        String content = new ObjectMapper().writeValueAsString(book);   // Object를 Json으로 바꾸는 함수

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

        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "스프링부트 따라하기", "임빈영"));
        books.add(new Book(2L, "리액트 따라하기", "임빈석"));
        books.add(new Book(3L, "리액트 따라하기", "홍길동"));
        bookRepository.saveAll(books);

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

// 통합테스트 (모든 Bean들을 메모리에 올리고 테스트)
// WebEnvironment.MOCK는 실제 톰캣을 올리는게 아니라, 다른 톰캣으로 테스트
// WebEnvironment.RANDOM_POR는 실제 톰캣으로 테스트
// "@AutoConfigureMockMvc"는 MockMvc를 IoC에 등록해줌
// "@Transactional"은 각 테스트 함수가 종료될 때마다 트랜잭션을 rollback 해줌

