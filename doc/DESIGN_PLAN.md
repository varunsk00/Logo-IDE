# DESIGN_PLAN
Parser Team 6
### NetIDs: qf30, mc608, aou

## Introduction
- Problem: The team intends to build a Java IDE that supports SLogo language and its commands
- Primary Goals: The flexibility of our code is a priority to its design. This stems from the plan to incorporate  multiple turtles and allow user defined commands. The program will handle a variety of logic expressions, mathematical operations, variable definitions, turtle specific commands, as well as combinations of all these. 
- Primary Architecture(open/closed): The main Turtle object is open to extension in terms of the creation of multiple turtles. It is closed to direct changes from the front end/user except through commands. We also plan to implement a command abstract object which is open to extension as new command objects can be created to perform different tasks. Also, our API design was done with the goal of minimal interactions as well as to allow for seamless API extraction and placement in another program. 
- High Level Design: There will be three main stages in the program. The first stage is the display of the program. It is in this stage that the user will be able to input commands as well as view turtle movement. The second stage is an interpretive one. Here, commands entered by the user are parsed and converted to actionable command objects that can perform change on the turtle(s). The third and final stage is an execution stage where the turtle is actually updated. 
## Overview
We intend to create 4 APIs: Compiler, Command Prompt, Turtle, and Controller. The Compiler API will handle parsing the strings into commands that will be used to run the program. The Command Prompt will read text input from the user and send it to the compiler, as well as store history and display text. The Turtle API will handle both the backend of tracking turtle location and orientation, as well as the frontend of drawing and updating the turtle's location, color, and image.

