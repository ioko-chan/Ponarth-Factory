
# Dev

> http://localhost:8082/   
> http://localhost:8082/swagger-ui/index.html

#  Prod

> http://82.202.160.180:5052/   
> http://82.202.160.180:8082/swagger-ui/index.html

# Doc about users

- В систему пользователя может зарегистрировать только существующий пользователь с правами у которых есть приставка ADMIN  
- При создании пользователя генерируется пароль из 10 символов и отправляется через sms по номеру указанный в начале  
- Также есть возможность генерации нового пароля, при утере старого, который также придет по sms  
- Номер телефона может менять только существующий пользователь с правами у которых есть приставка ADMIN  