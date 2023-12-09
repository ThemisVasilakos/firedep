package gr.agileadvisors.firedep.firedep.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "courses")
public class Courses {

    @Id
    private String courseId;

    private String enTitle;
    private String enSubtitle;

    @Lob
    private String enOverview;

    private String enAccreditations;
    private String enLength;
    private String enSectors;
    private String videoURL;
    private String templateType;

    @Lob
    private String enContent;

    @Lob
    private String enContent2;

    @Lob
    private String enContent3;

    @Lob
    private byte[] image;

    @Lob
    private byte[] image2;

    @Lob
    private byte[] image3;

    private String elTitle;
    private String elSubtitle;

    @Lob
    private String elOverview;

    private String elAccreditations;
    private String elLength;
    private String elSectors;
    @Lob
    private String elContent;

    @Lob
    private String elContent2;

    @Lob
    private String elContent3;

}
