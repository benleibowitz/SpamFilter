package email;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Email {
    public enum Source {
        SENDER,
        SUBJECT,
        BODY
    }

    private final String sender;
    private final String subject;
    private final String body;

    public Email(@NonNull String sender, @NonNull String subject, @NonNull String body) {
        this.body = processText(body);
        this.subject = processText(subject);
        this.sender = processText(sender);
    }

    /*
     * processText(String) takes in a text body and cleans it per regexes below.
     * Punctuation/special chars should be removed, all letters lowercase,
     * periods replcaed with space, and all spaces to one space. example:
     * INPUT:[ foo-bar!..?..$do #run @run..do run_, run? C_L_I_c_K h_E-Re]
     * OUTPUT:[foo bar do run run do run run click here]
     */
    private String processText(String text) {
        // only check the first 10000 characters for the sake of time
        String shortened = text.substring(0, Math.min(5000, text.length()));

        return shortened.replaceAll("-", " ")
                .replaceAll("([.|_|*|^|$|#|@|!|-|+])\\1+", "$1")
                .replaceAll("(?<=[a-z0-9])#(?=[a-z0-9])", "u")
                .replaceAll("(?<=[A-Za-z]{2,})[^\\w^\\s](?=[A-Za-z]{1,})", " ")
                .replaceAll("<br>|[^\\w^\\s]|_", "")
                .replaceAll("[\\s]{2,}", " ")
                .replaceAll("(?<=[a-z]{2,})(?=[A-Z])", " ").toLowerCase()
                .replaceAll("(?<=[a-z0-9])0(?=[a-z0-9])", "o")
                .replaceAll("(?<=[a-z0-9])4(?=[a-z0-9])", "a")
                .replaceAll("(?<=[a-z0-9])3(?=[a-z0-9])", "e")
                .replaceAll("(?<=[a-z0-9])1(?=[a-z0-9])", "i")
                .replaceAll("(?<=[a-z0-9])5(?=[a-z0-9])", "s").trim();
    }
}
