package cln.swiggy.restaurant.migration.repo;

import cln.swiggy.restaurant.migration.document.RestaurantImageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantImageMongoRepository extends MongoRepository<RestaurantImageDocument,String> {
}
