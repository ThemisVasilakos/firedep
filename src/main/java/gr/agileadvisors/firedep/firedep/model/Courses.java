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
@Table(name = "courses")
public class Courses {

    @Id
    private String courseId;

    private String title;
    private String subTitle;

    @Lob
    private String overview;

    private String accreditations;
    private String length;
    private String sectors;
    private String videoURL;
    private String templateType;

    @Lob
    private String content;

    @Lob
    private String content2;

    @Lob
    private String content3;

    @Lob
    private byte[] image;

    @Lob
    private byte[] image2;

    @Lob
    private byte[] image3;

}
