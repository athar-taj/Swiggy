package cln.swiggy.restaurant.migration.repo;

import cln.swiggy.restaurant.migration.document.FacilityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityMongoRepository extends MongoRepository<FacilityDocument,String> {
    Optional<FacilityDocument> findByMysqlId(Long id);
}
