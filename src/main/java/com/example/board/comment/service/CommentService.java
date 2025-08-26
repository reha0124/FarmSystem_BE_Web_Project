package com.example.board.comment.service;

import com.example.board.comment.domain.Comment;
import com.example.board.comment.dto.CommentRequest;
import com.example.board.comment.dto.CommentResponse;
import com.example.board.comment.repository.CommentRepository;
import com.example.board.post.domain.Post;
import com.example.board.post.repository.PostRepository;
import com.example.board.user.domain.User;
import com.example.board.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public CommentResponse createComment(CommentRequest request) {
        if (request.getUserId() == null || request.getPostId() == null) {
            throw new IllegalArgumentException("USER_ID_OR_POST_ID_IS_NULL");
        }
        User user = userRepository.findByIdAndIsDeletedFalse(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("USER_NOT_FOUND"));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("POST_NOT_FOUND"));
        Comment comment = new Comment(user, post, request.getContent());
        commentRepository.save(comment);
        return new CommentResponse(comment.getId(), user.getName(), comment.getContent(), comment.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("POST_NOT_FOUND"));
        List<Comment> comments = commentRepository.findByPostAndDeletedFalse(post);
        return comments.stream()
                .map(c -> new CommentResponse(c.getId(), c.getUser().getName(), c.getContent(), c.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("COMMENT_NOT_FOUND"));
        comment.setDeleted(true);
        commentRepository.save(comment);
    }
}
