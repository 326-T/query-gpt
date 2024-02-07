package org.example.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.time.LocalDateTime;
import java.util.List;
import org.example.persistence.entity.ExecutedQuery;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class ExecutedQueryMapperTest {

  @Autowired
  private ExecutedQueryMapper executedQueryMapper;

  @Order(1)
  @Nested
  class Index {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("実行済みクエリが取得できること")
      void index() {
        // when
        List<ExecutedQuery> actual = executedQueryMapper.findAll();
        // then
        assertThat(actual).extracting(ExecutedQuery::getQuery, ExecutedQuery::getIsSchema,
                ExecutedQuery::getExecutedAt)
            .containsExactly(
                tuple("SELECT * FROM companies;", false, LocalDateTime.of(2020, 1, 1, 0, 0, 0)),
                tuple("SELECT * FROM products;", false, LocalDateTime.of(2020, 1, 2, 0, 0, 0)),
                tuple(
                    "CREATE TABLE companies (id INT PRIMARY KEY, name VARCHAR(255), description TEXT, created_at TIMESTAMP, updated_at TIMESTAMP);",
                    true, LocalDateTime.of(2020, 1, 3, 0, 0, 0)));
      }
    }
  }

  @Order(1)
  @Nested
  class IndexSchemas {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("実行済みスキーマクエリが取得できること")
      void indexSchemas() {
        // when
        List<ExecutedQuery> actual = executedQueryMapper.findAllSchemas();
        // then
        assertThat(actual).extracting(ExecutedQuery::getQuery, ExecutedQuery::getIsSchema,
                ExecutedQuery::getExecutedAt)
            .containsExactly(
                tuple(
                    "CREATE TABLE companies (id INT PRIMARY KEY, name VARCHAR(255), description TEXT, created_at TIMESTAMP, updated_at TIMESTAMP);",
                    true, LocalDateTime.of(2020, 1, 3, 0, 0, 0)));
      }
    }
  }

  @Order(2)
  @Nested
  class Insert {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("実行済みクエリが登録できること")
      void insert() {
        // when
        executedQueryMapper.insert(ExecutedQuery.builder()
            .query("SELECT * FROM users;")
            .isSchema(false)
            .executedAt(LocalDateTime.of(2020, 1, 4, 0, 0, 0))
            .build());
        // then
        List<ExecutedQuery> actual = executedQueryMapper.findAll();
        assertThat(actual).extracting(ExecutedQuery::getQuery, ExecutedQuery::getIsSchema,
                ExecutedQuery::getExecutedAt)
            .containsExactly(
                tuple("SELECT * FROM companies;", false, LocalDateTime.of(2020, 1, 1, 0, 0, 0)),
                tuple("SELECT * FROM products;", false, LocalDateTime.of(2020, 1, 2, 0, 0, 0)),
                tuple(
                    "CREATE TABLE companies (id INT PRIMARY KEY, name VARCHAR(255), description TEXT, created_at TIMESTAMP, updated_at TIMESTAMP);",
                    true, LocalDateTime.of(2020, 1, 3, 0, 0, 0)),
                tuple("SELECT * FROM users;", false, LocalDateTime.of(2020, 1, 4, 0, 0, 0)));
      }
    }
  }
}