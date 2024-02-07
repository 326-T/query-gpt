package org.example.service;

import java.util.List;
import org.example.persistence.entity.ExecutedQuery;
import org.example.persistence.mapper.ExecutedQueryMapper;
import org.springframework.stereotype.Service;

@Service
public class ExecutedQueryService {

  private final ExecutedQueryMapper executedQueryMapper;

  public ExecutedQueryService(ExecutedQueryMapper executedQueryMapper) {
    this.executedQueryMapper = executedQueryMapper;
  }

  public List<ExecutedQuery> index() {
    return executedQueryMapper.findAll();
  }
}
