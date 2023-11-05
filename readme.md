# Spring boot Reactive API using Kotlin


![build.yaml](https://github.com/govardhanpagidi/kotlin-reactive-rest-api/actions/workflows/workflow-file.yml/badge.svg)


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


