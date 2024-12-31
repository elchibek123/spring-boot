package java15.instagram.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "followers")
public class Follower extends BaseEntity {
    @ElementCollection
    @CollectionTable(name = "follower_subscribers", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "subscriber_id")
    private List<Long> subscribers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "follower_subscriptions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "subscription_id")
    private List<Long> subscriptions = new ArrayList<>();

    @OneToOne(mappedBy = "follower")
    private User user;
}
