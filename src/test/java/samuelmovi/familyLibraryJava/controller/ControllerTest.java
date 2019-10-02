package samuelmovi.familyLibraryJava.controller;


import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import samuelmovi.familyLibraryJava.view.View;

@ContextConfiguration(locations = "classpath:Tests.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTest {

    @Autowired
    View view;
    @Autowired
    Controller controller;

    @Before
    public void before(){

    }

    @After
    public void after(){

    }

}
