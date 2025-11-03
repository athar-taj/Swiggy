package cln.swiggy.restaurant.migration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataMigrationScheduler {

    @Autowired
    private DataMigrationService dataMigrationService;

    private boolean isRunning = false;

    @Scheduled(fixedRate = 3600000) // every 1 hour = 60 * 60 * 1000 ms
    public void runPostgresMigration() {
        if (isRunning) {
            System.out.println("⚠️ Previous migration still running. Skipping this cycle.");
            return;
        }

        isRunning = true;
        try {
            System.out.println("🚀 Starting scheduled migration to Postgres...");
            String postgresResult = dataMigrationService.migrateToPostgres();
            String mongoResult = dataMigrationService.migrateToMongo();
            System.out.println(postgresResult);
            System.out.println(mongoResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isRunning = false;
            System.out.println("✅ Migration job finished.");
        }
    }
}

