package social.network.backend.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "post_comment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class post_comment {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    private String commentText;

    private LocalDateTime dateComment;

}
