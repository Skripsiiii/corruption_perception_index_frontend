package com.example.corruptionperceptionindex.src.connection;

public class Koneksi {
    String BASE_URL = "https://ec1f-180-252-232-45.ngrok-free.app/api/";

    public String connLogin(){
        return BASE_URL +"auth/login";
    }
    public String connRegister(){
        return BASE_URL +"auth/register";
    }
    public String connDomicili(){
        return BASE_URL +"addDomiciles";
    }

    public String connEducation(){
        return BASE_URL +"educations";
    }

    public String connOccupation(){
        return  BASE_URL +"occupations";
    }
    public String connProvince(){
        return  BASE_URL +"provinces";
    }
    public String connDataProvince(){
        return  BASE_URL +"provinceData";
    }


    public String connKotaDataDimension(){
        return  BASE_URL +"cityData/";
    }
    public String connDataDimension(){
        return  BASE_URL +"dimensionCityData/";
    }
    public String connDataIndikator(){
        return  BASE_URL +"indicatorCityData/";
    }

    public String connqmap(){
        return  BASE_URL +"map";
    }

    public String connquestions(){
        return  BASE_URL +"questions";
    }
    public String connquestionsViewpoints(){
        return  BASE_URL +"viewpoints";
    }
    public String connquestartQuestion(){
        return  BASE_URL +"startQuestionnaire";
    }
    public String connCities(int provinceId) {
        return BASE_URL +"cities/" + provinceId;
    }
    public String connDimension(int provinceId) {
        return BASE_URL +"cities/" + provinceId;
    }


}
