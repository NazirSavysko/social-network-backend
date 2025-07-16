package social.network.backend.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "post_like")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PostLike {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

}
