package model;

public class K2559251_LocalCustomer extends K2559251_Customer{
    private String nic;

    public K2559251_LocalCustomer(String name, String contactNumber, String email, String nic) {
        super(name, contactNumber, email);
        this.nic = nic;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    // Implementing the abstract method from K2559251_Customer
    @Override
    public String getIdentifier() {
        return "Customer : " + getName() + " | NIC: " + getNic();
    }
}
