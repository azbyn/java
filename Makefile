ARGS = 32968 4 1

HTML = info.htm

default: build

buildAndRun: build run

build:
	javac operatii.java
run:
	java operatii ${ARGS}  > ${HTML}
	xdg-open ${HTML}

