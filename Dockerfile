FROM openjdk:17-alpine

ADD target/todo-0.0.1-SNAPSHOT.jar todo.jar

CMD ["java","-jar","todo.jar"]