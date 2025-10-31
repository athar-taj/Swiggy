package cln.swiggy.restaurant.migration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataMigrationController {

    private final DataMigrationService dataMigrationService;

    public DataMigrationController(DataMigrationService dataMigrationService) {
        this.dataMigrationService = dataMigrationService;
    }

    @GetMapping("/migrate")
    public String migrate() {
        return dataMigrationService.migrateToPostgres();
    }

    @GetMapping("/migrate/mongo")
    public String migrateToMongo() {
        return dataMigrationService.migrateToMongo();
    }
}

