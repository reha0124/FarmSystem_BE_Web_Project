package com.example.board.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    // 사용자 정보를 조회할 repo ( 추후 구현 필요 )
    // private final UserRepository userRepository

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // 비밀번호 암호화 함수
    public void registerUser(String username, String rawPassword) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 사용자 정보를 DB에 저장
        // userRepository.save(new User(username, encodedPassword));
        System.out.println("암호화된 비밀번호: " + encodedPassword);
    }

    /*
    // 비밀번호 일치 확인 로직
    public boolean checkPassword(String username, String rawPassword) {
        // DB에서 사용자 정보 가져오기
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        // 입력된 기존 비밀번호와 DB에 저장된 암호화된 비밀번호 비교
        return passwordEncoder.matches(rawPassword, user.getEncodedPassword());
    }
     */

}
