package test;

import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import email.Email;
import email.ProbabilityCalculator;

@PropertySource("classpath:resources/application.properties")
public class test {
    private ProbabilityCalculator probCalc;
    
    @Autowired
    public void setProbabilityCalculator(ProbabilityCalculator probCalc) {
        this.probCalc = probCalc;
    }
    
    public static void main(String[] args) {
        
        try {
            System.getProperties().load(new FileReader("src/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot continue without loading active profile");
        } 
        
        test t = new test();
        
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "resources/emailbeans.xml");
        
        context.getAutowireCapableBeanFactory().autowireBeanProperties(t,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);

        Email email = new Email("NewSexAlert", "want to be my new bangbuddy",
                "hey i saw something online now and you foo");
        System.out.println(t.probCalc.isSpam(email));

        ((ClassPathXmlApplicationContext) context).close();
    }
}
