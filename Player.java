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
    private int cargaActual;
    private static final int CARGA_MAXIMA = 500;
    /**
     * Constructor de Player. Crea un jugador.
     */
    public Player(Room room)
    {
        // Inicializaci�n de las variables.
        lastRooms = new Stack<>();
        mochila = new ArrayList<>();
        currentRoom = room;
        cargaActual = 0;
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
     * @param command - El objeto a ejecutar que se identifica con el objeto.
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
                    if (cargaActual + itemToTake.getPeso() > CARGA_MAXIMA) {
                        System.out.println("No puedes moverte cogiendo tanto peso.");
                        mochila.add(itemToTake);
                        drop(command);
                        cargaActual += itemToTake.getPeso();
                    }
                    else {
                        mochila.add(itemToTake);
                        cargaActual += itemToTake.getPeso();
                        System.out.println("Has recogido " + itemToTake.getDescripcion() + " con un peso de " + itemToTake.getPeso() + " gramos");
                        System.out.println();
                    }
                }
                else {
                    System.out.println("Este objeto no se puede recoger.");
                }
            }            
        }
    }

    /**
     * Permite al jugador soltar objetos de su mochila en la sala que se encuentre.
     * @param command - El comando a ejecutar, que se identificar� con el objeto.
     */
    public void drop(Command command)
    {
        if (!command.hasSecondWord()) {
            System.out.println("�Soltar qu�?");
        }
        else {
            String item = command.getSecondWord();
            Item itemToDrop = null;
            for (Item itemASoltar : mochila) {
                if (itemASoltar.getId().equals(item)) {
                    itemToDrop = itemASoltar;                    
                }
            }
            mochila.remove(itemToDrop);
            if (itemToDrop == null) {
                System.out.println("�Si no tienes ese objeto!");
            }
            else {
                currentRoom.itemToDrop(itemToDrop);
                cargaActual -= itemToDrop.getPeso();
                System.out.println("Has soltado " + itemToDrop.getDescripcion() + ", con un peso de " + itemToDrop.getPeso());
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
    
    /**
     * Este m�todo permite usar distintos objetos que tengas en tu poder. Actualmente solo funciona con un solo objeto, pero podr�an 
     * implementarse distintas funcionalidades para otros objetos en el futuro.
     */
    public boolean use(Command command)
    {
        boolean terminado = false;
        if (!command.hasSecondWord()) {
            System.out.println("�Usar el qu�?");
        }
        else {
            if (currentRoom.getDescription().contains("salida")) {
                System.out.println("�Fant�stico! �Has conseguido salir de la c�rcel!");
                System.out.println("Ahora por fin se har� justicia.");
                terminado = true;
            }
            else {
                System.out.println("Esta habitaci�n no es la salida.");
            }
        }
        return terminado;
    }
}

