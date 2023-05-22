package peaksoft.spring_boot_rest_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CompanyResponse {
    private Long id;

    private String companyName;

    private String locatedCountry;
    private String directorName;
    private LocalDate localDate;
}
