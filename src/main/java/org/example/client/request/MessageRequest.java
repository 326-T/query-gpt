package org.example.client.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageRequest {
  private String role;
  private String content;
}
