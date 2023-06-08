package peaksoft.spring_boot_rest_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private  String email;
    private  String password;
}