![](https://i.imgur.com/EJptkZx.png)


One way that we could implement the compiler is to have commands be trees that contain further expressions, as all expressions (including defining variables!) can be nested infinitely. This would allow for recursively executing the innermost nested commands, allowing for incredibly powerful execution. Alternatively, we could use a stack-like data structure to hold the commands, which has the advantages of allowing the compiler to know initially how large the command is, as well as still allowing iterative execution of the highest nested commands.
## User Interface
As presented in the figure below, the user interface mianly consists of four components: the header bar, the terminal, the display screen for the turtle habitat, and the display tab for viewing previous commands, variables or user-defined commands. The following paragraphs would discuss the details of each component. 

- ![UI pic](https://i.imgur.com/UgRJE5v.png)

For the header bar, we plan to divide it into four sections based on its different functionalities: configuration, view, language, and help. The configuration section would be responsible for allowing user to change the features in display, such as uploading a new image for the turtle, changing background or the pen color. In the view part, user can choose in the displayed list of buttons to select the items he or she would like to see in the display screen at the bottom with options including the previous commands, the user-defined commands and varaibles. In the header bar, the user could also change the progamming language or display help information in the terminal.

Located at the left part of the screen, terminal allows user to directly interact with the application by typing commands. If receiving erroneous input, the terminal would return exception message as user feedback. If the command is correct, the result would reflect in the display screen on the right in which the status of turtle would be updated simultaneously. At the bottom is the display bar that shows the previous commands, user-defined commands, or the variable values. As mentioned above, user could trigger its display by interacting with the view section in the header bar.

## Design Details
### Turtle API
- Turtle.java
    - set/get X-Coordinate
    - set/get Y-Coordinate
    - set/get Color
    - move Turtle
    - rotate Turtle
    - PenUp
    - PenDown
- TurtleView.java
    - Visualizes **Turtle** as JavaFX object
    - Reads information from **Turtle.java**
- TurtleHabitat.java
    - Visualizes game board (perhaps with GridPane scene)
    - JavaFX Scene with **TurtleView** object(s)
    - Visualizes pen movement
    - Updates one (or many) **TurtleView** Objects based on their states
- TurtleMap.java
    - For SLogo situations involving > 1 turtle
    - Creates a legend to identify different **Turtle** objects 
    - Visualizes that legend

### Compiler API
- SLogoCompiler.java
    - Progam Counter to fetch and analyze each string in the queue
    - Handles Language input and changes accordingly
    - Sends command to **ProgramHandle.java** if involves programming logic
    - Sends command to **TurtleHandle.java** if "basic" command from library
    - Sends command to **MathHandle.java** if command involves arithmetic
    - Sends command to **StateHandle.java** if requesting information about turtle
    - Throws exception to **ErrorHandle.java** if unrecognized
- ErrorHandle.java
    - Checks exception and returns type of exception back to **SLogoCompiler.java**
- ProgramHandle.java
    - Decomposes programming logic into sequential order of basic commands
    - Initializes turtle **Command** objects appropriately (subclasses of **Command.java**)
    - Sends declared variables to **Memory.java**
    - Passes **Command** objects to **LogicAnalyzer.java**
- TurtleHandle.java
    - Initializes turtle **Command** objects appropriately (subclasses of **Command.java**)
    - Passes **Command** objects to **LogicAnalyzer.java**
- MathHandle.java
    - Performs arithmetic operation if constants
    - Else passes Math **Command** object to **LogicAnalyzer.java**
- StateHandle.java
    - Passes Return **Command** object to **LogicAnalyzer.java** to get state of **Turtle** object
- UserHandle.java
    - Decomposes User-defined command logic into sequential order of basic commands
    - Initializes turtle **Command** objects appropriately
    - Passes **Command** objects to **LogicAnalyzer.java**
- Command.java
    - Abstract class to define a function to be performed on a Turtle
    - Subclasses define different Turtle or Arithmetic Commands (i.e. move, rotate, penup, pendown, add, sub, mul, div)
- Memory.java
    - Stores values of variables defined in **LogicAnalyzer.java**
    - Can send variables to **LogicAnalyzer.java** as requested
    - Can send variables to **VariableView.java** as requested
- LogicAnalyzer.java
    - Passes declared variables to **Memory.java**
    - Formulates queue of **Command** objects
    - Pop **Commands** off the queue to execute on **Turtle** objects in the order they are received

### Controller API
- ParserController.java
    - Organizes **ButtonControls**, **CommandPrompt**, **VariableView**, and **TurtleHabitat** objects into a single JavaFX stage/window
    - Interprets Button presses based on Boolean values and modifies variables accordingly, including passing chosen languages to **SLogoCompiler.java**
    - Constantly updates the scene based on the updating values of **ButtonControls**, **CommandPrompt**, **VariableView**, and **TurtleHabitat**
- ButtonControls.java
    - Organizes JavaFX **Button** objects and returns booleans upon pressing

### Command Prompt API
- VariableView.java
    - Visualizes variables from **Memory.java** into a JavaFX scene
    - Allows users to see the value of each variable at any given time
- CommandPrompt.java
    - Visualizes a command prompt as a JavaFX scene
    - Allows user to view command history and re-enter
    - Takes user input as String
    - Returns each String

## API as Code
- API written as Java interfaces (cannot contain instance variables or private methods, should be able to compile and include extensive comments, could be generated direcly from a UML diagram)
```
package Compiler;

import java.util.*;

/**
 * Checks commands passed in from the compiler
 * Differentiates and Interprets each command before sending to Logic Analyzer
 */
public class Command Handler {

    /**
     * Default constructor
     */
    public Command Handler() {
    }

    /**
     * 
     */
    public void turtleCommandHandler;

    /**
     * 
     */
    public void stateHandler;

    /**
     * 
     */
    public void programCommandHandler;

}
```
```
package Compiler;

import java.util.*;

/**
 * Interprets input commands and checks for errors
 */
public class Compiler {

    /**
     * Default constructor
     */
    public Compiler() {
    }

}
```
```
package Compiler;

import java.util.*;

/**
 * Analyzes incoming command and sends result to turtle, memory, and or the prompt
 */
public class LogicAnalyzer {

    /**
     * Default constructor
     */
    public LogicAnalyzer() {
    }

    /**
     * 
     */
    public void commandQueue;

}
```
```
package GUI;

import java.util.*;

/**
 * Allows the User to enter commands and functions
 */
public class CommandPrompt {

    /**
     * Default constructor
     */
    public CommandPrompt() {
    }

    /**
     * Reads line from the command prompt. 
     */
    public void readLine;

    /**
     * clears the command prompt console
     */
    public void clear;

    /**
     * returns the history of commands inputed
     */
    public void getCommandHistory;

}
```
```
package GUI;

import java.util.*;

/**
 * Serves as a interface for all GUI and visualization objects. 
 */
public class Controller {

    /**
     * Default constructor
     */
    public Controller() {
    }

    /**
     * Allows for background change
     */
    public void changeBackgroundColor;

    /**
     * displays commands possible
     */
    public void showHelp;

    /**
     * Change pen color
     */
    public void changePenColor;
}
```
```
package GUI;

import java.util.*;

/**
 * 
 */
public class DisplayHeader {

    /**
     * Default constructor
     */
    public DisplayHeader() {
    }

    /**
     * Allows user to change color of turtles 
     */
    public void colorButton;

    /**
     * Allow user to choose image for turtle
     */
    public void setImage;

    /**
     * Allow user to set language of choice
     */
    public void setLanguage;

    /**
     * Button to present options
     */
    public void helpButton;

}
```
```
package Turtle Backend;

import java.util.*;

/**
 * Stores and Handles turtle parameters
 * Moves and updates turtle location based on inputed command
 */
public class Turtle Logic {

    /**
     * Default constructor
     */
    public Turtle Logic() {
    }

    /**
     * change turtle coordinates
     */
    public void move;

    /**
     * returns turtle x and y points
     */
    public void getLocation;

    /**
     * returns turtle speed
     */
    public void getVelocity;
    /**
     * rotate turtle coordinates by certain degree
     */
    public void rotate;
}
```
```
package Turtle Visualization;

import java.util.*;

/**
 * 
 */
public class Habitat {

    /**
     * Default constructor
     */
    public Habitat() {
    }

    /**
     * 
     */
    public void setTurtleColor;

    /**
     * Creates a pane that represents the habitat in which all the turtles will be displayed
     */
    public void createHabitat;

    /**
     * Adds a turtle to the habitat
     */
    public void addTurtle;
}
```
```
package Turtle Visualization;

import java.util.*;

/**
 * 
 */
public class TurtleView {

    /**
     * Default constructor
     */
    public TurtleView() {
    }

    /**
     * Houses the coordinates of the trail of a turtle and able to display it
     */
    public void turtleTrail;

    /**
     * Sets turtleColorS
     */
    public void turtleColor;
}
```
```
import java.util.*;

/**
 * Stores all variables set by the user and their values 
 */
public class memory {

    /**
     * Default constructor
     */
    public memory() {
    }

    /**
     * A map of variables and their values
     */
    public void VariableMap;

    /**
     * Add variable and value to memory
     */
    public void addVariable;


    /**
     * gets value of variable from memory
     */
    public void getVariable;
}
```

- Exceptions
    - Invalid Command Exception (compiler)
    - Invalid Syntax Exception (logic analyzer)
    - Stack Overflow Exception (logic analyzer)
    - Infinite Loop Exception (logic analyzer)
    - Invalid User Entry
        - Invalid Color (UI)
        - Invalid Image File (UI)
        - Invalid Reource File Exception (compiler)
- Steps for a use cases below

## Design Considerations

####  The Making of TurtleHabitat
In preparation for having multiple turtles living together in our SLogo environmnent, we decided to create a TurtleHabitat class to house multiple TurtleView objects. This allows the controller to interact with the habitat alone, sending commands such as set color and image to the habitat, and having the habitat decide the outcomes. We chose to do this over designing to hold a single turtle to make our design more dynamic and expandable, as support for multiple turtles is a very reasonable request.

#### Compiler Setup
The compiler setup took us a long time to hash out. The current setup is that the **CommandPrompt** feeds raw strings into the **Compiler**. The **Compiler** turns the strings into Command objects via the various **Handle** classes. These objects are then passed to the **LogicAnalyzer**, which runs the commands and executes the logic. We decided to do this over having the compiler exectute the logic to spread out the work among different classes and to support more complex program structures such as while loops, which require repeatedly checking a variable value at runtime.

## Use Cases

#### fd 50
```
CommandQueue.add(CommandPrompt.readNextLine())
Command c = Compiler.handle(Compiler.getNextCommand()) //reads from queue
LogicAnalyzer.add(c)
Turtle.getNextCommand() //reads from logicAnalyzer
TurtleHabitat.draw() //draws the habitat and turtles inside
```

#### set pen color
```
ButtonControls.setColor(String color) 
//called from a textbox where hex color is entered
```

#### Maverick Use Case 1
User selects the language to be used from a dropdown
```
File file = ButtonControls.getResourceFile(language)
Compiler.setResourceFile(file)
```

#### Maverick Use Case 2
User inputs set :x 2
```
CommandQueue.add(CommandPrompt.readNextLine())
Command c = Compiler.handle(Compiler.getNextCommand()) //reads from queue
LogicAnalyzer.add(c) //runs and adds variable to memory
VariableView.update()
```

#### Varun Use Case 1
User creates CLOCKWISECIRCLE command composed of FORWARD, RIGHT, BACK, LEFT
```
String CLOCKWISECIRCLE = CommandPrompt.getInput()
Collection<Command> userCommand = UserHandle.decompose(CLOCKWISECIRCLE)
LogicAnalyzer.execute(userCommand)
```
#### Varun Use Case 2
User wants to change background color to green
```
User chooses #00ff00 on the color wheel
//Within the ParseController.java Class
Habitat.setTurtleColor(#00ff00) 
```

#### Qiaoyi Use Case 1
User uploads an image for the turtle 
```
//Header also checks whether file passes the sanity check
File file = Header.getResoueceImage()
Habitat.setTurtleImage(file)
```

#### Qiaoyi Use Case 2
User enters IF EQUAL? distance 5 HOME
```
CommandQueue.add(CommandPrompt.readNextLine())
Command c = Compiler.handle(Compiler.getNextCommand())
Command c = ProgramHandle.process(c)
TurtleLogic.move(c)
```
### Alexander Use Case 1
User enters SUM 1 + 2
```
CommandQueue.add(CommandPrompt.readNextLine())
Command c = Compiler.handle(Compiler.getNextCommand()) //reads from queue
result ret = MathHandle.process(c)
LogicAnalzer returns ret to prompt 
```
### Alexander Use Case 2
HIDETURTLE
```
CommandQueue.add(CommandPrompt.readNextLine())
Command c = Compiler.handle(Compiler.getNextCommand()) //reads from queue
LogicAnalyzer.add(c)
Turtle.hideTurtle()
```

## Team Responsibilities
### Alexander
- Turtle API
- Compiler API
### Maverick
- Compiler API
- Parsing Strings
- Converting Strings to Objects for parsing
### Qiaoyi
- Header Bar
- Command Prompt
- Display Screen
### Varun
- Front-end visualization 
- Turtle Visualization
- Habitat Visualization
- Command Prompt Visualization
- Stepping through visualization