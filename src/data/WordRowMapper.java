package data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class WordRowMapper implements RowMapper<Word> {

    @Override
    public Word mapRow(ResultSet rs, int rowNum) throws SQLException {
        Word word = null
        ResultSetMetaData rsMetaData = rs.getMetaData();

        if(rsMetaData.getColumnCount > 0) {
            
            if(rsMetaData.getTableName().equals("generic_words")) {
                word = new GenericWord(rs.getString("word"));
            } else {
                word = new Word(rs.getString("word"));
                word.setSpamCount(rs.getInt("spam_count"));
                word.setRealCount(rs.getInt("real_count"));
            }
        }

        return word;
    }

}
