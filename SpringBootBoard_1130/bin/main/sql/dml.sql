INSERT INTO board
 (BOARD_NUM,
  BOARD_NAME,
  BOARD_PASS,
  BOARD_SUBJECT,
  BOARD_CONTENT,
  BOARD_FILE,
  BOARD_RE_REF,
  BOARD_RE_LEV,
  BOARD_RE_SEQ,
  BOARD_READCOUNT,
  BOARD_DATE)
VALUES
(board_seq.nextval,
 '작성자',
 '12345678',
 '제목',
 '내용',
 '',
 0,
 0,
 0,
 0,
 to_char(sysdate));

--------------------------------------------------

SELECT * FROM (
            SELECT
                   m.*,
                   FLOOR((ROWNUM - 1)/10 + 1) page
            FROM (
                     SELECT * FROM board
            		 ORDER BY BOARD_RE_REF DESC, BOARD_RE_SEQ ASC
                 ) m
          )
WHERE page = 1;

----------------------------------------------------

SELECT * FROM (
            SELECT
                   m.*,
                   FLOOR((ROWNUM - 1)/10 + 1) page
            FROM (
                     SELECT * FROM board
	   				 WHERE board_content like '%' || '포트폴리오를' || '%'
                     ORDER BY BOARD_RE_REF DESC, BOARD_RE_SEQ ASC
              	 ) m
)
WHERE page = 1;