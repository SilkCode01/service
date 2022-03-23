package ro.unibuc.hello;
//package CryptoController.;
import org.springframework.boot.SpringApplication;
import ro.unibuc.hello.controller.CryptoController;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option;
        int exit = 0;
        System.out.println("SilkCode Production\n");
        while(true){
            System.out.println("Type the number of your option then press enter:\n" +
                    "1.Show recent\n" +
                    "2.Search cryptocurrency\n" +
                    "3.Show all\n" +
                    "0.Exit");
            option = input.nextInt();
            switch(option){
                case 1:
                    System.out.println("Feature not yet available");
                    break;
                case 2:
                    System.out.println("Feature not yet available");
                    break;
                case 3:
                    CryptoController.main();
                    break;
                case 0:
                    exit = 1;
                    break;
                default:
                    System.out.println("Please enter a valid number");
            }
            if(exit == 1)
                break;
        }
    }
}
