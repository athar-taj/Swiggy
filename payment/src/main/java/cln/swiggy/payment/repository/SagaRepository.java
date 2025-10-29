package cln.swiggy.order.service;

import cln.swiggy.order.model.SagaEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaRepository extends JpaRepository<SagaEventEntity,String> {
}
