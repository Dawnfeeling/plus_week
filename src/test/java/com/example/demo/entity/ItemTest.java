package com.example.demo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  //Entity Test
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  //TestDB를 H2 DB가 아닌 MySQL사용(자동 교체 방지)
@TestPropertySource(locations = "classpath:application-test.yml") //Test 환경설정 파일 명시
class ItemTest {

    @Autowired
    private TestEntityManager testentityManager;

    @Test
    @DisplayName("status가 null일때 default값이 들어가는지 확인")
    void testItemDefault(){
        //Given
        Item item = new Item("Test Name", "Test Description", null, null);
        testentityManager.persist(item);
        testentityManager.flush();
        testentityManager.clear();

        //When
        Item savedItem = testentityManager.find(Item.class, item.getId());

        //Then
        assertNotNull(savedItem.getStatus(), "null이 아니어야 한다.");
        assertEquals("PENDING", savedItem.getStatus(), "기본값이 PENDING이어야 한다.");
    }

}