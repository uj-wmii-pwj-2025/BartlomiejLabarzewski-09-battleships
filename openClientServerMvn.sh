#!/bin/bash

function openKonsoleAndRun {
    konsole -e sh -c "$1" &
}

export mvn="/opt/intellij-idea-ultimate-edition/plugins/maven/lib/maven3/bin/mvn"

# "cd ~/Dokumenty/UJ/Programming\ in\ Java/BartlomiejLabarzewski-09-battleships/target/classes && java Main -mode server -port 1230 -map test-map.txt"
openKonsoleAndRun "mvn exec:java -Dexec.mainClass=\"Main\" -mode server -port 1230 -map test-map.txt"
sleep 0.5
# "cd ~/Dokumenty/UJ/Programming\ in\ Java/BartlomiejLabarzewski-09-battleships/target/classes && java Main -mode client -host localhost -port 1230 -map test-map.txt"
openKonsoleAndRun "mvn exec:java -Dexec.mainClass=\"Main\" -mode client -host localhost -port 1230 -map test-map.txt"