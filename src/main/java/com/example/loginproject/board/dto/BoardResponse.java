package com.example.loginproject.board.dto;

import com.example.loginproject.domain.board.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponse {
    private Long boardNo;
    private String title;
    private String content;
    private String category;
    private Long viewCount;
    private LocalDateTime createdDate;

    public static BoardResponse from(Board board) {
        return BoardResponse.builder()
                .boardNo(board.getBoardNo())
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .viewCount(board.getViewCount())
                .createdDate(board.getCreatedDate())
                .build();
    }
}