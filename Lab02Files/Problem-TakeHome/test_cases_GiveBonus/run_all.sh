#!/bin/bash
# Run all test cases against GiveBonus.jar
# Usage: bash run_all.sh /path/to/GiveBonus.jar

JAR="${1:-GiveBonus.jar}"

for infile in TC*_IN.TXT; do
    tc="${infile%%_IN.TXT}"
    cp "$infile" IN.TXT
    java -jar "$JAR" 2>/dev/null
    if [ -f OUT.TXT ]; then
        cp OUT.TXT "${tc}_actual.txt"
        echo "=== $tc ==="
        cat OUT.TXT
        echo "---"
    else
        echo "=== $tc === NO OUTPUT FILE"
    fi
done
