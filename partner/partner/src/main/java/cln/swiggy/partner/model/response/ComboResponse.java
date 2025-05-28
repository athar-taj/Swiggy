package cln.swiggy.partner.model.response;

import cln.swiggy.partner.model.Combo;
import cln.swiggy.partner.model.enums.MenuType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Component
public class ComboResponse {
    @Autowired
    private MenuResponse menuResponse;

    private String name;
    private String description;
    private double price;
    private int totalRating;
    private double rating;
    private int totalOrders;
    private MenuType comboType;
    private boolean isAvailable;
    private List<String> image;
    private Set<MenuResponse> menus;
    private Set<OfferResponse> offers;

    public ComboResponse convertToResponse(Combo combo){
        ComboResponse response = new ComboResponse();
        response.setName(combo.getName());
        response.setDescription(combo.getDescription());
        response.setPrice(combo.getPrice());
        response.setTotalRating(combo.getTotalRating());
        response.setRating(combo.getRating());
        response.setComboType(combo.getComboType());
        response.setTotalOrders(combo.getTotalOrders());
        response.setImage(combo.getImage());

        Set<OfferResponse> offerResponses = combo.getOffers().stream()
                .map(OfferResponse::convertToResponse)
                .collect(Collectors.toSet());
        response.setOffers(offerResponses);

        Set<MenuResponse> menuResponses = combo.getMenus().stream()
                .map(menu -> menuResponse.convertToResponse(menu))
                .collect(Collectors.toSet());
        response.setMenus(menuResponses);

        return response;
    }

    public List<ComboResponse> convertToResponse(List<Combo> combos){
        List<ComboResponse> response = new java.util.ArrayList<>();
        for (Combo combo : combos){
            response.add(convertToResponse(combo));
        }
        return response;
    }
}
