insert into principal value (1 , 'Principal' , 'principalbgc56@gmail.com' , null);
insert into headclark value (1 ,'Head Clark' , 'headclerkbgc56@gmail.com');

insert ignore into Department values
(1,'ASP(Advertisement and Sales Promotion)'),
(2,'Bengali'),
(3,'Botany'),
(4,'Chemistry'),
(5,'Commerce'),
(6,'Computer Science'),
(7,'Economics'),
(8,'Education'),
(9,'Electronic Science'),
(10,'English'),
(11,'Food & Nutrition'),
(12,'Environmental Science'),
(13,'Geography'),
(14,'Hindi'),
(15,'History'),
(16,'Journalism & Mass Com.'),
(17,'Mathematics'),
(18,'Philosophy'),
(19,'Physical Education'),
(20,'Physics'),
(21,'Physiology'),
(22,'Sanskrit'),
(23,'Sociology'),
(24,'Urdu'),
(25,'Zoology'),
(26,'Other');


insert ignore into TeacherType values
(1 ,'Permenent'),
(2 ,'SACT'),
(3 ,'Non Teach');

insert ignore into teacher (email) values
('sacteacherone@gmail.com'),
('poulastaadas2@gmail.com'),
('nonteach56@gmail.com'),
('permanentteacherone@gmail.com');

insert ignore into DepartmentHead (teacherId,departmentId ) values
(2 ,6);

insert ignore into Designation (`type`) values
('Assistant Professor-I'),
('Assistant Professor-II'),
('Assistant Professor-III'),
('Associate Professor'),
('SACT-I'),
('SACT-II'),
('Lab. Attendant'),
('Accountant'),
('Elec. Cum Caretaker'),
('Clerk'),
('Typist'),
('Lab. Attendant'),
('Gen./Pump Operator Cum Mechanic'),
('Mali'),
('Guard'),
('Peon');


insert ignore into DesignationTeacherTypeRelation values
(1,1),
(2,1),
(3,1),
(4,1),
(5,2),
(6,2),

(7,1),
(8,1),
(9,1),
(10,1),
(11,1),
(12,1),
(13,1),
(14,1),
(15,1);


insert ignore into Qualification (`type`) values
('Dr.'),
('Ph.D'),
('M.Tech'),
('M.Com'),
('MBA'),
('MSC'),
('MCA'),
('MLib'),
('Other');




insert ignore into AddressType (type) values
('PRESENT'),
('HOME');

insert ignore into `Path` (type) values
('Department Head'),
('Head Clark'),
('Principal');


insert ignore into LeaveType (type) values
('Casual Leave'),
('Medical Leave'),
('Study Leave'),
('Earned Leave'),
('On Duty Leave'),
('Special Study Leave'),
('Maternity Leave'),
('Quarintine Leave'),
('Commuted Leave'),
('Extraordinary Leave'),
('Compensatory Leave'),
('Leave Not Due'),
('Special Disability Leave');


insert ignore into Status (type) values
('Approved'),
('Pending'),
('Rejected'),
('Accepeted');


insert ignore into PendingEnd (type) values
('Principal Level'),
('Department Level'),
('Head Clark Level'),
('Not Pending');


insert ignore into LeaveAction (type) values
('Approve'),
('Reject'),
('Forward');

































