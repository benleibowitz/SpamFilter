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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

@Component
public class BayesEmailScoringSystem {
	private static final String BASE_URL = "src/main/resources/";
	
	//Maybe read these from a database instead...
	private static final String BODYMAP_FILE = BASE_URL + "bodyMap.csv";
	private static final String SUBJECTMAP_FILE = BASE_URL + "subjectMap.csv";
	private static final String SENDERMAP_FILE = BASE_URL + "senderMap.csv";
	private static final String GENERICWORD_FILE = BASE_URL + "genericWords.csv";
	
	//All words in map are lowercase.
	//Probability map contains: <word, { COUNT(spam messages containing word), COUNT(real messages containing word) }>
	private Map<String, int[]> bodyCountMap;
	private Map<String, int[]> subjectCountMap;
	private Map<String, int[]> senderCountMap;
	
	//Contains <MappingFileURLString, respectiveCountMap>
	private Map<String, Map<String, int[]>> fileMap;
	
	//Contains words like "if" "and" "the" "I"
	private List<String> genericWords;
	
	private List<String> outputCSVHeaders;
	
	public BayesEmailScoringSystem() {
		initialize();
	}
	
	private void initialize() {
		genericWords = new ArrayList<>();
		readGenericWords();
		
		fileMap = new HashMap<>();
    	
		bodyCountMap = new HashMap<>();
		senderCountMap = new HashMap<>();
		subjectCountMap = new HashMap<>();
		
		fileMap.put(BODYMAP_FILE, bodyCountMap);
		fileMap.put(SENDERMAP_FILE, senderCountMap);
		fileMap.put(SUBJECTMAP_FILE, subjectCountMap);
		
		for(String fileName : fileMap.keySet()) {
			Map<String, int[]> wordCountMap = fileMap.get(fileName);
			
			CSVReader csvReader = null;
			
			try {
				csvReader = new CSVReader(new FileReader(fileName));
				
				//Read headers
				outputCSVHeaders = Arrays.asList(csvReader.readNext());
				
				for(String[] line : csvReader.readAll()) {
					int spamMessages = Integer.valueOf(line[outputCSVHeaders.indexOf("SpamMessages")]);
					int realMessages = Integer.valueOf(line[outputCSVHeaders.indexOf("RealMessages")]);
					wordCountMap.put(line[outputCSVHeaders.indexOf("Word")], new int[]{spamMessages, realMessages});
				}
				
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				if(csvReader != null) {
					try {
						csvReader.close();
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void readGenericWords() {
		CSVReader csvReader = null;
		
		try {
			csvReader = new CSVReader(new FileReader(GENERICWORD_FILE));
			
			//Read headers
			List<String> headers = Arrays.asList(csvReader.readNext());
			
			String[] line;
			while((line = csvReader.readNext()) != null) {
			    genericWords.add(line[headers.indexOf("Word")]);
			}
			
		} catch(FileNotFoundException e) {
			System.out.println("Could not find generic words file: " + GENERICWORD_FILE);
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(csvReader != null) {
				try {
					csvReader.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void write() {
	    
		for(String fileName : fileMap.keySet()) {
			//Write new word map to file
			CSVWriter csvWriter = null;
			
			Map<String, int[]> wordCountMap = fileMap.get(fileName);
                    
			try {
				csvWriter = new CSVWriter(new FileWriter(fileName));
                
				//Convert outputCSVHeaders list to array and write headers
				csvWriter.writeNext(outputCSVHeaders.toArray(new String[outputCSVHeaders.size()]));
                
				for(String word : wordCountMap.keySet()) {
					csvWriter.writeNext(new String[]{word, String.valueOf(wordCountMap.get(word)[0]),
					        String.valueOf(wordCountMap.get(word)[1])});
				}
                
			} catch(FileNotFoundException e) {
	            System.out.println("Could not find word mapping file: " + fileName);
	            e.printStackTrace();
	        } catch(IOException e) {
	            e.printStackTrace();
	        } finally {
				if(csvWriter != null) {
					try {
						csvWriter.close();
					} catch(IOException e) {
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
