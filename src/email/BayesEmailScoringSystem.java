/*
 * BayesScoringSystem is an object containing probability maps for
 * sender, subject, and message body. Each probability map is in 
 * form:
 * 	Map<String wordOrPhrase, int[]{probSpamMessage, probRealMessage} >
 * WHERE:
 *	probSpamMessage = (# spam messages containing word) / (# total messages containing word)
 *	probRealMessage = (# real messages containing word) / (# total messages containing word)
 *
 */
package email;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import data.Word;
import data.WordDAO;

public class BayesEmailScoringSystem {
    private WordDAO wordDAO;

    // Contains words like "if" "and" "the" "I"
    private List<Word> genericWords;

    public BayesEmailScoringSystem(WordDAO wordDAO) {
        if(wordDAO == null)
            throw new IllegalArgumentException("Word Data Access Object cannot be null");
        
        this.wordDAO = wordDAO;
        initialize();
    }

    private void initialize() {
        genericWords = new ArrayList<>();
        readGenericWords();
        
        for (String fileName : fileMap.keySet()) {
            Map<String, int[]> wordCountMap = fileMap.get(fileName);

            CSVReader csvReader = null;

            try {
                csvReader = new CSVReader(new FileReader(fileName));

                // Read headers
                outputCSVHeaders = Arrays.asList(csvReader.readNext());

                for (String[] line : csvReader.readAll()) {
                    int spamMessages = Integer.valueOf(line[outputCSVHeaders
                            .indexOf("SpamMessages")]);
                    int realMessages = Integer.valueOf(line[outputCSVHeaders
                            .indexOf("RealMessages")]);
                    wordCountMap.put(line[outputCSVHeaders.indexOf("Word")],
                            new int[] { spamMessages, realMessages });
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (csvReader != null) {
                    try {
                        csvReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void readGenericWords() {
        genericWords = wordDAO.getGenericWords();
    }

    public void write() {

        for (String fileName : fileMap.keySet()) {
            // Write new word map to file
            CSVWriter csvWriter = null;

            Map<String, int[]> wordCountMap = fileMap.get(fileName);

            try {
                csvWriter = new CSVWriter(new FileWriter(fileName));

                // Convert outputCSVHeaders list to array and write headers
                csvWriter.writeNext(outputCSVHeaders
                        .toArray(new String[outputCSVHeaders.size()]));

                for (String word : wordCountMap.keySet()) {
                    csvWriter.writeNext(new String[] { word,
                            String.valueOf(wordCountMap.get(word)[0]),
                            String.valueOf(wordCountMap.get(word)[1]) });
                }

            } catch (FileNotFoundException e) {
                System.out.println("Could not find word mapping file: "
                        + fileName);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (csvWriter != null) {
                    try {
                        csvWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public Map<String, int[]> getBodyCountMap() {
        return bodyCountMap;
    }

    public Map<String, int[]> getSenderCountMap() {
        return senderCountMap;
    }

    public Map<String, int[]> getSubjectCountMap() {
        return subjectCountMap;
    }

    public List<String> getGenericWords() {
        return genericWords;
    }

}
