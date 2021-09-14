package com.example.book.service;

import com.example.book.domain.BookRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;
}

// 단위테스트 (Service 관련된 로직만 메모리에 올리고 테스트)
// "@InjectMocks"는 "@Mock"으로 등록된 모든 객체를 주입받음
