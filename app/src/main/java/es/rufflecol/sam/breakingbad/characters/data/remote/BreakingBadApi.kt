package es.rufflecol.sam.breakingbad.characters.data.remote

import es.rufflecol.sam.breakingbad.characters.data.remote.dto.BreakingBadCharacterDto
import retrofit2.Response
import retrofit2.http.GET

interface BreakingBadApi {

    @GET("characters")
    suspend fun fetchAllCharacters(): Response<List<BreakingBadCharacterDto>>

}