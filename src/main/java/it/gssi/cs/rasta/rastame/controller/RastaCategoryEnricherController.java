package it.gssi.cs.rasta.rastame.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/categoriesenricher")
@CrossOrigin(origins = "http://localhost:8080")
public class RastaCategoryEnricherController {

    @GetMapping("")

    public @ResponseBody Set<Integer> categoriesenricher() {
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
        Set<Integer> result = new HashSet<>();
        result.add(1);
        result.add(5);
        return result;
    }

}
