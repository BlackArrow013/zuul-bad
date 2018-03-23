import java.util.Stack;
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
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player(createRooms());
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room salida, salaDeVisitas, salaDeEstar, celdas, aseos, cocina,salaDeGuardias, despensa;

        // create the rooms 
        salida = new Room("en la salida");
        
        salaDeVisitas = new Room("en la sala de visitas");
        
        salaDeEstar = new Room("en la sala de estar");
        salaDeEstar.addItem("Sopa de letras", 200);
        salaDeEstar.addItem("Revista HOLA", 200);
        
        celdas = new Room("en las celdas");
        celdas.addItem("Almohada", 150);
        
        aseos = new Room("en los aseos");
        aseos.addItem("Pastilla de jabón", 100);
        aseos.addItem("Escobilla", 150);
        
        cocina = new Room("en la cocina");
        cocina.addItem("Cuchillo", 150);
        cocina.addItem("Cuchara", 150);
        cocina.addItem("Sartén", 235);
        cocina.addItem("Tenedor", 150);
        cocina.addItem("Tazas", 200);
        
        salaDeGuardias = new Room("en la sala de guardias");
        
        despensa = new Room("en la despensa");
        despensa.addItem("Cabeza de cochinillo", 1500);
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
        return celdas;  // start game outside
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
        player.look();
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
            System.out.println("No sé qué quieres decir...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            player.goRoom(command);
        }
        else if (commandWord.equals("look")) {
            player.look();
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry any more");
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("back")) {
            player.backRoom(command);
        }
        return wantToQuit;
    }
    
    
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
        System.out.println(parser.showCommands());
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
}
