package com.example.board.post.repository;

import com.example.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 삭제되지 않은 게시글만 조회
    List<Post> findByIsDeletedFalse();

    // 삭제되지 않은 게시글 페이징 조회
    Page<Post> findByIsDeletedFalse(Pageable pageable);

    // 삭제되지 않은 게시글 중 ID로 조회
    Optional<Post> findByIdAndIsDeletedFalse(Long id);

    // 특정 사용자의 삭제되지 않은 게시글 조회
    Page<Post> findByAuthorIdAndIsDeletedFalse(Long authorId, Pageable pageable);
}
