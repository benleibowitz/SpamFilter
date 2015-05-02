package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import email.Email;
import email.ProbabilityCalculator;

public class test {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "resources/emailbeans.xml");

        ProbabilityCalculator probCalc = (ProbabilityCalculator) context
                .getBean("probabilitycalculator");

        Email email = new Email("NewSexAlert", "want to be my new bangbuddy",
                "how do you do pussy f#cker :-P are you h#rny?");
        System.out.println(probCalc.isSpam(email));

        ((ClassPathXmlApplicationContext) context).close();
    }
}
