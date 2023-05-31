package peaksoft.spring_boot_rest_api.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.spring_boot_rest_api.entity.Course;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class GroupResponse {
    private Long id;
    private String groupName;
    private String dateOfStart;
    private String dateOfFinish;
  private  LocalDate localDate;
    private Boolean isActivity;
    private Boolean isDelete;
    private List<Course> courses;


}
