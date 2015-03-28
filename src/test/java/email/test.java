package email;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
	public static void main(String[] args) {
		
		ApplicationContext c = new ClassPathXmlApplicationContext("emailbeans.xml");
		ProbabilityCalculator probCalc = (ProbabilityCalculator)c.getBean("emailprobabilitycalculator");

		Email e = new Email("Robert DeVaux", "Your account information", "Please update your account information");
		System.out.println(probCalc.isSpam(e));
		
	 }
}
