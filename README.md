# Library

## Описание

Этот проект представляет собой систему управления библиотекой, реализованную с использованием Spring Boot. В проекте используется база данных H2 для разработки и тестирования, Swagger для документации API и Redis для кэширования.

## Конфигурация базы данных (H2)

Проект использует H2, встроенную базу данных, для разработки и тестирования. Ниже приведены детали настройки и доступа к базе данных H2.

### Конфигурация

В файле `application.properties` конфигурация H2 выглядит следующим образом:

```properties
# Конфигурация базы данных H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Включить консоль H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Подключение к базе данных H2
[Ссылка для подключения к H2](http://localhost:8080/h2-console)

### Конфигурация подключения:

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User Name:** `sa`
- **Password:** `password`

### Использование Swagger

Swagger интегрирован в проект для автоматической генерации и визуализации API-документации. Чтобы получить доступ к интерфейсу Swagger, перейдите по следующему URL после запуска приложения:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
