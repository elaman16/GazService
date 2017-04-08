package kz.elaman.gazservice.utils;

/**
 * Created by Yelaman Aitymbet on 17.01.2017.
 */

public class Constants {
    public static final String API = "http://mobile.zhaksy-adam.kz";

    public static final String API_AUDIENCES = API+"/api/Cabinet/GetAll?";
    public static final String API_GROUPS = API+"/api/Group/GetGroup?";
    public static final String API_TEACHERS = API+"/api/Teacher/GetAll?";
    public static final String API_STUDENTS = API+"/api/Student/GetAll?";
    public static final String API_INDIVIDUALLY = API+"/api/Group/GetIndividual?";


    public static final String API_SCHEDULE_BY_TEACHER = API+"/api/Schedule/ByTeacher?"; //
    public static final String API_SCHEDULE_BY_AUDIENCE = API+"/api/Schedule/ByCabinet?"; //
    public static final String API_TEACHERS_ID = API+"/api/Teacher/GetAll?";
    public static final String API_AUDIENCE_ID = API+"/api/Cabinet/GetAll?";
    public static final String API_LOGIN = "http://mobile.zhaksy-adam.kz/api/User/Login";

    public static final String API_HOMEWORK_GROUPS = API+"/api/Homework/GetGroups?"; //http://mobile.zhaksy-adam.kz/api/Homework/GetGroups?teacherid=2
    public static final String API_HOMEWORK_HOME_WORKS =API+"/api/Homework/GetHomeworks?"; //http://mobile.zhaksy-adam.kz/api/Homework/GetHomeworks?teacherid=2&groupid=1&month=2
    public static final String API_HOMEWORK_TEACHER =API+"/api/Homework/GetAllHomeworks?"; // http://mobile.zhaksy-adam.kz/api/Homework/GetAllHomeworks?teacherid=2
    public static final String API_HOMEWORK_NEW = "http://mobile.zhaksy-adam.kz/api/HomeWork/AddHomework";




    public static final String API_KOB = "http://api.kobyzbook.kz/api/Default";


    public static final String USER_ROLE_CENTER = "USER_ROLE_CENTER";
    public static final String USER_ROLE_TEACHER = "USER_ROLE_TEACHER";
    public static final String USER_ROLE_STUDENT = "USER_ROLE_STUDENT";


    public static final String LOGIN = "LOGIN";
    public static final String REGISTRATION = "REGISTRATION";
    public static final String FILE_TYPE_DOC = "doc";
    public static final String FILE_TYPE_DOCX = "docx";
    public static final String FILE_TYPE_WORD = "word";
    public static final String FILE_TYPE_PDF = "pdf";
    public static final String FILE_TYPE_IMG = "image";

    public static final String FILE_TYPE_EXEL = "xls";
    public static final String FILE_TYPE_WAR = "archive";
    public static final String FILE_TYPE_PPT = "ppt";
    public static final String FILE_TYPE_AUDIO = "audio";
    public static final String FILE_TYPE_VIDEO = "video";

    // TABLE

    public static final String INDICATE_TABLE = "INDICATE_TABLE";
    public static final String MY_EMAIL = "akerke0195@mail.ru";
}
