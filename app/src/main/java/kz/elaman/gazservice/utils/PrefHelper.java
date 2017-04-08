package kz.elaman.gazservice.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yelaman Aitymbet on 24.01.2017.
 */

public class PrefHelper {

    public static final String PREF = "polla";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_IMG = "user_img";
    public static final String USER_ROLE = "USER_ROLE";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public static final String PUSH_SETTING = "push_setting";
    public static final String NEWS_SETTING = "news_setting";
    public static final String LOGIN_STATE = "LOGIN_STATE";
    public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
    public static final String ADDRESS = "ADDRESS";
    public static final String IS_WRITE_DATA = "IS_WRITE_DATA";


    String userId;

    String role;

    Context mContext;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public PrefHelper(Context context) {
        mContext = context;
        sharedpreferences = mContext.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public String getUserId() {
        return sharedpreferences.getString(USER_ID, "-1");
    }

    public void setUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.apply();
    }

    public String getUserName() {
        return sharedpreferences.getString(USER_NAME, "Ақерке Сансызбаева");
    }

    public void setUserName(String userName) {
        editor.putString(USER_NAME, userName);
        editor.apply();
    }

    public String getUserEmail() {
        return sharedpreferences.getString(USER_EMAIL, "-1");
    }

    public void setUserEmail(String userEmail) {
        editor.putString(USER_EMAIL, userEmail);
        editor.apply();
    }

    public String getUserImg() {
        return sharedpreferences.getString(USER_IMG, "-1");
    }

    public void setUserImg(String userImg) {
        editor.putString(USER_IMG, userImg);
        editor.apply();
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedpreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setPushEnable(boolean enable) {
        editor.putBoolean(PUSH_SETTING, enable);
        editor.commit();
    }

    public boolean getPushEnabled() {
        return sharedpreferences.getBoolean(PUSH_SETTING, true);
    }

    public void setNewsNotificationEnable(boolean enable) {
        editor.putBoolean(NEWS_SETTING, enable);
        editor.commit();
    }

    public boolean getNewsNotificationEnabled() {
        return sharedpreferences.getBoolean(NEWS_SETTING, true);
    }

    public void setLoginState(String loginState) {
        editor.putString(LOGIN_STATE, loginState);
        editor.commit();
    }

    public String getLoginState() {
        return sharedpreferences.getString(LOGIN_STATE, Constants.LOGIN);
    }

    public void setEmailAddress(String emailAddress) {
        editor.putString(EMAIL_ADDRESS, emailAddress);
        editor.commit();
    }

    public String getEmailAddress() {
        return sharedpreferences.getString(EMAIL_ADDRESS, "aytymbet.e@gmail.com");
    }

    public void setAddress(String address) {
        editor.putString(ADDRESS, address);
        editor.commit();
    }

    public String getAddress() {
        return sharedpreferences.getString(ADDRESS, "г. Тараз ул. Абылай хана, дом 135Д, 11 кв.");
    }

    public void setWriteDate(boolean isWrited){
        editor.putBoolean(IS_WRITE_DATA, isWrited);
        editor.commit();
    }

    public boolean isWritedDate(){
        return sharedpreferences.getBoolean(IS_WRITE_DATA, false);
    }
}
