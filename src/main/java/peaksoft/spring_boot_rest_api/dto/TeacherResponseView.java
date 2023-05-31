package peaksoft.spring_boot_rest_api.dto;

import lombok.Getter;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
public class TeacherResponseView {
    private List<TeacherResponse>teacherResponses;
}
