package org.example.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsageResponse {
  @JsonProperty("prompt_tokens")
  private Integer promptTokens;
  @JsonProperty("completion_tokens")
  private Integer completionTokens;
  @JsonProperty("total_tokens")
  private Integer totalTokens;
}
