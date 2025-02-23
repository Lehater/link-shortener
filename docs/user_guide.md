[назад](../README.md)

# Руководство пользователя 
## Консольное приложение «Сервис Сокращения Ссылок»

### 1. **Назначение**  
   Приложение представляет собой консольный интерфейс для выполнения следующих задач:
    - Создание коротких ссылок на основе заданных оригинальных URL.
    - Редактирование параметров существующих ссылок (например, изменение лимита переходов, шт).
    - Удаление ссылок, доступных пользователю.
    - Перенаправление (redirect) по короткой ссылке при условии её активности.
    - Получение списка всех ссылок, привязанных к конкретному UUID пользователя.

### 2. **Аутентификация**
- При запуске приложения отображается приглашение для ввода UUID (строка в формате стандартного UUID, 36 символов).
- Если необходимо зарегистрировать нового пользователя, следует оставить поле UUID пустым. Приложение сгенерирует
  новую учётную запись (новый UUID).
- Ввод невалидного UUID (не соответствует формату `xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`) приводит к
  генерации валидного UUID (регистрации нового пользователя).

### 3. **Основные команды**  
   Все команды вводятся в консоли после успешной аутентификации:

- **create**  
  Выполняет создание новой короткой ссылки. После ввода команды приложение запросит «оригинальный URL». В случае
  успеха выводит сгенерированный короткий URL и UUID пользователя.
    - Срок действия короткой ссылки устанавливается из конфигурации (например, `24` часа)
    - Лимит переходов устанавливается из конфигурации, но пользователь может изменить его командой **`edit`**.
   
- **edit**  
  Осуществляет изменение лимита переходов (шт) для короткой ссылки. Требует указания короткого URL и нового лимита.
  После успешного изменения выводит сообщение о результатах обновления.

- **delete**  
  Удаляет ссылку на основании короткого URL, если владелец совпадает с текущим UUID. Выводит уведомление при
  успешном удалении.

- **redirect**  
  Запускает процедуру перенаправления по указанному короткому URL. При активной ссылке в терминале отображается
  целевой URL и производится открытие браузера (через Desktop API).
    - При каждом перенаправлении счётчик переходов (единица измерения — «переход», шт) увеличивается на 1.

- **list**  
  Генерирует список всех коротких ссылок, связанных с текущим UUID. Если ссылки отсутствуют, выводит соответствующее
  сообщение.

- **logout**  
  Выполняет завершение активной пользовательской сессии и возвращается к запросу UUID.

- **exit**  
  Полностью завершает работу приложения.

### 4. **Условия деактивации ссылок**
- Ссылка считается неактивной при достижении заданного лимита переходов `maxRedirects` (шт).
- Ссылка может быть просрочена при наступлении значения `expirationDate` (используется тип `LocalDateTime`,
  сравнивается с текущим временем).
- При обращении к неактивной или просроченной ссылке выводится сообщение об ошибке.

### 5. **Сообщения об ошибках**
- При вводе неправильных команд (отсутствующих в списке) отображается уведомление: «Неизвестная команда.»
- При вводе некорректного UUID, чужой короткой ссылки или несуществующей ссылки формируется соответствующее
  сообщение об ошибке (либо происходит регистрация нового пользователя, в зависимости от конфигурации).

### 6. **Завершение работы**
- Команда `exit` полностью останавливает выполнение приложения.
- Повторный запуск приложения позволяет снова пройти процедуру аутентификации и управлять ссылками.