package org.example.web.controller;

import java.util.List;
import org.example.persistence.entity.ExecutedQuery;
import org.example.service.ExecutedQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schema")
public class ExecutedSchemaController {

  private final ExecutedQueryService executedQueryService;

  public ExecutedSchemaController(ExecutedQueryService executedQueryService) {
    this.executedQueryService = executedQueryService;
  }

  @GetMapping
  public List<ExecutedQuery> index() {
    return executedQueryService.indexSchemas();
  }
}
