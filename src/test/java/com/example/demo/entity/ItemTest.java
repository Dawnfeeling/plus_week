package com.example.demo.entity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
@Rollback
class ItemTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("status가 null일때 default값이 들어가는지 확인")
    void testItemDefault(){
        //Given
        Item item = new Item("Test Name", "Test Description", null, null);
        entityManager.persist(item);
        entityManager.flush();
        entityManager.clear();

        //When
        Item savedItem = entityManager.find(Item.class, item.getId());

        //Then
        assertNotNull(savedItem.getStatus(), "null이 아니어야 한다.");
        assertEquals("PENDING", savedItem.getStatus(), "기본값이 PENDING이어야 한다.");
    }

}