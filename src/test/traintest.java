package test;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import email.BayesEmailProbabilityTrainer;
import email.Email;
import email.ProbabilityCalculator;

public class traintest {
    public static void main(String[] args) {

        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "emailbeans.xml");
        ProbabilityCalculator probCalc = (ProbabilityCalculator) appContext
                .getBean("probabilitycalculator");

        Scanner scan = new Scanner(System.in);
        BayesEmailProbabilityTrainer trainer = (BayesEmailProbabilityTrainer) appContext
                .getBean("probabilitytrainer");

        while (true) {
            System.out.print("sender or @ to break:");
            String sender = scan.nextLine();
            if (sender.equals("@"))
                break;

            System.out.print("subject:");
            String subject = scan.nextLine();
            System.out.print("message:");
            String message = scan.nextLine();

            trainer.train(new Email(sender, subject, message), false);
        }

        trainer.commit();

        ((ClassPathXmlApplicationContext) appContext).close();
    }
}
