package model;

public abstract class K2559251_Customer {
    private String name;
    private String contactNumber;
    private String email;

    public K2559251_Customer(String name, String contactNumber, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getIdentifier();

    @Override
    public String toString() {
        return "K2559251_Customer{" +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
