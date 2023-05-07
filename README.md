# springboot-kafka-microservices

C:\Users\joao_\Downloads\kafka

Start zookeeper windons
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

Star kakfa server on windowns
.\bin\windows\kafka-server-start.bat .\config\server.properties

create topic
kafka-topics.bat --create --topic topic-example --bootstrap-server localhost:9092

Create events
.\bin\windows\kafka-console-producer.bat --topic topic-example --bootstrap-server localhost:9092

read events
.\bin\windows\kafka-console-consumer.bat --topic topic-example --from-beginning --bootstrap-server localhost:9092