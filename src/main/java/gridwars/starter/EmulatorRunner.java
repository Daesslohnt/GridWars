package gridwars.starter;

import cern.ais.gridwars.Emulator;


/**
 * Instantiates the example bots and starts the game emulator.
 */
public class EmulatorRunner {

    public static void main(String[] args) {
        hackgrid blueBot = new hackgrid(8, true);
        hackgrid redBot = new hackgrid(8, false);

        Emulator.playMatch(blueBot, redBot);
    }
}
