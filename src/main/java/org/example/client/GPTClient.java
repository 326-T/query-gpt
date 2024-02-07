package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.example.client.request.GPTRequest;
import org.example.client.request.MessageRequest;
import org.example.client.response.GPTResponse;
import org.example.config.GPTClientConfig;
import org.example.error.exception.GPTException;
import org.springframework.stereotype.Component;

@Component
public class GPTClient {

  private final GPTClientConfig config;
  private final OkHttpClient client;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public GPTClient(GPTClientConfig config) {
    this.config = config;
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    builder.addInterceptor(loggingInterceptor);
    this.client = builder.build();
  }

  public GPTResponse ask(String prompt) throws IOException {
    String body = objectMapper.writeValueAsString(GPTRequest.builder()
        .model(config.getModel())
        .messages(List.of(MessageRequest.builder()
            .content(prompt)
            .role("system")
            .build())).build());
    RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json"));
    Request request = new Request.Builder()
        .url(config.getBaseUrl() + config.getAskPath())
        .post(requestBody)
        .addHeader("Authorization", "Bearer " + config.getApiKey())
        .addHeader("Content-Type", "application/json")
        .build();
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new GPTException("Failed to ask GPT: %s".formatted(response.body().string()));
      }
      return objectMapper.readValue(response.body().string(), GPTResponse.class);
    }
  }
}
