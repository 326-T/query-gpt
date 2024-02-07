package org.example.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChoiceResponse {
  private Integer index;
  private MessageResponse message;
  @JsonProperty("finish_reason")
  private String finishReason;
}
