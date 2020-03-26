parser
====

This project implements a development environment that helps users write SLogo programs.

Names: Maverick Chung (mc608), Qiaoyi Fang (qf30), Alexander Uzochukwu (aou), Varun Kosgi (vsk10)


### Timeline

Start Date: February 13th

Finish Date: March 6th

#### Hours Spent:
Planning:
* Maverick: 5
* Qiaoyi: 5
* Alex: 5
* Varun: 5

Basic:
* Maverick: 14
* Qiaoyi: 23
* Alex: 10
* Varun: 25

Complete:
* Maverick: 25
* Qiaoyi: 28
* Alex: 20
* Varun: 20

Total:
* Maverick: 44
* Qiaoyi: 46
* Alex: 35
* Varun: 50

### Primary Roles
Maverick:
* Wrote the Compiler and everything in the compiler package
    * Command.java
        * Abstract class that represents a command to be executed
    * Memory.java
        * Stores all variables, user commands, and turtles
* Designed all of the command parsing, memory storage, and turtle backend storage. 
* Implemented all backend tasks.

Qiaoyi:
- Wrote the terminal 
    - history buffer: allow user to jump to previous commands
    - color to special words: allow user to identify words with functionalities (numbers, commands, etc)
    - a welcome banner message to enhance the accessibility of the application
- Also wrote the TabPane class the realizes the variable explorer
    - utilized factory pattern to automatically generate tables from the content in properties file
    - created the interaction between it and terminal to allow the user directly make input from the tab
    - allows to automatically update with the compiler
- Also wrote the prefprocessor
    - allow workspace to directly load from the preset properties file
    - allow user to save the current workspace

Alex:
* Created the Turtle.java backend and logic, allowing for storage of turtle states
* Designed variable pen size
* Wrote turtle active status toggle on click


Varun:
* Created controller package
    * ParserController.java
         * Application Class that organizes multiple Slogo Workspaces/Tabs, Header, and Footer into a BorderPane and executes animation loop
         * Handles program-wide language switching
     * GUIButtons.java
         * Class that creates all Buttons and Combo Boxes that involve GUI controls, including loading .logo files, loading image files, getting Help, and setting GUI preferences like background color, pen color, language, etc.
         * Holds states representative of the Buttons being pressed or not pressed (Boolean for Buttons and String for Combo Box)
     * Sliders.java
         * Creates Sliders for zooming and resizing the TurtleViews on screen
     * Header.java
         * Organizes GUIButtons and Sliders into a JavaFX VBox
     * DirectionalButtons.java
         * Class to create the four directional Buttons to move the Turtle sans TerminalView
     * Footer.java
         * Organizes DirectionalButtons and Variable Explorer into a JavaFX VBox
     * FileSelect.java
         * Handles File Choosing for both .logo and Image files. Creates appropriate pop-up window and changes with language
     * ColorSelect.java
         * Handles a JavaFX Colorpicker and associated window to allow for color customization of the pen and background color

* Created turtle package
     * TurtleView.java
          * Extends JavaFX Rectangle to visually represent a Turtle on screen
          * Is constantly pinging the values of the underlying Turtle in order to update all states accordingly
     * TurtleHabitat.java
          * Extends JavaFX Pane to provide a region upon which the TurtleViews and Pen can be drawn and constantly updated
* Workspace package
     * Workspace.java
          * Extends BorderPane to organize a TerminalView object and TurtleHabitat object into a single Workspace that can then be instantiated for multiple tabs/Workspaces in ParserController
     * ColorFactory.java
          * Creates a Map from Slogo Compiler colors (int) to actual JavaFX Color objects that can then be used to update the visual state of the Workspace

### Resources Used
The 3rd party [Reflections Library](https://github.com/ronmamo/reflections) was used to register each Command class.
Additionally, the regex code was taken from the [Spike Lab](https://coursework.cs.duke.edu/compsci308_2020spring/spike_parser).


### Running the Program

Main class: ParserController

Data files needed: The files in the resources package are used for implementing multiple languages, style,
SLogo syntax, Turtle ASCII art, and turtle images.

Features implemented:
* Text commands can be entered into the terminal window, and the turtle will react accordingly
* The user can save values in variables and define user commands
* Logo errors (e.g syntax errors) are displayed in the terminal window
* The user can see command history, current stored variables, and current defined commands in the window
* The user can switch the language, changing the understood commands, as well as the button labels and other GUI members
* The user can access the most recent command by pressing the up arrow a la a normal terminal window
* The user can undo commands run by pressing Ctrl+Z
* The user can set the pen color of the turtle and the background color of the workspace
* Multiple workspaces are available, with shared user commands, but different variable and turtle memories
* Variable values are editable from the variable explorer
* View help about different commands
* The command window can be dragged around to see the turtles as they move offscreen
* Logo files can be loaded and run
* Code executed can be saved to a logo file to be loaded and run
* The user can input commands with infinite parameters via grouping, as well as recursive commands
* Preferences can be saved to a properties file
* The user can move the turtle graphically with buttons in the GUI
* The pen size of the turtles can be changed

### Notes/Assumptions

#### Assumptions or Simplifications:
* Due to turtle command arguments being evaluated for each active turtle, if there are n active turtles, the
kth nested command will be executed n^k times.
* Pressing Ctrl+Z to undo requires focus in the terminal input panel
* New workspaces cannot be created
* Error messages can be translated to different languages, but require the properties files to do so. As of now,
the only extant properties files are for Mandarin, and the text contained is the normal text with ` - Chinese Version` 
appended, just as a proof of extensibility. If the appropriate resource file is not found, the compiler defaults
to displaying English error messages.

#### Interesting data files:
* To demonstrate recursion and scope, 3 logo files are provided. The first is the bog-standard recursive factorial.
The second is again standard Fibonacci sequence. This is included because it has an exponential runtime which is 
easily demonstrated for values of n larger than ~30, and because it proves that scope is functional - if it were not,
there would be no way to pass a valid value into the second recursive call. And finally, there is a logo file that
calculates the value of e. This doesn't demonstrate too much more, except for adding in a command to calculate the
value of e, a command notably missing from the original set.

#### Known Bugs:
* Window dragging is erratic
* The background does not color past the starting shown window
* The `to` command cannot be grouped
* Attempting to undo redefining a command may have unexpected behavior due to stack logic
* Placing an extra close list character `]` within a command will cause execution of a command to terminate
without reading further if the already parsed commands represent a syntactically valid command
* The group end character `)` is an alias for 0 if not used to close a group
* Defining a user command that relies on as-yet-undefined user commands will pass parsing but will almost
certainly have unexpected results

#### Extra credit:
N/a, unfortunately


### Impressions

This project was very interesting. It was fascinating literally write a programming language, and see the
struggles and design decisions that can go into such an undertaking. I (Maverick) very much enjoyed 
the quirks of nested control structures and how they fell out of a few basic assumptions, and I'm very glad
to have spent my time programming this parser.