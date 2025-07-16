package social.network.backend.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "post_comment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class PostComment {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    private String commentText;

    private LocalDateTime dateComment;

}
