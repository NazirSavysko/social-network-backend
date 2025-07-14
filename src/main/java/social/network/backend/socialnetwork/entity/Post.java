package social.network.backend.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "post")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class Post {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    private String postText;

    @Column(name = "post_date")
    private LocalDateTime postDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
