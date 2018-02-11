package test;

import config.DevConfig;
import email.Email;
import email.ProbabilityCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DevConfig.class})
public class TestEmail {
    @Autowired
    private ProbabilityCalculator probCalc;

    @Test
    public void testProbCalc() {
        Email email = new Email("Jeremy M.", "Here is the job opportunity you requested", "foo bar");
        assertTrue(probCalc.isSpam(email));
    }
}
