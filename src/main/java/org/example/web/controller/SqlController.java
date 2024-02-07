package org.example.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.service.SqlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sql")
public class SqlController {

  private final SqlService sqlService;

  public SqlController(SqlService sqlService) {
    this.sqlService = sqlService;
  }

  @PostMapping
  public String execute(@RequestBody String requestText) throws JsonProcessingException {
    return sqlService.execute(requestText);
  }
}
