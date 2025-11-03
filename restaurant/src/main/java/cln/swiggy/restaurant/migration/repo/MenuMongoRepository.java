package cln.swiggy.restaurant.migration.repo;

import cln.swiggy.restaurant.migration.document.MenuDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuMongoRepository extends MongoRepository<MenuDocument,String> {
    Optional<MenuDocument> findByMysqlId(Long id);
}
