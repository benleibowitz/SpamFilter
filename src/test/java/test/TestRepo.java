package test;


import config.DevConfig;
import data.WordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DevConfig.class})
public class TestRepo {
    @Autowired
    private WordRepository wordRepository;

    @Test
    public void testWordsExist() {
        assertTrue(wordRepository.findAll().size() > 0);
    }
}
