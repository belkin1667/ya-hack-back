package com.belkin.yahack.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(path="/test")
    public String test(@RequestHeader("username") String username) {
        return "TEST! Hello " + username;
    }


    @GetMapping(path="/edit/test")
    public String editTest() { return "EDIT TEST!"; }


}
