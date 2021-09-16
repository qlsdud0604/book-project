package com.example.book.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
public class BookRepositoryUnitTest {

    @Autowired
    private BookRepository bookRepository;

}

// 단위테스트 (DB 관련된 로직만 메모리에 올리고 테스트)
// replace= AutoConfigureTestDatabase.Replace.ANY는 가상의 DB로 테스트
// replace= AutoConfigureTestDatabase.Replace.NONE은 실제 DB로 테스트
// "@DataJpaTest"는 Repository들을 IoC에 등록시킴