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
        entity1.setName("prova1");
        entity1.setLink("link prova1");
        result.add(entity1);
        Entity entity2 = new Entity();
        entity2.setName("prova2");
        entity2.setLink("link prova2");
        result.add(entity2);
        return result;
    }
}
