package data;

import email.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "WORDS")
public class WordEntity {
    @Id
    @Column(name = "WORD")
    private String word;

    @Column(name = "SPAM_COUNT")
    private int spamCount;

    @Column(name = "REAL_COUNT")
    private int realCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "SOURCE")
    private Email.Source source;

    public WordEntity(String word) {
        this.word = word;
    }

    public int incrementRealCount() {
        return ++realCount;
    }
    public int incrementSpamCount() {
        return ++spamCount;
    }
}
