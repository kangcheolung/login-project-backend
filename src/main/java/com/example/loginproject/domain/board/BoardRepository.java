package com.example.loginproject.domain.board;

import com.example.loginproject.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
    Page<Board> findByUser(User user, Pageable pageable);

}
