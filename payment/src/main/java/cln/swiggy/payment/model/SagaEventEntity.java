package cln.swiggy.order.model;
import cln.swiggy.order.model.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "saga_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaEventEntity {

    @Id
    @Column(name = "event_id", nullable = false, unique = true)
    private String eventId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "source_service", nullable = false)
    private String sourceService;

    @Column(name = "saga_id", nullable = false)
    private String sagaId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;

    @Lob
    @Column(name = "payload", columnDefinition = "TEXT")
    private String payloadJson;
}

