CREATE TABLE Users(
login char(30),
name varchar(40),
userType boolean, 
password varchar(100),
address varchar(50),
phone_num varchar(20),
PRIMARY KEY (login));

INSERT INTO Users
(login, name, userType, password, address, phone_num)
VALUES
('hello', 'helloworld', 'programmer', 'helloworld8', '222 south main', '(801)555-8460');

-----------------------------------------------------------------------------------------------
CREATE TABLE POI(
pid INTEGER,
name VARCHAR(50), 
category VARCHAR(20), 
PRIMARY KEY (pid));

INSERT INTO POI
(pid, name, category)
VALUES
(200, 'Jack Mormon', 'coffee shop');

INSERT INTO POI
(name, category)
VALUES
('Toshs Ramen', 'Restaurant');

-----------------------------------------------------------------------------------------------

CREATE TABLE VisEvent(
vid INTEGER,
cost INTEGER, 
numberofheads INTEGER,
PRIMARY KEY(vid));

INSERT INTO VisEvent
(vid, cost, numberofheads)
VALUES
(300, 20, 2);

-----------------------------------------------------------------------------------------------

CREATE TABLE Visit(
login char(30),
pid INTEGER,
vid INTEGER,
visitdate DATE,
PRIMARY KEY (login, pid, vid),
FOREIGN KEY (login) REFERENCES Users (login),
FOREIGN KEY (pid) REFERENCES POI (pid),
FOREIGN KEY (vid) REFERENCES VisEvent (vid));


INSERT INTO Visit
(login, pid, vid, visitdate)
VALUES
('chuy8jay', 200, 300, to_date(03142016));

ALTER TABLE VisEvent MODIFY COLUMN vid INT auto_increment;
-----------------------------------------------------------------------------------------------

CREATE TABLE Trust(
login1 char(30),
login2 char(30),
isTrusted boolean,
PRIMARY KEY (login1, login2),
FOREIGN KEY (login1) REFERENCES Users (login),
FOREIGN KEY (login2) REFERENCES Users (login));

INSERT INTO Trust
(login1, login2, isTrusted)
VALUES
('chuy8jay', 'jay8chuy', 1);

-----------------------------------------------------------------------------------------------

CREATE TABLE Rates(
login CHAR(30),
fid INTEGER,
rating CHAR(20),
PRIMARY KEY (login,fid),
FOREIGN KEY (login) REFERENCES Users (login),
FOREIGN KEY (fid) REFERENCES Feedback (fid));

INSERT INTO Rates
(login, fid, rating)
VALUES
('chuy8jay', 400, 'Very Good');

-----------------------------------------------------------------------------------------------

CREATE TABLE Feedback(
fid INTEGER,
pid INTEGER NOT NULL,
login char(30) NOT NULL,
text char(100),
fbdate date,
score INTEGER,
PRIMARY KEY (fid),
FOREIGN KEY (pid) REFERENCES POI (pid),
FOREIGN KEY (login) REFERENCES Users (login));


INSERT INTO Feedback
(fid, pid, login, text, fbdate, score)
VALUES
(400, 200, 'chuy8jay', 'Best coffee ever','2016-14-03', 10);

ALTER TABLE Feedback MODIFY COLUMN fid INT auto_increment;
ALTER TABLE Feedback AUTO_INCREMENT=300;
-----------------------------------------------------------------------------------------------

CREATE TABLE Favorites(
pid INTEGER NOT NULL,
login char(30) NOT NULL,
fvdate date,
PRIMARY KEY (pid, login),
FOREIGN KEY (pid) REFERENCES POI (pid),
FOREIGN KEY (login) REFERENCES Users (login));

INSERT INTO Favorites
(pid, login, fvdate)
VALUES
(200, 'chuy8jay', '2016-14-03');

-----------------------------------------------------------------------------------------------

CREATE TABLE Keywords(
wid INTEGER,
word CHAR(50), 
language CHAR(20),
PRIMARY KEY(wid));

INSERT INTO Keywords
(wid, word, language)
VALUES
(500, 'coffee', 'English');


-----------------------------------------------------------------------------------------------

CREATE TABLE HasKeywords(
pid INTEGER,
wid INTEGER,
PRIMARY KEY (pid, wid),
FOREIGN KEY (pid) REFERENCES POI (pid),
FOREIGN KEY (wid) REFERENCES Keywords (wid));

