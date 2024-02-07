package org.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.example.persistence.entity.ExecutedQuery;
import org.example.persistence.mapper.ExecutedQueryMapper;
import org.example.persistence.mapper.MapMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SqlServiceTest {

  @InjectMocks
  private SqlService sqlService;
  @Mock
  private MapMapper mapMapper;
  @Mock
  private ExecutedQueryMapper executedQueryMapper;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Nested
  class Execute {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("SELECT文を実行できること")
      void executeSelect() throws Exception {
        // given
        String sql = "SELECT * FROM companies;";
        List<Map<String, String>> expected = List.of(
            Map.of("ID", "1", "NAME", "A株式会社", "DESCRIPTION", "A株式会社の説明", "CREATED_AT",
                "2024-01-01 00:00:00.000", "UPDATED_AT", "2024-01-01 00:00:00.000"));
        when(mapMapper.select(sql)).thenReturn(expected);
        // when
        String actual = sqlService.execute(sql);
        // then
        assertThat(objectMapper.readValue(actual, new TypeReference<List<Map<String, String>>>() {
        })).isEqualTo(expected);
      }

      @Test
      @DisplayName("INSERT文を実行できること")
      void executeInsert() throws Exception {
        // given
        String sql = "INSERT INTO companies (name, description) VALUES ('C株式会社', 'C株式会社の説明');";
        doNothing().when(mapMapper).insert(anyString());
        // when
        String actual = sqlService.execute(sql);
        // then
        assertThat(actual).isEqualTo("データが追加されました。");
      }

      @Test
      @DisplayName("UPDATE文を実行できること")
      void executeUpdate() throws Exception {
        // given
        String sql = "UPDATE companies SET name = 'D株式会社' WHERE id = 1;";
        doNothing().when(mapMapper).update(anyString());
        // when
        String actual = sqlService.execute(sql);
        // then
        assertThat(actual).isEqualTo("データが更新されました。");
      }

      @Test
      @DisplayName("DELETE文を実行できること")
      void executeDelete() throws Exception {
        // given
        String sql = "DELETE FROM companies WHERE id = 1;";
        doNothing().when(mapMapper).delete(anyString());
        // when
        String actual = sqlService.execute(sql);
        // then
        assertThat(actual).isEqualTo("データが削除されました。");
      }

      @Test
      @DisplayName("CREATE TABLE文を実行できること")
      void executeCreateTable() throws Exception {
        // given
        String sql = "CREATE TABLE companies (id INT, name VARCHAR(255));";
        doNothing().when(mapMapper).createTable(anyString());
        doNothing().when(executedQueryMapper).insert(any(ExecutedQuery.class));
        // when
        String actual = sqlService.execute(sql);
        // then
        assertThat(actual).isEqualTo("テーブルが作成されました。");
      }
    }
  }
}