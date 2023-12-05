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

    private String title;
    private String subTitle;
    private String overview;
    private String accreditations;
    private String length;
    private String sectors;
    private String videoURL;
    private String templateType;
    private String content;
    private String content2;
    private String content3;
    private String image;
    private String image2;
    private String image3;
}
