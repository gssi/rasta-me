package it.gssi.cs.rasta.rastame.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/metadataenricher")
@CrossOrigin(origins = "http://localhost:8080")
public class RastaMEController {

    private static final Logger logger = LoggerFactory.getLogger(RastaMEController.class);

    private ChatClient chatClient;

    public RastaMEController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/{poiDescription}")
    public @ResponseBody List<Entity> metadataEnricher(@PathVariable("poiDescription") String poiDescription) {
        
        // Use the POI description to generate a prompt for extracting entities
        String promptText = "Extract named entities and provide their corresponding URLs from the following description: " 
            + poiDescription + 
            ". The response should include the name of the entity followed by a hyphen and then the URL, each entity on a new line. " +
            "If a specific URL is not available, provide a relevant Wikipedia or general informative link.";

        /*
        ChatResponse response = chatClient.call(
                new Prompt(
                        promptText,
                        OpenAiChatOptions.builder()
                                .withModel("gpt-3.5-turbo")
                                .withTemperature(0.4F)
                                .build()
                ));
        */
        ChatResponse chatResponse = chatClient.prompt()
                .user(promptText)
                .call()
                .chatResponse();
        List<Entity> result = new ArrayList<>();
        if (chatResponse.getResults() != null && !chatResponse.getResults().isEmpty()) {
            // Log the raw response content
            String content = chatResponse.getResults().get(0).getOutput().getContent();
            logger.info("Raw AI Response: {}", content);

            if (content != null && !content.isEmpty()) {
                // Parsing the response content assuming it comes in a structured format
                String[] lines = content.trim().split("\n");
                for (String line : lines) {
                    String[] parts = line.split(" - ");  // Assuming format is "EntityName - EntityURL"
                    if (parts.length >= 2) {
                        Entity entity = new Entity();
                        entity.setName(parts[0].trim());
                        String url = parts[1].trim();
                        if (url.equalsIgnoreCase("No URL provided")) {
                            entity.setLink(null);
                        } else {
                            entity.setLink(url);
                        }
                        result.add(entity);
                    }
                }
            }
        } else {
            logger.warn("Empty response or no results from the AI model.");
        }

        return result;
    }
}



