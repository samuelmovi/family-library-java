package app;

import model.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import controller.Controller;

public class Main {

	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		
		Controller controller = (Controller) context.getBean("controller");

		// create repositories
		BookRepository bookRepository = (BookRepository) context.getBean("bookRepository");
		BookViewRepository bookViewRepository = (BookViewRepository) context.getBean("bookViewRepository") ;
		LocationRepository locationRepository = (LocationRepository) context.getBean("locationRepository");
		LoanRepository loanRepository = (LoanRepository) context.getBean("loanRepository");

		// set controller requirements
		controller.setBooks(bookRepository);
		controller.setBookViews(bookViewRepository);
		controller.setLocations(locationRepository);
		controller.setLoans(loanRepository);

		// launch controller
		controller.initController();
		context.registerShutdownHook();
	}

}
