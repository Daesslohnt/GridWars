package gridwars.starter;

import cern.ais.gridwars.api.Coordinates;
import cern.ais.gridwars.api.UniverseView;
import cern.ais.gridwars.api.bot.PlayerBot;
import cern.ais.gridwars.api.command.MovementCommand;

import java.util.ArrayList;
import java.util.List;

public class BestBotter implements PlayerBot {


    @Override
    public void getNextCommands(UniverseView universeView, List<MovementCommand> movementCommands) {
        List<Coordinates> myCells = universeView.getMyCells();

        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);

            if (currentPopulation > (4.0 / (universeView.getGrowthRate() - 1))) {
                int split = 1;

                // Check left, right, up, down for cells that don't belong to me
                for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
                    if (!universeView.belongsToMe(cell.getNeighbour(direction))) {
                        split++;
                    }
                }
                int numNeighbours = 5 - split;
                // Expand
                if (numNeighbours == 4) {
                    List<MovementCommand.Direction> outside = getOutside(cell, universeView);
                    for (MovementCommand.Direction direction : outside) {
                        movementCommands.add(new MovementCommand(cell, direction, (currentPopulation/2)/outside.size()));
                    }
                } else{
                    for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
                        if (!universeView.belongsToMe(cell.getNeighbour(direction))) {
                            movementCommands.add(new MovementCommand(cell, direction, currentPopulation / split));
                        }
                    }
                }

            }
        }


    }

    private List<MovementCommand.Direction> getOutside(Coordinates cell, UniverseView universeView) {
        List<Integer> distances = new ArrayList<>();
        List<MovementCommand.Direction> result = new ArrayList<>();
        int maxDistance = 0;
        for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
            int distance = 1;

            while (universeView.belongsToMe(cell.getRelative(distance, direction))) {
                distance++;
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
            distances.add(distance);
        }
        for (int x = 0; x < 4; x++) {
            if (distances.get(x) == maxDistance) {
                result.add(MovementCommand.Direction.values()[x]);
            }
        }

        System.out.println(result);
        return result;
    }
}
