#!/bin/bash

function openKonsoleAndRun {
    konsole --hold -e sh -c "$1" &
}

# "cd ~/Dokumenty/UJ/Programming\ in\ Java/BartlomiejLabarzewski-09-battleships/target/classes && java Main -mode server -port 1230 -map test-map.txt"
openKonsoleAndRun "/opt/intellij-idea-ultimate-edition/plugins/maven/lib/maven3/bin/mvn -X exec:java -Dexec.mainClass=\"Main\" -Dexec.args=\"-mode server -port 1230 -map quick-map.txt\""
sleep 0.5
# "cd ~/Dokumenty/UJ/Programming\ in\ Java/BartlomiejLabarzewski-09-battleships/target/classes && java Main -mode client -host localhost -port 1230 -map test-map.txt"
openKonsoleAndRun "/opt/intellij-idea-ultimate-edition/plugins/maven/lib/maven3/bin/mvn -X exec:java -Dexec.mainClass=\"Main\" -Dexec.args=\"-mode client -host localhost -port 1230 -map quick-map.txt\""