# Yandex Mediaservices Hackathon Backend

## Локальный запуск

**Поднять локально сервис**  
Запустится на `localhost:8080`
```sh
docker build -t ya-hack-back . && docker run -p 8080:8080 ya-hack-back
```

**Поднять локально базу данных**  
Запустится на `localhost:5432`
```sh
docker-compose -f postgres.yml up
```