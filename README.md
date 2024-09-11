### Описание
SimpleStorageMicroservice - headless приложение для хранения файлов. Задание реализовано полностью, также реализованы все дополнительные требования.

### Примеры запросов для проверки API методов
#### Получение всех файлов с пагинацией
<p><b>get:</b> /api/v1/file <br></p>

```
localhost:8080/api/v1/file
```
Параметры:
- page - страница, по умолчанию 0
- pageSize - кол-во элементов на странице, по умолчанию 10
- creationDateSort - сортировка по дате создание "asc" или "desc", по умолчанию "asc"
#### Создание нового файла
<p><b>post:</b> /api/v1/file </p>

```
localhost:8080/api/v1/file
```
Тело запроса:
```
{
  "fileData": "dGhpcyBpcyBub3JtYWwgdGV4dAo=",
  "fileName": "text.txt",
  "fileDescription": "description"
  }
```
#### Получение файла по айди
<p><b>get:</b> /api/v1/file/{id} <br></p>

```
localhost:8080/api/v1/file/{id}
```
Здесь id - айди нужного файла
### Инструкция по запуску
- перед началом убедитесь, что у вас установлены <b>docker</b> и <b>docker-compose</b>
- в корневой директории проекта выполните команду ```docker-compose up --build```
- приложение будет доступно локально на порте 8080