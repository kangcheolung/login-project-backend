package com.example.loginproject.board.controller;

import com.example.loginproject.board.dto.BoardListResponse;
import com.example.loginproject.board.dto.BoardRequest;
import com.example.loginproject.board.dto.BoardResponse;
import com.example.loginproject.board.service.BoardService;
import com.example.loginproject.common.CommonResponse;
import com.example.loginproject.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 게시판 REST API Controller
 * - RESTful URL 패턴 적용
 * - @AuthenticationPrincipal로 안전한 사용자 인증
 */
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 전체 게시글 조회 (페이징)
    @GetMapping("/all")
    public CommonResponse<Page<BoardListResponse>> getAllBoards(
            @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardListResponse> boardListResponses = boardService.getAllBoards(pageable);
        return CommonResponse.ok(boardListResponses);
    }

    // 게시글 생성
    @PostMapping
    public CommonResponse<BoardResponse> createBoard(
            @RequestBody BoardRequest boardRequest,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        BoardResponse boardResponse = boardService.createBoard(boardRequest, currentUser.getUserNo());
        return CommonResponse.ok(boardResponse);
    }

    // 내가 쓴 게시글 조회 (페이징)
    @GetMapping("/my")
    public CommonResponse<Page<BoardListResponse>> getMyBoards(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardListResponse> boardListResponses = boardService.getMyBoardList(currentUser.getUserNo(), pageable);
        return CommonResponse.ok(boardListResponses);
    }

    // 특정 게시글 상세 조회
    @GetMapping("/{boardNo}")
    public CommonResponse<BoardResponse> getBoardDetail(
            @PathVariable Long boardNo,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        BoardResponse boardResponse = boardService.getBoardDetail(boardNo, currentUser.getUserNo());
        return CommonResponse.ok(boardResponse);
    }

    // 게시글 수정
    @PutMapping("/{boardNo}")
    public CommonResponse<BoardResponse> updateBoard(
            @PathVariable Long boardNo,
            @RequestBody BoardRequest boardRequest,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        BoardResponse boardResponse = boardService.updateBoard(boardNo, boardRequest, currentUser.getUserNo());
        return CommonResponse.ok(boardResponse);
    }

    // 게시글 삭제
    @DeleteMapping("/{boardNo}")
    public CommonResponse<Void> deleteBoard(
            @PathVariable Long boardNo,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        boardService.deleteBoard(boardNo, currentUser.getUserNo());
        return CommonResponse.ok(null);
    }
}