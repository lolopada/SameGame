### VARIABLES ###

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none

JVM = java
JVMFLAGS = 

### REGLES ESSENTIELLES ###

main.class : main.java Menu.class
	$(JC) $(JCFLAGS) main.java

GameFrame.class : GameFrame.java GameLogic.class GameListener.class GameCase.class ExportButtonListener.class PopUpFrame.class
	$(JC) $(JCFLAGS) GameFrame.java

GameLogic.class : GameLogic.java GameCase.class
	$(JC) $(JCFLAGS) GameLogic.java

GameCase.class : GameCase.java
	$(JC) $(JCFLAGS) GameCase.java

# Dépendance circulaire GameListener.class <- GameFrame.class abandonnée.
GameListener.class : GameListener.java #GameFrame.class
	$(JC) $(JCFLAGS) GameListener.java

# Dépendance circulaire ExportButtonListener.class <- GameFrame.class abandonnée.
ExportButtonListener.class : ExportButtonListener.java #GameFrame.class
	$(JC) $(JCFLAGS) ExportButtonListener.java

Menu.class : Menu.java MenuButtonListener.class GameFrame.class
	$(JC) $(JCFLAGS) Menu.java

# Dépendance circulaire MenuButtonListener.class <- Menu.class abandonnée.
MenuButtonListener.class : MenuButtonListener.java #Menu.class
	$(JC) $(JCFLAGS) MenuButtonListener.java

PopUpFrame.class : PopUpFrame.java PopUpListener.class
	$(JC) $(JCFLAGS) PopUpFrame.java

# Dépendance circulaire PopUpListener.class <- PopUpFrame.class abandonnée.
PopUpListener.class : PopUpListener.java  #PopUpFrame.class
	$(JC) $(JCFLAGS) PopUpListener.java

### REGLES OPTIONNELLES ###

run : main.class
	$(JVM) $(JVMFLAGS) main

clean :
	-rm -f *.class

mrproper : clean main.class

### BUTS FACTICES ###

.PHONY : run clean mrproper

### FIN ###