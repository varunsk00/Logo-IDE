## SIMULATION_API

### netids: mc608, vsk10, aou, qf30

Choose someone's previous Cell Society project and examine it from the perspective of an API by categorizing each public or protected method in the Simulation "module" from following perspectives:

Not in the API: Data structure that holds a Cell, data Structure that contains the Grid
Internal API: populate Neighbors, each of the different neighbor configurations (hex, triangle, four, eight) and the logic behind them, different Cell Type
External API: choose what type of grid (square, hex, triangular), simulation GUI functions (speed, frame skipping, loading xml)

Your written explanation should include both English and Java descriptions of the task:
English: how do you want programmers to think of the process of using the API to accomplish this task using metaphors or analogies (e.g., folder paradigm), or standard programming ideas (e.g., step by step or substitution via polymorphism) beyond simply restating the procedural steps

Using this API 
English: The task of creating and running a simulation should easy and not too involved. The API will allow the programmer to use the external API to display the simulation and the internal API to setup and do the main computation involved in the simulation. Part of the API are hierarchies/inheritances that allow easy and error free ways to define neighbors and different cells.  

Java: list the actual Java methods you expect to use from the API to accomplish this task with a comment about how they would be used (e.g., call, implement, or change)
* Simulation.loadXML(File file)
* Visualization vis = Simulation.createGrid()
* vis.update(Simulation.getStates())



