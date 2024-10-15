package it.gssi.cs.rasta.rastame.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/metadataenricher")
@CrossOrigin(origins = "http://localhost:8080")
public class RastaMEController {

    private static final Logger logger = LoggerFactory.getLogger(RastaMEController.class);

    private ChatClient chatClient;

    public RastaMEController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultSystem("Act as a content metadata enricher for a point of interest management system. Analyze the following content and extract relevant metadata such as keywords, entities (places, people, monuments, organizations), topics, categories, and any other pertinent information. Ensure that the metadata is provided in the same language as the content. If the content is in English, return the metadata in English; if the content is in another language, return the metadata in that language.").build();
    }

    @PostMapping("/description")
    public List<Entity> enrichPOIDescription(@RequestBody String description) {
        String prompt = "Analyze the following description of a point of interest: '{description}'. Extract all relevant entities (such as places, people, monuments, or organizations) and, if available, their associated URLs. If you cannot find an appropriate URL, provide a link to the Wikipedia page or another informative link related to the entity. Return a list with each entity and its corresponding URL.";

        return this.chatClient.prompt()
                .user(u -> u.text(prompt).param("description", description))
                .call()
                .entity(new ParameterizedTypeReference<List<Entity>>() {
                });
    }

    @PostMapping("/categories")
    public List<String> categorizePOI(@RequestBody PointOfInterestData pointOfInterestData) {
        String prompt = "Analyze the name and description of the following point of interest and return a list of categories that can be associated with it. The available categories are: {categories}. Only return categories that are exactly identical to the ones provided in the list. You may also suggest new categories, but these must be relevant to the information in the point of interest, and they must not be combinations or modifications of the categories from the provided list. The new categories must be original and should not combine or alter the input categories. The output must be a comma-separated list of the most relevant categories, both from the provided list and any new categories you suggest. Ensure that no new categories are simply combinations of the input categories. Point of interest: '{poiName}'. Description: '{poiDescription}'.";

        return this.chatClient.prompt()
                .user(u -> u.text(prompt).params(Map.of("categories", pointOfInterestData.categories(), "poiName", pointOfInterestData.name(), "poiDescription", pointOfInterestData.description())))
                .call()
                .entity(new ParameterizedTypeReference<List<String>>() {
                });
    }

}



