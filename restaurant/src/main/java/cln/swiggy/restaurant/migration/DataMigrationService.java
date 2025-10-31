package cln.swiggy.restaurant.migration;

import cln.swiggy.restaurant.migration.document.*;
import cln.swiggy.restaurant.migration.repo.*;
import cln.swiggy.restaurant.model.Address;
import cln.swiggy.restaurant.model.Facility;
import cln.swiggy.restaurant.model.Menu;
import cln.swiggy.restaurant.model.Restaurant;
import cln.swiggy.restaurant.repository.AddressRepository;
import cln.swiggy.restaurant.repository.FacilityRepository;
import cln.swiggy.restaurant.repository.MenuRepository;
import cln.swiggy.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DataMigrationService {

    private final RestaurantRepository mysqlRestaurantRepo;
    private final RestaurantMongoRepository mongoRepo;
    private final RestaurantPostgresRepository postgresRestaurantRepo;
    private final FacilityRepository facilityRepo;
    private final AddressRepository addressRepo;
    private final MenuRepository menuRepo;
    private final AddressMongoRepository addressMongoRepository;
    private final BookingMongoRepository bookingMongoRepository;
    private final CategoryMongoRepository categoryMongoRepository;
    private final FacilityMongoRepository facilityMongoRepository;
    private final MenuMongoRepository menuMongoRepository;
    private final OfferMongoRepository offerMongoRepository;
    private final RestaurantMenuImageMongoRepository restaurantMenuImageMongoRepository;
    private final RestaurantImageMongoRepository restaurantImageMongoRepository;

    @Autowired
    public DataMigrationService(RestaurantRepository mysqlRestaurantRepo, RestaurantMongoRepository mongoRepo,
                                RestaurantPostgresRepository postgresRestaurantRepo, FacilityRepository facilityRepo, AddressRepository addressRepo, MenuRepository menuRepo, AddressMongoRepository addressMongoRepository, BookingMongoRepository bookingMongoRepository, CategoryMongoRepository categoryMongoRepository, FacilityMongoRepository facilityMongoRepository, MenuMongoRepository menuMongoRepository, OfferMongoRepository offerMongoRepository, RestaurantMenuImageMongoRepository restaurantMenuImageMongoRepository, RestaurantImageMongoRepository restaurantImageMongoRepository) {
        this.mysqlRestaurantRepo = mysqlRestaurantRepo;
        this.mongoRepo = mongoRepo;
        this.postgresRestaurantRepo = postgresRestaurantRepo;
        this.facilityRepo = facilityRepo;
        this.addressRepo = addressRepo;
        this.menuRepo = menuRepo;
        this.addressMongoRepository = addressMongoRepository;
        this.bookingMongoRepository = bookingMongoRepository;
        this.categoryMongoRepository = categoryMongoRepository;
        this.facilityMongoRepository = facilityMongoRepository;
        this.menuMongoRepository = menuMongoRepository;
        this.offerMongoRepository = offerMongoRepository;
        this.restaurantMenuImageMongoRepository = restaurantMenuImageMongoRepository;
        this.restaurantImageMongoRepository = restaurantImageMongoRepository;
    }

    @Transactional("postgresTransactionManager")
    public String migrateToPostgres() {
        List<Restaurant> allRestaurants = mysqlRestaurantRepo.findAll();
        postgresRestaurantRepo.saveAll(allRestaurants);
        return "✅ Migrated " + allRestaurants.size() + " restaurants successfully.";
    }

    public String migrateToMongo() {
        List<Restaurant> restaurants = mysqlRestaurantRepo.findAll();

        List<RestaurantDocument> docs = restaurants.stream().map(r -> {
            RestaurantDocument doc = new RestaurantDocument();
            doc.setId(UUID.randomUUID().toString());

            // === BASIC FIELDS ===
            doc.setName(r.getName());
            doc.setOwnerId(r.getOwnerId());
            doc.setDescription(r.getDescription());
            doc.setContactNo(r.getContactNo());
            doc.setEmail(r.getEmail());
            doc.setLogo(r.getLogo());
            doc.setRestaurantType(r.getRestaurantType() != null ? r.getRestaurantType().name() : null);
            doc.setOpenDays(r.getOpenDays() != null ? r.getOpenDays() : Collections.emptyList());
            doc.setIsAvailable(r.getIsAvailable() != null ? r.getIsAvailable() : false);
            doc.setRating(r.getRating() != null ? r.getRating() : 0.0);
            doc.setTotalRating(r.getTotalRating() != null ? r.getTotalRating() : 0);
            doc.setCostForTwo(r.getCostForTwo() != null ? r.getCostForTwo() : 0.0);
            doc.setCreatedAt(r.getCreatedAt());
            doc.setUpdatedAt(r.getUpdatedAt());

            // === ADDRESS (Embedded) ===
            Address address = addressRepo.findByRestaurantId(r.getId());
            if (address != null) {
                AddressDocument addressDoc = new AddressDocument();
                addressDoc.setId(UUID.randomUUID().toString());
                addressDoc.setOutlet(address.getOutlet());
                addressDoc.setLatitude(address.getLatitude());
                addressDoc.setLongitude(address.getLongitude());
                addressDoc.setAddress(address.getAddress());
                addressDoc.setCity(address.getCity());
                addressDoc.setState(address.getState());
                addressDoc.setPincode(address.getPincode());
                addressDoc.setCreatedAt(address.getCreatedAt());
                addressDoc.setUpdatedAt(address.getUpdatedAt());
                addressMongoRepository.save(addressDoc); // save embedded object
                doc.setAddresses(Collections.singletonList(addressDoc));
            } else {
                doc.setAddresses(Collections.emptyList());
            }

            // === CATEGORIES (Embedded) ===
            if (r.getCategories() != null && !r.getCategories().isEmpty()) {
                List<CategoryDocument> categoryDocs = r.getCategories().stream().map(c -> {
                    CategoryDocument cd = categoryMongoRepository.findByName(c.getName())
                            .orElseGet(() -> {
                                CategoryDocument newCat = new CategoryDocument();
                                newCat.setId(UUID.randomUUID().toString());
                                newCat.setName(c.getName());
                                newCat.setDescription(c.getDescription());
                                categoryMongoRepository.save(newCat);
                                return newCat;
                            });
                    return cd;
                }).collect(Collectors.toList());
                doc.setCategories(categoryDocs);
            } else {
                doc.setCategories(Collections.emptyList());
            }

            // === OFFERS (Embedded) ===
            if (r.getOffers() != null && !r.getOffers().isEmpty()) {
                List<OfferDocument> offerDocs = r.getOffers().stream().map(o -> {
                    OfferDocument od = new OfferDocument();
                    od.setId(UUID.randomUUID().toString());
                    od.setOfferName(o.getOfferName());
                    od.setOfferDescription(o.getOfferDescription());
                    od.setOfferDiscount(o.getOfferDiscount());
                    od.setOfferType(o.getOfferType());
                    od.setStartDate(o.getStartDate());
                    od.setEndDate(o.getEndDate());
                    od.setIsActive(o.getIsActive());
                    offerMongoRepository.save(od);
                    return od;
                }).collect(Collectors.toList());
                doc.setOffers(offerDocs);
            } else {
                doc.setOffers(Collections.emptyList());
            }

            // === IMAGES (Embedded) ===
            if (r.getImages() != null && !r.getImages().isEmpty()) {
                List<RestaurantImageDocument> imageDocs = r.getImages().stream().map(img -> {
                    RestaurantImageDocument imgDoc = new RestaurantImageDocument();
                    imgDoc.setId(UUID.randomUUID().toString());
                    imgDoc.setImage(img.getImage());
                    imgDoc.setLogoImage(img.isLogoImage());
                    restaurantImageMongoRepository.save(imgDoc);
                    return imgDoc;
                }).collect(Collectors.toList());
                doc.setImages(imageDocs);
            } else {
                doc.setImages(Collections.emptyList());
            }

            // === FACILITIES (Reference IDs) ===
            List<String> facilityIds = new ArrayList<>();
            if (r.getFacilities() != null && !r.getFacilities().isEmpty()) {
                for (Facility f : r.getFacilities()) {
                    FacilityDocument fd = new FacilityDocument();
                    fd.setId(UUID.randomUUID().toString());
                    fd.setFacilityName(f.getFacilityName());
                    fd.setDescription(f.getDescription());
                    facilityMongoRepository.save(fd);
                    facilityIds.add(fd.getId());
                }
            }
            doc.setFacilityIds(facilityIds);

            // === MENUS (Reference IDs) ===
            List<String> menuIds = new ArrayList<>();
            if (r.getMenus() != null && !r.getMenus().isEmpty()) {
                for (Menu m : r.getMenus()) {
                    MenuDocument md = new MenuDocument();
                    md.setId(UUID.randomUUID().toString());
                    md.setName(m.getName());
                    md.setDescription(m.getDescription());
                    md.setPrice(m.getPrice());
                    md.setDiscount(m.getDiscount());
                    md.setTotalOrders(m.getTotalOrders());
                    md.setRating(m.getRating());
                    md.setMenuType(m.getMenuType());
                    md.setIsAvailable(m.getIsAvailable());
                    md.setCreatedAt(m.getCreatedAt());
                    md.setUpdatedAt(m.getUpdatedAt());
                    menuMongoRepository.save(md);
                    menuIds.add(md.getId());
                }
            }
            doc.setMenuIds(menuIds);

            return doc;
        }).collect(Collectors.toList());

        mongoRepo.saveAll(docs);
        return "✅ Migrated " + docs.size() + " restaurants with embedded documents and reference IDs safely.";
    }

}
