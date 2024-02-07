package org.example.service;

import java.util.List;
import org.example.persistence.entity.ExecutedQuery;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

  private static final String PROMPT = """
      あなたはデータベースの専門家です。
      以下の目的を満たすSQLをPostgreSQLの標準文法にしたがって出力してください。
      その際に、過去のクエリを参考にしてください。
      
      **目的**
      %s
      
      **過去のクエリ**
      %s
      
      **制約**
      - 回答はできる限り簡潔にSQLのみ答えてください。
      - 1つのクエリで目的を達成してください。
      - クエリは省略してはいけません。
      - クエリはmarkdown記法でトリプルクォートで囲んでください。
      - 使用できるSQLはSELECT, INSERT, UPDATE, DELETE, CREATE TABLEのみです。
      - 列に制約を加えてはいけません。
      """;

  public String prompt(List<ExecutedQuery> queries, String requestText) {
    return PROMPT.formatted(
        requestText,
        queries.stream()
            .map(ExecutedQuery::getQuery)
            .reduce("", (a, b) -> a + "\n" + b));
  }
}
