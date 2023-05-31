package peaksoft.spring_boot_rest_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
    private String  firstName;
    private  String  lastName;
    private String email;

    private  String roleName;
    private String studyFormat;
    private  String password;
    private Long studentId;

    private Long groupId;

}
