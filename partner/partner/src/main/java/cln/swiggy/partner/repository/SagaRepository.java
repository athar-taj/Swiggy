package cln.swiggy.partner.repository;

import cln.swiggy.partner.model.SagaEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaRepository extends JpaRepository<SagaEventEntity,String> {
}
