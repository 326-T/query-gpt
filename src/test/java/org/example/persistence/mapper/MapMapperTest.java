package org.example.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
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
class MapMapperTest {

  @Autowired
  private MapMapper mapMapper;

  @Order(1)
  @Nested
  class Select {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("企業が1件取得できること")
      void selectOneCompany() {
        // when
        List<Map<String, String>> actual = mapMapper.select("SELECT * FROM companies WHERE id = 1;");
        System.out.println(actual);
        // then
        assertThat(actual).extracting("ID", "NAME", "DESCRIPTION", "CREATED_AT", "UPDATED_AT")
            .containsExactly(tuple(1, "A株式会社", "A株式会社の説明",
                Timestamp.valueOf("2024-01-01 00:00:00.000"),
                Timestamp.valueOf("2024-01-01 00:00:00.000")));
      }

      @Test
      @DisplayName("企業が複数取得できること")
      void selectAllCompanies() {
        // when
        List<Map<String, String>> actual = mapMapper.select("SELECT * FROM companies;");
        System.out.println(actual);
        // then
        assertThat(actual).extracting("ID", "NAME", "DESCRIPTION", "CREATED_AT", "UPDATED_AT")
            .containsExactly(
                tuple(1, "A株式会社", "A株式会社の説明",
                    Timestamp.valueOf("2024-01-01 00:00:00.000"),
                    Timestamp.valueOf("2024-01-01 00:00:00.000")),
                tuple(2, "B株式会社", "B株式会社の説明",
                    Timestamp.valueOf("2024-01-02 00:00:00.000"),
                    Timestamp.valueOf("2024-01-02 00:00:00.000")),
                tuple(3, "C株式会社", "C株式会社の説明",
                    Timestamp.valueOf("2024-01-03 00:00:00.000"),
                    Timestamp.valueOf("2024-01-03 00:00:00.000")));
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
      @DisplayName("企業が1件登録できること")
      void insertOneCompany() {
        // when
        mapMapper.insert("""
            INSERT INTO companies (id, name, description, created_at, updated_at)
            VALUES (4, 'D株式会社', 'D株式会社の説明', '2024-01-04 00:00:00.000', '2024-01-04 00:00:00.000');
            """);
        // then
        List<Map<String, String>> actual = mapMapper.select("SELECT * FROM companies WHERE id = 4");
        assertThat(actual).extracting("ID", "NAME", "DESCRIPTION", "CREATED_AT", "UPDATED_AT")
            .containsExactly(tuple(4, "D株式会社", "D株式会社の説明",
                Timestamp.valueOf("2024-01-04 00:00:00.000"),
                Timestamp.valueOf("2024-01-04 00:00:00.000")));
      }
    }
  }

  @Order(3)
  @Nested
  class Update {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("企業が1件更新できること")
      void updateOneCompany() {
        // when
        mapMapper.update("""
            UPDATE companies
            SET name = 'E株式会社', description = 'E株式会社の説明',
                created_at = '2024-01-04 00:00:00.000',
                updated_at = '2024-01-05 00:00:00.000'
            WHERE id = 1;
            """);
        // then
        List<Map<String, String>> actual = mapMapper.select("SELECT * FROM companies WHERE id = 1");
        assertThat(actual).extracting("ID", "NAME", "DESCRIPTION", "CREATED_AT", "UPDATED_AT")
            .containsExactly(tuple(1, "E株式会社", "E株式会社の説明",
                Timestamp.valueOf("2024-01-04 00:00:00.000"),
                Timestamp.valueOf("2024-01-05 00:00:00.000")));
      }
    }
  }

  @Order(4)
  @Nested
  class Delete {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("企業が1件削除できること")
      void deleteOneCompany() {
        // when
        mapMapper.delete("DELETE FROM companies WHERE id = 2;");
        // then
        List<Map<String, String>> actual = mapMapper.select("SELECT * FROM companies WHERE id = 2");
        assertThat(actual).isEmpty();
      }
    }
  }

  @Nested
  class CreateTable {

    @Test
    @DisplayName("テーブルが作成できること")
    void createTable() {
      // when
      mapMapper.createTable("""
          CREATE TABLE IF NOT EXISTS test_table (
            id INT PRIMARY KEY,
            name VARCHAR(255) NOT NULL
          );
          """);
      // then
      List<Map<String, String>> actual = mapMapper.select("SELECT * FROM test_table;");
      assertThat(actual).isNotNull();
    }
  }
}