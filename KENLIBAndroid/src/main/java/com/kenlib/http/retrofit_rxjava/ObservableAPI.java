package com.kenlib.http.retrofit_rxjava;

import java.util.List;

import io.reactivex.Observable;
import com.kenlib.http.retrofit_rxjava.dto.BGDto;
import com.kenlib.http.retrofit_rxjava.dto.BGInfoDto;
import com.kenlib.http.retrofit_rxjava.dto.BaseResponseDto;
import com.kenlib.http.retrofit_rxjava.dto.BaseResponseDto1;
import com.kenlib.http.retrofit_rxjava.dto.ClientContactsHandlerDto;
import com.kenlib.http.retrofit_rxjava.dto.ClientFinanceInfoHandlerDto;
import com.kenlib.http.retrofit_rxjava.dto.ClientinfoDto;
import com.kenlib.http.retrofit_rxjava.dto.EngagementHandler1Dto;
import com.kenlib.http.retrofit_rxjava.dto.EngagementHandlerDto;
import com.kenlib.http.retrofit_rxjava.dto.FinLedgerHandlerDto;
import com.kenlib.http.retrofit_rxjava.dto.GDDto;
import com.kenlib.http.retrofit_rxjava.dto.GDInfoDto;
import com.kenlib.http.retrofit_rxjava.dto.GGDto;
import com.kenlib.http.retrofit_rxjava.dto.GGInfoDto;
import com.kenlib.http.retrofit_rxjava.dto.GSDto;
import com.kenlib.http.retrofit_rxjava.dto.HHRWPDto;
import com.kenlib.http.retrofit_rxjava.dto.HTDto;
import com.kenlib.http.retrofit_rxjava.dto.JHSSDto;
import com.kenlib.http.retrofit_rxjava.dto.KHInfoDto;
import com.kenlib.http.retrofit_rxjava.dto.LXRDto;
import com.kenlib.http.retrofit_rxjava.dto.LeaveHandlerDto;
import com.kenlib.http.retrofit_rxjava.dto.MyWorkFlowsDto;
import com.kenlib.http.retrofit_rxjava.dto.NotificationInfoDto;
import com.kenlib.http.retrofit_rxjava.dto.ProjectHandler1Dto;
import com.kenlib.http.retrofit_rxjava.dto.ProjectHandlerDto;
import com.kenlib.http.retrofit_rxjava.dto.QJInfoDto;
import com.kenlib.http.retrofit_rxjava.dto.RCAPDto;
import com.kenlib.http.retrofit_rxjava.dto.TestDto;
import com.kenlib.http.retrofit_rxjava.dto.UserInfoDto;
import com.kenlib.http.retrofit_rxjava.dto.UserInfoDto1;
import com.kenlib.http.retrofit_rxjava.dto.WFCountDto;
import com.kenlib.http.retrofit_rxjava.dto.WorkFlowsDto;
import com.kenlib.http.retrofit_rxjava.dto.WorkFlowsDto1;
import com.kenlib.http.retrofit_rxjava.dto.WorkFlowsInfoDto;
import com.kenlib.http.retrofit_rxjava.dto.XMCBDto;
import com.kenlib.http.retrofit_rxjava.dto.XMCYDto;
import com.kenlib.http.retrofit_rxjava.dto.XMJHDto;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * // 注解里传入 网络请求 的部分URL地址
 * // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
 * // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
 * // 采用Observable<...>接口
 */
public interface ObservableAPI {

    @GET
    Observable<BaseResponseDto<TestDto>> checkServer(@Url String url);

    @FormUrlEncoded
    @POST("/api/userinfo.ashx?action=login")
    Observable<BaseResponseDto<TestDto>> login(@Field("username") String username, @Field("pwd") String pwd);

    @GET("/api/NotificationInfo.ashx?action=GetNotiList")
    Observable<BaseResponseDto<List<NotificationInfoDto>>> GetNotiList();

    @GET("/api/userinfo.ashx?action=GetUserInfo")
    Observable<BaseResponseDto<UserInfoDto>> GetUserInfo();

