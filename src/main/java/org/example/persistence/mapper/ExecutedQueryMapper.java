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

  @Insert("""
      INSERT INTO executed_queries (query, executed_at)
      VALUES (#{query}, #{executedAt});
      """)
  void insert(ExecutedQuery executedQuery);
}
