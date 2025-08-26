package com.example.board.comment.controller;

import com.example.board.comment.dto.CommentRequest;
import com.example.board.comment.dto.CommentResponse;
import com.example.board.comment.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/crud/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request) {
        System.out.println("[DEBUG] CommentRequest: " + request);
        CommentResponse response = commentService.createComment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@RequestParam Long postId) {
        List<CommentResponse> responses = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
