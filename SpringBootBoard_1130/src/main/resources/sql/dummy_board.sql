-- dummy board data generator

CREATE OR REPLACE PROCEDURE spring_board_dummy_gen_proc
IS
BEGIN

    FOR i IN 1..100 LOOP
        INSERT INTO board VALUES
        (
        	board_seq.nextval,
        	'글쓴이' || i,
        	'123456789',
        	'글쓴이의 글 제목' || i,
        	'글쓴이의 글 내용' || i,
        	'',
        	board_seq.nextval,
        	0,
        	0,
        	0,
        	sysdate
        );
     END LOOP;

    COMMIT;
END;
/

EXECUTE spring_board_dummy_gen_proc;

COMMIT;