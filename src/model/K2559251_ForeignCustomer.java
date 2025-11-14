package model;

public class K2559251_ForeignCustomer extends K2559251_Customer{
    private String passport;

    public K2559251_ForeignCustomer(String name, String contactNumber, String email, String passport) {
        super(name, contactNumber, email);
        this.passport = passport;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    // Implementing the abstract method from K2559251_Customer
    @Override
    public String getIdentifier() {
        return "Customer : " + getName() + " | Passport: " + getPassport();
    }
}
