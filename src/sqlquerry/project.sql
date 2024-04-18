drop database if exists phoenix;
create database phoenix;

use phoenix;

create table grade( -- 직위
   gradeno int not null ,
    gradename varchar(10),

    primary key(gradeno)
);

insert into grade values(0,'전체');
insert into grade values(1,'사원');
insert into grade values(2,'대리');
insert into grade values(3,'팀장');
insert into grade values(4,'부장');
insert into grade values(5,'임원');


create table part( -- 부서
   partno int not null,
    partname varchar(30) unique,

    primary key(partno)
);
insert into part values(0,'미지정');
insert into part values(1,'인사');
insert into part values(2,'회계');
insert into part values(3,'개발');

create table employee( -- 사원
   eno int auto_increment,
    gradeno int not null default 0,
   ename varchar(30) not null,
    ephone varchar(15) not null unique,
   eemail varchar(50) not null unique,
    eid varchar(30) not null unique,
    epw varchar(30) not null,
    edate datetime default now(),
    partno int default 0,

    primary key(eno),
    foreign key(gradeno) references grade(gradeno),
    foreign key(partno) references part(partno) on update cascade on delete set null
);



insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(1,'일사원','010-0000-0000','a@co.kr','a','a',1);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(2,'일대리','010-0000-0001','b@co.kr','b','b',1);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(3,'일팀장','010-0000-0002','c@co.kr','c','c',1);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(4,'일부장','010-0000-0003','d@co.kr','d','d',1);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(5,'일임원','010-0000-0004','e@co.kr','e','e',1);

insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(1,'이사원','010-0000-0005','f@co.kr','f','f',2);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(2,'이대리','010-0000-0006','g@co.kr','g','g',2);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(3,'이팀장','010-0000-0007','h@co.kr','h','h',2);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(4,'이부장','010-0000-0008','i@co.kr','i','i',2);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(5,'이임원','010-0000-0009','j@co.kr','j','j',2);

insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(1,'삼사원','010-0000-0010','k@co.kr','k','k',3);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(2,'삼대리','010-0000-0011','l@co.kr','l','l',3);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(3,'삼팀장','010-0000-0012','m@co.kr','m','m',3);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(4,'삼부장','010-0000-0013','n@co.kr','n','n',3);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(5,'삼임원','010-0000-0014','o@co.kr','o','o',3);
insert into employee(gradeno,ename,ephone,eemail,eid,epw,partno) values(5,'admin','010-0000-0015','admin@co.kr','admin','admin',1);
/*
직급 샘플 데이터       마스터 : 임원 // 부장 팀장 대리 사원
*/

/*
부서 샘플 데이터       1회계 2인사 3개발
*/



-- insert into board(boardtitle,boardcontent,eno) values('1번 게시글','게시글 내용',1);
-- insert into board(boardtitle,boardcontent,eno) values('2번 게시글','게시글 내용',2);
-- insert into board(boardtitle,boardcontent,eno) values('3번 게시글','게시글 내용',3);
-- insert into board(boardtitle,boardcontent,eno) values('4번 게시글','게시글 내용',4);
-- insert into board(boardtitle,boardcontent,eno) values('5번 게시글','게시글 내용',5);
-- insert into board(boardtitle,boardcontent,eno) values('6번 게시글','게시글 내용',6);
-- insert into board(boardtitle,boardcontent,eno) values('7번 게시글','게시글 내용',7);
-- insert into board(boardtitle,boardcontent,eno) values('8번 게시글','게시글 내용',8);
-- insert into board(boardtitle,boardcontent,eno) values('9번 게시글','게시글 내용',9);
-- insert into board(boardtitle,boardcontent,eno) values('10번 게시글','게시글 내용',10);
-- insert into board(boardtitle,boardcontent,eno) values('11번 게시글','게시글 내용',11);
-- insert into board(boardtitle,boardcontent,eno) values('12번 게시글','게시글 내용',12);
-- insert into board(boardtitle,boardcontent,eno) values('13번 게시글','게시글 내용',13);
-- insert into board(boardtitle,boardcontent,eno) values('14번 게시글','게시글 내용',14);
-- insert into board(boardtitle,boardcontent,eno) values('15번 게시글','게시글 내용',15);

create table boardcategory(
   bcno int auto_increment,
    bcname text not null,
    constraint
    primary key(bcno)
);

create table board(
   boardno int auto_increment,
    boardtitle varchar(30) not null,
    boardcontent longtext not null,
    eno int,
    boarddate datetime default now(),
   bcno int,
    bcno2 int,

    primary key(boardno),
    foreign key(eno) references employee(eno) on delete set null,
    foreign key(bcno) references boardcategory(bcno)
);
-- insert into boardpermit(boardno,gradeno,partno) values(1,1,1);
-- insert into boardpermit(boardno,gradeno,partno) values(2,2,1);
-- insert into boardpermit(boardno,gradeno,partno) values(3,3,1);
-- insert into boardpermit(boardno,gradeno,partno) values(4,4,1);
-- insert into boardpermit(boardno,gradeno,partno) values(5,5,1);

