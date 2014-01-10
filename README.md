Emathcoursemanagement
=====================

What?
-----
Emathcoursemanagement is a server side connection for electronic schoolbooks
created in [E-Math project](http://emath.eu). The server acts both as
a backup service for content the user creates in his/her ebook, and as a
communication channel between the teacher and the students.

In course management system there are several courses, teachers and students.
Each course has assigned one teacher and (atleast) one ebook. Teacher can add
students to a course.

How?
----
Emathcoursemanagement is written in Java and runs as Tomcat servlet. Development
has been done in Eclipse environment.

Emathcoursemanagement uses following programs and libraries:
* [Apache Tomcat](http://tomcat.apache.org/)
* [Hibernate](http://wiki.apache.org/tomcat/TomcatHibernate)
* Gson
* Mysql-connector-java

Working version has used following versions of following libraries:
* antlr-2.7.6.jar
* c3p0-0.9.1.2.jar
* commons-collections-3.1.jar
* dom4j-1.6.1.jar
* gson-2.1.jar
* hibernate3.jar
* javassist.jar
* jta.jar
* log4j-1.2.15.jar
* mysql-connector-java-5.1.16-bin.jar
* slf4j-api-1.5.8.jar
* slf4j-log4j12-1.5.8.jar

Who?
----
The system was developed in EU-funded [E-Math -project](http://emath.eu) by
* Kaj Giersberg
* Teemu Rajala

and the copyrights are owned by [Four Ferries oy](http://fourferries.fi).

License?
--------
The course management system is licensed under
[GNU AGPL](http://www.gnu.org/licenses/agpl-3.0.html).

Other programs and libraries are licensed with their respective licenses.

Documentation
-------------
The documentation can be found in `doc`-folder.