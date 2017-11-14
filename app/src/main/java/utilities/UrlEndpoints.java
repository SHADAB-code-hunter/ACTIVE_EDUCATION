package utilities;

/**
 * Created by Windows on 10-02-2015.
 */
public class UrlEndpoints {
    public static String OTP_API_KEY = "2961a00d-6dd2-11e7-94da-0200cd936042";
    public static String OTP_VERIFY_API = "http://2factor.in/API/V1/2961a00d-6dd2-11e7-94da-0200cd936042/SMS/VERIFY/";
    public static String OTP_GENERATE_API = "http://2factor.in/API/V1/2961a00d-6dd2-11e7-94da-0200cd936042/SMS/+91";
    public static String SIGN_UP_API = "http://activeeduindia.com/admin/webservices/signup.php?";
    public static String LOGIN_API = "http://activeeduindia.com/admin/webservices/signin.php?";//sa@gmail.com&pwd=12345";
    public static String Agent_Login = "http://activeeduindia.com/admin/webservices/agentSignIn.php?";//=sa@gmail.com&pwd=123456";
    public static String URL_DEAL_CAT_MAIN = "http://activeeduindia.com/admin/webservices/getTopList.php?";
    public static String URL_GET_FULL_DETAIL = "http://activeeduindia.com/admin/webservices/getFullDetail.php?";//type=1&id=dps";
    public static String URL_GET_AVAIL_COURSES = "http://activeeduindia.com/admin/webservices/getAvailableCourses.php?";//type=1&id=dps";
    public static String URL_IMAGE_APTH = "http://activeeduindia.com/admin/media/college/";
    public static String URL_NO_IMAGE_APTH = "http://activeeduindia.com/admin/media/college/picture/";
    public static String URL_CUSTOM_IMAGE_APTH = "http://activeeduindia.com/admin/media/";
    public static String URL_TOP_COURSES = "http://activeeduindia.com/admin/webservices/getTopCourses.php";
    public static String URL_TOP_EXAMS = "http://activeeduindia.com/admin/webservices/getTopExams.php";
    public static String URL_GET_FACILITY_IMG = "http://activeeduindia.com/admin/media/facility/";
    public static String URL_GET_GALLERY = "http://www.activeeduindia.com/admin/webservices/getGallery.php";
    public static String URL_PROFILE_PATH="http://activeeduindia.com/admin/media/profile/";
    public static String REG_NUM="http://activeeduindia.com/admin/webservices/numberRegistration.php?mobile=";//8368251761";
    public static final String GALLERY_IMG_PATH = "http://www.activeeduindia.com/images/event/";
    public static final String get_filter_list = "http://activeeduindia.com/admin/webservices/getCategoryFilter.php?type=";
    public static final String GET_MAIN_LIST = "http://activeeduindia.com/admin/webservices/getMainList.php?type=2&state=1&city=1&course=1&branch=1";
    public static final String GET_COURSE_LIST = "http://activeeduindia.com/admin/webservices/getCollegeCourse.php?clgid=";
    public static final String GET_BRANCH_LIST = "http://activeeduindia.com/admin/webservices/getBranch.php?course=";
    public static final String GET_CLG_COURSE = "http://activeeduindia.com/admin/webservices/getCollegeBranchDetail.php?";//1&branch=1&clgid=lmcp";
    public static final String GET_OFFER_BANNER = "http://activeeduindia.com/admin/webservices/getNewOffers.php";//1&branch=1&clgid=lmcp";
    public static final String GET_CLG_DESC = "http://activeeduindia.com/admin/webservices/getFullDetail.php?";//type=2&id=2";
    public static final String GET_BOOK_OFFER = "http://activeeduindia.com/admin/webservices/getAgentBookingHistory.php?";
    public static final String GET_admission_personal = "http://activeeduindia.com/admin/webservices/userForm.php?";// +
    public static final String GET_BEST_DEAL = "http://activeeduindia.com/admin/webservices/getAgentDeal.php?";//email=sa@gmail.com&token=FFQosKVX4R";
    public static final String SET_LOG_OUT = "http://activeeduindia.com/admin/webservices/logout.php?";//email=sa@gmail.com&token=wvCfUJwlBE"
    public static final String GET_AGENT_TARGET = "http://activeeduindia.com/admin/webservices/getAgentTarget.php?";//email=sa@gmail.com&token=wvCfUJwlBE";
    public static final String GET_ALL_COURSES = "http://activeeduindia.com/admin/webservices/getMainList.php?type=2";//course=1
    public static final String SEND_FORM_DATA="http://activeeduindia.com/admin/webservices/getAcademicDetailsForm.php";
    public static final String GET_AGENT_OFFER_DETAIL="http://activeeduindia.com/admin/webservices/getOfferDetail.php?";
    public static final String GET_EXAM_DATA="http://activeeduindia.com/admin/webservices/getExamDetails.php?";
    public static final String GET_PROFILE_DATA ="http://activeeduindia.com/admin/webservices/getProfilePicture.php?";
    public static final String UPDATE_AGENT_IMAGE ="http://activeeduindia.com/admin/webservices/updateAgentProfile.php";
    public static final String GET_PROFILE ="http://activeeduindia.com/admin/media/";
    public static final String GET_STATE ="http://activeeduindia.com/admin/webservices/getState.php";
    public static final String GET_BOOKING_LIST="http://activeeduindia.com/admin/webservices/getUserBookingList.php?";
    public static final String GET_CITY="http://activeeduindia.com/admin/webservices/getCity.php?state=";
    public static final String UPDATE_PROFILE="http://activeeduindia.com/admin/webservices/updateAgentProfile.php";
    public static final String SEND_PROFILE_UPDATE="http://activeeduindia.com/admin/webservices/updateUserProfile.php?";
    public static final String SEARCH_API="http://activeeduindia.com/admin/webservices/getSearchResult.php?keyword=";
    public static final String PARTNER_DETAIL="http://activeeduindia.com/admin/webservices/setCollegeDetails.php?";
    public static final String UPLOAD_FILE="http://activeeduindia.com/admin/webservices/uploadBrochure.php?";//brochurename=";//aa.pdf&brochure=";
    public static final String SEAT_SUBMIT="http://activeeduindia.com/admin/webservices/getPartnerSeatSubmission.php?";
    public static final String IMAGE_PATH_ADAPTER="http://activeeduindia.com/admin/media/";
    public static final String SEAT_PROVIDER_SIGNIN="http://activeeduindia.com/admin/webservices/seatProviderSignin.php?";//email=sb@ss.com&pwd=123456&type=1";
    public static final String SEAT_PROVIDER_TARGET="http://activeeduindia.com/admin/webservices/getDashboardTarget.php?";//email=sb@ss.com&token=S6dqKXf8Zt&type=1&id=dps
    public static final String SEAT_PROVIDER_PRFL_IMG="http://activeeduindia.com/admin/media/profile/";//email=sb@ss.com&token=S6dqKXf8Zt&type=1&id=dps
    public static final String SEAT_PROVIDER_DETAIL="http://activeeduindia.com/admin/webservices/setDetails.php?";//crd_email=sb@ss.com&token=S6dqKXf8Zt&type=1&userid=7
    public static final String BROUCHURE_DOWNLOAD="http://activeeduindia.com/admin/webservices/downloadBrochure.php?type=";//type=1&brochure=dpsdwarka.pdf";//crd_email=sb@ss.com&token=S6dqKXf8Zt&type=1&userid=7
    public static final String SEAT_PROVIDER_SOCIAL="http://activeeduindia.com/admin/webservices/setSocialDetail.php?";//crd_email=sb@ss.com&token=S6dqKXf8Zt&type=1&userid=7
    public static final String PAYMENT_API="http://activeeduindia.com/admin/webservices/getUserPayment.php?";//email=sa@gmail.com&token=J54DQ8dulW&category=2&subcategory=lmcp&dealid=1&price=1000&type=agent\n" +
           // "{\"msg\":1}";
           // "{\"msg\":1}";