INSERT INTO HasKeywords
(pid, wid)
VALUES
(200, 500);

ALTER TABLE Keywords AUTO_INCREMENT=400;
ALTER TABLE Keywords MODIFY COLUMN wid INT auto_increment;

-----------------------------------------------------------------------------------------------
-- To delete without the constraints
SET foreign_key_checks = 1; 


-----------------------------------------------------------------------------------------------
-- Modifying column type
ALTER TABLE tablename MODIFY columnname INTEGER;


-----------------------------------------------------------------------------------------------
-- 10 useful feedback

select F.fid, P.pid, P.name, F.login, F.score, AVG(F.score) avgerage
from Feedback F, POI P 
Where F.pid = P.pid
order by P.pid;

select F.fid, P.pid, P.name, F.login, F.score
from Feedback F, POI P 
Where F.pid = P.pid

select pid, AVG(score)
from Feedback
group by pid;

SELECT * FROM
       POI P,
       (SELECT *, AVG(score) a
       FROM Feedback 
       GROUP BY pid) av 
       WHERE av.pid = P.pid
       ORDER BY av.a DESC
       LIMIT 2;

(select fid, rating, AVG(rating) avg_score
 from Rates group by fid) av

SELECT P.pid, P.name, P.category, P.address, P.URL, P.tel_num, P.yr_est, P.hours, P.price, av.avg_score
FROM Feedback F, POI P,
     (SELECT fid, AVG(rating) avg_score 
     FROM Rates GROUP BY fid) av
where F.fid = av.fid
AND P.pid = F.pid
ORDER BY av.avg_score DESC;

-----------------------------------------------------------------------------------------------
-- by average numerical score of feedback


SELECT P.pid, P.name, P.category, P.address, P.URL, P.tel_num, P.yr_est, P.hours, P.price, av.avg_score
FROM (SELECT * FROM POI P WHERE price >= 1 AND price <= 20) P,
     (SELECT pid, AVG(score) avg_score
     FROM Feedback GROUP BY pid) av
WHERE P.pid = av.pid
ORDER BY av.avg_score DESC;

SELECT P.pid, P.name, P.category, P.address, P.URL, P.tel_num, P.yr_est, P.hours, P.price,  av.avg_score 
FROM (SELECT * FROM POI P WHERE price >= 1 AND price <= 20) P (SELECT pid, AVG(score) avg_score FROM Feedback GROUP BY pid) av WHERE P.pid = av.pid ORDER BY av.avg_score DESC;

-----------------------------------------------------------------------------------------------
-- by the average numerical score of the trusted user feedbacks

SELECT P.pid, P.name, P.category, P.address, P.URL, P.tel_num, P.yr_est, P.hours, P.price, av.avg_score
FROM POI P,
     (SELECT pid, F.login, F.text, AVG(score) avg_score
     FROM Feedback F, Trust T 
     WHERE T.login2 = F.login 
     AND isTrusted = 1
     GROUP BY pid) av
WHERE P.pid = av.pid
ORDER BY av.avg_score DESC;


-----------------------------------------------------------------------------------------------
-- keywords

select * 
from HasKeywords h, Keywords k 
where h.wid = k.wid;
AND k.word LIKE '%coffee%'


SELECT P.pid, P.name, P.category, P.address, P.URL, P.tel_num, P.yr_est, P.hours, P.price,  av.avg_score 
FROM 
	(SELECT * 
	 FROM POI poi,
		(SELECT h.pid 
		FROM HasKeywords h, Keywords k 
		WHERE h.wid = k.wid 
		AND k.word LIKE '%coffee%') Kw
	 WHERE  address LIKE '%E%' AND Kw.pid = poi.pid) P,
(SELECT fb.pid, AVG(fb.score) avg_score FROM Feedback fb GROUP BY fb.pid) av 
 WHERE P.pid = av.pid ORDER BY av.avg_score DESC;

-----------------------------------------------------------------------------------------------
-- Get similar pois

select distinct pid 
from (select distinct login 
     from Visit 
     where pid = 200) S, Visit V 
where S.login = V.login 
and V.pid <> 200;

select *
from POI P,
     (select distinct V.pid
	from
	(select V.login
	    from Visit V 
	    where V.pid = 200 and V.login != 'chuy8jay' limit 1) S, Visit V
	 where S.login = V.login
	 and V.pid <> 200) upoi
where P.pid = upoi.pid;

