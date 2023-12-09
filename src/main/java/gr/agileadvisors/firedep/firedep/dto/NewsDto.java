package gr.agileadvisors.firedep.firedep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewsDto implements Serializable {
    private Long newsId;
    private String enTitle;
    private String enSubtitle;
    private String enContent;
    private LocalDateTime createdAt;
    private String category;
    private String videoURL;
    private String image;
    private String elTitle;
    private String elSubtitle;
    private String elContent;
}
