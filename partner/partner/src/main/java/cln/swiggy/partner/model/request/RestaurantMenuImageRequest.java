package cln.swiggy.partner.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class RestaurantMenuImageRequest {

        @NotNull(message = "Restaurant id is required")
        private Long restaurantId;
        private List<MultipartFile> images;
}
