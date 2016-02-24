package email;

import data.WordRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
@Setter
public class TestAlgo implements SpamAlgorithm {
    @Autowired
    private WordRepository wordRepository;

    @Override
    public boolean isSpam(Email email) {
//
//        System.out.println("Running test algorithm on email.");
//        System.out.println("_________________________________________");
//        System.out.println("Sender: " + email.getSender());
//        System.out.println("Subject: " + email.getSubject());
//        System.out.println("Body: " + email.getBody());
//        System.out.println("_________________________________________");
//        System.out.println("SUBJECT:");
//
//        for(String wordString : email.getSubject().split(" ")) {
//            System.out.println("Trying word: " + wordString);
//
//            SenderWordEntity word = new SenderWordEntity(wordString);
//
//            if(wordRepository.getGenericWords().contains(word)) {
//                System.out.println("GENERIC WORD");
//            } else {
//                word = wordRepository.getWord(wordString, Email.Source.SUBJECT);
//                System.out.println("Found word: " + word);
//
//                if(word != null)
//                    System.out.println("\tSpam: " + word.getSpamCount() + ", Real: " + word.getRealCount());
//            }
//
//        }
//
//        System.out.println("_________________________________________");
//        System.out.println("BODY:");
//
//        for(String wordString : email.getBody().split(" ")) {
//            System.out.println("Trying word: " + wordString);
//
//            SenderWordEntity word = new SenderWordEntity(wordString);
//
//            if(wordRepository.getGenericWords().contains(word)) {
//                System.out.println("GENERIC WORD");
//            } else {
//                word = wordRepository.getWord(wordString, Email.Source.BODY);
//                System.out.println("Found word: " + word);
//
//                if(word != null)
//                    System.out.println("\tSpam: " + word.getSpamCount() + ", Real: " + word.getRealCount());
//            }
//
//        }
//
        // I call this AssumptionAlgorithm :)
        System.out.println("_________________________________________");
        return true;
    }
}
