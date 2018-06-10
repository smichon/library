package com.crud.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class StaticWebPageController {
    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        model.put("api_documentation", "http://localhost:8080/swagger-ui.html");
        model.put("info", "http://localhost:8080/actuator/info");
        model.put("health", "http://localhost:8080/actuator/health");
        model.put("mappings", "http://localhost:8080/actuator/mappings");
        model.put("metrics", "http://localhost:8080/actuator/metrics");
        return "index";
    }
}
