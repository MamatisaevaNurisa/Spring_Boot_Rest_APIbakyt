package peaksoft.spring_boot_rest_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String jwt;
    private String message;
    private  String authorities;


}
