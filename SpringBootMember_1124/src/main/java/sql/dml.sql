-- 회원정보 조회
SELECT * FROM member_tbl
WHERE member_id='abcd1234';

-- 회원정보 생성(삽입)
INSERT INTO member_tbl VALUES
('abcd1234',
 '별명',
 '홍길동',
 'm',
 'green1@abcd.com',
 '010-1234-1111',
 '1990-1-1',  
 '12345',  
 '서울 강남구 역삼동*그린',
  SYSDATE);
        
INSERT INTO users VALUES 
('abcd1234', 
 '$2a$10$b8AMk778MLZM5MnB.ApzWu8wH1VVX0NWWxfkSxeRjcgpINrOt/YNm',
  1);
		  
INSERT INTO user_roles VALUES 
(user_roles_seq.nextval,
 'abcd1234',  
 'ROLE_USER'); 

-----------------------------------------

 -- 회원정보 수정
UPDATE users 
SET password='$2a$10$Sq6a3aJdtkVxKSjXYcFgSuf/1OPB2IALLMJUlvKNoSUdChtOPSNMq'
WHERE username='java1234';

-- 회원정보 수정시 이메일 중복 점검
SELECT count(*) FROM 
   (SELECT member_email FROM member_tbl 
	  WHERE member_id != 'javajava') 
WHERE member_email = 'abcd@abcd.com'; 

-- 회원정보 수정시 전화번호 중복 점검
SELECT count(*) FROM 
	(SELECT member_phone FROM member_tbl 
	 WHERE member_id != 'javajava') 
WHERE member_phone = '010-1234-5678';

