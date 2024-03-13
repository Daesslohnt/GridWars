package gridwars.starter;

import cern.ais.gridwars.Emulator;


/**
 * Instantiates the example bots and starts the game emulator.
 */
public class EmulatorRunner {

    public static void main(String[] args) {
        SmartCheckbot blueBot = new SmartCheckbot();
        Checkbot redBot = new Checkbot();

        Emulator.playMatch(blueBot, redBot);
    }
}
