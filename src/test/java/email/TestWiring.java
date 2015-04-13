package email;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpamFilterConfig.class)
public class TestWiring {

    @Autowired
    private ProbabilityCalculator p;
    
    @Test
    public void test() {
        Assert.assertNotNull(p);
    }
    
    @Test
    public void testIsSpam() {
        Email e = new Email("My sender", "my subject", "my body");
        System.out.println("Spam: " + p.isSpam(e));
    }

}
