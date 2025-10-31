package cln.swiggy.restaurant.migration.repo;

import cln.swiggy.restaurant.migration.document.RestaurantMenuImageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantMenuImageMongoRepository extends MongoRepository<RestaurantMenuImageDocument,String> {
}
