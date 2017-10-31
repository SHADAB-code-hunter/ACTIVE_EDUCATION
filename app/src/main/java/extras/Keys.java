package extras;

/**
 * Created by Windows on 10-02-2015.
 */
public interface Keys {
     interface EndpointBoxOffice{


         String KEY_CATEGORY="category";

         String KEY_DATA_ARRAY="data";

         String KEY_ADDED_DATE="added_date";

         String KEY_ADDRERSS="c_address";

         String KEY_CITY="c_city";

         String KEY_COUNTRY="c_country";;

         String KEY_EMAIL="c_email";

         String KEY_ESTEBLISHED="c_established";

         String KEY_CID="c_id";

         String KEY_IMAGE="c_image";

         String KEY_INFO="c_info";

         String KEY_C_NAME="c_name";

         String KEY_C_SHORT_NAME="c_short_name";

        //public static final String KEY_C_INFO="c_info";

         String KEY_STATE="c_state";

         String KEY_RATING="rating";

         String KEY_BROUCHER="broucher";

         String KEY_BRANCH_FEES="branch_fee";

         String KEY_C_INFO="c_info";

         String KEY_T_SEAT="total_seats";

         String KEY_R_SEAT="remaining_seats";

         String KEY_ID="id";

         String KEY_B_NAME="branch_name";

         String KEY_BNAME="bname";

         String KEY_B_SHORT_NAME="b_short_anme";

         String KEY_B_INFO="branch_info";

         String KEY_STATUS="statusArray";


         String KEY_CRS_ID="course_id";

         String KEY_BRN_ID="branch_id";

         String KEY_COURSE="c_course";

         String KEY_C_BRANCH="c_branch";

         String KEY_C_WEBSITE="c_website";

         String KEY_C_PHONE1="c_phone1";

         String KEY_DISCOUNT="discount";

        /*  state keys  */

         String KEY_STATE_ARRAY="stateData";

         String KEY_STATE_NAME="state";

         String KEY_STATE_ID="id";

         /*  city keys  */

         String KEY_CITY_ARRAY="cityData";

         String KEY_CITY_NAME="city_name";

       //  String KEY_CITY_ID="cityid";
         String KEY_CITY_ID="id";

         String KEY_CONTAIN_CITY_ID="state_id";

        /*  Branch keys  */

         String KEY_BRANCH_ARRAY="branchData";

         String KEY_BRANCH_NAME="branch_name";

         String KEY_BRANCH_SHORT_NAME="branch_short_name";

         String KEY_BRANCH_ID="id";

         /*  courseData keys  */

         String KEY_COURSE_ARRAY="courseData";

         String KEY_COURSE_NAME="c_name";

         String KEY_COURSE_SHORT_NAME="c_short_name";

         String KEY_COURSE_ID="id";

         String KEY_CNAME="cname";

        /* common pojo key*/

         String KEY_COMNID="id";

         String KEY_COMNNAME="name";

         String KEY_COM_COURSENNAME="c_short_name";

         String KEY_DESC="c_info";

    }

    public interface KEY_AGENT_NOTES {

         String KEY_A_ID="agent_id";
         String KEY_A_NAME="agent_name";
         String KEY_A_CATEGORY="category";
         String KEY_A_CATEGORY_NAME="category_name";
         String KEY_A_END_ADTE="end_date";
         String KEY_A_PROGRESS="progress";
         String KEY_A_SRT_DATE="start_date";
         String KEY_A_TOTAl_ADMISSION="total_admission";
         String KEY_A_TOTAL_ADM_DONE="total_admission_done";
         String KEY_A_BRANCH_ID="branch_id";
         String KEY_A_BRANCH_NAME="branch_name";
         String KEY_A_CLG_ID="clg_id";
         String KEY_A_CLG_NAME="clg_name";
         String KEY_A_COURSE_ID="course_id";
         String KEY_A_COURSE_NAME="course_name";
         String KEY_A_DEAL_ID="deal_id";
         String KEY_A_DEAL_MONEY="deal_money";
         String KEY_A_TOTAL_SEAT="total_seats";


    }


    public interface KEY_USER_LOGIN {

         String KEY_TOKEN="Login_Token";
         String KEY_NAME="agent_name";
         String KEY_IMAGE="image";
         String KEY_EMAIL="email";
         String KEY_MOBILE="mobile";

    }

    public interface KEY_PARTNER_DETAIL {

         String KEY_COLLEGE_NAME="College_Name";
         String KEY_COLLGE_SHORT_NAME="College_Short_Name";
         String KEY_ADDRESS="Address";
         String KEY_STATE="State";
         String KEY_CITY="City";
         String KEY_TYPE="Type";
         String KEY_EMAIL="Email";
         String KEY_PHONE="Phone";
         String KEY_WEBSITE="Website";
         String KEY_ESTABLISHED="Established";
         String KEY_INFORMATION="Information";
         String KEY_IMAGE="Image";
         String KEY_BROUCHERE="Brochure";
         String KEY_STATUS="STATUS";



    }

}
