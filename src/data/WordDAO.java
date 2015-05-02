package data;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import email.Email;

public class WordDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public WordDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public Word getWord(String desiredWord, Email.Source source) {
        String sql = String.format("SELECT word, spam_count, real_count FROM %s"
                + " WHERE word = ?", String.valueOf(source).toLowerCase());
        
        if(jdbcTemplate == null)
            jdbcTemplate = new JdbcTemplate(dataSource);
        
        Word word;
        
        try {
            word = jdbcTemplate.queryForObject(sql, new Object[]{desiredWord}, new WordRowMapper());
        } catch(org.springframework.dao.EmptyResultDataAccessException e) {
            word = null;
        }
        
        return word;
    }
    
    public List<Word> getGenericWords() {
        String sql = "SELECT word FROM generic_words";

        if(jdbcTemplate == null) 
            jdbcTemplate = new JdbcTemplate(dataSource);

        List<Word> words = jdbcTemplate.query(sql, new GenericWordRowMapper());
        return words;
    }
    
    public void insertWord(Word word, Email.Source source) {
        if(this.getWord(word.getWord(), source) == null) {
            
        }
    }
    
    private void insert(Word word, Email.Source source) {
        String sql = String.format("INSERT INTO %s (word, spam_count, real_count) VALUES "
                + "(?, ?, ?)", String.valueOf(source).toLowerCase());

        if(jdbcTemplate == null) 
            jdbcTemplate = new JdbcTemplate(dataSource);
        
        jdbcTemplate.update(sql, new Object[]{word.getWord(), word.getSpamCount(), word.getRealCount()});
    }
    
}
