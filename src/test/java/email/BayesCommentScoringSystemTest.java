package email;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class BayesCommentScoringSystemTest {

    @Test
    public void testNotNullProperties() {
        BayesEmailScoringSystem scoring = new BayesEmailScoringSystem();
        
        Assert.assertNotNull(scoring.getBodyCountMap());
        Assert.assertNotNull(scoring.getSenderCountMap());
        Assert.assertNotNull(scoring.getSubjectCountMap());
        Assert.assertNotNull(scoring.getGenericWords());
    }

}
