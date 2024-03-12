package gridwars.starter;

import cern.ais.gridwars.Emulator;


/**
 * Instantiates the example bots and starts the game emulator.
 */
public class EmulatorRunner {

    public static void main(String[] args) {
        MovingBot blueBot = new MovingBot();
        ExpandBot redBot = new ExpandBot();

        Emulator.playMatch(blueBot, redBot);
    }
}