-- insert into boardpermit(boardno,gradeno,partno) values(6,1,2);
-- insert into boardpermit(boardno,gradeno,partno) values(7,2,2);
-- insert into boardpermit(boardno,gradeno,partno) values(8,3,2);
-- insert into boardpermit(boardno,gradeno,partno) values(9,4,2);
-- insert into boardpermit(boardno,gradeno,partno) values(10,5,2);

-- insert into boardpermit(boardno,gradeno,partno) values(11,1,3);
-- insert into boardpermit(boardno,gradeno,partno) values(12,2,3);
-- insert into boardpermit(boardno,gradeno,partno) values(13,3,3);
-- insert into boardpermit(boardno,gradeno,partno) values(14,4,3);
-- insert into boardpermit(boardno,gradeno,partno) values(15,5,3);

-- gradno 별 3개, partno 별 5개

create table reply(
   eno int,
    boardno int not null,
    replyno int auto_increment,
    replycontent text not null,
    replydate datetime default now(),

    primary key (replyno),
    foreign key (eno) references employee(eno) on delete set null,
    foreign key (boardno) references board(boardno) on delete cascade
);


-- insert into reply(eno,boardno,replycontent) values(2,1,"댓글내용1");
-- insert into reply(eno,boardno,replycontent) values(3,1,"댓글내용2");
-- insert into reply(eno,boardno,replycontent) values(2,2,"댓글내용3");

create table reportcategory(
   cno int not null unique,
    cname text
);

insert into reportcategory values(1,"workreport");
insert into reportcategory values(2,"vacationreport");
insert into reportcategory values(3,"purchasereport");

create table report(
   eno int,
   reportno int auto_increment,
   reporttitle varchar(50) not null,
   reportcontent text,
    reportdate datetime default now(),
    cno int,

    primary key(reportno),
    foreign key(eno) references employee(eno) on delete set null,
    foreign key(cno) references reportcategory(cno) on delete cascade
);


create table workreport(
   reportno int auto_increment,
   content2 text,
    foreign key(reportno) references report(reportno) on delete cascade
);

create table vacationreport(
   reportno int auto_increment,
    startdate datetime,
    enddate datetime,
    foreign key(reportno) references report(reportno) on delete cascade
);

create table purchasereport(
   reportno int auto_increment,
    itemlist text,
    totalprice int,
    foreign key(reportno) references report(reportno) on delete cascade
);

create table reportlog(
   reportno int not null,
    eno int,
    confirm bool default false,

    foreign key(reportno) references report(reportno) on delete cascade,
    foreign key(eno) references employee(eno) on delete set null
);

create table mail(
   mailno int auto_increment,
    eno int,
    mailtitle text not null,
    mailcontetnt text not null,
    maildate datetime default now(),
    constraint
    primary key(mailno),
   foreign key(eno) references employee(eno) on delete cascade
);


create table maillog(
   mailno int,
    eno int,
    mailstate int default 0,
    constraint
    foreign key(eno) references employee(eno) on delete cascade,
    foreign key(mailno) references mail(mailno) on delete cascade
);

insert into mail(eno, mailtitle, mailcontetnt) values (1, '1번 메일', '1번 메일내용');
insert into maillog(mailno, eno) values (1, 2);
insert into maillog(mailno, eno) values (1, 3);

insert into mail(eno, mailtitle, mailcontetnt) values (2, '2번 메일', '2번 메일내용');
insert into maillog(mailno, eno) values (2, 3);
insert into maillog(mailno, eno) values (2, 4);

insert into mail(eno, mailtitle, mailcontetnt) values (3, '3번 메일', '3번 메일내용');
insert into maillog(mailno, eno) values (3, 4);
insert into maillog(mailno, eno) values (3, 5);

insert into mail(eno, mailtitle, mailcontetnt) values (4, '4번 메일', '4번 메일내용');
insert into maillog(mailno, eno) values (4, 5);
insert into maillog(mailno, eno) values (4, 6);

insert into mail(eno, mailtitle, mailcontetnt) values (5, '5번 메일', '5번 메일내용');
insert into maillog(mailno, eno) values (5, 6);
insert into maillog(mailno, eno) values (5, 7);

insert into mail(eno, mailtitle, mailcontetnt) values (6, '6번 메일', '6번 메일내용');
insert into maillog(mailno, eno) values (6, 7);
insert into maillog(mailno, eno) values (6, 8);

