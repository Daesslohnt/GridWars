package gridwars.starter;

import cern.ais.gridwars.api.Coordinates;
import cern.ais.gridwars.api.UniverseView;
import cern.ais.gridwars.api.bot.PlayerBot;
import cern.ais.gridwars.api.command.MovementCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class hackgridv2 implements PlayerBot {
    private static final double POP_SPLIT = 12;
    private static final boolean HALF = true;
    private static final int MAX_EXPANSION_DISTANCE = 25;

    public void getNextCommands(UniverseView universeView, List<MovementCommand> commandList) {
        List<Coordinates> myCells = universeView.getMyCells();

        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);

            if (currentPopulation > POP_SPLIT) {
                List<MovementCommand.Direction> outside = getOutside(cell, universeView);
                int split = outside.size();
                // Expand
                for (MovementCommand.Direction direction : outside) {
                    if (HALF) {
                        commandList.add(new MovementCommand(cell, direction, (currentPopulation - 5) / split));
                    } else {
                        commandList.add(new MovementCommand(cell, direction, currentPopulation / (split + 1)));
                    }
                }
            }
        }
    }

    private List<MovementCommand.Direction> getOutside(Coordinates cell, UniverseView universeView) {
        List<Integer> distances = new ArrayList<>();
        List<MovementCommand.Direction> result = new ArrayList<>();
        List<MovementCommand.Direction> enemy = new ArrayList<>();
        int minDistance = 10000;

        for (MovementCommand.Direction direction : MovementCommand.Direction.values()) {
            int distance = 1;
            while (universeView.belongsToMe(cell.getRelative(distance, direction)) && distance < universeView.getUniverseSize()) {
                distance++;
                if (!universeView.isEmpty(cell.getRelative(distance, direction)) && !universeView.belongsToMe(cell.getRelative(distance, direction)) && distance < MAX_EXPANSION_DISTANCE) {
                    enemy.add(direction);
                }
            }
            if (distance < minDistance) {
                minDistance = distance;
            }
            distances.add(distance);
        }

        for (int x = 0; x < 4; x++) {
            if (distances.get(x) == minDistance) {
                result.add(MovementCommand.Direction.values()[x]);
            }
        }

        result.addAll(enemy);
        return result;
    }
}