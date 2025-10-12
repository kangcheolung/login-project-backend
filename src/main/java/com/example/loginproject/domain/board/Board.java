package com.example.loginproject.domain.board;

import com.example.loginproject.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no")
    private Long boardNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String category;  // ← String 그대로

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Builder
    public Board(User user, String title, String content, String category) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.viewCount = 0L;
    }

    public void updateBoard(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}