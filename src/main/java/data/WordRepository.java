package data;

import email.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<WordEntity, String> {
    /**
     * Get word for source and word name
     *
     * @param word word
     * @param source {@link Email.Source}
     * @return WordEntity
     */
    WordEntity findByWordAndSource(final String word, final Email.Source source);
    /**
     * Get word for word name
     *
     * @param word word
     * @return WordEntity
     */
    WordEntity findByWord(final String word);
}
