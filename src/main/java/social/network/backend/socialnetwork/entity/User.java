package social.network.backend.socialnetwork.entity;


import jakarta.persistence.*;
import lombok.*;
import social.network.backend.socialnetwork.entity.enums.Role;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "social_user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class User {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String password;

    @Enumerated(value = STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "sender", cascade = ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "recipient", cascade = ALL)
    private List<Message> receivedMessages;

}
