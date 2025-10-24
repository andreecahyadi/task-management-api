package com.self_project.task_management_api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class HealthController {

    @GetMapping({"/", "/api/ping"})
    public Map<String, String> ping() {
        return Collections.singletonMap("status", "check hit api success.");
    }
}
