package cln.swiggy.partner.controller;

import cln.swiggy.partner.model.request.ComboOfferRequest;
import cln.swiggy.partner.model.request.RestaurantRequest;
import cln.swiggy.partner.model.response.CommonResponse;
import cln.swiggy.partner.service.ComboService;
import cln.swiggy.partner.serviceImpl.otherImple.ImageValidation;
import cln.swiggy.partner.serviceImpl.otherImple.ValidationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/combos")
public class ComboController {

    @Autowired ComboService comboService;
    @Autowired ValidationUtil validationUtil;

    @Operation(summary = "Add a new combo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Combo added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CommonResponse> addCombo(
            @RequestPart(value = "images") List<MultipartFile> images,
            @RequestPart(value = "ComboData", required = false) String ComboRequest) throws JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

        ComboOfferRequest request = mapper.readValue(ComboRequest, ComboOfferRequest.class);


        validationUtil.validateRequest(request);
        ImageValidation.validateImages(images);
        return comboService.addCombo(images, request);
    }

    @Operation(summary = "Delete a combo by ID and restaurant ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Combo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Combo not found", content = @Content)
    })
    @DeleteMapping("/{restaurantId}/{comboId}")
    public ResponseEntity<CommonResponse> deleteCombo(
            @PathVariable Long restaurantId,
            @PathVariable Long comboId) {
        return comboService.deleteCombo(restaurantId, comboId);
    }

    @Operation(summary = "Get all combos by restaurant ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Combos retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No combos found for the restaurant", content = @Content)
    })
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<CommonResponse> getCombosByRestaurantId(@PathVariable Long restaurantId) {
        return comboService.getCombosByRestaurantId(restaurantId);
    }

    @Operation(summary = "Get all available combos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All combos retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No combos found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<CommonResponse> getAllCombos() {
        return comboService.getAllCombos();
    }

    @Operation(summary = "Update an existing combo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Combo updated successfully"),
            @ApiResponse(responseCode = "404", description = "Combo not found", content = @Content)
    })
    @PutMapping("/{comboId}")
    public ResponseEntity<CommonResponse> updateCombo(
            @PathVariable Long comboId,
            @RequestPart(value = "images") List<MultipartFile> images,
            @RequestPart(value = "ComboData", required = false) String ComboRequest) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        ComboOfferRequest request = mapper.readValue(ComboRequest, ComboOfferRequest.class);


        validationUtil.validateRequest(request);
        ImageValidation.validateImages(images);
        return comboService.updateCombo(images, request,comboId);
    }

    @Operation(summary = "Get most trending combos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trending combos retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No trending combos found", content = @Content)
    })
    @GetMapping("/most-trending")
    public ResponseEntity<CommonResponse> mostTrendingCombos() {
        return comboService.mostTrendingCombos();
    }
}