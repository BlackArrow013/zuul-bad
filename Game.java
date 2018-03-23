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
        salaDeEstar.addItem("Pasatiempo", "una entretenida sopa de letras", 200, false);
        salaDeEstar.addItem("Revista", "la revista HOLA para el cotilleo", 200, false);
        
        celdas = new Room("en las celdas");
        celdas.addItem("Almohada", "una almohada aterciopelada digna de un buen preso", 150, true);
        
        aseos = new Room("en los aseos");
        aseos.addItem("Jabón", "una pastilla de jabón", 100, true);
        aseos.addItem("Escobilla", "una escobilla del retrete para limpiar lo que la cisterna no puede", 150, false);
        
        cocina = new Room("en la cocina");
        cocina.addItem("Cuchillo", "un cuchillo para cortar cualquier tipo de sustancia blandita", 150, true);
        cocina.addItem("Cuchara", "una cuchara para comer la sopa de una manera fina y elegante", 150, true);
        cocina.addItem("Sartén", "una sartén para freir unos buenos huevos fritos", 235, true);
        cocina.addItem("Tenedor", "un tenedor para pinchar la comida (o algunos ojos)", 150, true);
        cocina.addItem("Tazas", "una taza para beber el té", 200, false);
        
        salaDeGuardias = new Room("en la sala de guardias");
        
        despensa = new Room("en la despensa");
        despensa.addItem("Cerdo","una enorme cabeza de cochinillo, exquisita", 1500, false);
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
        System.out.println("Gracias por jugar. Adiós.");
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
        else if (commandWord.equals("take")) {
            player.take(command);
        }
        else if (commandWord.equals("items")) {
            player.items();
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
        System.out.println("Estás en la cárcel. Te han encerrado injustamente y no estás dispuesto a pudrirte allí dentro.");
        System.out.println();
        System.out.println("Tus comandos son:");
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
