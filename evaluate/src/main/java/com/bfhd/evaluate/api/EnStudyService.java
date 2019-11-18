package com.bfhd.evaluate.api;

import android.arch.lifecycle.LiveData;

import com.bfhd.evaluate.vo.AfterClassListVo;
import com.bfhd.evaluate.vo.BookListVo;
import com.bfhd.evaluate.vo.CataLogListVo;
import com.bfhd.evaluate.vo.EnStudyOutVo;
import com.bfhd.evaluate.vo.EnstuyHistoryVo;
import com.bfhd.evaluate.vo.LessonDetailVo;
import com.bfhd.evaluate.vo.LessonListVo;
import com.bfhd.evaluate.vo.RadioLessonVo;
import com.bfhd.evaluate.vo.RadioMenuVo;
import com.bfhd.evaluate.vo.StudyBeiSongCatchVo;
import com.bfhd.evaluate.vo.StudyFractionVo;
import com.bfhd.evaluate.vo.VideoItemVo;
import com.docker.core.di.netmodule.ApiResponse;
import com.docker.core.di.netmodule.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhangxindang on 2018/10/22.
 */

public interface EnStudyService {

    String HOST_URL = "http://www.hanfengyishu.cn/";

    String OSS_URL = "https://hanfengyishu.oss-cn-beijing.aliyuncs.com/";

    @POST(HOST_URL + "api.php?m=book.book_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<BookListVo>>>> bookList(@FieldMap HashMap<String, Object> map);


    @POST(HOST_URL + "api.php?m=book.catalog_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<CataLogListVo>>>> catalogList(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=book.lession_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<LessonListVo>>>> lessionList(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=book.lession_detail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<LessonDetailVo>>> lessionDetail(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=lession.book_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<RadioMenuVo>>>> lessionTopList(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=lession.lession_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<RadioLessonVo>>>> lessionRadioLessonList(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=lession.lession_detail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<LessonDetailVo>>> radioLessonDetail(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=study.study_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<AfterClassListVo>>>> studyList(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=study.study_detail")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<AfterClassListVo>>> studyDetail(@FieldMap HashMap<String, Object> map);


    @POST(HOST_URL + "api.php?m=study.history_study")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<EnstuyHistoryVo>>>> historyStudy(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=study.out_study_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<EnStudyOutVo>>>> outStudyList(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=lession.fraction")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<StudyFractionVo>>> lessonFraction(@FieldMap HashMap<String, Object> map);

    //lession.lession_pass
    @POST(HOST_URL + "api.php?m=lession.lession_pass")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<String>>> lessonPass(@FieldMap HashMap<String, Object> map);

    //read_pass_list
    @POST(HOST_URL + "api.php?m=lession.read_pass_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<StudyBeiSongCatchVo>>>> readPassList(@FieldMap HashMap<String, Object> map);

    //read_pass_list
    @POST(HOST_URL + "api.php?m=lession.lession_pass_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<StudyBeiSongCatchVo>>>> beiSongPassList(@FieldMap HashMap<String, Object> map);

    //read_pass_list
    @POST(HOST_URL + "api.php?m=lession.read_pass")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<Object>>> readPass(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=lession.mv_list")
    @FormUrlEncoded
    LiveData<ApiResponse<BaseResponse<List<VideoItemVo>>>> mvList(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=lession.book_list")
    @FormUrlEncoded
    Observable<BaseResponse<List<RadioMenuVo>>> tttt1(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=lession.lession_list")
    @FormUrlEncoded
    Observable<BaseResponse<List<RadioLessonVo>>> tttt2(@FieldMap HashMap<String, Object> map);

    @POST(HOST_URL + "api.php?m=book.book_list")
    @FormUrlEncoded
    Observable<BaseResponse<List<BookListVo>>> ttttt3(@FieldMap HashMap<String, Object> map);

}
