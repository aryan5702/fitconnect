package FitConnect.demo.Enum;

public enum UserQueries {
    FIND_USERS_BY_LOCATION("{ 'email': { $ne: ?0, $exists: true }, 'location': { $geoWithin: { $centerSphere: [ [ ?1, ?2 ], ?3 ] } } }");

    private final String query;

    UserQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
