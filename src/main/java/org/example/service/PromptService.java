package org.example.service;

import java.util.List;
import org.example.persistence.entity.ExecutedQuery;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

  public String prompt(List<ExecutedQuery> queries) {
    return "Enter a command: ";
  }
}
