package gridwars.starter;

import cern.ais.gridwars.api.Coordinates;
import cern.ais.gridwars.api.UniverseView;
import cern.ais.gridwars.api.bot.PlayerBot;
import cern.ais.gridwars.api.command.MovementCommand;

import java.util.ArrayList;
import java.util.List;


/**
 * Simple bot that expands into all directions if there is a cell that does not belong to the bot
 */
public class hackgrid implements PlayerBot {

    public void getNextCommands(UniverseView universeView, List<MovementCommand> commandList) {
        List<Coordinates> myCells = universeView.getMyCells();

        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);

            if (currentPopulation > 30) {
                List<MovementCommand.Direction> outside = getOutside(cell, universeView);
                int split = outside.size() + 1;
                // Expand
                for (MovementCommand.Direction direction : outside) {
                    commandList.add(new MovementCommand(cell, direction, currentPopulation / split));
                }
                System.out.println(outside);
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
                if (!universeView.isEmpty(cell.getRelative(distance, direction)) && !universeView.belongsToMe(cell.getRelative(distance, direction))&&distance<10) {
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
