# Microservices - Mini Shoping Services

## Introduction
This is a showcase of 3 microservices Fully conteneraized with all it dependency, working side by side to do simple task that start with you **Creating Order** and then **Make Some Payment**. In doing so, these microservices will utilize various important concept of microservices such as **Message Queque**, **REST API**, **Containerization**, **Log Interceptor**, **Error Handling**, and many more.

All This services are build utilizing several technology such as:
* SpringBoot v2.3.4
* Java 8
* Docker v19.03.12
* Docker-Compose v1.27.2
* MySQL v8
* RabbitMQ v3

## Prerequisite
In order to start the services smoothly, it is strongly reccomended that you have docker-compose and docker instaled. To do so you can visit this [LINK](https://www.docker.com/products/docker-desktop) to install docker desktop suitable with your operating system.
>Both docker and docker-compose are included in Docker Desktop

## General Instruction

To run all the services including its dependency make sure you have all the prerequisite. Once all prerequisite all meet, there is only several step left which will be explained on the later section


## Running the Services
It is crucial to do it in sequence before starting the next step

### 1. Clone this Repository
Go to your terminal or command prompt and clone this repository with the following command
```script
git clone https://github.com/alexanderkevin/ms-inventory.git
```

After the cloning process finish, navigate to the root directory of this project with the following command
```script
cd ms-inventory
```

### 2. Bring all Services up
On the root directory turn on all services with docker-compose
```script
docker-compose up
```
>Wait for a couple of minutes until you see something similar to this 
**... Started Ms[XXX]Application in 28.689 seconds ...**

Thats it, all services are up and running. Next step would explain on how the Microservices works and the background.

## Using the Services
First of all congratulation on setting and running all those services on your computer, now lets begin the fun stuff and let me welcome you to the Mini Shopping Services!

### 1. Let's Go Shopping
In this Mini Shopping Services, you gonna want to see all the shop can offer you. Lets start by seing our catalog right on our browser. Go fire up your browser and enter the following URL
```script
http://localhost:8001/swagger-ui.html
```
This will direct you to the **MS-Inventory's Swagger UI**. Let's go to the catalogs by expanding the ***Inventory Controller**. After that that find the following **/api/v1/ms-inventory/inventory/all**. Hit that **try it out** and then the **execute** button.

You will receive the following response
```script
[
  {
    "id": 1,
    "created_at": 1602530462,
    "updated_at": null,
    "product_name": "Baju",
    "price": 50000,
    "quantity": 20,
    "currency": "IDR"
  },
  {
    "id": 2,
    "created_at": 1602530462,
    "updated_at": 1602594773.363,
    "product_name": "Celana Jeans",
    "price": 150000,
    "quantity": 30,
    "currency": "IDR"
  },
  {
    "id": 3,
    "created_at": 1602530462,
    "updated_at": 1602594768.441,
    "product_name": "Celana Katun",
    "price": 50000,
    "quantity": 25,
    "currency": "IDR"
  },
  {
    "id": 4,
    "created_at": 1602530462,
    "updated_at": null,
    "product_name": "Set Piyama",
    "price": 150000,
    "quantity": 50,
    "currency": "IDR"
  },
  {
    "id": 5,
    "created_at": 1602530462,
    "updated_at": null,
    "product_name": "Topi",
    "price": 30000,
    "quantity": 100,
    "currency": "IDR"
  }
]
```

From this information we can see that there are 5 items with its own stock as well as prices. For today let's buy a couple of Celana Jeans and Celana Katun. We sure love Pants don't we!. Now remember the id of both of them which are 2 and 3 respectively.

### 2. Putting an Order
After you know which item you want to buy, now its time to put an order to buy all those shiny new items.

To do so, navigate to the swagger-ui of MS-Order by entering the following address on your favorite browser
```script
http://localhost:8003/swagger-ui.html
```

After it open up, navigate to the order-controller and expand it. Find the **/api/v1/ms-order/order-detail/create** and again expand it.

Click **try it out** and then enter the following example on the New Order Detail Box
```script
{
  "currency": "IDR",
  "orderDetail": [
    {
      "item_id": 3,
      "qty": 10
    },
    {
      "item_id": 2,
      "qty": 2
    }
  ],
  "status": "WAITING_FOR_PAYMENT"
}
```
What it does is it create an order in which you buy 10 Celana Katun with item_id 3  and 2 Celana Jeans with item_id 2 on which you prefer to buy using IDR(Indonesian Rupiah).

Press the execute button and you will receive the following response
```script
{
  "id": 3,
  "created_at": 1602594767.637,
  "updated_at": 1602594767.642,
  "status": "WAITING_FOR_PAYMENT",
  "totalAmount": 800000,
  "currency": "IDR",
  "orderDetail": [
    {
      "id": 4,
      "created_at": 1602594768.257,
      "updated_at": 1602594768.257,
      "item_id": 3,
      "qty": 10,
      "basePrice": 50000,
      "total": 500000
    },
    {
      "id": 5,
      "created_at": 1602594773.342,
      "updated_at": 1602594773.342,
      "item_id": 2,
      "qty": 2,
      "basePrice": 150000,
      "total": 300000
    }
  ]
}
```
This response will tell you that the grant total amount you need to pay are 800.000 IDR and the detail of the individual price for each item as well as the total price for each item type.

In the background, by putting an order on **MS-Order**, it will automatically trigger **MS-Inventory** to reduce the current inventory for the mentioned item as well as trigger **MS-Payment** to start creating a bill. All of this are achieved through communication using message broker in this case RabbitMQ. 

So **MS-Order** are putting a message on the 'mailbox' of **MS-Inventory** and **MS-Payment** on which eventually they will see the message and know what they need to do.

Using message broker in here will giving us a better guarantee of the data being process and acknowledge by the recepient even in the case of recepient failure or system crashing in general. This are the important concept of microservices in which we have to make sure that we can impement services that are **fault-tollerant**.

### 3. Making Payment

After an order has been successfully created, you can now open up a new tab and navigate to MS-Payment by entering the following URL on your favorite browser:
```script
http://localhost:8002/swagger-ui.html
```

We first have to know whether our bill are actually there, to do so afte the page fully loaded up. Navigate to the **Payment Controller** and expand it.
Then lets see all the bill that are available and see which one is ours. To do so, find the **/api/v1/ms-payment/payment/all
getAll** on the Payment Controller that you've expand previously.

On that tab, click on **try it out** then press the **execute** button

```script
[
  {
    "id": 1,
    "created_at": 1602583667.262,
    "updated_at": 1602583667.262,
    "trxId": "TESTTRXCODE",
    "amount": 500000,
    "currency": "IDR",
    "status": "WAITING_FOR_PAYMENT",
    "orderId": 1
  },
  {
    "id": 2,
    "created_at": 1602594778.49,
    "updated_at": 1602594778.495,
    "trxId": null,
    "amount": 800000,
    "currency": "IDR",
    "status": "WAITING_FOR_PAYMENT",
    "orderId": 3
  }
]
```
from that response we can know for sure that our bill are the one with **"id": 2,** becuase it has our orderId which are 3. 

Now we can proceed to finally make the payment and receive our goods. Scroll a little up and find the **/api/v1/ms-payment/payment/{id}/make-payment** on  **Payment Controller** and expand it. Again hit the *Try it out* button and put our bill number (which are **2**) on the id input box.

After that press execute and you will get the following response
```script
{
  "id": 2,
  "created_at": 1602594778.49,
  "updated_at": 1602595712.162,
  "trxId": "69025948-50e4-4eb3-abe8-8644b645e864",
  "amount": 800000,
  "currency": "IDR",
  "status": "PAID",
  "orderId": 3
}
```

From here we can know that the payment are succesfull and it has the transaction id of 69025948-50e4-4eb3-abe8-8644b645e864.

One last step is to check whether our order is now completed. Lets navigate one more time to the MS-Order Swagger-UI by entering the following URL

```script
http://localhost:8003/swagger-ui.html
```

Navigate to the **Order Controller** and expand it, then go to the **/api/v1/ms-order/order/{id}/detail** and hit those **try it out** button. Enter our order id which are *3* to the id input box and press the **execute** button. You should receive the wonderfull news like this one 
```script
{
  "id": 3,
  "created_at": 1602594767.637,
  "updated_at": 1602595712.242,
  "status": "ORDER_COMPLETED",
  "totalAmount": 800000,
  "currency": "IDR",
  "orderDetail": [
    {
      "id": 4,
      "created_at": 1602594768.257,
      "updated_at": 1602594768.257,
      "item_id": 3,
      "qty": 10,
      "basePrice": 50000,
      "total": 500000
    },
    {
      "id": 5,
      "created_at": 1602594773.342,
      "updated_at": 1602594773.342,
      "item_id": 2,
      "qty": 2,
      "basePrice": 150000,
      "total": 300000
    }
  ]
}
```
 

And thats it! your order status are completed and time to enjoy all those good stuff that you just bought!

Congratulation you have just finish reading everything and hope you can better understand how microservices and how its component are working together to create this fascinating technology! 

~ All the best 

## Appendix
Please find below all of the services endpoint as well as rabbitMQ and MySQLDB
1. MS-Inventory
```script
http://localhost:8001/swagger-ui.html
```
2. MS-Payment
```script
http://localhost:8002/swagger-ui.html
```
3. MS-Order
```script
http://localhost:8003/swagger-ui.html
```
4. RabbitMQ
```script
http://localhost:15672/
```
> ID = guest | Password = guest
5. MS-Inventory DB
```
jdbc:mysql://localhost:3306/ms-inventory?useSSL=false&allowPublicKeyRetrieval=true
```
6. MS-Payment DB
```
jdbc:mysql://localhost:3307/ms-payment?useSSL=false&allowPublicKeyRetrieval=true
```
7. MS-Order DB
```
jdbc:mysql://localhost:3308/ms-order?useSSL=false&allowPublicKeyRetrieval=true
```
> All Database ID are admin and Password root