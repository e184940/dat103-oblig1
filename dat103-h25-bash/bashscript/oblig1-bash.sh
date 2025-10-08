#!/bin/bash

echo "Input the name of the file to be processed: "
read filename

if [ -d "bashscript" ]; then
	rm -r bashscript
fi
mkdir bashscript

mv "$filename" bashscript/grieg.txt

ls bashscript

echo "---------------------------------"

header=$(grep "catalogue,genre,year,title" bashscript/grieg.txt)

{
	echo "$header"
	grep -v -F "$header" bashscript/grieg.txt
} > bashscript/grieg_2.txt

mv bashscript/grieg_2.txt bashscript/grieg.txt

cat bashscript/grieg.txt

echo "---------------------------------"

tail -n +2 bashscript/grieg.txt | sort -t, -k3,3nr

echo "---------------------------------"

tail -n +2 bashscript/grieg.txt | awk -F, '$2 == "Stage"'

echo "---------------------------------"

tail -n +2 bashscript/grieg.txt | awk -F, '$3 > 1870'

echo "---------------------------------"

tail -n +2 bashscript/grieg.txt | awk -F, '$2 == "Vocal"' > bashscript/vocal.txt
cat bashscript/vocal.txt

echo "---------------------------------"

tail -n +2 bashscript/grieg.txt | awk -F, '$2 == "Piano"' > bashscript/piano.txt
cat bashscript/piano.txt

echo "---------------------------------"

for file in bashscript/*.txt; do
    n=$(wc -l < "$file")
    if [ "$n" -lt 3 ]; then
        base=$(basename "$file" .txt)
        for i in $(seq 1 "$n"); do
            cp "$file" "bashscript/${base}-${i}.txt"
        done
    fi
done

wc bashscript/*
