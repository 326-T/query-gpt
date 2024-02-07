package org.example.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import org.example.persistence.entity.ExecutedQuery;
import org.example.service.ExecutedQueryService;
import org.example.service.GPTService;
import org.example.service.PromptService;
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
  @MockBean
  private ExecutedQueryService executedQueryService;
  @MockBean
  private PromptService promptService;
  @MockBean
  private GPTService gptService;

  @Autowired
  private WebTestClient webTestClient;

  @Nested
  class Execute {

    @Nested
    @DisplayName("正常系")
    class Regular {

      @Test
      @DisplayName("SQLを実行できること")
      void executeSql() throws IOException {
        // given
        when(executedQueryService.indexSchemas()).thenReturn(List.of(
            ExecutedQuery.builder().query("SELECT * FROM companies;").isSchema(false).build(),
            ExecutedQuery.builder().query("SELECT * FROM products;").isSchema(false).build()
        ));
        when(promptService.prompt(anyList())).thenReturn("prompt");
        when(gptService.generateResponse(anyString())).thenReturn("SELECT * FROM companies;");
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