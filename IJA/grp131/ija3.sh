ulimit -v unlimited
javac -d build/ src/ija/homework3/Homework3.java src/ija/homework2/board/*
jar -cvfm dest-client/Homework3.jar manifest.mf  -C build ija
java -jar dest-client/Homework3.jar 


