package java15.instagram.service;

import java15.instagram.model.dto.response.UserProfileView;

import java.util.List;

public interface FollowerService {
    boolean toggleSubscription(Long userId, Long targetUserId);

    List<UserProfileView> getAllSubscribersByUserId(Long userId);

    List<UserProfileView> getAllSubscriptionsByUserId(Long userId);
}
