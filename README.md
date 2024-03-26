# Java веб-приложение с встроенным сервером Tomcat

Веб-приложение позволяет просматривать в браузере список пациентов в БД, добавлять новых, изменять и удалять существующих. Также есть возможность добавить пациентов из файла.

## Стек
- Java 17
- Apache Tomcat
- Servlets
- JSP
- H2Database
- SL4J


## Запустить приложение
```
mvn clean
mvn package
java -jar ./target/patients-web-app.jar // будет использован порт 8082
java -jar ./target/patients-web-app.jar 8082
```
Открыть в браузере http://localhost:8082/
Логи с уровнем DEBUG записываются в папку /log