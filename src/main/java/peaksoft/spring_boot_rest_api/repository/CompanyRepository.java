package peaksoft.spring_boot_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.spring_boot_rest_api.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
