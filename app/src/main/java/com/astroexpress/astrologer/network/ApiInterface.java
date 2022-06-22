package com.astroexpress.astrologer.network;

import com.astroexpress.astrologer.models.request.BlockUserRequestModel;
import com.astroexpress.astrologer.models.request.ChatRequestModel;
import com.astroexpress.astrologer.models.request.ChatSessionRequestModel;
import com.astroexpress.astrologer.models.request.RaiseTicketRequestModel;
import com.astroexpress.astrologer.models.request.SaveNextOnlineTimeRequestModel;
import com.astroexpress.astrologer.models.request.UserRatingReviewRequestModel;
import com.astroexpress.astrologer.models.response.ApplyOfferResponseModel;
import com.astroexpress.astrologer.models.response.AstrologerContentModel;
import com.astroexpress.astrologer.models.response.AstrologerStatusModel;
import com.astroexpress.astrologer.models.response.BannersModel;
import com.astroexpress.astrologer.models.response.BlockUserResponseModel;
import com.astroexpress.astrologer.models.response.BoostModel;
import com.astroexpress.astrologer.models.response.ChatListResponseModel;
import com.astroexpress.astrologer.models.response.ChatRunningSessionResponseModel;
import com.astroexpress.astrologer.models.response.ChatSessionResponseModel;
import com.astroexpress.astrologer.models.response.EarningResponseModel;
import com.astroexpress.astrologer.models.response.LastMonthEarningModel;
import com.astroexpress.astrologer.models.response.LoginsModel;
import com.astroexpress.astrologer.models.response.NextOnlineResponseModel;
import com.astroexpress.astrologer.models.response.NotifyUsersResponseModel;
import com.astroexpress.astrologer.models.response.OfferResponseModel;
import com.astroexpress.astrologer.models.response.RaiseTicketResponseModel;
import com.astroexpress.astrologer.models.response.SaveChatResponseModel;
import com.astroexpress.astrologer.models.response.SaveNextOnlineTimeResponseModel;
import com.astroexpress.astrologer.models.response.SaveRemedyResponseModel;
import com.astroexpress.astrologer.models.response.StatusModel;
import com.astroexpress.astrologer.models.response.SuggestRemedyCategoryModel;
import com.astroexpress.astrologer.models.response.SuggestedRemedyModel;
import com.astroexpress.astrologer.models.response.TestimonialModels;
import com.astroexpress.astrologer.models.response.UserRatingReviewResponseModel;
import com.astroexpress.astrologer.models.response.UsersModel;
import com.astroexpress.astrologer.models.response.WalletsModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("astrologer/login")
    Call<LoginsModel> login(@Query("Email") String Email,@Query("Password") String Password);

    @GET("astrologer/banner")
    Call<BannersModel> banner();

    @GET("astrologer/testimonial")
    Call<TestimonialModels> testimonial();

    @GET("astrologer/getWalletTransaction")
    Call<WalletsModel> wallet(@Query("AstrologerId") String astrologerId);

    @POST("astrologer/updateOnlineStatus")
    Call<StatusModel> statusUpdate(@Query("AstrologerId") String astrologerId,@Query("IsOnlineForChat") String onlineChat,@Query("IsOnlineForCall") String onlineCall,@Query("IsOnlineForChatEM") String onlineChatEM,@Query("IsOnlineForCallEM") String onlineCallEM);

    @GET("astrologer/getRecentUser")
    Call<UsersModel> recentUsers(@Query("AstrologerId") String astrologerId);

    @POST("user/saveChat")
    Call<SaveChatResponseModel> saveChat(@Body ChatRequestModel chatRequestModel);

    @POST("user/updateChat")
    Call<SaveChatResponseModel> updateChat(@Body List<ChatRequestModel> chatRequestModelList);

    @GET("user/getChat")
    Call<ChatListResponseModel> getChatList(@Query("UserId") String UserId, @Query("AstrologerId") String AstrologerId);

    @GET("astrologer/getAstrologerOffer")
    Call<OfferResponseModel> getOffersList(@Query("AstrologerId") String astrologerId);

    @POST("astrologer/applyOffer")
    Call<ApplyOfferResponseModel> applyOffer(@Query("AstrologerId") String astrologerId,@Query("IsOfferApplied") boolean isOfferApplied,@Query("OfferId") String offerId);

    @GET("astrologer/getUserDetail")
    Call<NotifyUsersResponseModel> getUserForRequest(@Query("UserId") String userId);

    @GET("astrologer/astrologerStatus")
    Call<AstrologerStatusModel> getAstrologerStatus(@Query("AstrologerId") String astrologerId);

    @GET("astrologer/getEarningReport")
    Call<EarningResponseModel> getEarnings(@Query("AstrologerId") String astrologerId,@Query("StartDate") String startDate,@Query("EndDate") String endDate);

    @POST("astrologer/submitQuery")
    Call<RaiseTicketResponseModel> submitQuery(@Body RaiseTicketRequestModel raiseTicketRequestModel);

    @GET("astrologer/getLastMonthEarningReport")
    Call<LastMonthEarningModel> lastMonthEarningReport(@Query("AstrologerId") String astrologerId);

    @GET("astrologer/frequentAstrologerContent")
    Call<AstrologerContentModel> getAstrologerContent(@Query("AstrologerId")String astrologerId);

    @POST("astrologer/updateBoostStatus")
    Call<BoostModel> setBoost(@Query("AstrologerId")String astrologerId,@Query("IsEnabled")String isEnabled);
    
    @GET("astrologer/getSuggestedRemedy")
    Call<SuggestedRemedyModel> getSuggestedRemedy(@Query("AstrologerId")String astrologerId,@Query("UserId")String userId);

    @POST("astrologer/saveRemedy")
    Call<SaveRemedyResponseModel> saveRemedy(@Body RequestBody requestBody);

    @GET("astrologer/getRemedyCategory")
    Call<SuggestRemedyCategoryModel> getSuggestRemedyCategory();

    @POST("astrologer/saveUserRating")
    Call<UserRatingReviewResponseModel> saveRatingReview(@Body UserRatingReviewRequestModel userRatingReviewRequestModel);

    @POST("astrologer/blockUser")
    Call<BlockUserResponseModel> blockUser(@Body BlockUserRequestModel blockUserRequestModel);

    @POST("astrologer/saveNextOnline")
    Call<SaveNextOnlineTimeResponseModel> saveOnlineTime(@Body SaveNextOnlineTimeRequestModel saveNextOnlineTimeRequestModel);

    @GET("astrologer/getNextOnline")
    Call<NextOnlineResponseModel> getOnlineTime(@Query("AstrologerId")String astrologerId);

    @POST("astrologer/createChatSession")
    Call<ChatSessionResponseModel> createChatSession(@Body ChatSessionRequestModel chatSessionRequestModel);

    @GET("astrologer/getChatRunningSession")
    Call<ChatRunningSessionResponseModel> getChatRunningSession(@Query("AstrologerId")String astrologerId);
}