    @GET("/api/userinfo.ashx?action=GetOrgUserTree&user=1")
    Observable<BaseResponseDto<List<LXRDto>>> GetOrgUserTree(@Query("q") String q);

    @GET("/api/WFInfo.ashx?action=GETWFCOUNT&type=5")
    Observable<BaseResponseDto<WFCountDto>> GETWFCOUNT();

    @GET("/api/WFInfo.ashx?action=GetMyWorkFlows&pageSize=100&pageIndex=0")
    Observable<BaseResponseDto<List<MyWorkFlowsDto>>> GetMyWorkFlows(@Query("type") String type);

    @GET("/OA/Calendar/CalendarHandler.ashx?prefilter=My")
    Observable<RCAPDto> CalendarList();

    @GET("/PM/TimeSheet/TimesheetDetailHandler.ashx?prefilter=My")
    Observable<GSDto> gsList();

    @FormUrlEncoded
    @POST("/PM/Project/ProjectHandler.ashx?prefilter=DefaultAll")
    Observable<ProjectHandlerDto> ProjectHandler(
            @Field("page") int page
            , @Field("rows") int rows
            , @Field("sort") String sort
            , @Field("order") String order
            , @Field("filters") String filters,
            @Field("SearchText") String SearchText
            , @Field("eid") String eid);

    @FormUrlEncoded
    @POST("/PM/Client/ClientHandler.ashx?prefilter=DefaultAll")
    Observable<ClientinfoDto> clientinfo(
            @Field("page") int page
            , @Field("rows") int rows
            , @Field("sort") String sort
            , @Field("order") String order
            , @Field("SearchText") String SearchText);


    @FormUrlEncoded
    @POST("/PM/Engagement/EngagementHandler.ashx?prefilter=DefaultAll")
    Observable<EngagementHandlerDto> EngagementHandler(
            @Field("page") int page
            , @Field("rows") int rows
            , @Field("sort") String sort
            , @Field("order") String order
            , @Field("filters") String filters,
            @Field("SearchText") String SearchText);

    @FormUrlEncoded
    @POST("/PM/Archive/ArchiveHandler.ashx")
    Observable<GDDto> gdList(
            @Field("page") int page
            , @Field("rows") int rows
            , @Field("sort") String sort
            , @Field("order") String order
            , @Field("filters") String filters,
            @Field("SearchText") String SearchText);

    @GET("/api/ArchiveInfo.ashx?action=detail")
    Observable<BaseResponseDto<GDInfoDto>> gdInfo(@Query("id") String id);

    @GET("/api/EngagementInfo.ashx?action=detail")
    Observable<BaseResponseDto<List<EngagementHandler1Dto>>> EngagementInfo(@Query("id") String id);

    @GET("/api/BulletinInfo.ashx?action=detail")
    Observable<BaseResponseDto<GGInfoDto>> GGInfo(@Query("id") String id);

    @GET("/api/clientinfo.ashx?action=detail")
    Observable<BaseResponseDto<KHInfoDto>> clientinfo(@Query("id") String id);


    @GET("/PM/ClientContacts/ClientContactsHandler.ashx")
    Observable<ClientContactsHandlerDto> ClientContactsHandler(@Query("cid") String cid);

    @GET("/PM/ClientFinInfo/ClientFinanceInfoHandler.ashx")
    Observable<ClientFinanceInfoHandlerDto> ClientFinanceInfoHandler(@Query("cid") String cid);

    @GET("/PM/Finance/FinLedgerHandler.ashx")
    Observable<FinLedgerHandlerDto> FinLedgerHandler(@Query("cid") String cid);


    @GET("/api/ProjectInfo.ashx?action=detail")
    Observable<BaseResponseDto<List<ProjectHandler1Dto>>> ProjectInfo(@Query("id") String id);

    @GET("/PM/PrjStaff/PrjGroupScheduleHandler.ashx")
    Observable<XMJHDto> PrjGroupScheduleHandler(@Query("pid") String pid);

    @GET("/PM/PrjStaff/PrjStaffHandler.ashx")
    Observable<XMCYDto> PrjStaffHandler(@Query("pid") String pid);

