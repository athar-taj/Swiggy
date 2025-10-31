package cln.swiggy.restaurant.migration.repo;

import cln.swiggy.restaurant.migration.document.RestaurantDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantMongoRepository extends MongoRepository<RestaurantDocument, String> {}

