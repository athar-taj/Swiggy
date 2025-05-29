package cln.swiggy.notification.model;

import cln.swiggy.notification.model.enums.NotificationType;
import cln.swiggy.notification.model.enums.ReceiverType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long receiverId;

    @Column
    @Enumerated(EnumType.STRING)
    private ReceiverType receiverType;

    @Column
    private String type;

    private String title;

    private String message;

    @Column
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Column
    private LocalDateTime createdAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

}
