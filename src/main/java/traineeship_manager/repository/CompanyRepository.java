package traineeship_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import traineeship_manager.domainmodel.Company;

public interface CompanyRepository extends JpaRepository<Company, String> {
}