    /* active quiz */

    public static final String URL_BOX_OFFICE = "http://www.activeeduindia.com/admin/webservices/getAuditionList.php?";
    public static final String URL_DAILYQUIZ = "http://www.activeeduindia.com/admin/webservices/getQuizData.php?mobile=";//email=aa@bb.com&token=NfEP94dKUO";
    public static final String URL_UPDATE_ANS="http://www.activeeduindia.com/admin/webservices//getUserAnswer.php?email=aa@bb.com&token=NfEP94dKUO";
    public static final String URL_START_DAILY_QUIZ="http://www.activeeduindia.com/admin/webservices/startDailyQuiz.php?";//9599805321&token=0ci2wXiSi2";
    public static final String URL_QUIZ_TIME="http://www.activeeduindia.com/admin/webservices/quizTime.php?";
    public static final String URL_QUIZ_PSUBJECT="http://www.activeeduindia.com/admin/webservices/getQuizSubject.php?";//email=aa@bb.com&token=NfEP94dKUO&subject=2";
    public static final String URL_QUIZ_SUBJECT_QUESTION="http://www.activeeduindia.com/admin/webservices/getQuizSubjectData.php?";//email=aa@bb.com&token=NfEP94dKUO&subject=";
    public static final String Test_Api="http://www.activeeduindia.com/admin/webservices/getDailyQuizResult.php?";//?email=aa@bb.com&token=NfEP94dKUO&time_taken=";
    public static final String URL_UPCOMING = "http://api.rottentomatoes.org/api/public/v1.0/lists/movies/upcoming.json";
    public static final String URL_CHAR_QUESTION = "?";
    public static final String URL_CHAR_AMEPERSAND = "&";
    public static final String URL_PARAM_API_KEY = "apikey=";
    public static final String URL_PARAM_LIMIT = "limit=";
    public static final String URL_Img_Sub_URL="http://www.activeeduindia.com/admin/media/subject/";
    public static final String URL_GALLERY_IMG_PATH="http://www.activeeduindia.com/images/event/";
    public static final String BANNER_IMG_PATH="http://www.activeeduindia.com/images/banner/";
    public static final String GET_BANNER_LIST="http://www.activeeduindia.com/admin/webservices/getBanner.php";
    public static final String SignUp_URL="http://www.activeeduindia.com/admin/webservices/signup.php?";
    public static final String UPDATE_Profile="http://activequizindia.com/admin/webservices/updateProfile.php?";
    public static final String URL_login="http://www.activeeduindia.com/admin/webservices/signin.php?";
    public static final String Profile_pic="http://www.activeeduindia.com/admin/media/profile/";
    public static final String URL_Gift_image="http://www.activeeduindia.com/admin/media/reward/";
    public static final String URL_SAVE_EACH_ANS="http://www.activeeduindia.com/admin/webservices/saveDailyQuizQuestion.php?mobile=";//9166833551&token=R9mKOmT56Y&question_id=2&user_answer=Venus&time_taken=05:05:13";

