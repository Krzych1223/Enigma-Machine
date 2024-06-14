package pl.enigmaMachine;

/*
 * code created by Krzysztof Świątkowski
 */

import java.util.ArrayList;

public class Plugboard {

    private final int[][] connections = new int[6][2];

    private int num=0;
    public int checkConncetion(int t) {
        for (int i = 0; i < 6; i++) {
            if(connections[i][0] == t) {
                t = connections[i][1];
                break;
            }
            else if (connections[i][1] == t) {
                t = connections[i][0];
                break;
            }
        }
        return t;
    }

    private void createConnection(int con1, int con2) {
        if(num < 7) {
            connections[num][0] = con1;
            connections[num][1] = con2;
            num++;
        }
    }
    private int encrypter(char c)
    {
        int ret = (int)c >= 65 && (int)c <= 90 ? (int)c - 64: 0;
        return ret == 0 ? (int)c - 96 : ret;
    }


    public Plugboard() {} //creates empty Plugboard (with 0 connections)



    /**
     *
     * creates {@code Plugboard} with connections given by {@code connections} parameter
     *
     * @param connections List of strings up to 6, each string have to has 2 unique letters
     * <p>
     * @throws IllegalArgumentException when:
     * <p>- Letters are not unique
     * <p>- strings aren't a letter pair (don't have length 2)
     */
    public Plugboard(String[] connections) throws IllegalArgumentException {

        ArrayList<Character> presentLetters = new ArrayList<>();
        //Test if there are no repeating letters, then add connection
        for (int i = 0; i < connections.length; i++) {
            if(connections[i].length() == 2)
            {
                if(num < 7) {
                    if(!presentLetters.contains(connections[i].charAt(0)) &&
                       !presentLetters.contains(connections[i].charAt(1))) {

                        int letter1 = encrypter(connections[i].charAt(0));
                        int letter2 = encrypter(connections[i].charAt(1));
                        createConnection(letter1, letter2);
                        presentLetters.set(2 * i, connections[i].charAt(0));
                        presentLetters.set(2 * i + 1, connections[i].charAt(1));
                    }
                    else {
                        throw new IllegalArgumentException("Letters are not unique");
                    }
                }
                else {
                        break;
                    }

            }else{
                throw new IllegalArgumentException("Wrong connection pattern");
            }
        }
    }
}