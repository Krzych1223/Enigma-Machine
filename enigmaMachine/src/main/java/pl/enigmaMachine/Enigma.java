package pl.enigmaMachine;

/*
 * code created by Krzysztof Świątkowski
 */

public class Enigma {

    /**
     *
     * @param value letter to encrypt
     * @return encrypted character
     */
    public char hash(char value) {
        return enigma(value);
    }


    public static int[] start;
    public void resetPos()
    {
            rotationRotor1 = start[0];
            rotationRotor2 = start[1];
            rotationRotor3 = start[2];
    }

    private int rotationRotor1 = 0;
    private int rotationRotor2 = 0;
    private int rotationRotor3 = 0;         //Rotation positions of all Rotors
    private int ring1 = 0, ring2 = 0, ring3 = 0;     //Settings for "Break point" (where 1st rotor makes 2nd rotate etc.)
    private Rotors rotor;
    private Plugboard plugboard;

    private char enigma(char c)         //Machine mechanism
    {
        int t = encoder(c);


        t = plugboard.checkConncetion(t);                   //check if there is a connection on this letter
        int[] list = rotor.rotors(t, rotationRotor1, rotationRotor2, rotationRotor3, ring1, ring2, ring3);               //Encrypt input using rotors and return updated rotations
        t = list[0];
        rotationRotor1 = list[1];
        rotationRotor2 = list[2];
        rotationRotor3 = list[3];
        t = plugboard.checkConncetion(t);
        c = decoder(t);                                     //Change number to ASCII char
        return c;
    }

    private char decoder(int t) {
        t += 64;
        return Character.toChars(t)[0];
    }
    private int encoder(char c){
        int ret = (int)c >= 65 && (int)c <= 90 ? (int)c - 64: 0;
        return ret == 0 ? (int)c - 96 : ret;
    }

    /**
     *First 3 constructors are for custom start Rotor position
     * but with default {@code Rotor} wiring settings and an empty {@code Plugboard}



     *
     * @param r1setup  Start position of first rotor
     * @param r2setup  Start position of second rotor
     * @param r3setup  Start position of third rotor

     <p>
     *  Positions of ring notch / ring setting (i.e. point at which next rotor makes single spin)
     * @param ring1setup First rotor notch position
     * @param ring2setup Second rotor notch position
     * @param ring3setup Third rotor notch position
     */

    //For custom start rotation and ring setting
    Enigma(int r1setup, int r2setup, int r3setup, int ring1setup, int ring2setup, int ring3setup) {
        this.rotor = new Rotors();
        this.plugboard = new Plugboard();
        this.rotationRotor1 = r1setup != 0 ? r1setup : rotationRotor1;
        this.rotationRotor2 = r2setup != 0 ? r2setup : rotationRotor2;
        this.rotationRotor3 = r3setup != 0 ? r3setup : rotationRotor3;
        this.ring1 = ring1setup != 0 ? ring1setup : ring1;
        this.ring2 = ring2setup != 0 ? ring2setup : ring2;
        this.ring3 = ring3setup != 0 ? ring3setup : ring3;
    }

    //for custom setup configuration and default ring setting
    Enigma(int r1setup, int r2setup, int r3setup) {
        this.rotor = new Rotors();
        this.plugboard = new Plugboard();
        this.rotationRotor1 = r1setup != 0 ? r1setup : rotationRotor1;
        this.rotationRotor2 = r2setup != 0 ? r2setup : rotationRotor2;
        this.rotationRotor3 = r3setup != 0 ? r3setup : rotationRotor3;
    }

    //For all to equal 0 (default setup settings)
    Enigma() {
        this.rotor = new Rotors();
        this.plugboard = new Plugboard();
        this.rotationRotor1 = 0;
        this.rotationRotor2 = 0;
        this.rotationRotor3 = 0;
        this.ring1 = 0;
        this.ring2 = 0;
        this.ring3 = 0;
    }

    /**
     *
     * @param customRotorConfiguration {@code Rotor} Object with custom wiring
     */

    // for custom Rotors wiring, but default start positions and ring notches
    Enigma(Rotors customRotorConfiguration) {this.rotor = customRotorConfiguration;}

