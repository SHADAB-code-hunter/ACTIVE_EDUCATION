package json;


import android.app.Activity;
import android.util.Log;

import utilities.App_Static_Method;
import utilities.MyApplication;

import static utilities.UrlEndpoints.URL_BOX_OFFICE;
import static utilities.UrlEndpoints.URL_CHAR_AMEPERSAND;
import static utilities.UrlEndpoints.URL_CHAR_QUESTION;
import static utilities.UrlEndpoints.URL_DAILYQUIZ;
import static utilities.UrlEndpoints.URL_PARAM_API_KEY;
import static utilities.UrlEndpoints.URL_PARAM_LIMIT;
import static utilities.UrlEndpoints.URL_QUIZ_PSUBJECT;
import static utilities.UrlEndpoints.URL_QUIZ_SUBJECT_QUESTION;
import static utilities.UrlEndpoints.URL_START_DAILY_QUIZ;
import static utilities.UrlEndpoints.URL_UPCOMING;


/**
 * Created by Windows on 02-03-2015.
 */
public class Endpoints {
    private static String STR_URL,STR_URL2;

    public static String getFilter_RequestUrlBoxOfficeMovies(String URL) {

        return URL
               /* + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit*/;
    }

    public static String getRequestUrlBoxOfficeMovies(int limit) {

        return URL_BOX_OFFICE
               /* + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit*/;
    }

    public static String getRequestUrlUpcomingMovies(int limit) {

        return URL_UPCOMING
                + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;
    }
    public static String getRequestUrlDailyQuiz(int limit) {

        return URL_DAILYQUIZ+"type="+ App_Static_Method.get_Type();
              /*  + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;*/
    }
    public static String getRequestUrlDailyQuiz(String str_mob,String str_token) {
        // getQuizData.php?mobile=9599805321&token=3uNknfsjHX
        String str=URL_START_DAILY_QUIZ+"mobile="+str_mob+"&token="+str_token+"&type="+ App_Static_Method.get_Type();
        Log.d("gdfgf",str);
        return str;
              /*  + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;*/
    }
    public static String getRequestUrlDailyQuizPending(int limit) {

        return URL_START_DAILY_QUIZ+"type="+ App_Static_Method.get_Type();
              /*  + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;*/
    }
    public static String getQuiz_SubjectPending(Activity activity, String l_username, String str_token) {


        STR_URL=URL_QUIZ_PSUBJECT+"mobile="+l_username+"&token="+str_token+"&type="+ App_Static_Method.get_Type();
        Log.d("URLsgf",STR_URL);
        return STR_URL;
              /*  + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;*/
    }
    public static String getQuiz_SubjectQuestion(String l_username, String str_token,String sub_number) {

        STR_URL2=URL_QUIZ_SUBJECT_QUESTION+"mobile="+l_username+"&token="+str_token+"&subject="+sub_number+"&type="+ App_Static_Method.get_Type();
        Log.d("url_qques",STR_URL2);
        return STR_URL2;
              /*  + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApplication.API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;*/
    }
}
