package org.example.service;

import java.io.IOException;
import org.example.client.GPTClient;
import org.springframework.stereotype.Service;

@Service
public class GPTService {

  private final GPTClient gptClient;

  public GPTService(GPTClient gptClient) {
    this.gptClient = gptClient;
  }

  public String generateResponse(String requestText) throws IOException {
    return gptClient.ask(requestText).getChoices().get(0).getMessage().getContent();
  }
}
