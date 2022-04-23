package org.smartfiles.controller;

import org.smartfiles.dto.ApiResponse;
import org.smartfiles.dto.LoginRequest;
import org.smartfiles.dto.LoginResponse;
import org.smartfiles.model.User;
import org.smartfiles.service.AuthService;
import org.smartfiles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Controller
public class MainController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	@ResponseBody
	public Mono<ApiResponse> authorizeLogin(ServerWebExchange exchange) {
		Mono<MultiValueMap<String, String>> formData = exchange.getFormData();
		System.out.println(formData);
		
		return formData.flatMap(data -> {
			if (data.getFirst("email") != null && data.getFirst("password") != null) {
				System.out.println("data.getFirst(\"email\") " + data.getFirst("email"));
				LoginRequest request = new LoginRequest();
				request.setEmail(data.getFirst("email").toString());
				request.setPassword(data.getFirst("password").toString());
				try {
					LoginResponse loginResponse = authService.login(request);
					if (loginResponse != null) {
						return Mono.just(new ApiResponse(200, "Login Success", loginResponse));
					}
				} catch (Exception ex) {
					return Mono.just(new ApiResponse(401, ex.getMessage(), null));
				}
			}
			return Mono.just(new ApiResponse(401, "Error in login", null));
		});
	}
	
	@PostMapping("/register")
	@ResponseBody
	public Mono<ApiResponse> register(ServerWebExchange exchange) {
		Mono<MultiValueMap<String, String>> formData = exchange.getFormData();
		return formData.flatMap(data -> {
			if(data != null) {
				User user = new User();
				user.setTitle(data.getFirst("title"));
				user.setFirstName(data.getFirst("firstName"));
				user.setLastName(data.getFirst("lastName"));
				user.setEmail(data.getFirst("email"));
				user.setPhone(data.getFirst("phone"));
				user.setEnabled(true);
				try {
					userService.register(user);
				}
				catch(Exception ex) {
					return Mono.just(new ApiResponse(500, ex.getMessage(), null));
				}
				
				return Mono.just(new ApiResponse(200, "Registered Successfully, Please check your mail", null));
			}
			return Mono.just(new ApiResponse(401, "Registration Failed", null));
		});
	}
}
