Compilation:
Next command compiles .java files into .class files, put them into target directory
javac -d ./target -sourcepath ./src/java ./src/java/edu/school21/printer/app/Program.java

Program launch:
Usage: java -cp ./target edu.school21.printer.app.Program <full path to the image.bmp> <character for white pixels> <character for black pixels>

Example:
java -cp ./target edu.school21.printer.app.Program $PWD/../../../it.bmp . 0