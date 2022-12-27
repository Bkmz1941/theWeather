import java.io.Serializable;
import java.util.List;

public class CityDegrees implements Serializable {
    public List<Whether> weather;
    public Main main;

    class Whether {
        public String main;
        public String description;
    }
    class Main {
        public float temp;

        public int getCelsius() {
            return (int) (this.temp - 273.15);
        }
    }
}
