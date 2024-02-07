package org.example.error.exception;

import lombok.Getter;

@Getter
public class GPTException extends RuntimeException {

  private final String detail;

  public GPTException(String message) {
    super(message);
    detail = "%s.%s".formatted(Thread.currentThread().getStackTrace()[2].getClassName(),
        Thread.currentThread().getStackTrace()[2].getMethodName());
  }
}
