# KAFKA-TEST

## Integration Test Plan

### Test Zero - Все корректно

#### Тестовые данные
Заслать в kafka сообщение вида
```
{
	"messages": [
		{
			"messageId":2,
			"payload":"42"
		},
		{
        	"messageId":3,
        	"payload":"42"
        }
	]
}
```

#### Результат выполнения
##### В логе появится информация о получение сообщения с текстом
```
[INFO ] 2020-07-26 20:38:27.902 [batch-listener-4-C-1] [KafkaConsumer] - Получено сообщение ---> {
	"messages": [
		{
			"messageId":2,
			"payload":"42"
		},
		{
        	"messageId":3,
        	"payload":"42"
        }
	]
}
```
##### В логе приложения появится сообщение о сохранении и успешном сохранении записей в БД
```
[INFO ] 2020-07-26 20:38:27.943 [batch-listener-4-C-1] [MessageInteractorImpl] - Сохраняем сообщения в БД
[INFO ] 2020-07-26 20:38:28.012 [batch-listener-4-C-1] [MessageInteractorImpl] - Сообщения сохранены в БД
```
##### Offset в kafka topic будет перемещен на следующую позицию
```
[INFO ] 2020-07-26 20:38:28.012 [batch-listener-4-C-1] [KafkaConsumer] - Сообщения обработаны, смещение offset
```
##### В БД появится две новых записи с id 2 и 3
Проверяется с помощью ```select * from message m where m.id = 2 or m.id = 3```.

### Test One - Ошибка формата сообщений

#### Тестовые данные
Заслать в kafka сообщение вида
```
{
	"messages": [
		{8
			"messageId":1,
			"payload":"42"
		}
	]
}
```

#### Результат выполнения
##### В логе появится информация о получение сообщения с текстом
```
[INFO ] 2020-07-26 20:30:11.613 [batch-listener-0-C-1] [KafkaConsumer] - Получено сообщение ---> {
	"messages": [
		{8
			"messageId":1,
			"payload":"42"
		}
	]
}
```
##### В логе приложения появится сообщение с предупреждением
```
}
[WARN ] 2020-07-26 20:30:11.614 [batch-listener-0-C-1] [KafkaMessageJsonDeserializer] - Неверный формат входящего сообщения
com.fasterxml.jackson.databind.JsonMappingException: Unexpected character ('8' (code 56)): 
```
##### Offset в kafka topic будет перемещен на следующую позицию
```
[INFO ] 2020-07-26 20:30:11.615 [batch-listener-0-C-1] [KafkaConsumer] - Сообщения обработаны, смещение offset
```
##### В БД не появится сообщения с id 1 и payload "42"
Проверяется с помощью ```select * from message m where m.id = 1 and m.payload = 42```.

### Test Two - Сообщение Kafka с пустым списком messages

#### Тестовые данные
Заслать в kafka сообщение вида
```
{
	"messages": []
}
```

#### Результат выполнения
##### В логе появится информация о получение сообщения с текстом
```
[INFO ] 2020-07-26 20:42:35.028 [batch-listener-0-C-1] [KafkaConsumer] - Получено сообщение ---> {
	"messages": []
}
```
##### В логе приложения появится информационное сообщение
```
[INFO ] 2020-07-26 20:42:35.030 [batch-listener-0-C-1] [KafkaGroupMessage] - Пустое тело сообщения
```
##### Offset в kafka topic будет перемещен на следующую позицию
```
[INFO ] 2020-07-26 20:30:11.615 [batch-listener-0-C-1] [KafkaConsumer] - Сообщения обработаны, смещение offset
```

### Test Three - Сообщение с messageId меньше нуля

#### Тестовые данные
Заслать в kafka сообщение вида
```
{
	"messages": [
		{
			"messageId":-42,
			"payload":"42"
		}
	]
}
```

#### Результат выполнения
##### В логе появится информация о получение сообщения с текстом
```
[INFO ] 2020-07-26 20:44:28.834 [batch-listener-4-C-1] [KafkaConsumer] - Получено сообщение ---> {
	"messages": [
		{
			"messageId":-42,
			"payload":"42"
		}
	]
}
```
##### В логе приложения появится сообщение с предупреждением
```
[WARN ] 2020-07-26 20:44:28.836 [batch-listener-4-C-1] [KafkaMessage] - Некоректный идентификатор -42 сообщения 42 
```
##### Offset в kafka topic будет перемещен на следующую позицию
```
[INFO ] 2020-07-26 20:30:11.615 [batch-listener-0-C-1] [KafkaConsumer] - Сообщения обработаны, смещение offset
```

### Test Four - Ошибки сохранения в БД типа constraint violation или иные фатальные

#### Тестовые данные
Заслать в kafka сообщение вида
```
{
	"messages": [
		{
			"messageId":42,
			"payload":"1234567865898490213849081239048231094821390481230948213094812390482139048231097235908347589723490857324809572340985723408957890234758093247589032475908324750982347580927348590732408957324890570892347580923475089723409857234089572304975898023487590234790589023475980234759803274598072349058732490857234890759802347609342759073240957324905873240587324095732408957324809572093487508923487509238475089237450987234509723490476432187648732164872316487213648712364872136487213648723164872136487213648721364873216589723198478932174982317498321749231749872318947213984789123748912379841278213"
		},
        {
			"messageId":32,
			"payload":"42342"
		}
	]
}
```

