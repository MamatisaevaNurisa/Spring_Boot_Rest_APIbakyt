package peaksoft.spring_boot_rest_api.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.spring_boot_rest_api.entity.Company;
import peaksoft.spring_boot_rest_api.entity.User;

import java.time.LocalDate;

@Getter
@Setter
public class CourseResponse {
    private  Long id;
    private  String courseName;
    private  String durationMonth;
    private LocalDate localDate;
    private  Boolean isActivity;
    private  Boolean isDelete;
    private Company company;


}
