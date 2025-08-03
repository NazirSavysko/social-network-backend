package social.network.backend.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Subscription {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "subscriber_id")
    private User subscriber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "target_id")
    private User target;

    private LocalDateTime subscribedAt;
}
