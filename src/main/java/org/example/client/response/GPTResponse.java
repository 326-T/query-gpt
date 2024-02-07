package org.example.client.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GPTResponse {

  private String id;
  private String object;
  private String created;
  private String model;
  private List<ChoiceResponse> choices;
  private UsageResponse usage;
}
