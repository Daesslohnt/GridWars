package gridwars.starter;

import cern.ais.gridwars.api.Coordinates;
import cern.ais.gridwars.api.UniverseView;
import cern.ais.gridwars.api.bot.PlayerBot;
import cern.ais.gridwars.api.command.MovementCommand;

import java.util.List;

/**
 * Adjusted SmartExpandBot for faster expansion especially at the game start and increased support when encountering enemies.
 */
public class hackgrid implements PlayerBot {

    @Override
    public void getNextCommands(UniverseView universeView, List<MovementCommand> commandList) {
        List<Coordinates> myCells = universeView.getMyCells();

        universeView.log("Number of my cells: " + myCells.size());

        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);

            // Adjust the threshold for deciding on moving units for expansion or defense
            int threshold = Math.max(10, (int) (100 / (universeView.getGrowthRate() + 1)));

            if (currentPopulation > threshold) {
                int availableForMovement = currentPopulation - threshold;
                int moveAmount = availableForMovement / 4; // Start with a base division for fairness

                if (shouldExpand(cell, universeView)) {
                    // Check for empty adjacent cells to prioritize faster expansion
                    long emptyAdjacentCells = countEmptyAdjacentCells(cell, universeView);
                    if (emptyAdjacentCells > 0) {
                        // If there are empty cells, use a larger portion of the population to expand into them
                        moveAmount = availableForMovement / (int) Math.max(1, emptyAdjacentCells); // Adjust based on number of empty cells
                        if (moveAmount > 100){
                            moveAmount = 100;
                        }
                    }
                    expand(cell, moveAmount, universeView, commandList);
                } else {
                    // When not expanding, consolidate by supporting adjacent friendly cells
                    consolidate(cell, moveAmount, universeView, commandList);
                }
            }
        }
    }

    private boolean shouldExpand(Coordinates cell, UniverseView universeView) {
        for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
            if (!universeView.belongsToMe(cell.getNeighbour(direction))) {
                return true;
            }
        }
        return false;
    }

    private long countEmptyAdjacentCells(Coordinates cell, UniverseView universeView) {
        return java.util.Arrays.stream(MovementCommand.Direction.values())
                .map(cell::getNeighbour)
                .filter(universeView::isEmpty)
                .count();
    }

    private void expand(Coordinates cell, int moveAmount, UniverseView universeView, List<MovementCommand> commandList) {
        for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
            if (!universeView.belongsToMe(cell.getNeighbour(direction))) {
                commandList.add(new MovementCommand(cell, direction, moveAmount));
            }
        }
    }

    private void consolidate(Coordinates cell, int moveAmount, UniverseView universeView, List<MovementCommand> commandList) {
        for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
            Coordinates neighbour = cell.getNeighbour(direction);
            if (universeView.belongsToMe(neighbour) && universeView.getPopulation(neighbour) < 100) {
                commandList.add(new MovementCommand(cell, direction, moveAmount));
            }
        }
    }
}
