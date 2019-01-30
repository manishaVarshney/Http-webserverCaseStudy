# HTTP-WebServer
A multi-threaded (e.g. file-based) web server with thread-pooling implemented in Java.
Works on HTTP/1.1 protocol with keep-alive behavior.

## Installation
`mvn clean install`

## Command to start the server
`java -jar target/http-web-server-1.0-SNAPSHOT.jar <git_workspace_abs_path>`
For ex. /Users/manisha.v/AdobeCaseStudy/

## Usage
`localhost:8090/index.html`
`localhost:8090/randomFile.htm`

## Command to stop the server
`ctrl+c`

### References:
 1. Concept of file-based http web-server: https://medium.com/@ssaurel/create-a-simple-http-web-server-in-java-3fc12b29d5fd
 2. ThreadPoolExecutor: https://howtodoinjava.com/java/multi-threading/java-thread-pool-executor-example/

