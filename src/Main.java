import model.K2559251_LocalCustomer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        K2559251_LocalCustomer localCustomer = new K2559251_LocalCustomer("John Doe", "1234567890", "John Doe@gmail.com", "987654321V");
        System.out.println(localCustomer.getIdentifier());
    }
}