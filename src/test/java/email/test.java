package email;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("emailbeans.xml");
		ProbabilityCalculator probCalc = (ProbabilityCalculator)context.getBean("emailprobabilitycalculator");

		Email email = new Email("Robert DeVaux", "Your account information", "Please update your account information");
		System.out.println(probCalc.isSpam(email));
		
        ((ClassPathXmlApplicationContext)context).close();
	 }
}
