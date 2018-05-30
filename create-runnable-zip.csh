#!/bin/csh -f

rm -rf motrack-app motrack-app.zip

mvn install
mvn clean compile assembly:single
mkdir motrack-app
cp target/motrack-1.0-SNAPSHOT-jar-with-dependencies.jar motrack-app
chmod +x motrack-app/motrack-1.0-SNAPSHOT-jar-with-dependencies.jar
mv motrack-app/motrack-1.0-SNAPSHOT-jar-with-dependencies.jar motrack-app/motrack.jar
cp -r data motrack-app
zip -r motrack-app.zip motrack-app
rm -rf motrack-app

echo "unzip motrack-app.zip ; cd motrack-app ; java -jar motrack.jar"
