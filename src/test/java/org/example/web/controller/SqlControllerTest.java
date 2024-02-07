package org.example.web.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.service.SqlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebMvcTest(SqlController.class)
@AutoConfigureWebTestClient
class SqlControllerTest {

  @MockBean
  private SqlService sqlService;

  @Autowired
  private WebTestClient webTestClient;

  @Nested
  class Execute {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("SQLを実行できること")
      void executeSql() throws JsonProcessingException {
        // given
        when(sqlService.execute(anyString())).thenReturn("test");

        // when and then
        webTestClient.post().uri("/api/v1/sql")
            .bodyValue("SELECT * FROM companies;")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class).isEqualTo("test");
      }
    }
  }
}