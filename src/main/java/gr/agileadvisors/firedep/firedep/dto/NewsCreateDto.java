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
    private String enTitle;
    private String enSubtitle;
    private String enContent;
    private String category;
    private String videoURL;
    private String elTitle;
    private String elSubtitle;
    private String elContent;
}
