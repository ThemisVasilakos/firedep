package gr.agileadvisors.firedep.firedep.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsId;

    private String enTitle;
    private String enSubtitle;

    @Lob
    private String enContent;

    private String elTitle;
    private String elSubtitle;

    @Lob
    private String elContent;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Lob
    private byte[] image;

    private String category;
    private String videoURL;

}
