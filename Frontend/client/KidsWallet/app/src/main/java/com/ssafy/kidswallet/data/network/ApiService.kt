package com.ssafy.kidswallet.data.network

import com.ssafy.kidswallet.data.model.AccountDepositModel
import com.ssafy.kidswallet.data.model.AccountModel
import com.ssafy.kidswallet.data.model.AccountTransferModel
import com.ssafy.kidswallet.data.model.AccountWithdrawModel
import com.ssafy.kidswallet.data.model.ApiResponse
import com.ssafy.kidswallet.data.model.BeggingRequestModel
import com.ssafy.kidswallet.data.model.LoginModel
import com.ssafy.kidswallet.data.model.MissionResponse
import com.ssafy.kidswallet.data.model.RelationModel
import com.ssafy.kidswallet.data.model.SignUpModel
import com.ssafy.kidswallet.data.model.TogetherDetailResponse
import com.ssafy.kidswallet.data.model.TogetherListModel
import com.ssafy.kidswallet.data.model.TogetherListResponse
import com.ssafy.kidswallet.data.model.UserDataModel
import com.ssafy.kidswallet.fcm.dto.FcmDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("user/register")
    suspend fun registerUser(@Body signUpModel: SignUpModel): Response<Any>

    @POST("user/login")
    suspend fun loginUser(@Body loginModel: LoginModel): Response<ApiResponse>

    @POST("user/relation")
    suspend fun addRelation(@Body relationModel: RelationModel): Response<Any>

    @POST("account/regist/{userId}")
    suspend fun registerAccount(@Path("userId") userId: Int): Response<Any>

    @PATCH("user/card/{userId}")
    suspend fun registerCard(@Path("userId") userId: Int): Response<Any>

    @GET("account/view/transaction")
    suspend fun viewTransaction(@Query("id") accountId: String): Response<AccountModel>

    @POST("mission/beg")
    suspend fun beggingRequest(@Body beggingRequestModel: BeggingRequestModel): Response<Any>

    @GET("mission/list/{userId}")
    suspend fun beggingMissionList(
        @Path("userId") userId: Int,
        @Query("start") start: Int,
        @Query("end") end: Int
    ): Response<MissionResponse>

    @POST("fcm/token") // 기본 URL이 설정된 상태에서 상대 경로만 사용
    fun sendTokenToServer(@Body requestDto: FcmDto): Call<FcmDto> // Call<FcmDto>로 반환 타입 수정

    @PATCH("account/transfer")
    suspend fun accountTransfer(@Body accountTransferModel: AccountTransferModel): Response<Any>

    @PATCH("account/deposit")
    suspend fun accountDeposit(@Body accountDepositModel: AccountDepositModel): Response<Any>

    @PATCH("account/withdraw")
    suspend fun accountWithdraw(@Body accountWithdrawModel: AccountWithdrawModel): Response<Any>

    @GET("togetherrun/{userId}/list")
    suspend fun togetherList(@Path("userId") userId: Int): Response<TogetherListResponse>

    @GET("togetherrun/{savingContractId}/detail")
    suspend fun togetherDetail(@Path("savingContractId") savingContractId: Int): Response<TogetherDetailResponse>
}