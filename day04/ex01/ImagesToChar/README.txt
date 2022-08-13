Compilation:
Next command compiles .java files into .class files, put them into target directory
javac -d ./target -sourcepath ./src/java ./src/java/edu/school21/printer/app/Program.java

cp -R src/resources target/.

Jar file creation:
Next command packs .class files, resources and a manifest file into a single images-to-chars-printer.jar file
jar cmvf ./src/manifest.txt ./target/images-to-chars-printer.jar -C ./target . 

chmod u+x ./target/images-to-chars-printer.jar

Jar launch:
Usage: java -jar target/images-to-chars-printer.jar <character for white pixels> <character for black pixels>

Example:
java -jar target/images-to-chars-printer.jar . o