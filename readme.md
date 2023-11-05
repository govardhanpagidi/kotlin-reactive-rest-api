# Spring boot Reactive API using Kotlin


![Build](https://github.com/govardhanpagidi/kotlin-reactive-rest-api/actions/workflows/build.yml/badge.svg)
![Code Health](https://github.com/govardhanpagidi/kotlin-reactive-rest-api/actions/workflows/lint.yml/badge.svg)


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


