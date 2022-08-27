package cl.armin20.ecos.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EcosBookRetrofitClient {
    companion object {
        private const val BASE_URL = "https://ecos-book-api.herokuapp.com/"

        fun retrofitInstance(): BooksApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(BooksApi::class.java)
        }
    }
}