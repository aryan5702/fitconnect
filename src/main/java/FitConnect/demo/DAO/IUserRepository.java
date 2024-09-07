package FitConnect.demo.DAO;

import FitConnect.demo.Entities.User;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IUserRepository extends ReactiveMongoRepository<User, String> {

    @Query("{ 'email': { $ne: ?0, $exists: true }, 'location': { $geoWithin: { $centerSphere: [ [ ?1, ?2 ], ?3 ] } } }")
    Flux<User> findUsersByLocation(String email, double longitude, double latitude, double distanceInRadians);

    @Aggregation(pipeline = {
            "{ '$geoNear': { 'near': { 'type': 'Point', 'coordinates': [ ?1, ?2 ] }, 'distanceField': 'dist.calculated', 'maxDistance': ?3, 'spherical': true } }",
            "{ '$match': { 'email': { '$ne': ?0 }, 'gender': { '$eq': ?4 }, 'dob': { '$gte': { '$date': { '$subtract': [ new Date(), { '$multiply': [ ?5, 365 * 24 * 60 * 60 * 1000 ] } ] } }, '$lte': { '$date': { '$subtract': [ new Date(), { '$multiply': [ ?6, 365 * 24 * 60 * 60 * 1000 ] } ] } }, 'interests': { '$elemMatch': { 'interest': ?7, 'level': ?8 } } } }"
    })
    Flux<User> findUsersByCriteria(String email, double longitude, double latitude, double maxDistance, String gender, int minAge, int maxAge, String interest, Integer level);
}
