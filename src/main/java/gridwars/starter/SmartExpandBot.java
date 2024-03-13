package gridwars.starter;

import cern.ais.gridwars.api.Coordinates;
import cern.ais.gridwars.api.UniverseView;
import cern.ais.gridwars.api.bot.PlayerBot;
import cern.ais.gridwars.api.command.MovementCommand;

import java.util.List;

/**
 * Smart bot that expands, consolidates, and defends based on strategic considerations.
 */
public class SmartExpandBot implements PlayerBot {

    @Override
    public void getNextCommands(UniverseView universeView, List<MovementCommand> commandList) {
        List<Coordinates> myCells = universeView.getMyCells();

        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);

            // Basic threshold for splitting the population for expansion or defense
            int threshold = Math.max(10, (int) (100 / (universeView.getGrowthRate() + 1)));

            // If the population is above the threshold, consider moving
            if (currentPopulation > threshold) {
                int availableForMovement = currentPopulation - threshold;

                // Randomly decide on expansion or focusing on defense based on situation
                if (shouldExpand(cell, universeView)) {
                    // Expand to adjacent cells if they are empty or contain enemy units
                    expand(cell, availableForMovement, universeView, commandList);
                } else {
                    // Strengthen position by moving units to adjacent friendly cells if under threat
                    consolidate(cell, availableForMovement, universeView, commandList);
                }
            }
        }
    }

    private boolean shouldExpand(Coordinates cell, UniverseView universeView) {
        // Simple heuristic: expand if any adjacent cell does not belong to me
        for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
            if (!universeView.belongsToMe(cell.getNeighbour(direction))) {
                return true;
            }
        }
        return false;
    }

    private void expand(Coordinates cell, int availableForMovement, UniverseView universeView, List<MovementCommand> commandList) {
        for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
            if (!universeView.belongsToMe(cell.getNeighbour(direction)) && universeView.getPopulation(cell.getNeighbour(direction)) < availableForMovement) {
                commandList.add(new MovementCommand(cell, direction, availableForMovement / 4)); // Divide equally among directions
            }
        }
    }

    private void consolidate(Coordinates cell, int availableForMovement, UniverseView universeView, List<MovementCommand> commandList) {
        for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
            Coordinates neighbour = cell.getNeighbour(direction);
            if (universeView.belongsToMe(neighbour) && universeView.getPopulation(neighbour) < 100) {
                commandList.add(new MovementCommand(cell, direction, availableForMovement / 4)); // Support surrounding friendly cells
            }
        }
    }
}
