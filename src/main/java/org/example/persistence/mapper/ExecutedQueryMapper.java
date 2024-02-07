package org.example.persistence.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.persistence.entity.ExecutedQuery;

@Mapper
public interface ExecutedQueryMapper {

  @Select("SELECT * FROM executed_queries ORDER BY executed_at;")
  List<ExecutedQuery> findAll();

  @Select("SELECT * FROM executed_queries WHERE is_schema = true ORDER BY executed_at;")
  List<ExecutedQuery> findAllSchemas();

  @Insert("""
      INSERT INTO executed_queries (query, is_schema, executed_at)
      VALUES (#{query}, #{isSchema}, #{executedAt});
      """)
  void insert(ExecutedQuery executedQuery);
}
