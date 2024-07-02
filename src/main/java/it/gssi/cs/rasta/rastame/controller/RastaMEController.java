/*package it.gssi.cs.rasta.rastame.controller;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/metadataenricher")
@CrossOrigin(origins = "http://localhost:8080")
public class RastaMEController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/{poiDescription}")
    public @ResponseBody List<Entity> metadataEnricher(@PathVariable("poiDescription") String poiDescription) {

        ChatResponse response = chatClient.call(
                new Prompt(
                        "Generate the names of 5 famous pirates.",
                        OpenAiChatOptions.builder()
                                .withModel("gpt-3.5-turbo")
                                .withTemperature(0.4F)
                                .build()
                ));

         
        List<Entity> result = new ArrayList<>();
        Entity entity1 = new Entity();
        entity1.setName("Historical Museum of the Abruzzo, Lazio and Molise National Park");
        entity1.setLink("https://www.parks.it/parco.nazionale.abruzzo/cen_dettaglio.php?id=1213");
        result.add(entity1);
        Entity entity2 = new Entity();
        entity2.setName("Palazzo Sipari");
        entity2.setLink("https://it.wikipedia.org/wiki/Palazzo_Sipari");
        result.add(entity2);
        
        Entity entity3 = new Entity();
        entity3.setName("Erminio Sipari");
        entity3.setLink("https://it.wikipedia.org/wiki/Erminio_Sipari");
        result.add(entity3);
        
        Entity entity4 = new Entity();
        entity4.setName("Benedetto Croce");
        entity4.setLink("https://it.wikipedia.org/wiki/Benedetto_Croce");
        result.add(entity4);
        
        return result;
    }
}*/

/*package it.gssi.cs.rasta.rastame.controller;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/metadataenricher")
@CrossOrigin(origins = "http://localhost:8080")
public class RastaMEController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/{poiDescription}")
    public @ResponseBody List<Entity> metadataEnricher(@PathVariable("poiDescription") String poiDescription) {
        
        // Use the POI description to generate a prompt for extracting entities
        ChatResponse response = chatClient.call(
                new Prompt(
                        "Extract named entities and their corresponding URLs from the following description: " + poiDescription,
                        OpenAiChatOptions.builder()
                                .withModel("gpt-3.5-turbo")
                                .withTemperature(0.4F)
                                .build()
                ));

        List<Entity> result = new ArrayList<>();
        if (response.getResults() != null && !response.getResults().isEmpty()) {
            // Parsing the response content assuming it comes in a structured format
            String content = response.getResults().get(0).getOutput().getContent();
            String[] lines = content.trim().split("\n");
            for (String line : lines) {
                String[] parts = line.split(" - ");  // Assuming format is "EntityName - EntityURL"
                if (parts.length >= 2) {
                    Entity entity = new Entity();
                    entity.setName(parts[0].trim());
                    entity.setLink(parts[1].trim());
                    result.add(entity);
                }
            }
        }

        return result;
    }
}*/


package it.gssi.cs.rasta.rastame.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/metadataenricher")
@CrossOrigin(origins = "http://localhost:8080")
public class RastaMEController {

    private static final Logger logger = LoggerFactory.getLogger(RastaMEController.class);

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/{poiDescription}")
    public @ResponseBody List<Entity> metadataEnricher(@PathVariable("poiDescription") String poiDescription) {
        
        // Use the POI description to generate a prompt for extracting entities
        String promptText = "Extract named entities and provide their corresponding URLs from the following description: " 
            + poiDescription + 
            ". The response should include the name of the entity followed by a hyphen and then the URL, each entity on a new line. " +
            "If a specific URL is not available, provide a relevant Wikipedia or general informative link.";

        ChatResponse response = chatClient.call(
                new Prompt(
                        promptText,
                        OpenAiChatOptions.builder()
                                .withModel("gpt-3.5-turbo")
                                .withTemperature(0.4F)
                                .build()
                ));

        List<Entity> result = new ArrayList<>();
        if (response.getResults() != null && !response.getResults().isEmpty()) {
            // Log the raw response content
            String content = response.getResults().get(0).getOutput().getContent();
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



