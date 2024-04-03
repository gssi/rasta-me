package it.gssi.cs.rasta.rastame.controller;

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

    @GetMapping("")
    public @ResponseBody List<Entity> metadataEnricher() {
        /*
        ChatResponse response = chatClient.call(
                new Prompt(
                        "Generate the names of 5 famous pirates.",
                        OpenAiChatOptions.builder()
                                .withModel("gpt-3.5-turbo")
                                .withTemperature(0.4F)
                                .build()
                ));

         */
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
}
