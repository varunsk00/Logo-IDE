# Refactoring Discussion
#### Netids: mc608, vsk10, qf30, aou

### Refactoring Compiler to Avoid Special Casing

There were two main issues with the parser - the first was that some commands required access to a name, such as the MakeVariable command. Previously, this was handled with an `instanceof` and a cast, which is a horrible way to do things. This was fixed by adding a name instance variable to the Command superclass, allowing the cast to be removed.

The other issue was that many `instanceof` commands were used to check the syntax of commands such as loops, which require certain arguments to be lists. This was fixed by adding a type instance variable and a `.typeEquals()` method to the Command superclass. While this didn't effectively change much, the fact that the typeEquals method exists, and is being compared against a String means that the code is significantly more extensible, as the .typeEquals() method can be overridden or written in a way to allow for more than a simple instanceof.

Additionally, there were a few methods in the compiler package that did not need to be public, and their privacy settings were appropriately adjusted.

### Refactoring Buttons and Magic Numbers

One of the main problems with the GUI is the placement of different panes and buttons on different areas of the screen. This leads to a lot of Magic numbers floating around the code and it was sometimes difficult to understand what each number did as I looked over the code. Majority of these magic numbers have now been removed especially from the TurtleHabitat and should be completely removed from the Parser controller soon. 

Another change that was made was the movement of the "view Turtles" button from the habitat to the main GUI of the project. This also allowed for the usage of language property files in the creation of the button thereby making it more flexible.

The above change cascaded into making the "viewTurtleInformation()" method public. 

### Refactoring ParserController Class

The ParserController Class is waaaay too big, so a change was made to move functionality into a new class called Workspace.java, consisting of the TerminalView (and respective instantiation), TurtleHabitat, and TerminalController. This made it much easier to make multiple workspaces using a JavaFX TabPane with multiple Workspace objects. This also made it easy to check the current workspace and overall contributed to increased readabaility of the code.


### Recfactoring HistoryBuffer Class

Adjust and rearrange the functionalities to enhance the readability of the class structure. 