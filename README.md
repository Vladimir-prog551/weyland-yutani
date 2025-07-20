# Weyland-Yutani

Данный проект включает в себя synthetic-human-core-starter, который будет используется для реализации логики работы всех будущих моделей искусственных людей, а также простейший сервис-эмулятор синтетика bishop-prototype, который принимает команды с помощью REST API и демонстрирует разработанный стартер.

## Старт через докер

Склонировать репозиторий:
```
git clone -b master https://github.com/Vladimir-prog551/weyland-yutani.git
```
Собрать стартер:
```
cd weyland-yutani/synthetic-human-core-starter
mvn clean install
```
Затем, перед сборкой REST API сервиса, имеется возможность указать способ логирования в файле bishop-prototype/src/main/resources/application.properties
logger - режим аудита в консоль
kafka - режим аудита в кафку
```
synthetic.human.core.starter.audit.mode=logger
```
Собрать REST API сервис (нужно переместиться в директорию weyland-yutani):
```
cd ../../...../bishop-prototype
mvn clean install
```
Запустить докер контейнер:
```
cd ../
docker-compose up --build
```

## Отправка запросов

1. Просмотр текущего размера очереди:
```
GET http://localhost:8080/api/actuator/metrics/android.queue.size
```
2. Просмотр количества выполненных задач для каждого автора (доступно только после первой отправки задачи):
```
GET http://localhost:8080/api/actuator/metrics/android.commands.executed
```
3. Отправка задачи андроиду:
```
POST http://localhost:8080/api/commands
```

Пример задачи:
```
{
    "description": "TEST",
    "commandPriority": "CRITICAL",
    "author": "Tester",
    "time":"2025-07-19T12:00:00Z"
}
```

4. Для просмотра аудита в кафке нужно открыть новый терминал и выполнить следующее:
```
docker exec kafka /opt/bitnami/kafka/bin/kafka-console-consumer.sh --topic audit-topic --bootstrap-server localhost:9092
```
