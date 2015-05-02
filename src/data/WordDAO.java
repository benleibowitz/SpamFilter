package data;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import email.Email;

public class WordDAO {
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public WordDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public Word getWord(String desiredWord, Email.Source source) {
        String sql = "SELECT word, spam_count, real_count FROM $table WHERE word = ?"
                .replace("$table", String.valueOf(source).toLowerCase());

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

        List<Word> words = jdbcTemplate.query(sql, new GenericWordRowMapper());
        return words;
    }
    
    public void insertOrUpdate(Word word, Email.Source source) {
        if(this.getWord(word.getWord(), source) == null) {
            insert(word, source);
        } else {
            update(word, source);
        }
    }
    
    public void insert(Word word, Email.Source source) {
        String sql = "INSERT INTO $table (word, spam_count, real_count) VALUES (?, ?, ?)"
                .replace("$table", String.valueOf(source).toLowerCase());
        
        jdbcTemplate.update(sql, new Object[]{word.getWord(), word.getSpamCount(), word.getRealCount()});
    }

    public void update(Word word, Email.Source source) {
        String sql = "UPDATE $table SET spam_count=?, real_count=? WHERE word = ?"
                .replace("$table", String.valueOf(source).toLowerCase());
        
        jdbcTemplate.update(sql, new Object[]{word.getSpamCount(), word.getRealCount(), word.getWord()});
    }
    
}
