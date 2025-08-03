package social.network.backend.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Getter
@Setter
@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class Image {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    private String filePath;

    @Transient
    private String mimeType;
}