import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.*;

public class Main {
    static ArrayList<Product> Inventory = new ArrayList<>();
    static ArrayList<Product> Cart = new ArrayList<>();
    static Scanner userInput = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        boolean done = false;
        loadInventory();

        while (!done) {
            System.out.println("---------------------------");
            System.out.println("The Store Home Screen: ");
            System.out.println("1 - Show Products");
            System.out.println("2 - Show Cart");
            System.out.println("3 - Exit");

            int answer = userInput.nextInt();

            switch (answer) {
                case 1:
                    System.out.println("---------------------------");
                    showInventory();
                    System.out.println("---------------------------");
                    addToCart();
                    break;
                case 2:
                    System.out.println("---------------------------");
                    showCart();
                    break;
                case 3:
                    done = true;
                    return;
                default:
                    System.out.println("Wrong input, please try again");
                    break;
            }

        }
    }
        public static void loadInventory(){
        String id;
        double price;
        String name;
        String info;

        try {
            Scanner reader = new Scanner(new File("Inventory.txt"));
            while(reader.hasNext()) {
                info = reader.nextLine();
                String[] information = info.split(Pattern.quote("|"));
                id = information[0];
                name = information[1];
                price = Double.parseDouble(information[2]);

                Product item = new Product(id,name,price);
                Inventory.add(item);

            }
        }catch(FileNotFoundException e){
            System.out.println("Could not find file");
        }
    }
        public static void showInventory(){
            for (Product value : Inventory) {
                System.out.println(value);
            }
    }
        public static void addToCart(){
            System.out.println("Select one option:");
            System.out.println("1 - Add item to cart");
            System.out.println("X - Exit to home screen");
            String response = userInput.next();
            if(response.equals("1")){
                System.out.print("Enter product ID: ");
                String name = userInput.next();
                for(int i = 0; i < Inventory.size(); i++){
                    if(Inventory.get(i).getId().equals(name)){
                        Cart.add(Inventory.get(i));
                        System.out.println("Item has been added to cart");
                        break;
                    }
                }
            }
            if(response.equalsIgnoreCase("x")){
            }
        }
        public static void showCart() {
        double totalPrice = 0;
            for (Product item: Cart){
                System.out.println(item);
            }
            System.out.println("Select one option:");
            System.out.println("C - Check Out");
            System.out.println("X - Exit to home screen");
            String response = userInput.next();
            if(response.equalsIgnoreCase("C")){
                for(Product value: Cart){
                   totalPrice += value.getPrice();
                }
                System.out.println("The total price of all the items in your cart are: " + totalPrice);
                System.out.println("Please enter the amount you would like to pay");
                double payment = userInput.nextDouble();
                while(payment < totalPrice){
                    System.out.println("Sorry that wasn't enough, Please try again");
                    payment = userInput.nextInt();
                }
                if(payment >= totalPrice){
                    for (Product item: Cart){
                        System.out.println(item);
                    }
                    System.out.println("Thank you for your payment, you are owed "+ (payment - totalPrice)+ " ..., Thank you and come again.");
                    for (Product item: Cart){
                        Cart.remove(item);
                    }
                }

            }
        }
}