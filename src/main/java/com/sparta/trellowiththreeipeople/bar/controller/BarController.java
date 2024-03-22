package com.sparta.trellowiththreeipeople.bar.controller;

import com.sparta.trellowiththreeipeople.bar.dto.BarRequestDto;
import com.sparta.trellowiththreeipeople.bar.dto.BarResponseDto;
import com.sparta.trellowiththreeipeople.bar.dto.OrderNumDto;
import com.sparta.trellowiththreeipeople.bar.service.BarService;
import com.sparta.trellowiththreeipeople.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards/{boardId}/bars")
public class BarController {
    private final BarService barService;

    @PostMapping
    public ResponseEntity<String> createBar(
            @PathVariable Long boardId,
            @RequestBody BarRequestDto barRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long barId = barService.createBar(boardId, barRequestDto.getTitle(), userDetails.getUserId());
        return ResponseEntity
                .created(URI.create("/api/boards/" + boardId + "/bars/" + barId))
                .body("Bar가 정상적으로 생성되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<BarResponseDto>> getBarList(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<BarResponseDto> barResponseDtoList = barService.getBarList(boardId, userDetails.getUserId());
        return ResponseEntity.ok().body(barResponseDtoList);
    }

    @GetMapping("/{barId}")
    public ResponseEntity<BarResponseDto> getBar(
            @PathVariable Long boardId,
            @PathVariable Long barId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        BarResponseDto barResponseDto = barService.getBar(boardId, barId, userDetails.getUserId());
        return ResponseEntity.ok().body(barResponseDto);
    }

    @PatchMapping("/{barId}")
    public ResponseEntity<String> switchOrder(
            @PathVariable Long boardId,
            @PathVariable Long barId,
            @RequestBody OrderNumDto orderNumDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        barService.switchOrder(boardId, barId, orderNumDto.getSwitchedBarId(), userDetails.getUserId());
        return ResponseEntity.ok().body("순서가 변경되었습니다.");
    }

    @PutMapping("/{barId}")
    public ResponseEntity<String> updateBar(
            @PathVariable Long boardId,
            @PathVariable Long barId,
            @RequestBody BarRequestDto barRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        barService.updateBar(boardId, barId, barRequestDto.getTitle(), userDetails.getUserId());
        return ResponseEntity
                .created(URI.create("/api/boards/" + boardId + "/bars/" + barId))
                .body("Bar가 정상적으로 수정되었습니다.");
    }

    @DeleteMapping("/{barId}")
    public ResponseEntity<String> deleteBar(
            @PathVariable Long boardId,
            @PathVariable Long barId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        barService.deleteBar(boardId, barId, userDetails.getUserId());
        return ResponseEntity.ok().body("Bar가 정상적으로 삭제되었습니다.");
    }
}
