INSERT INTO generic_words VALUES ('the');
INSERT INTO generic_words VALUES ('and');
INSERT INTO generic_words VALUES ('an');
INSERT INTO generic_words VALUES ('is');

INSERT INTO body (word, spam_count, real_count) VALUES ('foo', 30, 2);
INSERT INTO sender (word, spam_count, real_count) VALUES ('sex', 10, 1);
INSERT INTO subject (word, spam_count, real_count) VALUES ('sex', 10, 1);
INSERT INTO body (word, spam_count, real_count) VALUES ('sex', 10, 1);
INSERT INTO body (word, spam_count, real_count) VALUES ('hey', 8, 5);
INSERT INTO sender (word, spam_count, real_count) VALUES ('buddy', 10, 1);
INSERT INTO subject (word, spam_count, real_count) VALUES ('buddy', 10, 5);
INSERT INTO body (word, spam_count, real_count) VALUES ('buddy', 10, 8);