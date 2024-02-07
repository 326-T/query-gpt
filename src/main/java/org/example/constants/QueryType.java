package org.example.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QueryType {

  SELECT("SELECT"),
  INSERT("INSERT"),
  UPDATE("UPDATE"),
  DELETE("DELETE"),
  CREATE_TABLE("CREATE TABLE"),
  INVALID("INVALID");

  private final String type;
}
