package pl.enigmaMachine;

/*
  code created by Krzysztof Świątkowski
 */

import java.util.ArrayList;

public class Rotors {

    /**
     * {@code Rotors} class responsible for whole rotor encryption mechanism
     * <p>
     * {@code Alphabet} is a constant array holding numbers of letters in alphabet used to get letter from it
     * to next encryption steps
     * <p>
     * {@code rotorsConfiguration} holds configuration of every rotor and reflector as numbers
     */


    private final int[] Alphabet = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
    private int[][] rotorsConfiguration = new int[4][26];


    /**
     *
     * @param letter is letter set to be encrypted as a number
     * <p>
     *        <p>
     *          Parameters {@code r1} , {@code r2} and {@code r3} are current rotation of rotors
     * </p>
     * <p>
     *               <p>
     *          Parameters {@code ring1} , {@code ring2} and {@code ring3} are ring notches position for all rotors
     * </p>
     */


    public int[] rotors(int letter, int r1, int r2, int r3, int ring1, int ring2, int ring3) {
        int[] rings = new int[]{ring1, ring2, ring3};
        int[] r = rotation(r1, r2, r3, rings);
        int encryptedLetter = rotorsBefore(letter, r);
        encryptedLetter = reflector(encryptedLetter);
        encryptedLetter = rotorsAfter(encryptedLetter, r);
        return new int[]{encryptedLetter, r[0], r[1], r[2]};
    }

    /**
     * {@code rotation} function is responsible for whole rotation mechanic
     *<p>
     * @param rotationRotor1 current position of first rotor
     * @param rotationRotor2 current position of second rotor
     * @param rotationRotor3 current position os third rotor
     * @param ring array of ring notches for each rotor
     *
     * <p>
     *
     */
    private int[] rotation(int rotationRotor1, int rotationRotor2, int rotationRotor3, int[] ring) {
        rotationRotor1 = (rotationRotor1 + 1) % 26;
        if (rotationRotor1 % 26 == ring[0]) {
            rotationRotor2 = (rotationRotor2 + 1) % 26;
            if (rotationRotor2 % 26 == ring[1]) {
                rotationRotor3 = (rotationRotor3 + 1) % 26;
            }
        }
        return new int[]{rotationRotor1, rotationRotor2, rotationRotor3};
    }

    //Rotors encryption before reflection
    private int rotorsBefore(int letterToEncrypt, int[] rotations) {
        for (int i = 0; i < 3; i++) {
            letterToEncrypt = innerLoopBefore(i, letterToEncrypt, rotations[i]);
        }
        return letterToEncrypt;
    }

    //Inner loop for Rotors encryption before
    private int innerLoopBefore(int rotorID, int letterToEncrypt, int rotation) {
        for (int j = 0; j < 26; j++) {
            if (Alphabet[j] == letterToEncrypt) {
                letterToEncrypt = rotorsConfiguration[rotorID][(j + rotation) % 26];
                break;
            }
        }
        return letterToEncrypt;
    }

    private int reflector(int letter) {
        return rotorsConfiguration[3][letter - 1];
    }

    private int rotorsAfter(int Encrypt, int[] r) {
        for (int i = 2; i > -1; i--) {
            Encrypt = innerLoopAfter(i, Encrypt, r[i]);
        }
        return Encrypt;
    }

    private int innerLoopAfter(int i, int Encrypt, int rotation) {

        for (int j = 0; j < 26; j++) {
            if (rotorsConfiguration[i][(j + rotation) % 26] == Encrypt) {
                Encrypt = Alphabet[j];
                break;
            }
        }

        return Encrypt;
    }

    /*
    Constructors
     */

    public Rotors() {
        rotorsConfiguration = new int[][]{
                {10, 7, 4, 17, 15, 24, 21, 19, 3, 1, 13, 9, 6, 18, 22, 20, 16, 14, 5, 23, 11, 2, 12, 26, 25, 8},
                {14, 20, 26, 16, 19, 6, 2, 15, 11, 13, 23, 18, 3, 10, 4, 9, 22, 12, 1, 5, 25, 21, 24, 8, 7, 17},
                {10, 22, 9, 21, 2, 8, 20, 3, 4, 25, 1, 11, 5, 17, 26, 16, 15, 19, 7, 24, 14, 18, 13, 23, 6, 12},
                {17, 25, 8, 15, 7, 14, 5, 3, 22, 16, 21, 26, 20, 6, 4, 10, 1, 24, 23, 13, 11, 9, 19, 18, 2, 12}
        };
    }

    /**
     * 
     * @param rotors rotors settings (must be 3 elements)
     * @param reflector reflectors wiring settings
     * @throws IllegalArgumentException when any of the strings doesn't have length of 26 or when any of characters is not letter
     */
    public Rotors(String[] rotors, String[] reflector) throws IllegalArgumentException {     //custom configuration
        if (rotors.length == 3 && rotors[0].length() == 26 && rotors[1].length() == 26 && rotors[2].length() == 26 && reflector.length == 13) {
            for (int i = 0; i < 4; i++) {
                int[] alphabet = new int[26];
                ArrayList<Integer> letters = new ArrayList<>();
                String value;
                for (int j = 0; j < 26; j++) {
                    if(i != 3)
                    {
                        if (Character.isLetter(rotors[i].charAt(j)) && !letters.contains(encodeLetter(rotors[i].charAt(j)))) {
                            letters.add(encodeLetter(rotors[i].charAt(j)));
                            alphabet[j] = encodeLetter(rotors[i].charAt(j));
                        } else {
                            if(!letters.contains(j))
                               value = "Duplicated letter at: " + i + ":" + j;
                            else
                                value = "Invalid character";

                            throw new IllegalArgumentException(value);
                        }
                    }
                    else{
                        for (int k = 0; k < 13; k++) {
                            if(reflector[k].length() == 2) {
                                int first = encodeLetter(reflector[k].charAt(0));
                                int second = encodeLetter(reflector[k].charAt(1));
                                if(first != second)
                                {
                                        alphabet[first - 1] = second;
                                        alphabet[second - 1] = first;
                                }
                                else{
                                    throw new IllegalArgumentException("Letters are identical at: " + k);
                                }
                            }
                            else{
                                throw new IllegalArgumentException("Invalid connection at index: " + k);
                            }
                        }
                        break;
                    }
                }
                rotorsConfiguration[i] = alphabet;
            }
        } else {
            throw new IllegalArgumentException("Strings too short");
        }

    }

    /**
     *
     * @param c is character input to change into number on which my Enigma machine can operate i.e. in range 1 - 26 inclusive
     */

    private int encodeLetter(char c) {
        int ret = (int) c >= 65 && (int) c <= 90 ? (int) c - 64 : 0;
        return ret == 0 ? (int) c - 96 : ret;
    }
}