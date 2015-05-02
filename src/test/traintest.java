package test;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import email.BayesEmailProbabilityTrainer;
import email.Email;

public class traintest {
    private BayesEmailProbabilityTrainer trainer;
    
    @Autowired
    public void setProbTrainer(BayesEmailProbabilityTrainer trainer) {
        this.trainer = trainer;
    }
    
    public static void main(String[] args) {
        traintest test = new traintest();
        
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "emailbeans.xml");
        appContext.getAutowireCapableBeanFactory().autowireBeanProperties(test,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("sender or @ to break:");
            String sender = scan.nextLine();
            if (sender.equals("@"))
                break;

            System.out.print("subject:");
            String subject = scan.nextLine();
            System.out.print("message:");
            String message = scan.nextLine();

            test.trainer.train(new Email(sender, subject, message), false);
        }

        ((ClassPathXmlApplicationContext) appContext).close();
    }
}
