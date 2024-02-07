package org.example.client.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GPTRequest {
  private String model;
  private List<MessageRequest> messages;
}
