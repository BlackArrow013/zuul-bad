/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room salida, salaDeVisitas, salaDeEstar, celdas, aseos, cocina,salaDeGuardias, despensa;

        // create the rooms
        salida = new Room("en la salida");
        salaDeVisitas = new Room("en la sala de visitas");
        salaDeEstar = new Room("en la sala de estar");
        celdas = new Room("en las celdas");
        aseos = new Room("en los aseos");
        cocina = new Room("en la cocina");
        salaDeGuardias = new Room("en la sala de guardias");
        despensa = new Room("en la despensa");
        // initialise room exits
        salida.setExit("south", salaDeVisitas);

        salaDeVisitas.setExit("north", salida);
        salaDeVisitas.setExit("east", salaDeEstar);

        salaDeEstar.setExit("west", salaDeVisitas);
        salaDeEstar.setExit("east", cocina);
        salaDeEstar.setExit("south", celdas);

        celdas.setExit("north", salaDeEstar);
        celdas.setExit("east", aseos);

        aseos.setExit("west", celdas);

        cocina.setExit("west", salaDeEstar);
        cocina.setExit("northwest", despensa);
        cocina.setExit("southeast", salaDeGuardias);

        despensa.setExit("southeast", cocina);
        currentRoom = celdas;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);

        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido a ¡Escapa mientras puedas!");
        System.out.println("¡Escapa mientras puedas! es un juego de rol donde tú eres un preso y debes escapar de la cárcel.");
        System.out.println("Muévete entre las distintas habitaciones hasta encontrar la salida.");        
        System.out.println("Escribe 'help' si necesitas ayuda.");
        System.out.println();
        printLocationInfo();
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry any more");
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {        
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("¿Ir a dónde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
            System.out.println();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Método privado que sirve para reutilizar código en otros métodos. En 
     * este caso da información sobre la localización.
     */
    private void printLocationInfo()
    {
        System.out.println(currentRoom.getLongDescription());

    }

    private void look() 
    {
        System.out.println(currentRoom.getLongDescription());
    }
}
