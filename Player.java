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
    /**
     * Constructor de Player. Crea un jugador.
     */
    public Player(Room room)
    {
        // Inicialización de las variables.
        lastRooms = new Stack<>();
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
            lastRooms.push(currentRoom);
            currentRoom = nextRoom;
            look();
            System.out.println();
        }
    }

    /**
     * Imprime por pantalla información de la habitación en la que el jugador
     * se encuentra.
     */
    public void look() 
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * El jugador puede volver por las distintas habitaciones por las que ha 
     * pasado. Si ya no hay habitación a la que volver informa por pantalla.
     */
    public void backRoom(Command command)
    {
        if (command.hasSecondWord()) {
            System.out.println("¿Qué me estás contando? Solo una palabra, por favor.");
        }
        else {
            if (lastRooms.isEmpty()) {
                System.out.println("No se puede retroceder. ¡Muévete!");
            }
            else {
                currentRoom = lastRooms.peek();
                look();
                System.out.println();
                lastRooms.pop();
            }
        }
    }
}
