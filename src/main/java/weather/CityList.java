package weather;

import java.util.ArrayList;
import java.util.List;

public class CityList {
    public static final List<CityInfo> CITIES = new ArrayList<>();

    static {
        // Инициализация списка городов
        CITIES.add(new CityInfo("Нью-Йорк", 40.7128, -74.0060, -4));
        CITIES.add(new CityInfo("Лос-Анджелес", 34.0522, -118.2437, -7));
        CITIES.add(new CityInfo("Чикаго", 41.8781, -87.6298, -5));
        CITIES.add(new CityInfo("Хьюстон", 29.7604, -95.3698, -5));
        CITIES.add(new CityInfo("Финикс", 33.4484, -112.0740, -7));
        CITIES.add(new CityInfo("Лондон", 51.5074, -0.1278, 0));
        CITIES.add(new CityInfo("Париж", 48.8566, 2.3522, 1));
        CITIES.add(new CityInfo("Токио", 35.6895, 139.6917, 9));
        CITIES.add(new CityInfo("Сидней", -33.8688, 151.2093, 10));
        CITIES.add(new CityInfo("Берлин", 52.5200, 13.4050, 1));
        CITIES.add(new CityInfo("Москва", 55.7558, 37.6173, 3));
        CITIES.add(new CityInfo("Дубай", 25.2048, 55.2708, 4));
        CITIES.add(new CityInfo("Сингапур", 1.3521, 103.8198, 8));
        CITIES.add(new CityInfo("Гонконг", 22.3193, 114.1694, 8));
        CITIES.add(new CityInfo("Торонто", 43.6511, -79.3832, -5));
        CITIES.add(new CityInfo("Пекин", 39.9042, 116.4074, 8));
        CITIES.add(new CityInfo("Сан-Паулу", -23.5505, -46.6333, -3));
        CITIES.add(new CityInfo("Сеул", 37.5665, 126.9780, 9));
        CITIES.add(new CityInfo("Бангкок", 13.7563, 100.5018, 7));
        CITIES.add(new CityInfo("Стамбул", 41.0082, 28.9784, 3));
        CITIES.add(new CityInfo("Буэнос-Айрес", -34.6037, -58.3816, -3));
        CITIES.add(new CityInfo("Каир", 30.0444, 31.2357, 2));
        CITIES.add(new CityInfo("Мехико", 19.4326, -99.1332, -6));
        CITIES.add(new CityInfo("Джакарта", -6.2088, 106.8456, 7));
        CITIES.add(new CityInfo("Лагос", 6.5244, 3.3792, 1));
        CITIES.add(new CityInfo("Йоханнесбург", -26.2041, 28.0473, 2));
        CITIES.add(new CityInfo("Шанхай", 31.2304, 121.4737, 8));
        CITIES.add(new CityInfo("Рио-де-Жанейро", -22.9068, -43.1729, -3));
        CITIES.add(new CityInfo("Куала-Лумпур", 3.1390, 101.6869, 8));
        CITIES.add(new CityInfo("Мадрид", 40.4168, -3.7038, 1));
        CITIES.add(new CityInfo("Рим", 41.9028, 12.4964, 1));
        CITIES.add(new CityInfo("Ванкувер", 49.2827, -123.1207, -8));
        CITIES.add(new CityInfo("Сан-Франциско", 37.7749, -122.4194, -8));
        CITIES.add(new CityInfo("Майами", 25.7617, -80.1918, -5));
        CITIES.add(new CityInfo("Лас-Вегас", 36.1699, -115.1398, -8));
        CITIES.add(new CityInfo("Вашингтон", 38.9072, -77.0369, -5));
        CITIES.add(new CityInfo("Бостон", 42.3601, -71.0589, -5));
        CITIES.add(new CityInfo("Атланта", 33.7490, -84.3880, -5));
        CITIES.add(new CityInfo("Денвер", 39.7392, -104.9903, -7));

        // Основные города Беларуси
        CITIES.add(new CityInfo("Минск", 53.9045, 27.5615, 3));
        CITIES.add(new CityInfo("Брест", 52.0976, 23.7341, 3));
        CITIES.add(new CityInfo("Гомель", 52.4412, 30.9878, 3));
        CITIES.add(new CityInfo("Гродно", 53.6778, 23.8298, 3));
        CITIES.add(new CityInfo("Могилев", 53.8945, 30.3300, 3));
        CITIES.add(new CityInfo("Витебск", 55.1904, 30.2049, 3));
        CITIES.add(new CityInfo("Барановичи", 53.1327, 26.0130, 3));
        CITIES.add(new CityInfo("Пинск", 52.1212, 26.0720, 3));
        CITIES.add(new CityInfo("Орша", 54.5155, 30.4171, 3));
        CITIES.add(new CityInfo("Новополоцк", 55.5300, 28.6479, 3));
        CITIES.add(new CityInfo("Бобруйск", 53.1384, 29.2214, 3));
        CITIES.add(new CityInfo("Борисов", 54.2279, 28.5050, 3));
        CITIES.add(new CityInfo("Лида", 53.8831, 25.2999, 3));
        CITIES.add(new CityInfo("Солигорск", 52.7877, 27.5303, 3));
        CITIES.add(new CityInfo("Мозырь", 52.0490, 29.2454, 3));
        CITIES.add(new CityInfo("Полоцк", 55.4875, 28.7851, 3));
        CITIES.add(new CityInfo("Жлобин", 52.8921, 30.0242, 3));
        CITIES.add(new CityInfo("Светлогорск", 52.6325, 29.7380, 3));
        CITIES.add(new CityInfo("Слуцк", 53.0270, 27.5590, 3));
    }
}
