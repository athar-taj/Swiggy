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

import java.util.*;
import java.util.function.Function;
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
        List<Long> existingIds = postgresRestaurantRepo.findAllIds();
        List<Restaurant> allRestaurants = mysqlRestaurantRepo.findAll();

        List<Restaurant> newRestaurants = allRestaurants.stream()
                .filter(r -> !existingIds.contains(r.getId()))
                .collect(Collectors.toList());

        if (!newRestaurants.isEmpty()) {
            postgresRestaurantRepo.saveAll(newRestaurants);
        }

        return "✅ Migrated " + newRestaurants.size() + " new restaurants successfully.";
    }

    public String migrateToMongo() {
        List<Restaurant> restaurants = mysqlRestaurantRepo.findAll();

        List<RestaurantDocument> newDocs = new ArrayList<>();

        for (Restaurant r : restaurants) {
            if (mongoRepo.existsByMysqlId(r.getId())) {
                continue;
            }

            RestaurantDocument doc = new RestaurantDocument();
            doc.setId(UUID.randomUUID().toString());
            doc.setMysqlId(r.getId());

            doc.setName(r.getName());
            doc.setOwnerId(r.getOwnerId());
            doc.setDescription(r.getDescription());
            doc.setContactNo(r.getContactNo());
            doc.setEmail(r.getEmail());
            doc.setLogo(r.getLogo());
            doc.setRestaurantType(r.getRestaurantType() != null ? r.getRestaurantType().name() : null);
            doc.setOpenDays(r.getOpenDays() != null ? r.getOpenDays() : Collections.emptyList());
            doc.setIsAvailable(Boolean.TRUE.equals(r.getIsAvailable()));
            doc.setRating(Optional.ofNullable(r.getRating()).orElse(0.0));
            doc.setTotalRating(Optional.ofNullable(r.getTotalRating()).orElse(0));
            doc.setCostForTwo(Optional.ofNullable(r.getCostForTwo()).orElse(0.0));
            doc.setCreatedAt(r.getCreatedAt());
            doc.setUpdatedAt(r.getUpdatedAt());

            Address address = addressRepo.findByRestaurantId(r.getId());
            if (address != null) {
                AddressDocument addressDoc = new AddressDocument();
                addressDoc.setId(UUID.randomUUID().toString());
                addressDoc.setMysqlId(address.getId());
                addressDoc.setOutlet(address.getOutlet());
                addressDoc.setLatitude(address.getLatitude());
                addressDoc.setLongitude(address.getLongitude());
                addressDoc.setAddress(address.getAddress());
                addressDoc.setCity(address.getCity());
                addressDoc.setState(address.getState());
                addressDoc.setPincode(address.getPincode());
                addressDoc.setCreatedAt(address.getCreatedAt());
                addressDoc.setUpdatedAt(address.getUpdatedAt());
                doc.setAddresses(List.of(addressDoc));
            }

            if (r.getCategories() != null && !r.getCategories().isEmpty()) {
                List<CategoryDocument> categoryDocs = r.getCategories().stream().map(c ->
                        categoryMongoRepository.findByMysqlId(c.getId())
                                .orElseGet(() -> {
                                    CategoryDocument newCat = new CategoryDocument();
                                    newCat.setId(UUID.randomUUID().toString());
                                    newCat.setMysqlId(c.getId());
                                    newCat.setName(c.getName());
                                    newCat.setDescription(c.getDescription());
                                    return categoryMongoRepository.save(newCat);
                                })
                ).toList();
                doc.setCategories(categoryDocs);
            }

            List<String> facilityIds = new ArrayList<>();
            if (r.getFacilities() != null) {
                for (Facility f : r.getFacilities()) {
                    FacilityDocument fd = facilityMongoRepository.findByMysqlId(f.getId())
                            .orElseGet(() -> {
                                FacilityDocument newFd = new FacilityDocument();
                                newFd.setId(UUID.randomUUID().toString());
                                newFd.setMysqlId(f.getId());
                                newFd.setFacilityName(f.getFacilityName());
                                newFd.setDescription(f.getDescription());
                                return facilityMongoRepository.save(newFd);
                            });
                    facilityIds.add(fd.getId());
                }
            }
            doc.setFacilityIds(facilityIds);

            List<String> menuIds = new ArrayList<>();
            if (r.getMenus() != null) {
                for (Menu m : r.getMenus()) {
                    MenuDocument md = menuMongoRepository.findByMysqlId(m.getId())
                            .orElseGet(() -> {
                                MenuDocument newMd = new MenuDocument();
                                newMd.setId(UUID.randomUUID().toString());
                                newMd.setMysqlId(m.getId());
                                newMd.setName(m.getName());
                                newMd.setDescription(m.getDescription());
                                newMd.setPrice(m.getPrice());
                                newMd.setDiscount(m.getDiscount());
                                newMd.setRating(m.getRating());
                                newMd.setIsAvailable(m.getIsAvailable());
                                newMd.setCreatedAt(m.getCreatedAt());
                                newMd.setUpdatedAt(m.getUpdatedAt());
                                return menuMongoRepository.save(newMd);
                            });
                    menuIds.add(md.getId());
                }
            }
            doc.setMenuIds(menuIds);

            newDocs.add(doc);
        }

        if (!newDocs.isEmpty()) {
            mongoRepo.saveAll(newDocs);
        }

        rebuildRelations();

        return "✅ Migrated " + newDocs.size() + " new restaurants safely to Mongo.";
    }

    public void rebuildRelations() {
        Map<Long, String> restaurantMap = mongoRepo.findAll().stream()
                .collect(Collectors.toMap(RestaurantDocument::getMysqlId, RestaurantDocument::getId));

        List<Menu> mysqlMenus = menuRepo.findAll();

        Map<Long, MenuDocument> menuMap = menuMongoRepository.findAll().stream()
                .collect(Collectors.toMap(MenuDocument::getMysqlId, Function.identity()));

        for (Menu menu : mysqlMenus) {
            Long restaurantMysqlId = menu.getRestaurant().getId();
            String restaurantMongoId = restaurantMap.get(restaurantMysqlId);
            if (restaurantMongoId != null) {
                MenuDocument menuDoc = menuMap.get(menu.getId());
                if (menuDoc != null) {
                    menuDoc.setRestaurantId(restaurantMongoId);
                    menuMongoRepository.save(menuDoc);
                }
            }
        }
        System.out.println("✅ Menu → Restaurant relations rebuilt successfully!");
    }
}
