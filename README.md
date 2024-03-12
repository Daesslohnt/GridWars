<pre>
         .    .        .      .             . .     .        .          .          .
  .    .          .                 .                    .                .    .
           .               A long time ago in a galaxy far, far away...   .
    *         .               .           .               .        .             .
              .      .            .                 .                         +      .
      .   .      .         .         .   . :::::+::::...      .          .         .
   .          .         .      .    ..::.:::+++++:::+++++:+::.    .     .
                                 .:.  ..:+:..+|||+..::|+|+||++|:.             .     .
                     .   .    :::....:::::::::++||||O||O#OO|OOO|+|:.    .
     .   .      .      .    .:..:..::+||OO#|#|OOO+|O||####OO###O+:+|+               .
                          .:...:+||O####O##||+|OO|||O#####O#O||OO|++||:     .    .
.          .             ..::||+++|+++++|+::|+++++O#O|OO|||+++..:OOOOO|+  .         .
   .   .      .   .     +++||++:.:++:..+#|. ::::++|+++||++O##O+:.++|||#O+    .
         .           . ++++++++...:+:+:.:+: ::..+|OO++O|########|++++||##+            .
.          .       .  :::+++|O+||+::++++:::+:::+++::+|+O###########OO|:+OO       .  .
              .       +:+++|OO+|||O:+:::::.. .||O#OOO||O||#@###@######:+|O|  .
          .          ::+:++|+|O+|||++|++|:::+O#######O######O@############O
     +             . ++++: .+OO###O++++++|OO++|O#@@@####@##################+         .
               .     ::::::::::::::::::::++|O+..+#|O@@@@#@###O|O#O##@#OO####     .
          .        . :. .:.:. .:.:.: +.::::::::  . +#:#@:#@@@#O||O#O@:###:#| .      .
                                    `. .:.:.:.:. . :.:.:%::%%%:::::%::::%:::
         .      .                                      `.:.:.:.:   :.:.:.:.  .   .
                    .                                                                .
  .            .
         .          .                                                       .   .
 .                                                                                    .
             .        .                                                           .
             .     .                                                           .    *  .
    .      .     .                                                        .
                       .   A terrible civil war burns throughout the  .        .     .
                          galaxy: a rag-tag group of freedom fighters   .  .
              .       .  has risen from beneath the dark shadow of the            .
         .        .     evil monster the Galactic Empire has become.                  .
  .        .             Imperial  forces  have  instituted  a reign of   .      .
        *             terror,  and every  weapon in its arsenal has  been
   .               . turned upon the Rebels  and  their  allies:  tyranny, .   .
            .       oppression, vast fleets, overwhelming armies, and fear.        .  .
      .  .      .  Fear  keeps  the  individual systems in line,  and is the   .
                  prime motivator of the New Order.             .
   .        .      Outnumbered and outgunned,  the Rebellion burns across the   .    .
         .      vast reaches of space and a thousand-thousand worlds, with only     .
             . their great courage - and the mystical power known as the Force -
   .         flaming a fire of hope.        .          .    .    .            .      .
               .               ..       .       .   .             .           +          .
                     .              .       .              +     .      .    .          .
.        .               .       .     .            .        .                 .          .
   .           .        .                     .        .            .          .     .      .
             .               .    .          .              .   .         .
    _______ .______       __   _______     ____    __    ____  ___      .______      +   _______.
   /  _____||   _  \   . |  | |       \    \   \ ./  \  /   / /   \     |   _  \        /       |
  |  |  __  |  |_)  |    |  | |  .--.  |  . \   \/    \/   / /  ^  \  . |  |_)  |      |   (----`
  |  | |_ | |      / .   |  | |  |  |  |     \            / /  /_\  \   |      /   .    \   \
  |  |__| | |  |\  \----.|  | |  '--'  |      \    /\    / /  _____  \  |  |\  \----.----)   | .
   \______| | _| `._____||__| |_______/   .    \__/  \__/ /__/     \__\ | _| `._____|_______/    .
     .             .             .         .               .                 .
.        .               .       .     .            .    .       *        .        .        .

                                         Episode VI

                            T H E   G R I D   A D V E N T U R E
</pre>

## GridWars Starter Project
---
This starter project provides everything to get you going with the development of your own bot for the GridWars coding competition.

Please refer to the [getting started page](https://gridwars.cern.ch/docs/getting-started) available online for a comprehensive guide. You can also refer to the PDF slides shipped in this starter project that explains the game mechanics and some rules.

### Project Content

| Content        | Description |
|----------------|-------------|
| `build.gradle` | Gradle build config file. You need to configure the class name of your bot here. |
| `/src/`        | Source code to run the emulator and two simple example bots. |
| `/slides/`     | Slides of the GridWars intro session. |
| `/docs/`       | JavaDocs of the GridWars API classes. Open the `index.html` in a browser. |
| `/lib/`        | GridWars API and the emulator Java libs (no need to touch). |
| `/build/`      | Automatically generated, used for build output. |
| `gradlew`      | Gradle wrapper executable for Linux and Mac (no need to touch). |
| `gradlew.bat`  | Gradle wrapper executable for Windows (no need to touch). |
| `/gradle/`     | Gradle wrapper files (no need to touch). |

### Run the emulator

To run the GridWars emulator, execute the `run` Gradle task in the IDE. The output of the bots will be logged to the files `bot1.log` and `bot2.log` in the project root folder.

Modify the `gridwars.starter.EmulatorRunner` class to define what bot classes are used to have your own bot fight with another bot. This can either be one of the provided example bots to get started, or other versions of your own bot to compare them and see, which version is the strongest.

To run the emulator you can execute the Gradle `run` command from the IDE or from the console:

**Linux/Mac:** `./gradlew run`

**Windows:** `gradlew.bat run`

### Create the uploadable bot jar file

Once you have created your own bot, you first need to fill in the fully qualified class name of your bot in the `build.gradle` file to be used for the `Bot-Class-Name` manifest header. For example:

```
manifest {
    attributes( 
        'Bot-Class-Name': 'gridwars.starter.MyAwesomeBot'
    )
}
```

Then execute the `jar` Gradle task in the IDE or from the console:

**Linux/Mac:** `./gradlew jar`

**Windows:** `gradlew.bat jar`

The resulting bot jar file will be located at `<project root>/build/libs/gridwars-bot.jar`.

Finally, visit your [team page](https://gridwars.cern.ch/team) and upload the bot and see how it competes. Good luck and have fun!

Play fair... and may the code be with you ;)

<pre>
                                    _.=+._
 \::::::::::::::::::::::::::.\`--._/[_/~|;\_.--'/.:::::::::\
  \::::::::::::::::::::::::::.`.  ` __`\.-.(  .'.:::::::::::\
  /::::::::::::::::::::::::::::.`-:.`'..`-'/\'.:::::::::::::/
 /::::::::::::::::::::::::::::::::.\ `--')/  ) ::::::::::::/
                                     `--'
</pre>
