package gridwars.starter;

import cern.ais.gridwars.api.Coordinates;
import cern.ais.gridwars.api.UniverseView;
import cern.ais.gridwars.api.bot.PlayerBot;
import cern.ais.gridwars.api.command.MovementCommand;

import java.util.List;

public class AggressiveExpansionBot implements PlayerBot {

    @Override
    public void getNextCommands(UniverseView universeView, List<MovementCommand> commandList) {
        List<Coordinates> myCells = universeView.getMyCells();

        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);

            // Berechne die Anzahl der freien Nachbarzellen
            int freeNeighbours = 0;
            for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
                Coordinates neighbour = cell.getNeighbour(direction);
                if (!universeView.belongsToMe(neighbour) || (universeView.belongsToMe(neighbour) && universeView.getPopulation(neighbour) < 100)) {
                    freeNeighbours++;
                }
            }

            // Berechne, wie viele Einheiten insgesamt bewegt werden können, wobei mindestens 5 Einheiten zurückbleiben müssen
            int unitsToMove = Math.min((currentPopulation - 5) / freeNeighbours, 5);
            if (unitsToMove > 0) {
                // Bewege Einheiten in jede freie Nachbarzelle
                for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
                    Coordinates neighbour = cell.getNeighbour(direction);
                    if (!universeView.belongsToMe(neighbour) || (universeView.belongsToMe(neighbour) && universeView.getPopulation(neighbour) < 100)) {
                        commandList.add(new MovementCommand(cell, direction, unitsToMove));
                    }
                }
            }
        }
    }
}
