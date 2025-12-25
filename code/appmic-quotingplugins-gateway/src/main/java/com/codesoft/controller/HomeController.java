package com.codesoft.controller;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  private final Environment environment;

  public HomeController(final Environment environment) {
    this.environment = environment;
  }

  @RequestMapping("/")
  public String home() {
    String info = "podInfo: " + environment.getProperty("MY_POD_NAME") + ": " + environment.getProperty("MY_POD_IP");
    String text = "texto: " + environment.getProperty("config.texto");
    return "The microservice appmic-quotingplugins-gateway has been activated" + " | " + info + " | " + text;
  }
}
