# Project2 - REST API на Spring Boot

Проект реализует полнофункциональное REST API с поддержкой аутентификации через JWT, валидации данных, обработки ошибок, Swagger документации и асинхронных операций.

## Требования

- Java 17
- Maven 3.6+
- PostgreSQL

## Функциональность

### Оценка 3 (Базовое):
- ✅ REST API с операциями CRUD для сущностей User и Order
- ✅ Использование аннотаций Spring (@RestController, @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping)
- ✅ Тестирование API через Postman или Swagger UI

### Оценка 4 (Расширенное):
- ✅ Несколько сущностей (User и Order) с отношениями One-to-Many
- ✅ Валидация данных с использованием @Valid, @NotNull, @NotBlank, @Email, @Size и др.
- ✅ Глобальная обработка ошибок через @ControllerAdvice и кастомные исключения
- ✅ Документация API через Swagger/OpenAPI

### Оценка 5 (Продвинутое):
- ✅ JWT аутентификация и авторизация
- ✅ Асинхронные операции с использованием @Async и CompletableFuture

## Запуск приложения

1. Убедитесь, что PostgreSQL запущен и создана база данных `testdb`
2. Настройте параметры подключения в `src/main/resources/application.yaml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/testdb
       username: postgres
       password: ваш_пароль
   ```
3. Запустите приложение:
   ```bash
   mvn spring-boot:run
   ```

Приложение будет доступно по адресу: http://localhost:8888

## Использование API

### Swagger UI

После запуска приложения, документация API доступна по адресу:
- Swagger UI: http://localhost:8888/swagger-ui.html
- OpenAPI JSON: http://localhost:8888/v3/api-docs

### Аутентификация

#### 1. Регистрация пользователя
```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "user1",
  "email": "user1@example.com",
  "password": "password123"
}
```

Ответ:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Регистрация прошла успешно",
  "username": "user1"
}
```

#### 2. Вход пользователя
```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "user1",
  "password": "password123"
}
```

Ответ:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Вход выполнен успешно",
  "username": "user1"
}
```

### Использование JWT токена

Все защищенные endpoints требуют JWT токен в заголовке:
```
Authorization: Bearer <ваш_jwt_токен>
```

### API Endpoints

#### Пользователи (User)

- `GET /api/users` - Получить всех пользователей
- `GET /api/users/{id}` - Получить пользователя по ID
- `POST /api/users` - Создать пользователя
- `PUT /api/users/{id}` - Обновить пользователя
- `DELETE /api/users/{id}` - Удалить пользователя

Асинхронные версии:
- `GET /api/users/async` - Получить всех пользователей асинхронно
- `GET /api/users/{id}/async` - Получить пользователя по ID асинхронно
- `POST /api/users/async` - Создать пользователя асинхронно
- `PUT /api/users/{id}/async` - Обновить пользователя асинхронно
- `DELETE /api/users/{id}/async` - Удалить пользователя асинхронно

#### Заказы (Order)

- `GET /api/orders` - Получить все заказы
- `GET /api/orders/{id}` - Получить заказ по ID
- `GET /api/orders/user/{userId}` - Получить заказы пользователя
- `POST /api/orders` - Создать заказ
- `PUT /api/orders/{id}` - Обновить заказ
- `DELETE /api/orders/{id}` - Удалить заказ

Асинхронные версии:
- `GET /api/orders/async` - Получить все заказы асинхронно
- `GET /api/orders/{id}/async` - Получить заказ по ID асинхронно
- `GET /api/orders/user/{userId}/async` - Получить заказы пользователя асинхронно
- `POST /api/orders/async` - Создать заказ асинхронно
- `PUT /api/orders/{id}/async` - Обновить заказ асинхронно
- `DELETE /api/orders/{id}/async` - Удалить заказ асинхронно

#### Пример создания заказа

```bash
POST /api/orders
Authorization: Bearer <ваш_jwt_токен>
Content-Type: application/json

{
  "title": "Заказ #1",
  "price": 1000.50,
  "user": {
    "id": 1
  }
}
```

## Тестирование

### Тестирование через Postman

1. Импортируйте коллекцию Postman или создайте запросы вручную
2. Начните с регистрации/входа для получения JWT токена
3. Используйте токен в заголовке Authorization для всех защищенных запросов

### Примеры тестовых запросов

#### 1. Регистрация
```bash
curl -X POST http://localhost:8888/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'
```

#### 2. Вход
```bash
curl -X POST http://localhost:8888/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'
```

#### 3. Создание пользователя (с JWT токеном)
```bash
curl -X POST http://localhost:8888/api/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <ваш_токен>" \
  -d '{"username":"newuser","email":"newuser@example.com","password":"password123"}'
```

## Структура проекта

```
src/main/java/com/example/project2/
├── config/
│   ├── AsyncConfig.java          # Конфигурация асинхронности
│   └── OpenApiConfig.java        # Конфигурация Swagger
├── controller/
│   ├── AuthController.java       # Контроллер аутентификации
│   ├── OrderController.java      # Контроллер заказов
│   ├── StudentApiController.java # Контроллер студентов (старый)
│   └── UserController.java       # Контроллер пользователей
├── exception/
│   ├── ErrorResponse.java        # Модель ответа об ошибке
│   ├── GlobalExceptionHandler.java # Глобальная обработка ошибок
│   └── ResourceNotFoundException.java # Кастомное исключение
├── model/
│   ├── Order.java               # Модель заказа
│   ├── StudentModel.java        # Модель студента (старая)
│   └── User.java                # Модель пользователя
├── repository/
│   ├── OrderRepository.java     # Репозиторий заказов
│   └── UserRepository.java      # Репозиторий пользователей
├── security/
│   ├── CustomUserDetailsService.java # Сервис для работы с пользователями
│   ├── JwtAuthenticationFilter.java  # JWT фильтр
│   ├── JwtService.java               # Сервис для работы с JWT
│   └── SecurityConfig.java           # Конфигурация безопасности
└── service/
    ├── OrderService.java        # Интерфейс сервиса заказов
    ├── OrderServiceImpl.java    # Реализация сервиса заказов
    ├── UserService.java         # Интерфейс сервиса пользователей
    └── UserServiceImpl.java     # Реализация сервиса пользователей
```

## Особенности реализации

### Валидация
- Использование Jakarta Bean Validation (@Valid, @NotNull, @NotBlank, @Email, @Size)
- Автоматическая проверка входящих данных в контроллерах
- Обработка ошибок валидации через GlobalExceptionHandler

### Обработка ошибок
- Глобальная обработка через @ControllerAdvice
- Кастомные исключения (ResourceNotFoundException)
- Стандартизированные ответы об ошибках (ErrorResponse)

### JWT Аутентификация
- Генерация JWT токенов при регистрации и входе
- Проверка токенов через JwtAuthenticationFilter
- Защита всех endpoints кроме /api/auth/** и Swagger

### Асинхронность
- Использование @Async для выполнения операций в отдельных потоках
- Методы возвращают CompletableFuture для асинхронной обработки
- Настройка пула потоков через AsyncConfig

### Swagger
- Автоматическая генерация документации API
- Поддержка JWT аутентификации в Swagger UI
- Интерактивное тестирование API прямо в браузере

