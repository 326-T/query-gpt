package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.constants.QueryType;
import org.example.persistence.mapper.MapMapper;
import org.springframework.stereotype.Service;

@Service
public class SqlService {

  private final MapMapper mapMapper;
  private final ObjectMapper objectMapper;

  public SqlService(MapMapper mapMapper, ObjectMapper objectMapper) {
    this.mapMapper = mapMapper;
    this.objectMapper = objectMapper;
  }

  public String execute(String sql) throws JsonProcessingException {
    QueryType queryType = getQueryType(sql);
    return switch (queryType) {
      case SELECT -> objectMapper.writeValueAsString(mapMapper.select(sql));
      case INSERT -> {
        mapMapper.insert(sql);
        yield "データが追加されました。";
      }
      case UPDATE -> {
        mapMapper.update(sql);
        yield "データが更新されました。";
      }
      case DELETE -> {
        mapMapper.delete(sql);
        yield "データが削除されました。";
      }
      case CREATE_TABLE -> {
        mapMapper.createTable(sql);
        yield "テーブルが作成されました。";
      }
      default -> "クエリの発行に失敗しました。再度お問い合わせください。";
    };
  }

  private QueryType getQueryType(String sql) {
    if (sql.toLowerCase().contains(QueryType.SELECT.getType())) {
      return QueryType.SELECT;
    }
    if (sql.toLowerCase().contains(QueryType.INSERT.getType())) {
      return QueryType.INSERT;
    }
    if (sql.toLowerCase().contains(QueryType.UPDATE.getType())) {
      return QueryType.UPDATE;
    }
    if (sql.toLowerCase().contains(QueryType.DELETE.getType())) {
      return QueryType.DELETE;
    }
    if (sql.toLowerCase().contains(QueryType.CREATE_TABLE.getType())) {
      return QueryType.CREATE_TABLE;
    }
    return QueryType.INVALID;
  }
}
