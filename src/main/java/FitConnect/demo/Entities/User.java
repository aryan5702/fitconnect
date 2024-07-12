package FitConnect.demo.Entities;

import FitConnect.demo.POJO.Location;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import FitConnect.demo.POJO.Interest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {

    @Id
    @Indexed(unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private String gender;
    private String bio;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dob;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Location location;

    private byte[] profilePic;
    private ArrayList<Interest> interests;

}
