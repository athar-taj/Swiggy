package cln.swiggy.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingCheckpoint {
    private String status;
    private LocalDateTime timestamp;
    private String description;
}
