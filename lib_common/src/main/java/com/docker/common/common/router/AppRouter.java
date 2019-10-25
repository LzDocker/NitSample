package com.docker.common.common.router;

public class AppRouter {


    public static final String AppGroup = "/App/";

    public static final String App_SEARCH = AppGroup + "search";

    public static final String HOME = AppGroup + "home";

    public static final String COMPANY_GROUP = AppGroup + "companygroup";


    public static final String AccountGroup = "/Account/";

    public static final String ACCOUNT_LOGIN = AccountGroup + "login";

    public static final String ACCOUNT_COUNTRY = AccountGroup + "country";

    public static final String ACCOUNT_ATTEN_LISt = AccountGroup + "attention_list";

    /*
     * 设置*/
    public static final String ACCOUNT_ATTEN_SETTING = AccountGroup + "setting";

    /*
     * 设置*/
    public static final String ACCOUNT_ATTEN_SETTING_FRAG = AccountGroup + "settingfragment";


//    public static final String ACCOUNT_MESSAGE_LIST = AccountGroup + "message_list";

    public static final String ACCOUNT_MESSAGE_LIST = "/a/b";  // todo


    public static final String ACCOUNT_SYSTEM_sMESSAGE = AccountGroup + "system_message_list";


    public static final String VideoGroup = "/Video/";

    public static final String Video_Player = VideoGroup + "player";


    public static final String ViewDocGroup = "/ViewDoc/";

    public static final String ViewDoc_Document = ViewDocGroup + "document";

    public static final String AUDIO_DEMO = ViewDocGroup + "audio";


    public static final String CircleGroup = "/Circle/";

    public static final String MINECIRCLELIST = CircleGroup + "minecirclelist";

    public static final String CIRCLE_CREATE = CircleGroup + "circlecreate";

    public static final String CIRCLE_DETAIL = CircleGroup + "circledetail";

    public static final String COMMONH5 = CircleGroup + "commonh5";

    public static final String COMMENTLIST = CircleGroup + "Commentlist";

    public static final String CIRCLE_persion_detail = CircleGroup + "CIRCLE_persion_detail";

    public static final String CIRCLEHOME = CircleGroup + "CircleHome";


    public static final String ProGroup = "/Pro/";

    public static final String STUDY = ProGroup + "study";

    public static final String Video_Pro_Training = ProGroup + "pro_video_player";

    public static final String Pro_Country_select = ProGroup + "country_select";

    public static final String Pro_Training_collect = ProGroup + "pro_training_collect";


    public static final String HomeGroup = "/Home/";


    public static final String VIDEOGROUP = "/video/";

    public static final String VIDEOMAIN = VIDEOGROUP + "main";


    public static final String COMMONGROUP = "/common/";

    public static final String COMMON_CONTAINER = COMMONGROUP + "container";

    public static final String COMMON_CONTAINER_FRAGMENT = COMMONGROUP + "container_fragment";

}
