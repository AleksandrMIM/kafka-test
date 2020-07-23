# KAFKA-TEST

## Create Topic
kafka-topics --create --topic testTopic --partitions 5 --replication-factor 5 --if-not-exists --zookeeper zookeeper:2181

## Jmeter Template
```
{
	"messages": [
		{
			"messageId":{{SEQUENCE("messageId", 1, 1)}},
			"payload":"{{RANDOM_ALPHA_NUMERIC("abcedefghijklmnopqrwxyzABCDEFGHIJKLMNOPQRWXYZ", 100)}}"
		},
		{
			"messageId":{{SEQUENCE("messageId", 1, 1)}},
			"payload":"{{RANDOM_ALPHA_NUMERIC("abcedefghijklmnopqrwxyzABCDEFGHIJKLMNOPQRWXYZ", 100)}}"
		},
		{
			"messageId":{{SEQUENCE("messageId", 1, 1)}},
			"payload":"{{RANDOM_ALPHA_NUMERIC("abcedefghijklmnopqrwxyzABCDEFGHIJKLMNOPQRWXYZ", 100)}}"
		},
		{
			"messageId":{{SEQUENCE("messageId", 1, 1)}},
			"payload":"{{RANDOM_ALPHA_NUMERIC("abcedefghijklmnopqrwxyzABCDEFGHIJKLMNOPQRWXYZ", 100)}}"
		}
	]
}
```