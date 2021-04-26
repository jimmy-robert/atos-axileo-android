package cloud.robert.mcumovies.business.network

import cloud.robert.mcumovies.business.models.responses.ApiResponse
import retrofit2.http.GET

interface McuApi {
    @GET("/api/items/4461acb8-6202-4ce9-a5e3-f3647ad7e5fa")
    suspend fun fetchData(): ApiResponse
}