## COLLECTIONS_API

### netids: mc608, vsk10, aou, qf30

1. In your experience using these collections, are they hard or easy to use?

    Java's Collections API has intuitive method names (such as .get, s) and provides an easy way to interact with Collection objects without knowing about their core functionality. This is a good example of a well-designed API that hides complicated code from the user.

2. In your experience using these collections, do you feel mistakes are easy to avoid?

    In large part, mistakes are easy to avoid because the code won't compile for many types of mistakes (e.g. inserting different types of Objects into a set). Additionally, if the code runs and crashes due to a collection-based error, the error messages are clear to use to debug. However, due to only being usable with objects, using Collections with primitive wrapers is unintuitive and can lead to mistakes.
    
3. How many interfaces do specific concrete collection classes implement (such as LinkedList)? What do you think is the purpose of each interface?

    Most concrete collection classes implement one interface from Set, List, Deque, or Map. For example, a linkedList implements List and Deque, and a HashMap implements a map. 
 
4. How many different implementations are there for a specific collection class (such as Set)? Do you think the number justifies it being an interface or not?

    There are 8 different implementations for Set, which absolutely justifies the need for the interface, as each of the 8 classes share much of the same functionality, even with vastly different implementations.

5. How many levels of superclasses do specific concrete collection classes have? What do you think is the purpose of each inheritance level?

    A linkedList has 4 superclasses, an ArrayList has 3 superclasses, and a HashMap has 2. The purpose of each inheritance level is to give the collection class an abstract functionality. This makes it easier to extend the classes to implement new functionality.

 
6. Why does it make sense to have the utility classes instead of adding that functionality to the collection types themselves? Are there any overlapping methods (ones that are in both a specific collection and a utility class)? If so, is there any guidance on which one you should use?

    The different utility classes have vastly different implementations of the methods. When methods overlap, the utility class method should be used.