package com.example.corruptionperceptionindex.src.connection;

public class Koneksi {
    String BASE_URL = "https://ea19-180-252-232-45.ngrok-free.app/";

    public String connLogin(){
        String connLogin = BASE_URL +"api/auth/login";
        return  connLogin;
    }
    public String connRegister(){
        String connLogin = BASE_URL +"api/auth/register";
        return  connLogin;
    }
    public String connDomicili(){
        String conDomicili = BASE_URL +"api/addDomiciles";
        return  conDomicili;
    }

    public String connEducation(){
        return BASE_URL +"api/educations";
    }

    public String connOccupation(){
        return  BASE_URL +"api/occupations";
    }
    public String connProvince(){
        return  BASE_URL +"api/provinces";
    }
    public String connDataProvince(){
        return  BASE_URL +"api/provinceData";
    }


    public String connKotaDataDimension(){
        return  BASE_URL +"api/cityData/";
    }
    public String connDataDimension(){
        return  BASE_URL +"api/dimensionCityData/";
    }
    public String connDataIndikator(){
        return  BASE_URL +"api/indicatorCityData/";
    }

    public String connqmap(){
        return  BASE_URL +"api/map";
    }

    public String connquestions(){
        return  BASE_URL +"api/questions";
    }
    public String connquestionsViewpoints(){
        return  BASE_URL +"api/viewpoints";
    }
    public String connCities(int provinceId) {
        return BASE_URL +"api/cities/" + provinceId;
    }
    public String connDimension(int provinceId) {
        return BASE_URL +"api/cities/" + provinceId;
    }


}
