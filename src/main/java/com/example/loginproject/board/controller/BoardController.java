package com.example.loginproject.board.controller;


import com.example.loginproject.board.dto.BoardListResponse;
import com.example.loginproject.board.dto.BoardRequest;
import com.example.loginproject.board.dto.BoardResponse;
import com.example.loginproject.board.service.BoardService;
import com.example.loginproject.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/create")
    public CommonResponse<BoardResponse> createBoard(
            @RequestBody BoardRequest boardRequest,
            @RequestParam("userNo") Long userNo
    ) {
        BoardResponse boardResponse = boardService.createBoard(boardRequest, userNo);
        return CommonResponse.ok(boardResponse);
    }

    @GetMapping("/list")
    public CommonResponse<Page<BoardListResponse>> getListBoards(
            @RequestParam("userNo") Long userNo,
            @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable)
    {
        Page<BoardListResponse> boardListResponses = boardService.getMyBoardList(userNo, pageable);
        return CommonResponse.ok(boardListResponses);
    }

    @GetMapping("/detail")
    public CommonResponse<BoardResponse> getBoardDetail(
            @RequestParam("boardNo") Long boardNo,
            @RequestParam("userNo") Long userNo
    ) {
        BoardResponse boardResponse = boardService.getBoardDetail(boardNo, userNo);
        return CommonResponse.ok(boardResponse);
    }

    @PutMapping("/update")
    public CommonResponse<BoardResponse> updateBoard(
            @RequestParam("boardNo") Long boardNo,
            @RequestBody BoardRequest boardRequest,
            @RequestParam("userNo") Long userNo
    ) {
        BoardResponse boardResponse = boardService.updateBoard(boardNo, boardRequest, userNo);
        return CommonResponse.ok(boardResponse);
    }

    @DeleteMapping("/delete")
    public CommonResponse<Void> deleteBoard(
            @RequestParam("boardNo") Long boardNo,
            @RequestParam("userNo") Long userNo
    ) {
        boardService.deleteBoard(boardNo, userNo);
        return CommonResponse.ok(null);
    }


}
