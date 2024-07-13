package FitConnect.demo.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private double[] coordinates; // {Longitude, Latitude}
    private String type="Point";
}
