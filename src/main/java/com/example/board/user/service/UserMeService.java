package com.example.board.user.service;

import com.example.board.user.domain.User;
import com.example.board.user.repository.UserRepository;
import com.example.board.user.dto.UserResponse;
import com.example.board.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserMeService {

    private final UserRepository users;

    @Transactional(readOnly = true)
    public UserResponse getMe(Long userId) {
        User u = users.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));
        return UserResponse.from(u);
    }

    @Transactional
    public UserResponse updateName(Long userId, UserUpdateRequest req) {
        User u = users.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));
        // 이름만 변경하도록 검증 (필요 시 서비스에서 제한)
        u.setName(req.getName());
        return UserResponse.from(u);
    }

    @Transactional
    public void softDelete(Long userId) {
        User u = users.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));
        u.setIsDeleted(true); // BaseEntity 사용
        // 필요시: refresh 토큰/세션 정리 로직 호출
    }
}
