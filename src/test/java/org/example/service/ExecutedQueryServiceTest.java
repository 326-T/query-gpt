package org.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import org.example.persistence.entity.ExecutedQuery;
import org.example.persistence.mapper.ExecutedQueryMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExecutedQueryServiceTest {

  @InjectMocks
  private ExecutedQueryService executedQueryService;
  @Mock
  private ExecutedQueryMapper executedQueryMapper;

  @Nested
  class Index {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("実行済みクエリが取得できること")
      void index() {
        // given
        when(executedQueryMapper.findAll()).thenReturn(List.of(
            ExecutedQuery.builder().query("SELECT * FROM companies;").executedAt(
                LocalDateTime.of(2020, 1, 1, 0, 0, 0)).build(),
            ExecutedQuery.builder().query("SELECT * FROM products;")
                .executedAt(LocalDateTime.of(2020, 1, 2, 0, 0, 0)).build(),
            ExecutedQuery.builder().query("SELECT * FROM orders;")
                .executedAt(LocalDateTime.of(2020, 1, 3, 0, 0, 0)).build()
        ));
        // when
        List<ExecutedQuery> actual = executedQueryService.index();
        // then
        assertThat(actual).extracting(ExecutedQuery::getQuery, ExecutedQuery::getExecutedAt)
            .containsExactly(
                tuple("SELECT * FROM companies;", LocalDateTime.of(2020, 1, 1, 0, 0, 0)),
                tuple("SELECT * FROM products;", LocalDateTime.of(2020, 1, 2, 0, 0, 0)),
                tuple("SELECT * FROM orders;", LocalDateTime.of(2020, 1, 3, 0, 0, 0)));
      }
    }
  }
}