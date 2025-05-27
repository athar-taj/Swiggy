package cln.swiggy.partner.search.repository;

import cln.swiggy.partner.search.model.ElasticObject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticRepository extends ElasticsearchRepository<ElasticObject,String> {
}
