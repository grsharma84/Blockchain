Language Used: Java

FrameWork: Spring Boot with Kafka

Database: H2 (Creates a file to store the records, overwrites the file everytime we start the application)

WebService port : 9000 (Can be configured in the application.yml)

Kafka port : 9092 (Can be configured in the application.yml)

Kafka Topic : mercury (Hard coded, will create this topic if does not exist)

Databse Console URL: http://localhost:9000/h2 (Use this to get the transaction ID, as  check the database) 


Database Table/Confiuration:
In the application.yml change the location of the database.
url: jdbc:h2:file:C:\Gaurav\aa;DB_CLOSE_ON_EXIT=FALSE

Here you have to change "C:\Gaurav\aa" to your file location, this will be created as soon as you run te applciation.

Database tables are defined in the schema.sql, they get created at the startup.

Tables:
UserDetails: Contains the userId, token and quantity.
Transaction: Once you call the withddraw API, a record will be stored in the trasaction table as unsettled, the 
transaction ID is auto generated. In the Kafka message you will have to provide the matchin transaction ID.
You can retrieve the transaction ID using the H2 console.


APIS:

To add the user with the initial quantity. Call the below rest service to add the user for specific tokens
Put Request to add a userId
URL: http://localhost:9000/user/add/userid/{userId}/token/{token}/quantity/{quantity}
Example: http://localhost:9000/user/add/userid/2/token/BTC/quantity/11.78	
Return : Message saying that the user was added succesfully


To Withdraw from account call the below GET request
URL: http://localhost:9000/user/withdraw/userid/{userID}/token/{token}/quantity/{quantity}
Example: http://localhost:9000/user/withdraw/userid/2/token/BTC/quantity/10.00
return: SUFFICIENT_BALANCE/INSUFFICIENT_BALANCE/User does not exists

To settle a transaction: 
Kafka publish url:  http://localhost:9000/kafka/publish
Settlement Message format:
 {"userId": "2","transactionId": "2","broughtToken": USD,"broughtQuantity": 500,"soldToken": BTC , "soldQuantity" : 9}
If the transaction ID does not exist, the process will skip that message.

Cache:
I have used a thread safe least recently cache. The record get evicted when the size of the cache is more then 300 or when the trasaction settles.
After a trasaction settles we can either update the cache accordingly or evict it from the cache so that the next call gets the updated data.


Test Cases:
Didnt get time to write the test cases but these are the test cases i would have wrote:
1) Mock the services
 a) Test if the user data is getting stored in the database
 b) Test the withdraw api, and see if i'm getting the right answer (SUFFICIENT_BALANCE/INSUFFICIENT_BALANCE)
 c) Check if the cache contains the most recently used record
 d) Check if the kafka invalid format throws an error
 e) Check if kafka message with a invalid trasaction id is skipped
 f) check if kafka message is being consumed and transaction is updated and stored in the db.
 
2) Check if the cache removes the LRU entry as soon as it reaches it max size
 

Assumptions/Error Conditions
1) I assume that in the kafka message the sold quantity<=requested quantity
2) I'm not looking at the precision of the quantity
3) I'm assuming the kafka message will be passed as a string.
