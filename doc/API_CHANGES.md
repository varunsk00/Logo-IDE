## Compiler API Changes
#### Maverick Chung, mc608

### Removals
* LogicAnalyzer.java was completely removed, as entire files of SLogo code could be parsed into a
command all at once, negating the need for a "program counter" of sorts.
* There is no queue of commands waiting to be parsed or executed - an entire command (or file) is 
parsed and executed and returned before the next command is entered.

### Modifications
* All of the *Handle.java classes were collapsed into Compiler.java, as I discovered upon writing the
Compiler that creating the different types of Command did not require different code.

### Additions
* Memory was expanded to include user defined commands, turtles, and display information.
* Many accessor methods were added to Compiler, such that features like the variable explorer could
access and display that data.
* Factory classes were added to create Command objects via reflection
* *MemoryState classes were added to facilitate undoing commands. Each stores the memory at a certain
point in time such that the memory can be reverted to that state.
