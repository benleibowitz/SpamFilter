package data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class WordRowMapper implements RowMapper<Word> {

    @Override
    public Word mapRow(ResultSet rs, int rowNum) throws SQLException {
        Word word = new Word();

        word.setSpamCount(rs.getInt("spam_count"));
        word.setRealCount(rs.getInt("real_count"));
        word.setWord(rs.getString("word"));

        return word;
    }

}
