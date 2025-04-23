# SameGame

A Java implementation of the classic tile-matching puzzle game SameGame, where you strategically remove groups of colored gems to clear the grid.

## Game Description

SameGame is a puzzle and strategy game based on simple principles:

- The game board consists of a 15×10 grid filled with three distinct colors of gems
- Players can remove groups of same-colored gems
- Two gems are considered adjacent if they share a side (top, bottom, left, right)
- A group is a set of 2 or more gems connected through adjacency
- The goal is to clear as much of the grid as possible by strategically removing gem groups

## Game Mechanics

- When your mouse hovers over a group of gems, they are visually highlighted
- Clicking on a group removes it from the grid
- Gems above will fall down to fill the empty spaces
- If a column becomes empty, all columns to the right shift left
- The game ends when no more valid groups exist

## Requirements

- Java Development Kit (JDK) 8 or higher 
- Any operating system that supports Java (Windows, macOS, Linux)

## Installation

### Installing Java

1. **Check if Java is installed:**
```bash
java -version
```

2. **If Java is not installed:**

   - **Windows:**
     1. Download the JDK from [Oracle's website](https://www.oracle.com/java/technologies/downloads/)
     2. Run the installer and follow the instructions
     3. Add Java to your PATH environment variable if not done automatically

   - **macOS:**
```bash
brew install openjdk@11
```
   Or download from [Oracle's website](https://www.oracle.com/java/technologies/downloads/)

   - **Linux (Debian/Ubuntu):**
```bash
sudo apt update
sudo apt install default-jdk
```

   - **Linux (Fedora/RHEL):**
```bash
sudo dnf install java-11-openjdk-devel
```

3. **Verify installation:**
```bash
java -version
javac -version
```

## Building and Running the Game

### Using the Makefile

The project includes a Makefile to simplify compilation and execution:

1. **Clone or download the repository:**
```bash
git clone [repository-url]
cd [repository-directory]
```

2. **Compile the game:**
```bash
make
```

3. **Run the game:**
```bash
make run
```

4. **Clean compiled files:**
```bash
make clean
```

### Manual Compilation

If you don't want to use the Makefile, you can compile and run manually:

1. **Compile all Java files:**
```bash
javac -encoding UTF-8 *.java
```

2. **Run the game:**
```bash
java main
```

## How to Play

1. **Launch the game** using one of the run methods above
2. **Select a game mode** in the menu:
   - **Random Map:** Generates a random distribution of gems
   - **Search Game Set:** Load a pattern from a file
3. **If using pattern mode:**
   - Click "Select File" to choose a pattern file
   - Valid pattern files should:
     - Have 10 rows and 15 columns
     - Use 'R' for ruby (red), 'V' for emerald (green), and 'B' for diamond (blue)
4. **Click "Start Game"** to begin playing
5. **Remove gems** by clicking on groups
6. **Game ends** when no more moves are possible

## Creating Custom Patterns

You can create your own game patterns in text files with the following format:
- 10 lines of text
- Each line contains exactly 15 characters 
- Use 'R' for Ruby (red), 'V' for Emerald (green), and 'B' for Diamond (blue)

Example:
```text
RRVVVVRBVVVRRRR
VVVVRVRBVRBBVBB
RRRBVVBVVVRVVRB
VVVVRBBBBRVBRVB
VVRBBVBVRRVBBBV
VBBBVRVBRBRBRRB
VBBRRBBVVBVBVBV
RVBBRBRVRRVBBBB
VVRVBBBBRRVVRRV
BBRRVVVVBRVBBVV
```

## Authors

- Loic Sainton
- Mathis Pauron

## Version

Version 1.1

