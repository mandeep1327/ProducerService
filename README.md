# Kafka-Stream Maven Project
This project is based on kafka stream. There is one micro service which will get the data from topic queue. 


| Service endpoint                               | Verb    |                               
| -----------------------------------------------| ------- |
/v1/getAllCustomers                               |  GET    |    

 

Get Api: 
http://localhost:8085/V1/getAllCustomers/


### Structure

The template contains the following major components: Producer and Consumer.

Producer will produce the message and send to topic bank-customers and bank-products. There is schedular which will
read the message from the txt files in every 1 day. 

Consumer will read the message from the queue when we call the api endpoint.

#Run
step 1:
docker-compose up

step 2:
Run Spring boot application.

step 3:
call endpoint to see the result in browser/postman

