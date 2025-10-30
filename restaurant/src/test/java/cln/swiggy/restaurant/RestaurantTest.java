package cln.swiggy.restaurant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cln.swiggy.restaurant.model.Category;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.model.response.CommonResponse;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import cln.swiggy.restaurant.serviceImpl.RestaurantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RestaurantTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;


    @Test
    void testGetRestaurantCategories_withMock() throws Exception {
        // Arrange
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Athar's Kitchen");
        restaurant.setCategories(List.of(new Category("Indian"), new Category("Chinese")));

        Mockito.when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        // Act
        ResponseEntity<CommonResponse> response = restaurantService.getRestaurantCategories(1L);

        // Assert
        assertEquals(200, response.getBody().getStatusCode());
        assertEquals(List.of("Indian", "Chinese"), response.getBody().getData());
        Mockito.verify(restaurantRepository).findById(1L);
    }

}
