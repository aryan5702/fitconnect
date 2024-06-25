package FitConnect.demo.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import FitConnect.demo.POJO.Interest;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {

    @Column(name="Email")
    @Id
    private String email;

    @Column(name="FirstName")
    private String firstName;

    @Column(name="LastName")
    private String lastName;

    @Column(name="Gender")
    private String gender;

    @Column(name="Bio")
    private String bio;

    @Column(name="DOB")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name="PhoneNumber")
    private Integer phoneNumber;

    @Column(name="Longitude")
    private String longitude;

    @Column(name="Latitude")
    private String latitude;

    @Column(name="ProfilePic")
    private byte[] profilePicture;

    @Column(name="Interests")
    private HashSet<Interest> interests;

}
