package peaksoft.spring_boot_rest_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.spring_boot_rest_api.dto.CompanyRequest;
import peaksoft.spring_boot_rest_api.dto.CompanyResponse;
import peaksoft.spring_boot_rest_api.service.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
    @PreAuthorize("hasAuthority('ADMIN')")
public class CompanyController {
    private  final CompanyService companyService;

    @GetMapping
    public List<CompanyResponse>getAll(){
        System.out.println("I'm in company controller");
      return companyService.getAllCompanies();
    }

    @GetMapping("{id}")
    public CompanyResponse getCompany(@PathVariable("id") Long id){
        return companyService.getCompanyById(id);
    }

    @PostMapping
    public  CompanyResponse save(@RequestBody CompanyRequest companyRequest){
        return companyService.saveCompany(companyRequest);
    }

    @PutMapping("{id}")
    public  CompanyResponse update(@PathVariable("id")Long id,@RequestBody CompanyRequest companyRequest){
        return  companyService.updateCompany(id,companyRequest);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id")Long id){
        companyService.deleteCompany(id);
        return "Successfully delete"+id;
    }
}
