CREATE TABLE blog(
    blog_id int auto_increment primary key,
    writer varchar(16) not null,
    blog_title varchar(200) not null,
    blog_content varchar(4000) not null,
    published_at datetime default now(),
    updated_at datetime default now(),
	blog_count int default 0
);

INSERT INTO blog VALUES
    (null, '1번유저', '1번제목', '1번본문', now(), now(), null),
    (null, '2번유저', '2번제목', '2번본문', now(), now(), null),
    (null, '3번유저', '3번제목', '3번본문', now(), now(), null);

    # 댓글 테이블 설정
    CREATE TABLE reply (
    	reply_id int primary key auto_increment,
        blog_id int not null,
        reply_writer varchar(40) not null,
        reply_content varchar(200) not null,
        published_at datetime default now(),
        updated_at datetime default now()
    );
    # 외래키 설정 (blog_id에는 기존에 존재하는 blog_id만 들어가야 한다.)
    alter table reply add constraint fk_reply foreign key (blog_id) references blog(blog_id);

    # 더미데이터 입력
    INSERT INTO reply VALUES(null, 2, "댓글작성자", "1빠", now(), now()),
    (null, 2, "개소리하면짖는멍멍이", "멍멍", now(), now()),
    (null, 2, "나무", "나무처럼살자", now(), now()),
    (null, 2, "햄지가짱", "햄스터키우고싶다", now(), now()),
    (null, 3, "치즈", "퇴근하고싶어요", now(), now());
