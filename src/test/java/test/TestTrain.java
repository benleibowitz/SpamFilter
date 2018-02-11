package test;

import config.DevConfig;
import data.WordEntity;
import data.WordRepository;
import email.Email;
import email.ProbabilityTrainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DevConfig.class})
public class TestTrain {
    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private ProbabilityTrainer probabilityTrainer;

    @Test
    public void testTrain() {
        probabilityTrainer.train(new Email("test@sender.com", "here here is a test subject", "here is a test body"),
                true);
        assertEquals(wordRepository.findById(new WordEntity.WordEntityPrimaryKey("here", Email.Source.SUBJECT)).get().getSpamCount(), 2);
        assertEquals(wordRepository.findById(new WordEntity.WordEntityPrimaryKey("here is", Email.Source.SUBJECT)).get().getSpamCount(), 1);
        assertEquals(wordRepository.findById(new WordEntity.WordEntityPrimaryKey("test", Email.Source.SUBJECT)).get().getSpamCount(), 1);
        assertEquals(wordRepository.findById(new WordEntity.WordEntityPrimaryKey("body", Email.Source.BODY)).get().getSpamCount(), 1);
    }
}
