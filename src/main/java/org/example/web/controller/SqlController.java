package org.example.web.controller;

import java.io.IOException;
import java.util.List;
import org.example.persistence.entity.ExecutedQuery;
import org.example.service.ExecutedQueryService;
import org.example.service.GPTService;
import org.example.service.PromptService;
import org.example.service.SqlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sql")
public class SqlController {

  private final SqlService sqlService;
  private final ExecutedQueryService executedQueryService;
  private final PromptService promptService;
  private final GPTService gptService;

  public SqlController(SqlService sqlService, ExecutedQueryService executedQueryService,
      PromptService promptService, GPTService gptService) {
    this.sqlService = sqlService;
    this.executedQueryService = executedQueryService;
    this.promptService = promptService;
    this.gptService = gptService;
  }

  @PostMapping
  public String execute(@RequestBody String requestText) throws IOException {
    List<ExecutedQuery> executedQueries = executedQueryService.indexSchemas();
    String prompt = promptService.prompt(executedQueries, requestText);
    String sql = gptService.generateResponse(prompt);
    return sqlService.execute(sql);
  }
}
