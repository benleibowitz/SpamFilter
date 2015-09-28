package test;

import static org.junit.Assert.*;

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
@ActiveProfiles(profiles="dev")
@ContextConfiguration("classpath:emailbeans.xml")
public class TestEmail {
    
    @Autowired
    private ProbabilityCalculator probCalc;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNotNull() {
        assertNotNull(probCalc);
    }
    
    @Test
    public void testProbCalc() {
        Email e1 = new Email("Frank McJohnson", "Here is the job opportunity you requested",
                "untz untz untz");
        Email e2 = new Email("Joe Masterson", "",
                "Hey Frank, what time is our business meeting to discuss biznis today?");
        assertTrue(probCalc.isSpam(e1));
        assertFalse(probCalc.isSpam(e2));
    }

}
