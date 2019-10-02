package samuelmovi.familyLibraryJava.app;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import samuelmovi.familyLibraryJava.controller.Controller;
import samuelmovi.familyLibraryJava.repo.BookRepository;
import samuelmovi.familyLibraryJava.repo.BookViewRepository;
import samuelmovi.familyLibraryJava.repo.LoanRepository;
import samuelmovi.familyLibraryJava.repo.LocationRepository;

public class Main {

	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		
		Controller controller = (Controller) context.getBean("controller");

		// create repositories
		BookRepository bookRepository = (BookRepository) context.getBean("bookRepository");
		BookViewRepository bookViewRepository = (BookViewRepository) context.getBean("bookViewRepository") ;
		LocationRepository locationRepository = (LocationRepository) context.getBean("locationRepository");
		LoanRepository loanRepository = (LoanRepository) context.getBean("loanRepository");

		// set samuelmovi.familyLibraryJava.controller requirements
		controller.setBooks(bookRepository);
		controller.setBookViews(bookViewRepository);
		controller.setLocations(locationRepository);
		controller.setLoans(loanRepository);

		// launch samuelmovi.familyLibraryJava.controller
		controller.initController();
		context.registerShutdownHook();
	}

}
