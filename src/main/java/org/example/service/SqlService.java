package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.constants.QueryType;
import org.example.persistence.entity.ExecutedQuery;
import org.example.persistence.mapper.ExecutedQueryMapper;
import org.example.persistence.mapper.MapMapper;
import org.springframework.stereotype.Service;

@Service
public class SqlService {

  private final MapMapper mapMapper;
  private final ExecutedQueryMapper executedQueryMapper;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private static final String CODE_BLOCK_PATTERN = "```sql\\n(.*?)\\n```";

  public SqlService(MapMapper mapMapper, ExecutedQueryMapper executedQueryMapper) {
    this.mapMapper = mapMapper;
    this.executedQueryMapper = executedQueryMapper;
  }

  public String execute(String sql) throws JsonProcessingException {
    String trimmedSql = trimSql(sql);
    QueryType queryType = getQueryType(trimmedSql);
    return switch (queryType) {
      case SELECT -> objectMapper.writeValueAsString(mapMapper.select(trimmedSql));
      case INSERT -> {
        mapMapper.insert(trimmedSql);
        executedQueryMapper.insert(
            ExecutedQuery.builder()
                .query(trimmedSql)
                .isSchema(false).executedAt(LocalDateTime.now()).build());
        yield "データが追加されました。";
      }
      case UPDATE -> {
        mapMapper.update(trimmedSql);
        executedQueryMapper.insert(
            ExecutedQuery.builder()
                .query(trimmedSql)
                .isSchema(false).executedAt(LocalDateTime.now()).build());
        yield "データが更新されました。";
      }
      case DELETE -> {
        mapMapper.delete(trimmedSql);
        executedQueryMapper.insert(
            ExecutedQuery.builder()
                .query(trimmedSql)
                .isSchema(false).executedAt(LocalDateTime.now()).build());
        yield "データが削除されました。";
      }
      case CREATE_TABLE -> {
        mapMapper.createTable(trimmedSql);
        executedQueryMapper.insert(
            ExecutedQuery.builder()
                .query(trimmedSql)
                .isSchema(true).executedAt(LocalDateTime.now()).build());
        yield "テーブルが作成されました。";
      }
      default -> "クエリの発行に失敗しました。再度お問い合わせください。";
    };
  }

  private String trimSql(String sql) {

    Pattern pattern = Pattern.compile(CODE_BLOCK_PATTERN, Pattern.DOTALL);
    Matcher matcher = pattern.matcher(sql);
    return matcher.find() ? matcher.group(1).trim() : "invalid sql";
  }

  private QueryType getQueryType(String sql) {
    if (sql.toUpperCase().contains(QueryType.SELECT.getType())) {
      return QueryType.SELECT;
    }
    if (sql.toUpperCase().contains(QueryType.INSERT.getType())) {
      return QueryType.INSERT;
    }
    if (sql.toUpperCase().contains(QueryType.UPDATE.getType())) {
      return QueryType.UPDATE;
    }
    if (sql.toUpperCase().contains(QueryType.DELETE.getType())) {
      return QueryType.DELETE;
    }
    if (sql.toUpperCase().contains(QueryType.CREATE_TABLE.getType())) {
      return QueryType.CREATE_TABLE;
    }
    return QueryType.INVALID;
  }
}
