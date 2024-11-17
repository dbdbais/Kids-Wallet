package com.ssafy.kidswallet.data.network

import com.ssafy.kidswallet.data.model.AccountDepositModel
import com.ssafy.kidswallet.data.model.AccountModel
import com.ssafy.kidswallet.data.model.AccountTransferModel
import com.ssafy.kidswallet.data.model.AccountWeeklyResponse
import com.ssafy.kidswallet.data.model.AccountWithdrawModel
import com.ssafy.kidswallet.data.model.ApiResponse
import com.ssafy.kidswallet.data.model.BeggingRequestModel
import com.ssafy.kidswallet.data.model.GiveMissionModel
import com.ssafy.kidswallet.data.model.HandleMissionModel
import com.ssafy.kidswallet.data.model.LoginModel
import com.ssafy.kidswallet.data.model.MissionResponse
import com.ssafy.kidswallet.data.model.PlayMissionModel
import com.ssafy.kidswallet.data.model.RelationModel
import com.ssafy.kidswallet.data.model.SignUpModel
import com.ssafy.kidswallet.data.model.TestMissionModel
import com.ssafy.kidswallet.data.model.TogetherCompleteListResponse
import com.ssafy.kidswallet.data.model.TogetherDetailResponse
import com.ssafy.kidswallet.data.model.TogetherListModel
import com.ssafy.kidswallet.data.model.TogetherListResponse
import com.ssafy.kidswallet.data.model.TogetherrunReisterModel
import com.ssafy.kidswallet.data.model.UserDataModel
import com.ssafy.kidswallet.fcm.dto.FcmDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
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
    suspend fun accountTransfer(@Body accountTransferModel: AccountTransferModel): Response<Unit>

    @PATCH("account/deposit")
    suspend fun accountDeposit(@Body accountDepositModel: AccountDepositModel): Response<Unit>

    @PATCH("account/withdraw")
    suspend fun accountWithdraw(@Body accountWithdrawModel: AccountWithdrawModel): Response<Unit>

    @GET("account/weekly")
    suspend fun getAccountWeekly(@Query("accountId") accountId: String): Response<AccountWeeklyResponse>

    @GET("togetherrun/{userId}/proceedlist")
    suspend fun togetherList(@Path("userId") userId: Int): Response<TogetherListResponse>

    @GET("togetherrun/{userId}/completelist")
    suspend fun togetherCompleteList(@Path("userId") userId: Int): Response<TogetherCompleteListResponse>

    @GET("togetherrun/{togetherRunId}/detail")
    suspend fun togetherDetail(@Path("togetherRunId") togetherRunId: Int): Response<TogetherDetailResponse>

    @PUT("mission/beg")
    suspend fun handleMission(@Body handleMissionModel: HandleMissionModel): Response<Unit>

    @POST("mission/assign")
    suspend fun giveMission(@Body giveMissionModel: GiveMissionModel): Response<Unit>

    @PUT("mission/complete")
    suspend fun playMission(@Body playMissionModel: PlayMissionModel): Response<Unit>

    @PUT("mission/complete/check")
    suspend fun testMission(@Body testMissionModel: TestMissionModel): Response<Unit>

    @GET("user/status/{userId}")
    suspend fun updateUser(@Path("userId") userId: Int): Response<ApiResponse>

    @POST("togetherrun/register")
    suspend fun registerTogetherrun(@Body requestModel: TogetherrunReisterModel): Response<Unit>

    @DELETE("togetherrun/savings/{savingContractId}")
    suspend fun deleteTogetherRunSavings(@Path("savingContractId") savingContractId: Int): Response<Unit>

}