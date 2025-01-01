package java15.instagram.service;

import java15.instagram.model.dto.response.UserProfileView;
import java15.instagram.model.dto.response.UserView;

import java.util.List;

public interface FollowerService {
    boolean toggleSubscription(Long userId, Long targetUserId);

    List<UserView> getAllSubscribersByUserId(Long userId);

    List<UserView> getAllSubscriptionsByUserId(Long userId);
}
