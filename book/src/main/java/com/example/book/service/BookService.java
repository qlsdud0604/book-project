package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book registerBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Book getBookDetail(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요."));
    }

    public List<Book> getBookList() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book updateBook(Long id, Book book) {

        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id를 확인해주세요."));

        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());

        return bookEntity;   // 함수 종료 -> 트랜잭션 종료 -> 영속화 되어있는 데이터를 DB로 갱신(flush) -> commit ===> 이러한 방식을 더티체킹이라 함
    }

    @Transactional
    public String deleteBook(Long id) {
        bookRepository.deleteById(id);
        return "삭제되었습니다.";
    }
}