    /*OTP URL*/
    public static  final String get_Aud_detail="http://www.activestudentcrorepati.org/admin/webservices/getAuditionDetail.php?";//mobile=9166833551&token=P7zIWpmejX&audition_id=1"
    public static  String Aud_Conf_API="http://www.activestudentcrorepati.org/admin/webservices/saveUserAudition.php?";//mobile=9166833551&token=0AGycwSZPk&audition_id=1";
    public static  String URL_OTP_API_KEY="02e4b5cc-3ae3-11e7-8473-00163ef91450";
    public static  String URL_OTP_GENERATE_API="http://2factor.in/API/V1/02e4b5cc-3ae3-11e7-8473-00163ef91450/SMS/";
    public static  String URL_OTP_VERIFY_API="http://2factor.in/API/V1/";
    public static  String City_LISt="http://www.activestudentcrorepati.org/admin/webservices/getCity.php?state=";
    public static  String STATE_LISt="http://www.activestudentcrorepati.org/admin/webservices/getstate.php";
    public static  String Filter_list="http://www.activestudentcrorepati.org/admin/webservices/filterAudition.php?state=";//1&city=1";
    public static   String URL_DATE="http://www.activeeduindia.com/admin/webservices/showRewardDate.php";
    public static String URL_Send_DAte="http://www.activeeduindia.com/admin/webservices/getRewardsList.php?date=";
    public static String URL_Winner="http://www.activeeduindia.com/admin/webservices/getWinnerList.php?date=";
    public static String ADV_SP_RANK_API="http://www.activeeduindia.com/admin/webservices/showSponserAndWinner.php?mobile=";//9599805321&token=v93frmyJHi&slot=2"
    /*audition pre confirmation  detail*/
    public  static String Aud_Pre_Conf_detail="http://www.activestudentcrorepati.org/admin/webservices/getAuditionDetail.php?mobile=9166833551&token=P7zIWpmejX&audition_id=1";

    public static String  URL_URL_GET_GALLERY ="http://www.activeeduindia.com/admin/webservices/getGallery.php";
    public  static String URL_save_result_practice="http://activequizindia.com/admin/webservices/getPracticeQuizResult.php?";
    public  static String URL_get_Result="http://activequizindia.com/admin/webservices/getUserResult.php?";//mobile=9166833551&token=uoA65P3R4J&type=practice";


}
