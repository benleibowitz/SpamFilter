package test;

import static org.junit.Assert.*;

import config.DevConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import email.Email;
import email.ProbabilityCalculator;

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
