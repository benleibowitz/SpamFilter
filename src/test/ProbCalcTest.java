package test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import email.Email;
import email.ProbabilityCalculator;

public class ProbCalcTest {
    private static ApplicationContext app = new ClassPathXmlApplicationContext(
            "emailbeans.xml");

    @Test
    public void testIsSpam() {
        ProbabilityCalculator calc = (ProbabilityCalculator) app
                .getBean("probabilitycalculator");
        Assert.assertNotNull(calc);

        Email email = new Email("test sender", "test subject", "test body");
        Assert.assertNotNull(email);

        calc.isSpam(email);
    }

}
