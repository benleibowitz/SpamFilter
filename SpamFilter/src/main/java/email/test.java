package email;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
	public static void main(String[] args) {
		
		ApplicationContext c = new ClassPathXmlApplicationContext("emailbeans.xml");
		ProbabilityCalculator probCalc = (ProbabilityCalculator)c.getBean("emailprobabilitycalculator");
		
		Email e = new Email("hugh chase", "", "pur chase me dociments");
		System.out.println(probCalc.isSpam(e));
		//Scanner s = new Scanner(System.in);
		BayesEmailProbabilityTrainer t = (BayesEmailProbabilityTrainer)c.getBean("emailprobabilitytrainer");
		//System.out.println(m.getBody());
		/*
		while(true) {
			System.out.print("sender or @ to break:");
			String se = s.nextLine();
			System.out.print("subject:");
			String sub = s.nextLine();
			System.out.print("message:");
			String mess = s.nextLine();
			if(se.equals("@")) break;
			t.train(new Message(se, sub, mess), true);
		}
		t.commit();
		*/
		
	 }
}