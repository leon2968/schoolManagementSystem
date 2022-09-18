SMS Application
---------------

How to run:

1. DB information is in hibernate.cfg.xml, please change accordingly.

<property name="connection.url">jdbc:mariadb://localhost:3306/smsdb</property>
<property name="connection.username">user1</property>
<property name="connection.password">user1</property>


2. Run the following SQL script to initilize DB.


./src/main/resources/initializeDB.sql


3. Run the application via SMSRunner, choose option 9 to feed sample data into DB.

Files are located in the resources folder:
./src/main/resources/Student-1.sql
./src/main/resources/Course-1.sql

4. Run the application via SMSRunner again to perform various operations.
Example below:

******************************
   School Management System  
******************************

1. Student Login
2. Quit Application

9. Reset Database (Choose this first if you are running the application for the first time. This will initialize database and reset all data!)
Please Enter Selection: 
1
Enter your email address: 
hguerre5@deviantart.com
Enter your password: 
OzcxzD1PGs
******************************
 Welcome, Harcourt Guerre!
******************************
My Classes :
   ID  COURSE NAME                     Instructor Name     
    2  MATHEMATICS                     Eustace Niemetz     
    7  OBJECT ORIENTED PROGRAMMING     Giselle Ardy        

1. Register a class
2. Logout
Please Enter Selection: 
1
All Courses: 
   ID  COURSE NAME                     Instructor Name     
    1  ENGLISH                         Anderea Scamaden    
    2  MATHEMATICS                     Eustace Niemetz     
    3  ANATOMY                         Reynolds Pastor     
    4  ORGANIC CHEMISTRY               Odessa Belcher      
    5  PHYSICS                         Dani Swallow        
    6  DIGITAL LOGIC                   Glenden Reilingen   
    7  OBJECT ORIENTED PROGRAMMING     Giselle Ardy        
    8  DATA STRUCTURES                 Carolan Stoller     
    9  POLITICS                        Carmita De Maine    
   10  ART                             Kingsly Doxsey      

Enter Course Number: 5
Course is successfully registered!
My Classes :
   ID  COURSE NAME                     Instructor Name     
    2  MATHEMATICS                     Eustace Niemetz     
    7  OBJECT ORIENTED PROGRAMMING     Giselle Ardy        
    5  PHYSICS                         Dani Swallow        

1. Register a class
2. Logout
Please Enter Selection: 
2
Goodbye!
