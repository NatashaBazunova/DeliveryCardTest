package ru.netology.delivery;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }
    public static String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    public static class Registration {
        private Registration() {
        }

        public static String generateCity() {
            Faker faker = new Faker(new Locale("ru", "Russia"));
            return faker.address().city();
        }


        public static String generateName() {
            Faker faker = new Faker(new Locale("ru", "Russia"));
            return faker.name().lastName() + " " + faker.name().firstName();
        }

        public static String generatePhone() {
            Faker faker = new Faker(new Locale("ru"));
            return faker.phoneNumber().phoneNumber();
        }


        public static UserInfo generateUserInfo() {
            return new UserInfo(generateCity(), generateName(), generatePhone());

        }


    }
}
