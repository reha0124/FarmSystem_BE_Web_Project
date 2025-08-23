package com.example.board.user.service;

import com.example.board.user.domain.User;
import com.example.board.user.dto.UserCreateRequest;
import com.example.board.user.dto.UserResponse;
import com.example.board.user.dto.UserUpdateRequest;
import com.example.board.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 사용자 생성 (Create)
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        // 이메일 중복 확인
        if (userRepository.existsByEmailAndIsDeletedFalse(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + request.getEmail());
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // User 엔티티 생성
        User user = new User(request.getEmail(), request.getName(), encodedPassword);

        // 저장
        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }

    // 모든 사용자 조회 (Read)
    public List<UserResponse> getAllUsers() {
        return userRepository.findByIsDeletedFalse()
                .stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }

    // 특정 사용자 조회 (Read)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));

        return UserResponse.from(user);
    }

    // 이메일로 사용자 조회
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));

        return UserResponse.from(user);
    }

    // 사용자 정보 수정 (Update)
    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));

        // 이름 수정
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            user.updateName(request.getName());
        }

        // 비밀번호 수정
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            user.updatePassword(encodedPassword);
        }

        User updatedUser = userRepository.save(user);
        return UserResponse.from(updatedUser);
    }

    // 사용자 삭제 (Delete - 소프트 삭제)
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));

        user.delete();
        userRepository.save(user);
    }
}
