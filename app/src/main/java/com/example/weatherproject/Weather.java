package com.example.weatherproject;

public class Weather {

    private String cityName ;
    private float min ;
    private float max ;

    public Weather() {

    }

    public Weather(String cityName, float min, float max) {
        this.cityName = cityName;
        this.min = min;
        this.max = max;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "cityName='" + cityName + '\'' +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}




//package com.example.weatherproject;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WeatherJasonParser {
//
//    public static Weather getObjectFromJason(String jason) {
//
//        try{
//            JSONArray jsonArray = new JSONArray(jason);
//        }
//        catch (JSONException e){
//            e.printStackTrace();
//            return null;
//        }
//
//
//        List<Student> students;
//        try {
//            JSONArray jsonArray = new JSONArray(jason);
//            students = new ArrayList<>();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject = (JSONObject) jsonArray.get(i);
//                Student student = new Student();
//                student.setID(jsonObject.getInt("id"));
//                student.setName(jsonObject.getString("name"));
//                student.setAge(jsonObject.getDouble("age"));
//                students.add(student);
//            }
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return students;
//    }
//
//}

