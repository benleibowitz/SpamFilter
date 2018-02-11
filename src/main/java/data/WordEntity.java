package data;

import email.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "WORDS")
@IdClass(WordEntity.WordEntityPrimaryKey.class)
public class WordEntity {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class WordEntityPrimaryKey implements Serializable {
        private String word;
        private Email.Source source;
    }

    @Id
    @Column(name = "WORD")
    private String word;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "SOURCE")
    private Email.Source source;

    @Column(name = "SPAM_COUNT")
    private int spamCount;

    @Column(name = "REAL_COUNT")
    private int realCount;

    public int incrementRealCount() {
        return ++realCount;
    }
    public int incrementSpamCount() {
        return ++spamCount;
    }
}