    //for custom Rotors and start positions, but with empty plugboard, ring notches -> default
    Enigma(Rotors customRotorConfiguration, int r1setup, int r2setup, int r3setup){
        this.rotor = customRotorConfiguration;
        this.plugboard = new Plugboard();
        this.rotationRotor1 = r1setup != 0 ? r1setup : rotationRotor1;
        this.rotationRotor2 = r2setup != 0 ? r2setup : rotationRotor2;
        this.rotationRotor3 = r3setup != 0 ? r3setup : rotationRotor3;
    }

    //for fully custom settings and positions, but with empty plugboard
    Enigma(Rotors customRotorConfiguration, int r1setup, int r2setup, int r3setup, int ring1setup, int ring2setup, int ring3setup){
        this.rotor = customRotorConfiguration;
        this.plugboard = new Plugboard();
        this.rotationRotor1 = r1setup != 0 ? r1setup : rotationRotor1;
        this.rotationRotor2 = r2setup != 0 ? r2setup : rotationRotor2;
        this.rotationRotor3 = r3setup != 0 ? r3setup : rotationRotor3;
        this.ring1 = ring1setup != 0 ? ring1setup : ring1;
        this.ring2 = ring2setup != 0 ? ring2setup : ring2;
        this.ring3 = ring3setup != 0 ? ring3setup : ring3;
        start = new int[]{r1setup, r2setup, r3setup, ring1setup, ring2setup, ring3setup};
    }

    /**
     *
     * @param customPlugboardConnections {@code Plugboard}  object with set connections
     */
   //for custom Plugboard only
    Enigma(Plugboard customPlugboardConnections){
        this.plugboard = customPlugboardConnections;
    }

    //for custom Plugboard and Rotor wiring
    Enigma(Plugboard customPlugboardConnections, Rotors customRotorConfiguration){
        this.plugboard = customPlugboardConnections;
        this.rotor = customRotorConfiguration;
    }

    //for custom Plugboard, start rotation and Rotor wiring
    Enigma(Plugboard customPlugboardConnections, Rotors customRotorConfiguration,
           int r1setup, int r2setup, int r3setup){
        this.plugboard = customPlugboardConnections;
        this.rotor = customRotorConfiguration;
        this.rotationRotor1 = r1setup != 0 ? r1setup : rotationRotor1;
        this.rotationRotor2 = r2setup != 0 ? r2setup : rotationRotor2;
        this.rotationRotor3 = r3setup != 0 ? r3setup : rotationRotor3;
    }

    //for fully custom start settings
    Enigma(Plugboard customPlugboardConnections, Rotors customRotorConfiguration,
           int r1setup, int r2setup, int r3setup,
           int ring1setup, int ring2setup, int ring3setup){
        this.plugboard = customPlugboardConnections;
        this.rotor = customRotorConfiguration;
        this.rotationRotor1 = r1setup != 0 ? r1setup : rotationRotor1;
        this.rotationRotor2 = r2setup != 0 ? r2setup : rotationRotor2;
        this.rotationRotor3 = r3setup != 0 ? r3setup : rotationRotor3;
        this.ring1 = ring1setup != 0 ? ring1setup : ring1;
        this.ring2 = ring2setup != 0 ? ring2setup : ring2;
        this.ring3 = ring3setup != 0 ? ring3setup : ring3;
    }

    //for custom plugboard and starting rotations
    Enigma(Plugboard customPlugboardConnections, int r1setup, int r2setup, int r3setup){
        this.plugboard = customPlugboardConnections;
        this.rotor = new Rotors();
        this.rotationRotor1 = r1setup != 0 ? r1setup : rotationRotor1;
        this.rotationRotor2 = r2setup != 0 ? r2setup : rotationRotor2;
        this.rotationRotor3 = r3setup != 0 ? r3setup : rotationRotor3;
    }
    //for custom Plugboard, starting rotations and ring settings
    Enigma(Plugboard customPlugboardConnections, int r1setup, int r2setup, int r3setup,
           int ring1setup, int ring2setup, int ring3setup){
        this.plugboard = customPlugboardConnections;
        this.rotor = new Rotors();
        this.rotationRotor1 = r1setup != 0 ? r1setup : rotationRotor1;
        this.rotationRotor2 = r2setup != 0 ? r2setup : rotationRotor2;
        this.rotationRotor3 = r3setup != 0 ? r3setup : rotationRotor3;
        this.ring1 = ring1setup != 0 ? ring1setup : ring1;
        this.ring2 = ring2setup != 0 ? ring2setup : ring2;
        this.ring3 = ring3setup != 0 ? ring3setup : ring3;
    }


}