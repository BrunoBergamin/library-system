package entities;

public class Admin extends Person {

    public Admin(String name, String email) {
        super(name, email);
    }

    @Override
    public String getProfileType() {
        return "Administrador";
    }
}
