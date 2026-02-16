package com.jaianper.marketplace.user.infrastructure.web;

import static com.jaianper.marketplace.user.infrastructure.web.AuthDtos.*;

import com.jaianper.marketplace.user.application.LoginUseCase;
import com.jaianper.marketplace.user.application.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final RegisterUserUseCase registerUserUseCase;
  private final LoginUseCase loginUseCase;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<AuthResponse> register(@RequestBody RegisterRequest request) {
    return registerUserUseCase
        .execute(request.email(), request.password(), request.role())
        .map(user -> new AuthResponse("User registered successfully. Please login."));
  }

  @PostMapping("/login")
  public Mono<AuthResponse> login(@RequestBody LoginRequest request) {
    return loginUseCase.execute(request.email(), request.password()).map(AuthResponse::new);
  }
}
