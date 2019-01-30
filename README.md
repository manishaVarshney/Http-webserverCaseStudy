# HTTP-WebServer
A multi-threaded (e.g. file-based) web server with thread-pooling implemented in Java.
Works on HTTP/1.1 protocol with keep-alive behavior.

#Build
mvn clean install

#Command to start the server
java -jar target/http-web-server-1.0-SNAPSHOT.jar <git_workspace_abs_path>
For ex. /Users/manisha.v/AdobeCaseStudy/

#Command to stop the server
ctrl+c
