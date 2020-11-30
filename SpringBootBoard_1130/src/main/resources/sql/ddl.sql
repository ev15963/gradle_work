CREATE SEQUENCE board_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;

CREATE TABLE board (
    board_num NUMBER DEFAULT 0,
    board_name VARCHAR2(30) NOT NULL,
    board_pass VARCHAR2(20) NOT NULL,
    board_subject VARCHAR2(100) NOT NULL,
    board_content CLOB NOT NULL,
    board_file VARCHAR2(100),
    board_re_ref NUMBER NOT NULL,
    board_re_lev NUMBER NOT NULL,
    board_re_seq NUMBER NOT NULL,
    board_readcount NUMBER DEFAULT 0,
    board_date DATE,
    PRIMARY KEY(board_num)
);

drop table board;