package services;

import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        boolean userExists = users.stream().
                anyMatch(u -> u.getEmail()
                        .equals(user.getEmail()));

        if (userExists) {
            throw new IllegalArgumentException("Email ja cadastrado.");
        }
        users.add(user);
    }
    public List<User> listUsers() {
        return new ArrayList<>(users);
    }
    public Optional<User> findByEmail( String email) {
        return users.stream()
                .filter (user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();

    }

}
