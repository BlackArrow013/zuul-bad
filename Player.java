import java.util.Stack;
import java.util.ArrayList;
/**
 * La clase Player representa a un jugador dentro de nuestro videojuego.
 *
 * @author (Jorge Jaular Lasaga)
 * @version (23/03/2018)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private Stack<Room> lastRooms;
    private ArrayList<Item> mochila;
    /**
     * Constructor de Player. Crea un jugador.
     */
    public Player(Room room)
    {
        // Inicializaci�n de las variables.
        lastRooms = new Stack<>();
        mochila = new ArrayList<>();
        currentRoom = room;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("�Ir a d�nde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            lastRooms.push(currentRoom);
            currentRoom = nextRoom;
            look();
            System.out.println();
        }
    }

    /**
     * Imprime por pantalla informaci�n de la habitaci�n en la que el jugador
     * se encuentra.
     */
    public void look() 
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * El jugador puede volver por las distintas habitaciones por las que ha 
     * pasado. Si ya no hay habitaci�n a la que volver informa por pantalla.
     */
    public void backRoom(Command command)
    {
        if (command.hasSecondWord()) {
            System.out.println("�Qu� me est�s contando? Solo una palabra, por favor.");
        }
        else {
            if (lastRooms.isEmpty()) {
                System.out.println("No se puede retroceder. �Mu�vete!");
            }
            else {
                currentRoom = lastRooms.peek();
                look();
                System.out.println();
                lastRooms.pop();
            }
        }
    }

    /**
     * Este m�todo permite al jugador recoger objetos de la sala en la que se encuentre.
     */
    public void take(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("�Coger qu�?");        
        }
        else {        
            String item = command.getSecondWord();
            Item itemToTake = currentRoom.itemToTake(item);
            if (itemToTake == null) {
                System.out.println("Ese objeto no existe en esta habitaci�n.");
            }
            else {
                if (itemToTake.getCogerObjeto()) {
                    mochila.add(itemToTake);
                    System.out.println("Has recogido " + itemToTake.getDescripcion());
                }
                else {
                    System.out.println("Este objeto no se puede recoger.");
                }
            }            
        }
    }

    /**
     * Este m�todo muestre por pantalla la informaci�n de los items que hay en la mochila del jugador. Si no tiene items en la mochila 
     * tambi�n informa de ello.
     */
    public void items()
    {
        if (!mochila.isEmpty()) {
            for (Item itemActual : mochila) {
                System.out.println(itemActual.getInformationItem());
            }
        }
        else {
            System.out.println("Tienes la mochila vac�a.");
        }
    }
}

