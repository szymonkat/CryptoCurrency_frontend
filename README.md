# Crypto-exchange project
Szymon Katarzynski

### 1. Project description
The project consist of backend crypto client service, based on REST architecture and frontend layer,
based on Vaadin. <br>
You can add wallets, top up funds and download currency ratings from Nomics or CoinLayer. 
Ratings are also downloaded by a scheduler. You can finalize the transaction using item to buy options. <br>

There is also an analyzer and split view of all tables in the program. 

### 2. Demo
Project is not uploaded to remote server yet.

### 3. Requirements
Please make sure You have following software:
1) Java 11
2) Gradle 6.6.1
3) MySQL

### 4. Project
In order to run project: <br>
1) Create a database in MySQL, based on application.properties settings 
2) Run "TestApplication" in project_back repository (https://github.com/szymonkat/CryptoCurrency_backend).
3) Run "TestApplication" in project_front repository (https://github.com/szymonkat/CryptoCurrency_frontend).
4) Enter "http://localhost:8082/" in Your browser

### 5. Endpoint description
For a documentation You can visit "http://localhost:8083/swagger-ui.html" (vaadin_back).

### 6. Future plans

1) Spring authentication
2) Transactional features
3) Automatic mailing features
4) Selling cryptocurrencies feature and trading within wallets

### 7. Troubleshooting
If You encounter any problems regarding operation, please let me know. 
<br>
Currently I am aware of ongoing issues (I am working on them):
1) Bidirectional oneToMany relation wallet - wallet item
2) H2 tests database set up
