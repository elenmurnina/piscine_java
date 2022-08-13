cd ChaseLogic
mvn install
cd ../Game
mvn package
mv target/Game-1.0-SNAPSHOT-jar-with-dependencies.jar ../Game.jar
