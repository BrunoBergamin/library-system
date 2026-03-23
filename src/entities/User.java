package entities;

public class User extends Person {

    public User(String name, String email) {
        super(name, email);
    }

    @Override
    public String getProfileType() {
        return "Usuario";
    }
}
