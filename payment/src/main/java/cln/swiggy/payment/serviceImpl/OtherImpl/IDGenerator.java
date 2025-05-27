package cln.swiggy.payment.serviceImpl.OtherImpl;

import java.util.Random;

public class IDGenerator {

    public static String generateId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            id.append(characters.charAt(random.nextInt(characters.length())));
        }

        return id.toString();
    }
}
