## SLOGO_QUESTIONS

### netids: mc608, vsk10, aou, qf30

1. When does parsing need to take place and what does it need to start properly?

    Parsing needs to be separate from Turtle object/moving. There needs to be a way to convert the user input to a predefined set of instructions that can move the turtle object. The parser needs valid syntax that has been defined for the program in order to start.

2. What is the result of parsing and who receives it?

    Parsing should take the user input and put it into a Command Class with different "method" subclasses that can interact with the turtle appropriately.

3. When are errors detected and how are they reported?

    Command syntax not predefined, FLASHING RED LIGHTS/Popup window
    ALL SYNTAX ERRORS THAT HAVE NOT BEEN DEFINED
    Invalid args

4. What do commands know, when do they know it, and how do they get it?

    Commands only should their execution and arguments, no other variables should be accessible. Commands get information from Command Prompot passed arguments

5. How is the GUI updated after a command has completed execution?

    The GUI is keeping track of the turtle state, and updates the history of locations accordingly. This information is not avilable to the turtle. The Gui doesnt execute all the logic and then update, but rather interpret

    Visualization must have a step-queue of commands

    Turtles should have a command-queue of method calls