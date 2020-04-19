This application is using <b>Basic Auth</b> method;<br>
This application uses H2 database;<br>
All endpoints are covered by Spring Boot tests;<br>
Liquibase for database version control;<br>
This application uses Hibernate ORM;<br>
Other databases can be managed in application.properties file;<br>

Here are all endpoints with examples;

To register (POST):<br>
localhost:8080/user/register<br><br>
{   
    "username": "test@test.lt",<br>
    "password": "password"
<br>}
<br>====================================<br>
To login (POST):<br>
localhost:8080/user/login<br><br>
{   
    "username": "test@test.lt",<br>
    "password": "password"
<br>}
<br>====================================<br>
To add new record (POST):<br>
localhost:8080/blog/addBlog<br>

<b>BasicAuth for user from DB</b><br>
username: username<br>
password: password

{   
    "title": "title",<br>
    "text": "text"
<br>}
<br>====================================<br>
To delete record (DELETE):<br>
localhost:8080/blog/delete/{id}<br>

<b>BasicAuth for user from DB</b><br>
username: username<br>
password: password
<br>====================================<br>
To get record list (GET):<br>
localhost:8080/blog/list<br>

<b>BasicAuth for user from DB</b><br>
username: username<br>
password: password
<br>====================================<br>
To update record (POST):<br>
localhost:8080/blog/update/{id}<br>

<b>BasicAuth for user from DB</b><br>
username: username<br>
password: password

{   
    "title": "updatedTitle",<br>
    "text": "updatedText"
<br>}<br>
====================================<br>