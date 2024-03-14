package gridwars.starter;

import cern.ais.gridwars.Emulator;


/**
 * Instantiates the example bots and starts the game emulator.
 */
public class EmulatorRunner {

    public static void main(String[] args) {
        hackgrid blueBot = new hackgrid(8, true);
        hackgridv2 redBot = new hackgridv2();

        Emulator.playMatch(blueBot, redBot);
    }
}
