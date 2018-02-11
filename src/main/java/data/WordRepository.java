package data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<WordEntity, WordEntity.WordEntityPrimaryKey> {
    /**
     * Get word for word name
     *
     * @param word word
     * @return WordEntity
     */
    WordEntity findByWord(final String word);
}
