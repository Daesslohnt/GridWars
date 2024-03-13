package gridwars.starter;

import cern.ais.gridwars.api.Coordinates;
import cern.ais.gridwars.api.UniverseView;
import cern.ais.gridwars.api.bot.PlayerBot;
import cern.ais.gridwars.api.command.MovementCommand;

import java.util.List;

public class Checkbot implements PlayerBot {

    public void getNextCommands(UniverseView universeView, List<MovementCommand> commandList) {
        List<Coordinates> myCells = universeView.getMyCells();

        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);

            if (currentPopulation > 20) {
                int split = 4;
                // Expand
                for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
                    commandList.add(new MovementCommand(cell, direction, currentPopulation / split));
                }
            }
        }
    }
}

