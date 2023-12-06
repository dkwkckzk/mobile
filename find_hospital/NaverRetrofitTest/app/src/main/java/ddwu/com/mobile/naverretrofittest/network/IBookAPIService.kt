package ddwu.com.mobile.naverretrofittest.network

import retrofit2.Call
import retrofit2.http.Header

interface IBookAPIService {

    fun getBooksByKeyword (
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,

    )  : Call<     >

}