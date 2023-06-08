package peaksoft.spring_boot_rest_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.spring_boot_rest_api.dto.CompanyRequest;
import peaksoft.spring_boot_rest_api.dto.CompanyResponse;
import peaksoft.spring_boot_rest_api.dto.CompanyResponseView;
import peaksoft.spring_boot_rest_api.service.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
@Tag(name = "Company Auth", description = "We can create new Company")
@PreAuthorize("hasAuthority('ADMIN')")
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    @Operation(summary = "Get all companies", description = "Only Admin get all companies")
    public List<CompanyResponse> getAll() {
        return companyService.getAllCompanies();
    }

    @GetMapping("{id}")
    @Operation(summary = "Get by id", description = "Admin can get Company by id")
    public CompanyResponse getCompany(@PathVariable("id") Long id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping
    @Operation(summary = "create",description = "Admin can create new Company")
    public CompanyResponse save(@RequestBody CompanyRequest companyRequest) {
        return companyService.saveCompany(companyRequest);
    }

    @PutMapping("{id}")
    @Operation(summary = "update",description = "Admin can create new Company")
    public CompanyResponse update(@PathVariable("id") Long id, @RequestBody CompanyRequest companyRequest) {
        return companyService.updateCompany(id, companyRequest);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete",description = "Admin can delete Company by id")
    public String delete(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return "Successfully Company  deleted with id : " + id;
    }
    @GetMapping("search")
    public CompanyResponseView getAllCompanies(@RequestParam(name = "text",required = false)String text,
                                               @RequestParam int page,
                                               @RequestParam int size){
return  companyService.searchAndPagination(text,page,size);

    }
}