    @GET("/api/EngagementInfo.ashx?action=GetEngagementContract")
    Observable<BaseResponseDto<List<JHSSDto>>> EngagementInfoEngagementID(@Query("EngagementID") String EngagementID);

    @GET("/PM/EngagementPartners/EngagementPartnersHandler.ashx")
    Observable<HHRWPDto> EngagementPartnersHandler(@Query("eid") String eid);

    @GET("/PM/Report/ProjectRptViewHandler.ashx")
    Observable<BGDto> ProjectRptViewHandler(@Query("pid") String pid);

    @FormUrlEncoded
    @POST("/PM/Report/ProjectRptViewHandler.ashx")
    Observable<BGDto> ProjectRptViewHandler(
            @Field("page") int page
            , @Field("rows") int rows
            , @Field("sort") String sort
            , @Field("order") String order
            , @Field("filters") String filters,
            @Field("SearchText") String SearchText
    );

    @FormUrlEncoded
    @POST("/PM/Leave/LeaveHandler.ashx")
    Observable<LeaveHandlerDto> LeaveHandler(
            @Field("page") int page
            , @Field("rows") int rows
            , @Field("sort") String sort
            , @Field("order") String order
            , @Field("filters") String filters,
            @Field("SearchText") String SearchText
    );


    @GET("/api/ReportInfo.ashx?action=detail")
    Observable<BaseResponseDto1<BGInfoDto>> ReportInfo(@Query("id") String id);

    @GET("/PM/PrjCost/PrjCostHandler.ashx")
    Observable<XMCBDto> PrjCostHandler(@Query("pid") String pid);

    @FormUrlEncoded
    @POST("/PM/Contract/ContractHandler.ashx")
    Observable<HTDto> ContractHandler(
            @Field("page") int page
            , @Field("rows") int rows
            , @Field("sort") String sort
            , @Field("order") String order
            , @Field("filters") String filters,
            @Field("SearchText") String SearchText
    );

    @GET("/PM/Contract/ContractHandler.ashx?action=detail")
    Observable<HTDto> ContractHandler(@Query("id") String id);

    @GET("/api/WFInfo.ashx?action=GetWorkflowLog")
    Observable<BaseResponseDto<List<WorkFlowsDto>>> GetWorkflowLog(@Query("wfid") String wfid);

    @GET("/api/LeaveInfo.ashx?action=detail")
    Observable<BaseResponseDto<QJInfoDto>> qjDetail(@Query("id") String id);

    @GET("/api/WFInfo.ashx?action=GetWorkFlowInfo")
    Observable<BaseResponseDto<WorkFlowsInfoDto>> GetWorkFlowInfo(@Query("wfid") String wfid);

    @GET("/api/WFActionInfo.ashx?action=GetWFUsers")
    Observable<BaseResponseDto<List<UserInfoDto1>>> GetWFUsers(@Query("Source") String Source,
                                                         @Query("SourceName") String SourceName);

    @GET("/api/WFActionInfo.ashx?action=GetWorkflowActions")
    Observable<BaseResponseDto<List<WorkFlowsDto1>>> GetWorkflowActions(@Query("wfid") String wfid);

    @GET("/api/wfinfo.ashx?action=STARTPROCESS")
    Observable<BaseResponseDto<WorkFlowsDto>> tuiHui(@Query("wfid") String wfid
            , @Query("mark") String mark, @Query("targetNoti") String targetNoti
            , @Query("involvedNoti") String involvedNoti
            , @Query("creatorNoti") String creatorNoti
            , @Query("nodeLast") String nodeLast, @Query("selectedNode") String selectedNode);

    @GET("/api/wfinfo.ashx?action=delegate")
    Observable<BaseResponseDto<WorkFlowsDto>> delegate(@Query("wfid") String wfid
            , @Query("mark") String mark, @Query("targetNoti") String targetNoti
            , @Query("involvedNoti") String involvedNoti
            , @Query("creatorNoti") String creatorNoti
            , @Query("toUserID") String toUserID);

