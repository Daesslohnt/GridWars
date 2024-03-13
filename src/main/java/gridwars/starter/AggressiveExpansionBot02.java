package gridwars.starter;

import cern.ais.gridwars.api.Coordinates;
import cern.ais.gridwars.api.UniverseView;
import cern.ais.gridwars.api.bot.PlayerBot;
import cern.ais.gridwars.api.command.MovementCommand;

import java.util.List;

public class AggressiveExpansionBot02 implements PlayerBot {

    private boolean initialWaitDone = false;

    public void getNextCommands(UniverseView universeView, List<MovementCommand> commandList) {
        List<Coordinates> myCells = universeView.getMyCells();
        int totalPopulation = myCells.stream().mapToInt(universeView::getPopulation).sum();
        int averagePopulation = totalPopulation / myCells.size();

        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);

            // Warten, bis eine Zelle 60 Einheiten erreicht
            if (!initialWaitDone) {
                if (currentPopulation >= 60) {
                    initialWaitDone = true;
                }
                continue; // Überspringen des aktuellen Durchlaufs und Fortfahren mit der nächsten Zelle
            }

            // Bestimmen der Mindestpopulation, die in einer Zelle verbleiben soll
            int minimumPopulationToLeave = 10;
            if (averagePopulation > 20 && currentPopulation > minimumPopulationToLeave) {
                // Wenn die durchschnittliche Population > 30, aggressiver expandieren
                int excessPopulation = currentPopulation - minimumPopulationToLeave;
                int unitsToMovePerDirection = excessPopulation / 2; // Teilen durch 2 für zwei Richtungen

                // Bewegung nach rechts und unten
                for (MovementCommand.Direction direction : new MovementCommand.Direction[]{MovementCommand.Direction.RIGHT, MovementCommand.Direction.DOWN}) {
                    if (unitsToMovePerDirection > 0) {
                        commandList.add(new MovementCommand(cell, direction, unitsToMovePerDirection));
                    }
                }
            }
        }
    }
}
