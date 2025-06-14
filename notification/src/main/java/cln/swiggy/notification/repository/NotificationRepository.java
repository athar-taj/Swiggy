package cln.swiggy.notification.repository;

import cln.swiggy.notification.model.Notification;
import cln.swiggy.notification.model.enums.ReceiverType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverIdAndReceiverType(Long receiverId, ReceiverType receiverType);

}
