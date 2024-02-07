package org.example.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QueryType {

  SELECT("select"),
  INSERT("insert"),
  UPDATE("update"),
  DELETE("delete"),
  CREATE_TABLE("create table"),
  INVALID("invalid");

  private final String type;
}
