package cln.swiggy.restaurant.migration.repo;

import cln.swiggy.restaurant.migration.document.FacilityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityMongoRepository extends MongoRepository<FacilityDocument,String> {
}
