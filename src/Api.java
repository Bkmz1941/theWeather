import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Api {
    private String url = "https://api.openweathermap.org";
    private String apiKey = "9b1478c50fad200be9b4d0611f877037";
    private static Api instance = null;

    public CityGeo getGeoByCityName(String city) throws Exception {
        String response = this.get("/geo/1.0/direct?q=" + city + "&limit=1&appid=" + this.apiKey);
        Gson gson = new Gson();
        List<CityGeo> citiesGeo = gson.fromJson(response, new TypeToken<ArrayList<CityGeo>>(){}.getType());
        return (CityGeo)citiesGeo.get(0);
    }

    public int getDegrees(CityGeo city) throws Exception {
        String response = this.get("/data/2.5/weather?lat=" + city.lat + "&lon=" + city.lon + "&appid=" + this.apiKey);
        Gson gson = new Gson();
        CityDegrees object = gson.fromJson(response, CityDegrees.class);
        return object.main.getCelsius();
    }

    private String get(String path) throws Exception {
        HttpURLConnection con = null;
        try {
            URL url = new URL(this.url + path);

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            if (status != 200) {
                throw new Exception("Error");
            }
            return convertStreamToString(con.getInputStream());
        } catch (Throwable e) {
            System.out.println("ERROR 1: " + e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static Api getInstance() {
        if (Api.instance == null) {
            Api.instance = new Api();
        }
        return Api.instance;
    }
}
