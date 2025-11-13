import model.K2559251_ForeignCustomer;
import model.K2559251_LocalCustomer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        K2559251_LocalCustomer localCustomer = new K2559251_LocalCustomer("John Doe", "1234567890", "JohnDoe@gmail.com", "987654321V");
        System.out.println(localCustomer.getIdentifier());

        K2559251_ForeignCustomer foreignCustomer = new K2559251_ForeignCustomer("Jane Smith", "0987654321", "JaneSmith@gmail.com", "A1234567");
        System.out.println(foreignCustomer.getIdentifier());
    }
}