#### Результат выполнения
##### В логе появится информация о получение сообщения с текстом
```
[INFO ] 2020-07-26 20:49:44.260 [batch-listener-0-C-1] [KafkaConsumer] - Получено сообщение ---> {
	"messages": [
		{
			"messageId":42,
			"payload":"1234567865898490213849081239048231094821390481230948213094812390482139048231097235908347589723490857324809572340985723408957890234758093247589032475908324750982347580927348590732408957324890570892347580923475089723409857234089572304975898023487590234790589023475980234759803274598072349058732490857234890759802347609342759073240957324905873240587324095732408957324809572093487508923487509238475089237450987234509723490476432187648732164872316487213648712364872136487213648723164872136487213648721364873216589723198478932174982317498321749231749872318947213984789123748912379841278213"
		},
        {
			"messageId":32,
			"payload":"42342"
		}
	]
}
```
##### В логе приложения появится сообщение о попытке сохранить данные в БД
```
[INFO ] 2020-07-26 20:49:44.310 [batch-listener-0-C-1] [MessageInteractorImpl] - Сохраняем сообщения в БД
```
##### В логе приложения появится сообщение с ошибкой выполнения транзакции
```
[ERROR] 2020-07-26 20:49:44.382 [batch-listener-0-C-1] [SqlExceptionHelper] - ERROR: value too long for type character varying(256)
[WARN ] 2020-07-26 20:49:44.390 [batch-listener-0-C-1] [MessageInteractorImpl] - Нарушено ограничение целлостности при сохранении пачки записей в БД. Сохраняем сообщения по одному
org.springframework.dao.DataIntegrityViolationException: could not execute batch; SQL [update message set payload=? where id=?]; nested exception is org.hibernate.exception.DataException: could not execute batch
```
##### Так как записей больше одной, пытаемся сохранить их по отдельности
```
[INFO ] 2020-07-26 20:49:44.390 [batch-listener-0-C-1] [MessageInteractorImpl] - Сохраняем в БД сообщение 42
...
[ERROR] 2020-07-26 20:49:44.394 [batch-listener-0-C-1] [SqlExceptionHelper] - ERROR: value too long for type character varying(256)
[WARN ] 2020-07-26 20:49:44.395 [batch-listener-0-C-1] [MessageInteractorImpl] - Нарушено ограничение целлостности при сохранении записи по одной в БД
org.springframework.dao.DataIntegrityViolationException: could not execute batch; SQL [update message set payload=? where id=?]; nested exception is org.hibernate.exception.DataException: could not execute batch
...
[INFO ] 2020-07-26 20:49:44.395 [batch-listener-0-C-1] [MessageInteractorImpl] - Сохраняем в БД сообщение 32
[INFO ] 2020-07-26 20:49:44.399 [batch-listener-0-C-1] [MessageInteractorImpl] - Сообщение 32 сохранено в БД
```
##### Offset в kafka topic будет перемещен на следующую позицию
```
[INFO ] 2020-07-26 20:30:11.615 [batch-listener-0-C-1] [KafkaConsumer] - Сообщения обработаны, смещение offset
```
##### В БД появится сообщения с id 32 и не появится с id 42 
Проверяется с помощью ```select * from message m where m.id = 32 or m.id = 42```.


### Test Five - Временные ошибки (физическая недоступность БД и т.п.)

#### Тестовые данные
Выключить БД и заслать в kafka сообщение вида
```
{
	"messages": [
        {
			"messageId":32,
			"payload":"42342"
		}
	]
}
```
#### Результат выполнения
##### В логе появится информация о получение сообщения с текстом
```
[INFO ] 2020-07-26 20:57:11.749 [batch-listener-4-C-1] [KafkaConsumer] - Получено сообщение ---> {
	"messages": [
        {
			"messageId":32,
			"payload":"42342"
		}
	]
}
```
##### В логе приложения появится сообщение с ошибкой выполнения транзакции
```
[INFO ] 2020-07-26 20:57:11.749 [batch-listener-4-C-1] [MessageInteractorImpl] - Сохраняем сообщения в БД
[ERROR] 2020-07-26 20:57:12.750 [batch-listener-4-C-1] [SqlExceptionHelper] - HikariPool-1 - Connection is not available, request timed out after 1000ms.
[ERROR] 2020-07-26 20:57:12.750 [batch-listener-4-C-1] [SqlExceptionHelper] - Connection to localhost:5432 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
[ERROR] 2020-07-26 20:57:12.750 [batch-listener-4-C-1] [KafkaMessageListenerContainer$ListenerConsumer] - Error handler threw an exception
org.springframework.kafka.KafkaException: Seek to current after exception; nested exception is org.springframework.kafka.listener.ListenerExecutionFailedException: Listener method 'public void ru.mina.test.kafka.consumer.KafkaConsumer.consume(java.util.List<java.lang.String>)' threw exception; nested exception is org.springframework.transaction.CannotCreateTransactionException: Could not open JPA EntityManager for transaction; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to acquire JDBC Connection; nested exception is org.springframework.transaction.CannotCreateTransactionException: Could not open JPA EntityManager for transaction; nested exception is org.hibernate.exception.JDBCConnectionException: Unable to acquire JDBC Connection
```
##### Offset в kafka topic не будет перемещен на следующую позицию
Приложение будет повторно пытаться сохранить сообщение в БД.