package FitConnect.demo.Entities;
import FitConnect.demo.POJO.Interest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private User user;
    private Interest interest;
    private String gender;
    private Integer minAge;
    private Integer maxAge;
    private double distance;
}
