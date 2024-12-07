package linkshortener.adomain.entities;

import linkshortener.adomain.valueobjects.UUID;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;

public class User {

    private final UUID UUID; // Уникальный идентификатор пользователя

    // Конструктор
    public User() {
        this.UUID = new UUID(java.util.UUID.randomUUID().toString());
    }

    public User(UUID uuid) {
        this.UUID = uuid;
    }

    // Геттеры
    public UUID getUuid() {
        return UUID;
    }


    // Переопределение equals и hashCode
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        User user = (User) o;
//        return o.equals(uuid, user.uuid);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(uuid);
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "uuid=" + uuid +
//                ", links=" + links.size() +
//                '}';
//    }
}
