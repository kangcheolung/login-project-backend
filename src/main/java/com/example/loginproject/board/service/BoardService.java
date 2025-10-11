package com.example.loginproject.board.service;

import com.example.loginproject.board.dto.BoardListResponse;
import com.example.loginproject.board.dto.BoardRequest;
import com.example.loginproject.board.dto.BoardResponse;
import com.example.loginproject.domain.board.Board;
import com.example.loginproject.domain.board.BoardRepository;
import com.example.loginproject.domain.user.User;
import com.example.loginproject.domain.user.UserRepository;
import com.example.loginproject.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponse createBoard(BoardRequest request, Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(UserException.UserNotFoundException::new);

        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .user(user)
                .build();
        Board savedBoard = boardRepository.save(board);
        return BoardResponse.from(savedBoard);
    }

    @Transactional(readOnly = true)
    public Page<BoardListResponse> getMyBoardList(Long userNo,Pageable pageable) {
        User user = userRepository.findById(userNo)
                .orElseThrow(UserException.UserNotFoundException::new);

        Page<Board> boards = boardRepository.findByUser(user, pageable);
        return boards.map(BoardListResponse::from);
    }

    @Transactional
    public BoardResponse getBoardDetail(Long boardNo, Long userNo) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!board.getUser().getUserNo().equals(userNo)) {
            board.incrementViewCount();
        }

        return BoardResponse.from(board);
    }

    @Transactional
    public BoardResponse updateBoard(Long boardNo, BoardRequest request, Long userNo) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!board.getUser().getUserNo().equals(userNo)) {
            throw new IllegalArgumentException("게시글 작성자만 접근할 수 있습니다");
        }

        board.updateBoard(request.getTitle(), request.getContent(), request.getCategory());
        return BoardResponse.from(board);
    }

    @Transactional
    public void deleteBoard(Long boardNo, Long userNo) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!board.getUser().getUserNo().equals(userNo)) {
            throw new IllegalArgumentException("게시글 작성자만 접근할 수 있습니다");
        }

        boardRepository.delete(board);
    }


}
