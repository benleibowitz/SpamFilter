package application;

import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import email.Email;
import email.ProbabilityCalculator;

public class App {

    private ProbabilityCalculator probCalc;
    
    @Autowired
    public void setProbabilityCalculator(ProbabilityCalculator probCalc) {
        this.probCalc = probCalc;
    }
    
    public static void main(String[] args) {
        
        try {
            System.getProperties().load(new FileReader("target/classes/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot continue without loading active profile");
        } 
        App b = new App();

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "emailbeans.xml");
        
        context.getAutowireCapableBeanFactory().autowireBeanProperties(b,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);

        Email email = new Email("Jeremy M.", "Here is the job opportunity you requested", "foo bar");
        System.out.println(b.probCalc.isSpam(email));

        ((ClassPathXmlApplicationContext) context).close();
    }
}
