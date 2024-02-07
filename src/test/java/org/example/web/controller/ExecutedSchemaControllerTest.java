package org.example.web.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import org.example.persistence.entity.ExecutedQuery;
import org.example.service.ExecutedQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebMvcTest(ExecutedSchemaController.class)
@AutoConfigureWebTestClient
class ExecutedSchemaControllerTest {


  @MockBean
  private ExecutedQueryService executedQueryService;
  @Autowired
  private WebTestClient webTestClient;

  @Nested
  class Index {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("実行済みスキーマクエリが取得できること")
      void index() {
        // given
        when(executedQueryService.indexSchemas()).thenReturn(List.of(
            ExecutedQuery.builder().query("SELECT * FROM companies;").isSchema(false)
                .executedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0)).build(),
            ExecutedQuery.builder().query("SELECT * FROM products;").isSchema(false)
                .executedAt(LocalDateTime.of(2020, 1, 2, 0, 0, 0)).build(),
            ExecutedQuery.builder().query("SELECT * FROM orders;").isSchema(false)
                .executedAt(LocalDateTime.of(2020, 1, 3, 0, 0, 0)).build()
        ));
        // when
        webTestClient.get().uri("/api/v1/schema").exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$[0].query").isEqualTo("SELECT * FROM companies;")
            .jsonPath("$[0].isSchema").isEqualTo(false)
            .jsonPath("$[0].executedAt").isEqualTo("2020-01-01T00:00:00")
            .jsonPath("$[1].query").isEqualTo("SELECT * FROM products;")
            .jsonPath("$[1].isSchema").isEqualTo(false)
            .jsonPath("$[1].executedAt").isEqualTo("2020-01-02T00:00:00")
            .jsonPath("$[2].query").isEqualTo("SELECT * FROM orders;")
            .jsonPath("$[2].isSchema").isEqualTo(false)
            .jsonPath("$[2].executedAt").isEqualTo("2020-01-03T00:00:00");
      }
    }
  }
}