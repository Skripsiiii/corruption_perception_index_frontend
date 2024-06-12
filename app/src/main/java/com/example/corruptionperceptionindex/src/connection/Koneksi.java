package com.example.corruptionperceptionindex.src.connection;

public class Koneksi {

    public String connLogin(){
        String connLogin = "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/auth/login";
        return  connLogin;
    }
    public String connRegister(){
        String connLogin = "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/auth/register";
        return  connLogin;
    }
    public String connDomicili(){
        String conDomicili = "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/addDomiciles";
        return  conDomicili;
    }

    public String connEducation(){
        return "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/educations";
    }

    public String connOccupation(){
        return  "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/occupations";
    }
    public String connProvince(){
        return  "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/provinces";
    }
    public String connDataProvince(){
        return  "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/provinceData";
    }


    public String connKotaDataDimension(){
        return  "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/cityData/";
    }
    public String connDataDimension(){
        return  "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/dimensionCityData/";
    }
    public String connDataIndikator(){
        return  "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/indicatorCityData/";
    }


    public String connquestions(){
        return  "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/questions";
    }
    public String connquestionsViewpoints(){
        return  "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/viewpoints";
    }
    public String connCities(int provinceId) {
        return "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/cities/" + provinceId;
    }
    public String connDimension(int provinceId) {
        return "https://6e49-2404-8000-1003-61d1-39cf-8c86-8a14-b8e8.ngrok-free.app/api/cities/" + provinceId;
    }


}
