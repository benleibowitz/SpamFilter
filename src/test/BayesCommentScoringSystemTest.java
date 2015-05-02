package test;

import org.junit.Assert;
import org.junit.Test;

import email.BayesEmailScoringSystem;

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
