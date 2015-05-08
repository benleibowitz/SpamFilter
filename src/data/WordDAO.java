package data;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import email.Email;

@PropertySource("classpath:resources/application.properties")
public class WordDAO {
    //@Value("${database.table.sender}")
    private String senderTableName = "sender";
    
    //@Value("${database.table.subject}")
    private String subjectTableName = "subject";
    
    //@Value("${database.table.body}")
    private String bodyTableName = "body";
    
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public WordDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private String getTableNameForSource(Email.Source source) {
        String tableName = null;
        
        switch(source) {
            case SUBJECT:
                tableName = subjectTableName;
                break;
            case SENDER:
                tableName = senderTableName;
                break;
            case BODY:
                tableName = bodyTableName;
                break;
        }
        
        return tableName;
    }
    
    //Gets word information from sql table, where sql table comes in as enum in
    //either BODY, SUBJECT, SENDER
    public Word getWord(String desiredWord, Email.Source source) {
        String sql = "SELECT word, spam_count, real_count FROM $table WHERE word = ?"
                .replace("$table", getTableNameForSource(source));

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
    
    //Gets word information from sql table, where sql table comes in as enum in
    //either BODY, SUBJECT, SENDER
    public void insert(Word word, Email.Source source) {
        String sql = "INSERT INTO $table (word, spam_count, real_count) VALUES (?, ?, ?)"
                .replace("$table", getTableNameForSource(source));
        
        jdbcTemplate.update(sql, new Object[]{word.getWord(), word.getSpamCount(), word.getRealCount()});
    }

    //Gets word information from sql table, where sql table comes in as enum in
    //either BODY, SUBJECT, SENDER
    public void update(Word word, Email.Source source) {
        String sql = "UPDATE $table SET spam_count=?, real_count=? WHERE word = ?"
                .replace("$table", getTableNameForSource(source));
        
        jdbcTemplate.update(sql, new Object[]{word.getSpamCount(), word.getRealCount(), word.getWord()});
    }
    
}
