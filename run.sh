docker network create mysql-network
docker container run --name mysqldb --network mysql-network -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=tedtalkdb -d mysql:8

mvn clean package spring-boot:repackage

docker build -t tedTalkApp .
docker run -p 8080:8080 tedTalkApp