insert into mail(eno, mailtitle, mailcontetnt) values (7, '7번 메일', '7번 메일내용');
insert into maillog(mailno, eno) values (7, 8);
insert into maillog(mailno, eno) values (7, 9);

insert into mail(eno, mailtitle, mailcontetnt) values (8, '8번 메일', '8번 메일내용');
insert into maillog(mailno, eno) values (8, 9);
insert into maillog(mailno, eno) values (8, 10);

insert into mail(eno, mailtitle, mailcontetnt) values (9, '9번 메일', '9번 메일내용');
insert into maillog(mailno, eno) values (9, 10);
insert into maillog(mailno, eno) values (9, 11);

insert into mail(eno, mailtitle, mailcontetnt) values (10, '10번 메일', '10번 메일내용');
insert into maillog(mailno, eno) values (10, 11);
insert into maillog(mailno, eno) values (10, 12);

insert into mail(eno, mailtitle, mailcontetnt) values (11, '11번 메일', '11번 메일내용');
insert into maillog(mailno, eno) values (11, 12);
insert into maillog(mailno, eno) values (11, 13);

insert into mail(eno, mailtitle, mailcontetnt) values (12, '12번 메일', '12번 메일내용');
insert into maillog(mailno, eno) values (12, 13);
insert into maillog(mailno, eno) values (12, 14);

insert into mail(eno, mailtitle, mailcontetnt) values (13, '13번 메일', '13번 메일내용');
insert into maillog(mailno, eno) values (13, 15);
insert into maillog(mailno, eno) values (13, 1);

insert into mail(eno, mailtitle, mailcontetnt) values (14, '14번 메일', '14번 메일내용');
insert into maillog(mailno, eno) values (14, 1);
insert into maillog(mailno, eno) values (14, 2);

insert into mail(eno, mailtitle, mailcontetnt) values (15, '15번 메일', '15번 메일내용');
insert into maillog(mailno, eno) values (15, 2);
insert into maillog(mailno, eno) values (15, 3);

select * from employee;
select * from board;
select * from boardcategory;
select * from reply;

select * from report;
select * from reportlog;

-- 심재훈
SELECT  report.reportno ,COUNT(*)FROM report JOIN reportlog ON report.reportno = reportlog.reportno GROUP BY report.reportno;
SELECT  report.reportno ,reportlog.confirm FROM report JOIN reportlog ON report.reportno = reportlog.reportno where report.eno = 1 GROUP BY report.reportno, reportlog.confirm;
SELECT report.*,reportlog.eno FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE reportlog.eno = 1;
SELECT report.*,reportlog.confirm FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE reportlog.eno = 4;

SELECT  report.* ,reportlog.confirm FROM report JOIN reportlog ON report.reportno = reportlog.reportno;

SELECT  report.* ,reportlog.confirm FROM report JOIN reportlog ON report.reportno = reportlog.reportno where reportlog.eno = 2;



SElect report.reportno,Count(*) as count FROM report JOIN reportlog ON report.reportno = reportlog.reportno group by report.reportno;
SELECT report.reportno,Count(*) FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE reportlog.confirm =true group by report.reportno, reportlog.confirm;

select * from report where reportno = 5;

select * from reportcategory;
select * from report;
select * from purchasereport;
select * from vacationreport;
select * from workreport;

select cname from reportcategory where cno = 1;

select * from report r1 left join purchasereport r2 on r1.reportno = r2.reportno left join vacationreport r3 on r1.reportno = r3.reportno left join workreport r4 on r1.reportno = r4.reportno;
select * from reportlog;

SELECT report.*, r1.log_count, r2.log_count_confirm
FROM report
LEFT JOIN (SELECT report.reportno, COUNT(*) as log_count
         FROM report JOIN reportlog ON report.reportno = reportlog.reportno GROUP BY report.reportno) r1 ON report.reportno = r1.reportno
LEFT JOIN ( SELECT report.reportno, COUNT(*) AS log_count_confirm FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE reportlog.confirm = true GROUP BY report.reportno, reportlog.confirm) r2
ON report.reportno = r2.reportno;

insert into boardcategory(bcname) values('공지');
insert into boardcategory(bcname) values('전체');
insert into boardcategory(bcname) values('부서');
insert into boardcategory(bcname) values('직급');

insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('1번 공지글','게시글 내용',5,1,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('2번 공지글','게시글 내용',10,1,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('3번 공지글','게시글 내용',15,1,0);

insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('1번 전체 게시글','게시글 내용',1,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('2번 전체 게시글','게시글 내용',2,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('3번 전체 게시글','게시글 내용',3,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('4번 전체 게시글','게시글 내용',4,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('5번 전체 게시글','게시글 내용',5,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('6번 전체 게시글','게시글 내용',6,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('7번 전체 게시글','게시글 내용',7,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('8번 전체 게시글','게시글 내용',8,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('9번 전체 게시글','게시글 내용',9,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('10번 전체 게시글','게시글 내용',10,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('11번 전체 게시글','게시글 내용',11,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('12번 전체 게시글','게시글 내용',12,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('13번 전체 게시글','게시글 내용',13,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('14번 전체 게시글','게시글 내용',14,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('15번 전체 게시글','게시글 내용',15,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('16번 전체 게시글','게시글 내용',1,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('17번 전체 게시글','게시글 내용',6,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('18번 전체 게시글','게시글 내용',11,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('19번 전체 게시글','게시글 내용',2,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('20번 전체 게시글','게시글 내용',7,2,0);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('21번 전체 게시글','게시글 내용',12,2,0);

insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('1번 부서 게시글','게시글 내용',1,3,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('2번 부서 게시글','게시글 내용',2,3,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('3번 부서 게시글','게시글 내용',3,3,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('4번 부서 게시글','게시글 내용',4,3,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('5번 부서 게시글','게시글 내용',5,3,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('6번 부서 게시글','게시글 내용',6,3,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('7번 부서 게시글','게시글 내용',7,3,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('8번 부서 게시글','게시글 내용',8,3,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('9번 부서 게시글','게시글 내용',9,3,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('10번 부서 게시글','게시글 내용',10,3,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('11번 부서 게시글','게시글 내용',11,3,3);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('12번 부서 게시글','게시글 내용',12,3,3);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('13번 부서 게시글','게시글 내용',13,3,3);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('14번 부서 게시글','게시글 내용',14,3,3);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('15번 부서 게시글','게시글 내용',15,3,3);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('16번 부서 게시글','게시글 내용',1,3,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('17번 부서 게시글','게시글 내용',6,3,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('18번 부서 게시글','게시글 내용',11,3,3);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('19번 부서 게시글','게시글 내용',2,3,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('20번 부서 게시글','게시글 내용',7,3,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('21번 부서 게시글','게시글 내용',12,3,3);

insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('1번 직급 게시글','게시글 내용',1,4,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('2번 직급 게시글','게시글 내용',2,4,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('3번 직급 게시글','게시글 내용',3,4,3);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('4번 직급 게시글','게시글 내용',4,4,4);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('5번 직급 게시글','게시글 내용',5,4,5);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('6번 직급 게시글','게시글 내용',6,4,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('7번 직급 게시글','게시글 내용',7,4,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('8번 직급 게시글','게시글 내용',8,4,3);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('9번 직급 게시글','게시글 내용',9,4,4);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('10번 직급 게시글','게시글 내용',10,4,5);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('11번 직급 게시글','게시글 내용',11,4,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('12번 직급 게시글','게시글 내용',12,4,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('13번 직급 게시글','게시글 내용',13,4,3);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('14번 직급 게시글','게시글 내용',14,4,4);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('15번 직급 게시글','게시글 내용',15,4,5);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('16번 직급 게시글','게시글 내용',1,4,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('17번 직급 게시글','게시글 내용',6,4,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('18번 직급 게시글','게시글 내용',11,4,1);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('19번 직급 게시글','게시글 내용',2,4,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('20번 직급 게시글','게시글 내용',7,4,2);
insert into board(boardtitle,boardcontent,eno,bcno,bcno2) values('21번 직급 게시글','게시글 내용',12,4,2);

select * from report;
select * from mail;
select * from maillog;
select * from employee;
select * from maillog join employee on maillog.eno = employee.eno where mailno = 13;
select * from maillog where mailno = 1;
--
select *from maillog join mail on maillog.mailno = mail.mailno where maillog.eno = 1 order by mail.mailno;
select *from maillog join mail on mail.eno = maillog.eno where mail.eno = 1;




DELIMITER //

CREATE EVENT IF NOT EXISTS update_report_confirm
ON SCHEDULE EVERY 1 DAY
DO
BEGIN
  UPDATE reportlog
  SET confirm = false
  WHERE reportno IN (
    SELECT report.reportno
    FROM report
    LEFT JOIN (
      SELECT report.reportno, COUNT(*) FROM report JOIN reportlog ON report.reportno = reportlog.reportno GROUP BY report.reportno
    ) r1 ON report.reportno = r1.reportno
    LEFT JOIN (
      SELECT report.reportno, COUNT(*) FROM report JOIN reportlog ON report.reportno = reportlog.reportno WHERE reportlog.confirm = true GROUP BY report.reportno, reportlog.confirm
    ) r2 ON report.reportno = r2.reportno
    WHERE report.reportdate < NOW() - INTERVAL 7 DAY AND COALESCE(r1.count, 0) != COALESCE(r2.count, 0)
  );
END//

DELIMITER ;

show events;