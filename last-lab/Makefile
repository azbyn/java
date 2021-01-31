buildAndRun: build run

lambda = personal_projects/lambda/Lambda
main = exemple/Exemple

build:
	javac ${main}.java

buildAndRunLambda: buildLambda runLambda

buildLambda:
	javac ${lambda}.java
runLambda:
	java ${lambda}


run:
	java ${main}

clean:
	rm  -f $(shell find . -name *.class)
