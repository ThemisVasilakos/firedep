package gr.agileadvisors.firedep.firedep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoursesDto implements Serializable {
    private String courseId;

    private String enTitle;
    private String enSubtitle;
    private String enOverview;
    private String enAccreditations;
    private String enLength;
    private String enSectors;
    private String videoURL;
    private String templateType;
    private String enContent;
    private String enContent2;
    private String enContent3;
    private String image;
    private String image2;
    private String image3;

    private String elTitle;
    private String elSubtitle;
    private String elOverview;
    private String elAccreditations;
    private String elLength;
    private String elSectors;
    private String elContent;
    private String elContent2;
    private String elContent3;
}
