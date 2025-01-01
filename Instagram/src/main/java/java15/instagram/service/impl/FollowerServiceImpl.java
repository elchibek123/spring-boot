package java15.instagram.service.impl;

import java15.instagram.model.dto.response.UserView;
import java15.instagram.model.entity.Follower;
import java15.instagram.model.entity.User;
import java15.instagram.repository.FollowerRepository;
import java15.instagram.repository.UserRepository;
import java15.instagram.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    @Override
    public boolean toggleSubscription(Long userId, Long targetUserId) {
        User user = userRepository.findByIdOrThrow(userId);
        User targetUser = userRepository.findByIdOrThrow(targetUserId);

        Follower userFollower = getOrCreateFollower(user);

        boolean isSubscribed = userFollower.getSubscriptions().contains(targetUserId);

        if (isSubscribed) {
            userFollower.getSubscriptions().remove(targetUserId);
            Follower targetUserFollower = getOrCreateFollower(targetUser);
            targetUserFollower.getSubscribers().remove(userId);

        } else {
            userFollower.getSubscriptions().add(targetUserId);
            Follower targetUserFollower = getOrCreateFollower(targetUser);
            targetUserFollower.getSubscribers().add(userId);
        }

        followerRepository.save(userFollower);
        followerRepository.save(getOrCreateFollower(targetUser));

        return !isSubscribed;
    }

    @Override
    public List<UserView> getAllSubscribersByUserId(Long userId) {
        Follower follower = followerRepository.findByUserId(userId);

        if (follower == null || follower.getSubscribers().isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> subscriberIds = follower.getSubscribers();

        List<User> subscribers = userRepository.findAllById(subscriberIds);

        return subscribers.stream()
                .map(UserView::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserView> getAllSubscriptionsByUserId(Long userId) {
        Follower follower = followerRepository.findByUserId(userId);

        if (follower == null || follower.getSubscriptions().isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> subscriptionIds = follower.getSubscriptions();

        List<User> subscriptions = userRepository.findAllById(subscriptionIds);

        return subscriptions.stream()
                .map(UserView::fromUser)
                .collect(Collectors.toList());
    }

    private Follower getOrCreateFollower(User user) {
        Follower follower = user.getFollower();
        if (follower == null) {
            follower = new Follower();
            user.setFollower(follower);
        }
        return follower;
    }
}