select *
from
(select v1.login, v1.pid v1pid, count(*) num_visits
from Visit v1
group by v1.login, v1.pid) C1,

(select P.pid c2pid, upoi.login, 
P.name, P.category, P.address, P.URL, P.tel_num, P.yr_est, P.hours, P.price
from POI P,
     (select distinct V.pid, S.login
	from
	(select V.login
	    from Visit V 
	    where V.pid = 202 and V.login != 'chuy8jay' limit 1) S, Visit V
	 where S.login = V.login
	 and V.pid <> 202) upoi
where P.pid = upoi.pid) C2

where C1.v1pid = C2.c2pid
and C1.login = C2.login
order by C1.num_visits DESC;



select *
from POI P,
(select distinct V.pid
from
(select V.login
from Visit V 
where V.pid = 200 and V.login != 'chuy8jay' limit 1) S, Visit V
where S.login = V.login
and V.pid <> 200) upoi
where P.pid = upoi.pid;

-----------------------------------------------------------------------------------------------
-- Degrees of seperation
select * 
from 
     (select distinct * from Favorites where login='jay8chuy') R1,
     (select distinct * from Favorites where login='chuy8jay') R2
where
R1.pid = R2.pid
having count(*) > 0;


select count(*) from      (select distinct * from Favorites where login='jay8chuy') R1,      (select distinct * from Favorites where login='chuy8jay') R2 where R1.pid = R2.pid;


select count(*)
from
(select R2.pid, R2.login, R2.fvdate
from
	(select distinct F1.pid, F1.login, F1.fvdate from Favorites F1 where login='Test') R1, 
	Favorites R2
where
R1.pid = R2.pid
and R2.login <> 'Test') P1,


(select R2.pid, R2.login, R2.fvdate
from
        (select distinct F1.pid, F1.login, F1.fvdate from Favorites F1 where login='hello') R1,
	Favorites R2
where
R1.pid = R2.pid
and R2.login <>	'hello') P2

WHERE P1.login = P2.login

-- Now combine the two and join them where their logins are the same!

-----------------------------------------------------------------------------------------------
-- Statistics
select * 
from POI P, 
(select pid, count(*) cnt from Visit group by pid) V 
where V.pid = P.pid 
and P.category = 'Restaurant'
order by V.cnt DESC;

-- costliest per category
select *
from POI P, 
(select V.login, V.pid, AVG(cost/numberofheads) cst_per_head 
from Visit V, VisEvent ve where V.vid =ve.vid 
group by pid) V
where V.pid = P.pid 
and P.category = 'Restaurant'
order by V.cst_per_head DESC;

-- best rated
select *
from POI P,
(select pid, AVG(score) cnt from Feedback group by pid) V
where V.pid = P.pid 
and P.category = 'Restaurant'
order by V.cnt DESC;


-- most trusted
select 
from Users U
select Trusted.L, Trusted.trusted, NTrusted.not_trusted from
(select T1.L, T1.trust trusted
from 
(select login2 L, isTrusted, count(isTrusted) trust from Trust group by isTrusted, login2) T1
where T1.isTrusted = 1
and T1.L = 'jay8chuy') Trusted,

(select T2.L, T2.trust not_trusted
from
(select login2 L, isTrusted, count(isTrusted) trust from Trust group by isTrusted, login2) T2
where T2.isTrusted = 0
and T2.L = 'jay8chuy') NTrusted

where Trusted.L = NTrusted.L

--

select (case when TT.L = NULL then NT.L end) login, TT.trusted, NT.not_trusted, TT.trusted - NT.not_trusted from
(select T1.L, T1.trust trusted
from 
(select login2 L, isTrusted, count(isTrusted) trust from Trust group by isTrusted, login2) T1
where T1.isTrusted = 1) TT
right outer join
(select T2.L, T2.trust not_trusted
from
(select login2 L, isTrusted, count(isTrusted) trust from Trust group by isTrusted, login2) T2
where T2.isTrusted = 0) NT

on TT.L = NT.L



select TT.Login, TT.trust - TT.not_trust trust
from
(select login2 Login,
count(case when isTrusted = 1 then 1 end) trust, 
count(case when isTrusted = 0 then 1 end) not_trust
from Trust 
group by login2) TT
order by TT.trust - TT.not_trust desc;


-- Most useful users

select F.login, AVG(R.rating) avg_rtg 
from Rates R, Feedback F 
where R.fid = F.fid group by login order by avg_rtg desc; 