    @GET("/api/wfinfo.ashx?action=Submit")
    Observable<BaseResponseDto<WorkFlowsDto>> Submit(@Query("wfid") String wfid
            , @Query("mark") String mark, @Query("targetNoti") String targetNoti
            , @Query("involvedNoti") String involvedNoti
            , @Query("creatorNoti") String creatorNoti
            , @Query("menualUser") String menualUser
            , @Query("popUsers") String popUsers
            , @Query("txtDocNumber") String txtDocNumber
            , @Query("txtDocTitle") String txtDocTitle
            , @Query("targetNode") String targetNode
    );



    @GET("/api/wfinfo.ashx?action=Revoke")
    Observable<BaseResponseDto<List<WorkFlowsDto1>>> Revoke(@Query("wfid") String wfid);

    @GET("/api/wfinfo.ashx?action=Receive")
    Observable<BaseResponseDto<List<WorkFlowsDto1>>> Receive(@Query("wfid") String wfid);

    @GET("/api/wfinfo.ashx?action=Term")
    Observable<BaseResponseDto<List<WorkFlowsDto1>>> Term(@Query("wfid") String wfid);

    @GET("/api/wfinfo.ashx?action=TERMINATE")
    Observable<BaseResponseDto<List<WorkFlowsDto1>>> TERMINATE(@Query("wfid") String wfid);

    @GET("/api/wfinfo.ashx?action=STARTPROCESS")
    Observable<BaseResponseDto<List<WorkFlowsDto1>>> STARTPROCESS(@Query("wfid") String wfid, @Query("mark") String mark);


    @FormUrlEncoded
    @POST("/DynamicHandler.ashx?daokey=OA.Bulletin&prefilter=PubView&sort=ID&order=desc")
    Observable<GGDto> GGList(

            @Field("filters") String filters,
            @Field("SearchText") String SearchText

    );

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);


// url 写法例子
//    @POST
//    Observable<BaseResponseDto<TestDto>> checkServer(@Url String url);

//    @GET("api/aliPayment/{order_id}")
//    Call<PayBean> payment(@Path("order_id") String order_id, @Query("promotion_code") String promotionCode);
//
//    @GET("api/V3/aliPayment_zuimei/{order_id}")
//    Call<PayBean> payment(@Path("order_id") String order_id, @QueryMap Map<String, String> stringMap);


//    RequestBody 参数的设置
    //    HashMap<String, Object> paramsMap = new HashMap<>();
    //        paramsMap.put("geoId", baseMissionInfoEntity.getTaskId());
    //        paramsMap.put("geoName", baseMissionInfoEntity.getTaskName());
    //        paramsMap.put("featureType", baseMissionInfoEntity.getFeatureType().getType());
    //        paramsMap.put("userName", userInfo.getUserName());
    //        paramsMap.put("geoStr", stringBuilder);
    //        paramsMap.put("bizType", "300");
    //
    //    //需要把数据转成json格式
    //    String strEntity = GsonUtil.toJson(paramsMap);
    //
    //    //添加contentType
    //    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), strEntity);

//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/check_specification")
//    Call<CheckResultBean> checkSpecification(@Body RequestBody jsonString);
//
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("/api/upload_spec_pic")
//    Call<UploadResultBean> uploadSpecPic(@Body RequestBody jsonString, @Query("version_number") String version_number);


//    @Multipart
//    @POST("/apiv2/mail/sendtemplate")
//    Call<ResponseBody> email(@Part("apiUser") RequestBody apiUser,
//                             @Part("apiKey") RequestBody apiKey,
//                             @Part("from") RequestBody from,
//                             @Part("fromName") RequestBody fromName,
//                             @Part("to") RequestBody to,
//                             @Part("subject") RequestBody subject,
//                             @Part("templateInvokeName") RequestBody templateInvokeName,
//                             @Part("xsmtpapi") RequestBody html,
//                             @Part("contentSummary") RequestBody contentSummary,
//                             @Part() List<MultipartBody.Part> parts);
//


//    @Streaming
//    @GET
//    Call<ResponseBody> downloadIdPhoto(@Url String fileUrl);

    @GET("/test")
    Observable<BaseResponseDto<TestDto>> getTest1();

    @GET("/test")
    Observable<TestDto> getTest();
}


