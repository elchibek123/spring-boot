package java15.instagram.service;

public interface LikeService {
    void toggleLike(Long postId, String username);

    void toggleLikeForComment(Long commentId, String username);
}
