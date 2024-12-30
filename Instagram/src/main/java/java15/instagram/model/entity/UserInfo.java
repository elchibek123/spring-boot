package java15.instagram.model.entity;

import jakarta.persistence.*;
import java15.instagram.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_infos")
public class UserInfo extends BaseEntity {
    private String fullName;
    private String biography;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String image;

    @OneToOne(mappedBy = "userInfo")
    private User user;
}
