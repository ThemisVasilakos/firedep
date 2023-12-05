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
public class NewsCreateDto implements Serializable {
    private String title;
    private String subTitle;
    private String content;
    private String category;
    private String videoURL;
}
