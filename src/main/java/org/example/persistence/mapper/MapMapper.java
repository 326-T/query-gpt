package org.example.persistence.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

@Mapper
public interface MapMapper {

  @SelectProvider(type = MapProvider.class, method = "select")
  List<Map<String, String>> select(String sql);

  @InsertProvider(type = MapProvider.class, method = "insert")
  void insert(String sql);

  @UpdateProvider(type = MapProvider.class, method = "update")
  void update(String sql);

  @DeleteProvider(type = MapProvider.class, method = "delete")
  void delete(String sql);

  @UpdateProvider(type = MapProvider.class, method = "createTable")
  void createTable(String sql);
}
