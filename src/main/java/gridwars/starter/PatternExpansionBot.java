package gridwars.starter;

import cern.ais.gridwars.api.Coordinates;
import cern.ais.gridwars.api.UniverseView;
import cern.ais.gridwars.api.bot.PlayerBot;
import cern.ais.gridwars.api.command.MovementCommand;

import java.util.List;

public class PatternExpansionBot implements PlayerBot {

    public void getNextCommands(UniverseView universeView, List<MovementCommand> commandList) {
        List<Coordinates> myCells = universeView.getMyCells();

        for (Coordinates cell : myCells) {
            int currentPopulation = universeView.getPopulation(cell);

            // Wenn die aktuelle Population größer als 1 ist, expandieren
            if (currentPopulation > 1) {
                // Berechnen, wie viele Einheiten in jede Richtung bewegt werden sollen
                // Hinweis: Wir passen die Logik an, um zu vermeiden, dass wir mehr Einheiten senden, als verfügbar.
                int unitsToMove = (currentPopulation - 1) / 4; // Teilen für zwei Richtungen

                // Bewegung nach rechts und unten, jeweils mit der berechneten Anzahl von Einheiten
                for (MovementCommand.Direction direction : new MovementCommand.Direction[]{
                        MovementCommand.Direction.RIGHT,
                        MovementCommand.Direction.DOWN,
                        MovementCommand.Direction.UP,
                        MovementCommand.Direction.LEFT}) {
                    if (unitsToMove > 0) {
                        commandList.add(new MovementCommand(cell, direction, unitsToMove));
                    }
                }
            }
        }
    }
}
