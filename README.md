# Yandex Mediaservices Hackathon Backend

## Локальный запуск
Почитать про Docker можно [тут](https://docker-curriculum.com/)  
  
**Поднять локально сервис**  
Сервис запустится на `localhost:8080`  
Есть 3 варианта:

1. **Из Idea**   
   Нужна установленная Java  
   `YaHackApplication.java` ---> зеленый треугольник у метода `main`


2. **Используя Docker**   
   Пока не работает, потом починю
   ```sh
   docker build -t ya-hack-back . && docker run -p 8080:8080 ya-hack-back
   ```

3. **Используя gradle**  
   ~~тут сами разбирайтесь~~ напишу, если нужно потом - у меня почему-то не подцепилась Java к gradlew в этот раз и 
   я решил не тратить время на разборы 

**Поднять локально базу данных**  
Запустится на `localhost:5432`
```sh
docker-compose -f postgres.yml up
```

**Swagger2 доступен в ручке [/swagger-ui.html](http://localhost:8080/swagger-ui.html)**