package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now ().plusDays (shift).format (DateTimeFormatter.ofPattern ("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        var cities = new String[]{"Владивосток", "Москва", "Санкт-Петербург", "Казань", "Уфа", "Волгоград", "Екатеринбург", "Нальчик", "Хабаровск", "Самара"};
        return cities[new Random ().nextInt (cities.length)];
    }

    public static String generateName(String locale) {
        var faker = new Faker (new Locale (locale));
        return faker.name ().lastName () + " " + faker.name ().firstName ();
    }

    public static String generatePhone(String locale) {
        var faker = new Faker (new Locale (locale));
        return faker.phoneNumber ().phoneNumber ();
    }

    public static String generateWrongPhone (String locale) {
        var faker = new Faker (new Locale (locale));
        return faker.phoneNumber ().cellPhone ();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo (generateCity (locale), generateName (locale), generatePhone (locale));
            // TODO: добавить логику для создания пользователя user с использованием методов generateCity(locale),
            // generateName(locale), generatePhone(locale)
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}