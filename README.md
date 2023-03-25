# Drones Project
HealthDrones is a Spring Boot project using Gradle.

## Getting Started

These instructions will show you how to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

* Java 8 or later
* Gradle 

### Installing

A step by step series of examples that tell you how to get a development env running:

1. Clone the repository: `git clone https://github.com/htaimal/drones.git`
2. Build the project: `./gradlew build`
3. Run the project: `./gradlew bootRun`
4. Stop the project: `./gradlew --stop`

## Running the Tests

Explain how to run the automated tests for this system:

* Run all tests: `./gradlew test`

# Testing with Postman

To test the API endpoints, you can use the included Postman collection located in the "postman" directory. To import the collection in Postman, follow these steps:

1. Open Postman and click on the "Import" button in the top left corner.

2. Select the "Import From Folder" option and browse to the "postman" directory in your project.

3. Select the exported collection file and click "Open".

4. Once the collection is imported, you can use it to test the API endpoints.

# Testing with CURL

## Register new Drone
### request
````cmd
curl --location --request POST 'http://localhost:8080/drone' \
--header 'Content-Type: application/json' \
--data-raw '{
    "serial":"SN234222",
    "model":"M",
    "maxLoadCapacity":150,
    "battery":56,
    "medications": [],
    "state":"LOADING"
}'
````
### response
````json
{
  "id": 12,
  "serial": "SN234222",
  "model": "M",
  "maxLoadCapacity": 150,
  "battery": 56,
  "state": "LOADING",
  "medications": [],
  "lowBattery": false
}
````

## Loading Drone
### request1
````cmd
curl --location --request POST 'http://localhost:8080/drone/4/medication' \
--header 'Content-Type: application/json' \
--data-raw '[
1,2,3,4
]'
````
### response1
````
Item with weight 500 exceeds available capacity 450
````

### request2
````cmd
curl --location --request POST 'http://localhost:8080/drone/4/medication' \
--header 'Content-Type: application/json' \
--data-raw '[
1,2,3
]'
````

### response
````json
{
  "id": 4,
  "serial": "SN234224",
  "model": "H",
  "maxLoadCapacity": 450,
  "battery": 60,
  "state": "RETURNING",
  "medications": [
    {
      "id": 1,
      "name": "Ibuprofen",
      "weight": 50,
      "code": "DIP",
      "imageUrl": "https://img1.freepng.es/20180723/jt/kisspng-ibuprofen-tablet-analgesic-fever-brand-5b5641a183f382.4115227915323795535405.jpg"
    },
    {
      "id": 2,
      "name": "Ibuprofen",
      "weight": 100,
      "code": "DIP",
      "imageUrl": "https://img1.freepng.es/20180723/jt/kisspng-ibuprofen-tablet-analgesic-fever-brand-5b5641a183f382.4115227915323795535405.jpg"
    },
    {
      "id": 3,
      "name": "Ibuprofen",
      "weight": 150,
      "code": "DIP",
      "imageUrl": "https://img1.freepng.es/20180723/jt/kisspng-ibuprofen-tablet-analgesic-fever-brand-5b5641a183f382.4115227915323795535405.jpg"
    }
  ],
  "lowBattery": false
}
````

## Checking Drone
### request
````cmd
curl --location --request GET 'http://localhost:8080/drone/4' \
--header 'Content-Type: application/json' 

````
### response
````json
{
  "id": 4,
  "serial": "SN234224",
  "model": "H",
  "maxLoadCapacity": 450,
  "battery": 60,
  "state": "RETURNING",
  "medications": [
    {
      "id": 1,
      "name": "Ibuprofen",
      "weight": 50,
      "code": "DIP",
      "imageUrl": "https://img1.freepng.es/20180723/jt/kisspng-ibuprofen-tablet-analgesic-fever-brand-5b5641a183f382.4115227915323795535405.jpg"
    },
    {
      "id": 2,
      "name": "Ibuprofen",
      "weight": 100,
      "code": "DIP",
      "imageUrl": "https://img1.freepng.es/20180723/jt/kisspng-ibuprofen-tablet-analgesic-fever-brand-5b5641a183f382.4115227915323795535405.jpg"
    },
    {
      "id": 3,
      "name": "Ibuprofen",
      "weight": 150,
      "code": "DIP",
      "imageUrl": "https://img1.freepng.es/20180723/jt/kisspng-ibuprofen-tablet-analgesic-fever-brand-5b5641a183f382.4115227915323795535405.jpg"
    }
  ],
  "lowBattery": false
}
````


## Checking Drone Battery
### request
````cmd
curl --location --request GET 'http://localhost:8080/drone/2/battery' \
--header 'Content-Type: application/json' 

````
### response
````json
70
````

## Get Available Drones
### request
````cmd
curl --location --request GET 'http://localhost:8080/drone/available' \
--header 'Content-Type: application/json' 

````
### response
````json
[
  {
    "id": 1,
    "serial": "SN234221",
    "model": "L",
    "maxLoadCapacity": 50,
    "battery": 100,
    "state": "LOADED",
    "medications": [],
    "lowBattery": false
  },
  {
    "id": 2,
    "serial": "SN234222",
    "model": "M",
    "maxLoadCapacity": 150,
    "battery": 70,
    "state": "LOADING",
    "medications": [],
    "lowBattery": false
  },
  {
    "id": 3,
    "serial": "SN234223",
    "model": "C",
    "maxLoadCapacity": 250,
    "battery": 20,
    "state": "DELIVERING",
    "medications": [],
    "lowBattery": true
  },
  {
    "id": 5,
    "serial": "SN234225",
    "model": "C",
    "maxLoadCapacity": 250,
    "battery": 20,
    "state": "DELIVERING",
    "medications": [],
    "lowBattery": true
  },
  {
    "id": 6,
    "serial": "SN234226",
    "model": "H",
    "maxLoadCapacity": 450,
    "battery": 50,
    "state": "RETURNING",
    "medications": [],
    "lowBattery": false
  },
  {
    "id": 7,
    "serial": "SN234227",
    "model": "C",
    "maxLoadCapacity": 250,
    "battery": 80,
    "state": "DELIVERED",
    "medications": [],
    "lowBattery": false
  },
  {
    "id": 8,
    "serial": "SN234228",
    "model": "H",
    "maxLoadCapacity": 450,
    "battery": 20,
    "state": "RETURNING",
    "medications": [],
    "lowBattery": true
  },
  {
    "id": 9,
    "serial": "SN234229",
    "model": "H",
    "maxLoadCapacity": 500,
    "battery": 100,
    "state": "IDLE",
    "medications": [],
    "lowBattery": false
  },
  {
    "id": 10,
    "serial": "SN234230",
    "model": "H",
    "maxLoadCapacity": 500,
    "battery": 20,
    "state": "LOADED",
    "medications": [],
    "lowBattery": true
  },
  {
    "id": 11,
    "serial": "SN234222",
    "model": "M",
    "maxLoadCapacity": 150,
    "battery": 56,
    "state": "LOADING",
    "medications": [],
    "lowBattery": false
  },
  {
    "id": 12,
    "serial": "SN234222",
    "model": "M",
    "maxLoadCapacity": 150,
    "battery": 56,
    "state": "LOADING",
    "medications": [],
    "lowBattery": false
  }
]
````