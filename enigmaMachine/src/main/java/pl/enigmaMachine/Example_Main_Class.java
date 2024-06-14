package pl.enigmaMachine;

/*
 * code created by Krzysztof Świątkowski
 */

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Example_Main_Class {
    /**
     * This is an example of main class.
     * <p>
     *     In thie main function, {@code Enigma} is being created with custom rotor settings,
     *     starting rotations set to 3, 8 and 11 and
     *     ring settings set to 25, 2 and 16
     * </p>
     *
     * <p>
     *     Input method is {@code Scanner}.{@code next()} getting word to hash, and it prints encrypted word
     * </p>
     */
    public static void main(String[] args) throws IOException {
        String[] rotors = new String[]{"bhuijnmkoplqazxswedcvfrytg", "QWERTYUIOPLAKSJDHFGZMXNCBV", "MNBVCXZAQSWDEFRGTHYJUKILOP"};
        String[] reflectorPairs = new String[]{"QZ","WX", "EC", "RV", "TB", "YN", "UM", "IK", "OL", "PA","SD","FG","HJ"};
        Rotors r = new Rotors(rotors, reflectorPairs);
        Enigma enigma = new Enigma(r, 3,8,11,25,2,16);
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("Please enter the letters to hash:");
            String input = sc.nextLine();
            if(Objects.equals(input, "-exit"))
            {break;}
            else if(Objects.equals(input, "-reset"))
            {
                enigma.resetPos();
            }
            else {
                char[] hashedInput = new char[input.length()];
                for (int i = 0; i < input.length(); i++) {
                    try {
                        if (!Character.isLetter(input.charAt(i))) {
                            throw new RuntimeException("Invalid character");
                        }
                        hashedInput[i] = enigma.hash(input.charAt(i));
                    } catch (RuntimeException e) {
                        hashedInput = new char[0];
                        break;
                    }
                }
                if (hashedInput.length == 0) {
                    System.out.println("Invalid hash");
                } else {
                    System.out.println(hashedInput);
                }
            }
        }
    }
}
