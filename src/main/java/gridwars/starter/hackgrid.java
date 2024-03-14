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
    private double popsplit;

    public hackgrid() {
        this.popsplit = 8;
    }

    public hackgrid(int popsplit) {
        this.popsplit = popsplit;
    }

    public void getNextCommands(UniverseView universeView, List<MovementCommand> commandList) {
        List<Coordinates> myCells = universeView.getMyCells();
        if (this.popsplit < 60) {
            this.popsplit = this.popsplit + 0.1;
        }


        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);
            if (currentPopulation > popsplit) {
                List<MovementCommand.Direction> outside = getOutside(cell, universeView);
                int split = outside.size();
                // Expand
                int popToMove = 0;
                for (MovementCommand.Direction direction : outside) {
                    if ((currentPopulation - 5) / split>0) {
                        commandList.add(new MovementCommand(cell, direction, Math.max(0, (currentPopulation - 5) / split)));
                        popToMove += (currentPopulation - 5) / split;
                    }
                }
                if (popToMove > currentPopulation) {
                    universeView.log("Tried Moving more pop than available");
                }
            } else if (currentPopulation > (popsplit / 2)+1) {
                List<MovementCommand.Direction> outside2 = getOutside(cell, universeView);
                int split2 = outside2.size();
                // Expand
                int popToMove2 = 0;
                for (MovementCommand.Direction direction : outside2) {
                    if (direction == MovementCommand.Direction.LEFT || direction == MovementCommand.Direction.RIGHT) {
                        split2--;
                        continue;
                    }
                    if ((currentPopulation - 5) / split2>0){
                        commandList.add(new MovementCommand(cell, direction, Math.max(0, (currentPopulation - 5) / split2)));
                        popToMove2 += (currentPopulation - 5) / split2;
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
                if (!universeView.isEmpty(cell.getRelative(distance, direction)) && !universeView.belongsToMe(cell.getRelative(distance, direction)) && distance < 25) {
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
