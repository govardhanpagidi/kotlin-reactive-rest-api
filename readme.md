# Spring boot Reactive API using Kotlin


[![Build Status](https://travis-ci.com/govardhanpagidi/kotlin-reactive-rest-api.svg?branch=main)](https://travis-ci.com/govardhanpagidi/kotlin-reactive-rest-api)


* Uses mongodb as database
  * REST api for CRUD operations on a Fx Rate Conversions
      * GET /conversions/{id}
      * GET /conversions
      * POST /conversions
        Request Body:
        ```json
        {
            "fromCurrency" : "EUR",
            "toCurrency":"USD",
            "amount" : 1000
         }
        ```


