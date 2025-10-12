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

    /**
     * 전체 게시판 조회
     */
    @Transactional(readOnly = true)
    public Page<BoardListResponse> getAllBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        return boards.map(BoardListResponse::from);
    }

    /**
     * 내 게시글만 조회 (정보 조회 페이지)
     */
    @Transactional(readOnly = true)
    public Page<BoardListResponse> getMyBoardList(Long userNo, Pageable pageable) {
        User user = userRepository.findById(userNo)
                .orElseThrow(UserException.UserNotFoundException::new);

        Page<Board> boards = boardRepository.findByUser(user, pageable);
        return boards.map(BoardListResponse::from);
    }

    /**
     * 게시글 상세 조회 (조회수 증가)
     */
    @Transactional
    public BoardResponse getBoardDetail(Long boardNo, Long userNo) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 본인이 아니면 조회수 증가
        if (!board.getUser().getUserNo().equals(userNo)) {
            board.incrementViewCount();
        }

        return BoardResponse.from(board);
    }

    /**
     * 게시글 작성
     */
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

    /**
     * 게시글 수정 (본인만 가능)
     */
    @Transactional
    public BoardResponse updateBoard(Long boardNo, BoardRequest request, Long userNo) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!board.getUser().getUserNo().equals(userNo)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }

        board.updateBoard(request.getTitle(), request.getContent(), request.getCategory());
        return BoardResponse.from(board);
    }

    /**
     * 게시글 삭제 (본인만 가능)
     */
    @Transactional
    public void deleteBoard(Long boardNo, Long userNo) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!board.getUser().getUserNo().equals(userNo)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }

        boardRepository.delete(board);
    }
}