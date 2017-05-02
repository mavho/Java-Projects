JAVASRC    = AnagramGenerator.java FindAng.java
SOURCES    = Makefile $(JAVASRC)
MAINCLASS  = AnagramGenerator
CLASSES    =  AnagramGenerator.class FindAng.class
JARFILE    = AnagramGenerator.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)	
