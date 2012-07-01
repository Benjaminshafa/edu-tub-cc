SRC:=$(wildcard src/cc/*.java)

all:$(SRC)
	javac -classpath /usr/local/hadoop/hadoop-core-1.0.3.jar -d classes $(SRC)
	jar -cvf orders.jar -C classes/ .
