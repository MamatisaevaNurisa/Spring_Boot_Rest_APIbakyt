package peaksoft.spring_boot_rest_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {
    private  String courseName;
    private  String durationMonth;
    private Long companyId;

}
