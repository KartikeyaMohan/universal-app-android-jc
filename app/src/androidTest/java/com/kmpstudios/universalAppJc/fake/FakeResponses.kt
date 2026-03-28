package com.kmpstudios.universalAppJc.fake

object FakeResponses {

    val moviesSuccessResponse = """
        {
          "status": 200,
          "data": {
            "list": [
              { "id": 1, "name": "Tenet", "heroImageUrl": "", "rating": 4.5 },
              { "id": 2, "name": "Inception", "heroImageUrl": "", "rating": 4.8 },
              { "id": 3, "name": "Interstellar", "heroImageUrl": "", "rating": 4.7 },
              { "id": 4, "name": "Dunkirk", "heroImageUrl": "", "rating": 4.2 },
              { "id": 5, "name": "Oppenheimer", "heroImageUrl": "", "rating": 4.9 }
            ],
            "meta": { "prevPage": null, "nextPage": null }
          }
        }
    """.trimIndent()

    val moviesEmptyResponse = """
        {
          "status": 200,
          "data": {
            "list": [],
            "meta": { "prevPage": null, "nextPage": null }
          }
        }
    """.trimIndent()

    val moviesErrorResponse = """
        {
          "status": 500,
          "errors": [{ "type": "DEFAULT_ERROR", "message": "Something went wrong" }]
        }
    """.trimIndent()

    val movieDetailSuccessResponse = """
        {
          "status": 200,
          "data": {
            "id": 1,
            "name": "Tenet",
            "description": "A mind-bending thriller about time inversion.",
            "rating": 4.5,
            "trailerUrl": "",
            "casts": [
              { "id": 1, "name": "John David Washington", "castType": "Actor", "imageUrl": "" },
              { "id": 2, "name": "Robert Pattinson", "castType": "Actor", "imageUrl": "" },
              { "id": 3, "name": "Christopher Nolan", "castType": "Director", "imageUrl": "" }
            ]
          }
        }
    """.trimIndent()

    val movieDetailErrorResponse = """
        {
          "status": 404,
          "errors": [{ "type": "DEFAULT_ERROR", "message": "Movie not found" }]
        }
    """.trimIndent()

    val loginSuccessResponse = """
        {
          "status": 200,
          "data": {
            "id": 1,
            "tokens": {
              "auth_token": "test_auth_token",
              "refresh_token": "test_refresh_token"
            }
          }
        }
    """.trimIndent()

    val registerSuccessResponse = """
        {
          "status": 200,
          "data": {
            "id": 2,
            "tokens": {
              "auth_token": "new_auth_token",
              "refresh_token": "new_refresh_token"
            }
          }
        }
    """.trimIndent()
}