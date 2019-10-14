package samuelmovi.familyLibraryJava.repo;

import samuelmovi.familyLibraryJava.model.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long>{
    Optional<Loan> findByBorrower(String borrower);
    List<Loan> findAll();
}
