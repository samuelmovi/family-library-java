package samuelmovi.familyLibraryJava.repo;

import samuelmovi.familyLibraryJava.model.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long>{

}
