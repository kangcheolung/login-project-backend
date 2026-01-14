package com.example.loginproject.domain.location;

import com.example.loginproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByUser(User user);

    List<Location> findByUserOrderByIsPinnedDescLocationNameAsc(User user);
}