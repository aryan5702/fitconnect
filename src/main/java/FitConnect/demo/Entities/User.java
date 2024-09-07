package FitConnect.demo.Entities;

import FitConnect.demo.POJO.Location;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;

import FitConnect.demo.POJO.Interest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {

    @Id
    @Indexed(unique = true)
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotNull(message = "First Name cannot be null")
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;
    private String lastName;
    @NotNull(message = "Gender cannot be null")
    @NotBlank(message = "Gender cannot be blank")
    private String gender;
    private String bio;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "DOB cannot be null")
    @NotBlank(message = "DOB cannot be blank")
    private Date dob;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Location location;

    private byte[] profilePic;
    private HashSet<Interest> interests;

}
