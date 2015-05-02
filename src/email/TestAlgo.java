package email;

import org.springframework.beans.factory.annotation.Autowired;

import data.Word;
import data.WordDAO;

public class TestAlgo implements SpamAlgorithm {
    private WordDAO wordDAO;
    
    @Autowired
    public TestAlgo(WordDAO wordDAO) {
        this.wordDAO = wordDAO;
    }

    public TestAlgo() {

    }

    @Override
    public boolean isSpam(Email email) {

        System.out.println("Running test algorithm on email.");
        System.out.println("Sender: " + email.getSender());
        System.out.println("Subject: " + email.getSubject());
        System.out.println("Body: " + email.getBody());
        
        for(String wordString : email.getBody().split(" ")) {
            System.out.println("Trying word: " + wordString);

            Word word = new Word();word.setWord(wordString);
            if(wordDAO.getGenericWords().contains(word)) {
                System.out.println("GENERIC WORD");
            } else {
                word = wordDAO.getWord(wordString, Email.Source.BODY);
                System.out.println("Found word: " + word);
                if(word != null)
                    System.out.println("\tSpam: " + word.getSpamCount() + ", Real: " + word.getRealCount());
            }
            
        }
        
        // I call this AssumptionAlgorithm :)
        return true;
    }
}
