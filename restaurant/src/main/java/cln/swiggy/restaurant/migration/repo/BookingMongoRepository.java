package cln.swiggy.restaurant.migration.repo;

import cln.swiggy.restaurant.migration.document.BookingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingMongoRepository extends MongoRepository<BookingDocument,String> {
}