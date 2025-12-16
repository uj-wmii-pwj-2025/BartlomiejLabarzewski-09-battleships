#!/bin/bash

function openKonsoleAndRun {
    feed="bash -c \""$1"; exec bash\""
    konsole --hold -e "$feed" &
}

# "cd ~/Dokumenty/UJ/Programming\ in\ Java/BartlomiejLabarzewski-09-battleships/target/classes && java Main -mode server -port 1230 -map test-map.txt"
openKonsoleAndRun "cd ~/Dokumenty/UJ/Programming\ in\ Java/BartlomiejLabarzewski-09-battleships/target/classes && java Main -mode server -port 1230 -map test-map.txt"
sleep 0.5
# "cd ~/Dokumenty/UJ/Programming\ in\ Java/BartlomiejLabarzewski-09-battleships/target/classes && java Main -mode client -host localhost -port 1230 -map test-map.txt"
openKonsoleAndRun "cd ~/Dokumenty/UJ/Programming\ in\ Java/BartlomiejLabarzewski-09-battleships/target/classes && java Main -mode client -host localhost -port 1230 -map test-map.txt"
