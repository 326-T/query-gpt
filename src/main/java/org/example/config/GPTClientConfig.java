package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gpt")
@Data
public class GPTClientConfig {
  private String baseUrl;
  private String askPath;
  private String apiKey;
  private String model;
  private Long timeout;
}
