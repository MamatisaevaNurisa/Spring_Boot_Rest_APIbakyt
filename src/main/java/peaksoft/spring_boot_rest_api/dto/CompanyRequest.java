package peaksoft.spring_boot_rest_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {

    private String companyName;

    private String locatedCountry;
    private String directorName;

}
