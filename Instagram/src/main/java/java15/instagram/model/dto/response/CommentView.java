package java15.instagram.model.dto.response;

import java15.instagram.model.entity.Comment;

import java.time.LocalDateTime;

public record CommentView(
        Long id,
        String comment,
        LocalDateTime createdAt,
        UserView user,
        int likesCount
) {
    public static CommentView fromComment(Comment comment) {
        return new CommentView(
                comment.getId(),
                comment.getComment(),
                comment.getCreatedAt(),
                UserView.fromUser(comment.getUser()),
                comment.getLikes().size()
        );
    }
}
