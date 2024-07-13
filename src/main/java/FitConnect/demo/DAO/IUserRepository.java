package FitConnect.demo.DAO;

import FitConnect.demo.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends MongoRepository<User,String> {
    @Query("{ 'email': { $ne: ?0, $exists: true }, 'location': { $geoWithin: { $centerSphere: [ [ ?1, ?2 ], ?3 ] } } }")
    List<User> findUsersByLocation(String email, double longitude, double latitude, double distanceInRadians);
}
