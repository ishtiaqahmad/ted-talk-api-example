# ted-talk-api-example
Learning example of TED Talk API


compile : `./mvnw spring-boot:run`  or `mvn clean package spring-boot:repackage`
run: ` ./mvnw spring-boot:run` or `mvn spring-boot:run`

# for doctor
 `docker compose up -d`
 `docker compose down`
 
 Endpoint are protected with basic authentication user:password
use `curl -X -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8080/tedTalk/v1.0/1` to test