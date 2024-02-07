package org.example.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GPTResponse {

  private String id;
  private String object;
  private String created;
  private String model;
  private List<ChoiceResponse> choices;
  private UsageResponse usage;
}
