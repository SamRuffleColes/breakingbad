package es.rufflecol.sam.breakingbad.data.api

import es.rufflecol.sam.breakingbad.data.api.dto.BreakingBadCharacterDto
import retrofit2.Response
import retrofit2.http.GET

interface BreakingBadApi {

    @GET("characters")
    suspend fun fetchAllCharacters(): Response<List<BreakingBadCharacterDto>>

}