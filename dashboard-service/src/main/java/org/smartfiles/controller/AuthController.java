package org.smartfiles.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/smart-files")
public class AuthController {
	
	private static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@GetMapping("/auth/login")
	public Mono<Rendering> login() {
		return Mono.just(Rendering.view("login").build());
	}

}
