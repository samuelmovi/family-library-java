package samuelmovi.familyLibraryJava.repo;

import java.util.List;
import java.util.Optional;

import samuelmovi.familyLibraryJava.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>{

    List<Book> findAll();
    List<Book> findByLoaned(boolean loaned);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);
    List<Book> findByPublisher(String publisher);
    List<Book> findByIsbn(String isbn);

}
