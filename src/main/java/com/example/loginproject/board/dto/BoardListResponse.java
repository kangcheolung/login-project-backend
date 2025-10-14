package com.example.loginproject.board.dto;

import com.example.loginproject.domain.board.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardListResponse {
    private Long boardNo;
    private String title;
    private String category;
    private Long viewCount;
    private LocalDateTime createdDate;

    public static BoardListResponse from(Board board) {
        return BoardListResponse.builder()
                .boardNo(board.getBoardNo())
                .title(board.getTitle())
                .category(board.getCategory())
                .viewCount(board.getViewCount())
                .createdDate(board.getCreatedDate())
                .build();
    }
}