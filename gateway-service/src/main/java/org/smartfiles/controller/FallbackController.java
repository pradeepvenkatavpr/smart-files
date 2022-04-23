package org.smartfiles.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

	@GetMapping("/:id")
	public ResponseEntity<String> handleFallback() {
		return new ResponseEntity<String>("Unavailable", HttpStatus.BAD_REQUEST);
	}
}
