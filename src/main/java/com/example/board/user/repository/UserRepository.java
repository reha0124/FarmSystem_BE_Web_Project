package com.example.board.user.repository;

import com.example.board.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 사용자 조회
    Optional<User> findByEmail(String email);

    // 삭제되지 않은 사용자만 조회
    List<User> findByIsDeletedFalse();

    // 삭제되지 않은 사용자 중 이메일로 조회
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    // 삭제되지 않은 사용자 중 아이디로 조회
    Optional<User> findByIdAndIsDeletedFalse(Long id);

    // 이메일 존재 여부 확인 (삭제되지 않은 사용자만)
    boolean existsByEmailAndIsDeletedFalse(String email);
}
