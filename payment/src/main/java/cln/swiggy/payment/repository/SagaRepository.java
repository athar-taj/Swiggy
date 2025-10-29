package cln.swiggy.payment.repository;

import cln.swiggy.payment.model.SagaEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaRepository extends JpaRepository<SagaEventEntity,String> {
}
