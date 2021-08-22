package com.belkin.yahack.api.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock")
public class MockController {

    @GetMapping("/episodes")
    public String getEpisode() {
        return "{\n" +
                "    \"podcastId\": \"base64\",\n" +
                "    \"episodeId\": \"base64\",\n" +
                "    \"episodeNumber\": 1,\n" +
                "    \"title\": \"Cool podcast\",\n" +
                "    \"duration\": 17,\n" +
                "    \"url\": \".mp3\",\n" +
                "    \"description\": \"blah-blah-blah\",\n" +
                "    \"published\": true,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"id\": \"base64\",\n" +
                "            \"timeStart\": 0,\n" +
                "            \"timeEnd\": 4,\n" +
                "            \"type\": \"poll\",\n" +
                "            \"question\": \"Hmm??\",\n" +
                "            \"options\": [\"A\", \"B\", \"C\"],\n" +
                "            \"multipleOptions\": true\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"base64\",\n" +
                "            \"timeStart\": 4,\n" +
                "            \"timeEnd\": 8,\n" +
                "            \"type\": \"imagebutton\",\n" +
                "            \"imageUrl\": \"putin.png\",\n" +
                "            \"buttonUrl\": null,\n" +
                "            \"buttonText\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"base64\",\n" +
                "            \"timeStart\": 8,\n" +
                "            \"timeEnd\": 12,\n" +
                "            \"type\": \"imagebutton\",\n" +
                "            \"imageUrl\": null,\n" +
                "            \"buttonUrl\": \"www.amazon.com/shop\",\n" +
                "            \"buttonText\": \"Купи скайрим\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"base64\",\n" +
                "            \"timeStart\": 12,\n" +
                "            \"timeEnd\": 17,\n" +
                "            \"type\": \"imagebutton\",\n" +
                "            \"imageUrl\": \"putin.png\",\n" +
                "            \"buttonUrl\": \"www.amazon.com/shop\",\n" +
                "            \"buttonText\": \"Купи скайрим\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"base64\",\n" +
                "            \"timeStart\": 12,\n" +
                "            \"timeEnd\": 17,\n" +
                "            \"type\": \"text\",\n" +
                "            \"text\": \"Купи скайрим\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"base64\",\n" +
                "            \"timeStart\": 12,\n" +
                "            \"timeEnd\": 17,\n" +
                "            \"type\": \"form\",\n" +
                "            \"text\": \"Напиши что-нибудь\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
