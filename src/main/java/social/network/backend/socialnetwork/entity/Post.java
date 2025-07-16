package social.network.backend.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "post")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class Post {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    private String postText;

    private LocalDateTime postDate;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = ALL)
    private List<PostLike> postLikes;

    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = ALL)
    private List<PostComment> postComments;
}
