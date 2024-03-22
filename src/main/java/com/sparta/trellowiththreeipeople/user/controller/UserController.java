package com.sparta.trellowiththreeipeople.user.controller;

import com.sparta.trellowiththreeipeople.security.UserDetailsImpl;
import com.sparta.trellowiththreeipeople.user.dto.request.CreateUserRequestDto;
import com.sparta.trellowiththreeipeople.user.dto.request.UpdateUserRequestDto;
import com.sparta.trellowiththreeipeople.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(
            @RequestBody @Valid CreateUserRequestDto createUserRequestDto
    ) throws BadRequestException {
        userService.createUser(createUserRequestDto);

        return ResponseEntity.created(URI.create("/api/auth/login"))
                .body("회원가입이 정상적으로 완료되었습니다.");
    }

    @PutMapping
    public ResponseEntity<String> updateUser(
            @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws BadRequestException {
        userService.updateUser(updateUserRequestDto, userDetails.getUser());

        return ResponseEntity.created(URI.create("/api/auth/login"))
                .body("정상적으로 비밀번호가 변경되었습니다.");
    }
}
