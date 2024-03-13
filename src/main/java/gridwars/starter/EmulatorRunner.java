package gridwars.starter;

import cern.ais.gridwars.Emulator;


/**
 * Instantiates the example bots and starts the game emulator.
 */
public class EmulatorRunner {

    public static void main(String[] args) {
        AggressiveExpansionBot02 blueBot = new AggressiveExpansionBot02();
        hackgrid redBot = new hackgrid();

        Emulator.playMatch(blueBot, redBot);
    }
}
