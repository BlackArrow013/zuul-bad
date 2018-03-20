import java.util.HashMap;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> salidas;
    private ArrayList<Item> items;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas = new HashMap<String, Room>();
        items = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor)
    {
        salidas.put(direction, neighbor);
    }

    public Room getExit(String direccion)
    {
        return salidas.get(direccion);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString()
    {
        String salidaDisponible = "";
        for (String salida : salidas.keySet()) {
            salidaDisponible += salida + " ";
        }
        return salidaDisponible.trim();
    }
    
    /**
     * Permite añadir objetos para el juego.
     */
    public void addItem(String descripcion, int peso)
    {
        Item item = new Item(descripcion, peso);
        items.add(item);
    }
    
    /**
     * Devuelve la descripción de los objetos de la sala.
     * @return 
     */
    public String getItemDescription()
    {
        String objetoDisponible = "";
        for (Item item : items) {
            objetoDisponible += item.getDescripcion() + ", ";
        }
        if (objetoDisponible.length() > 1) {
            objetoDisponible = objetoDisponible.substring(0, objetoDisponible.length() - 2);
        }
        else {
            objetoDisponible = "No hay objetos en esta habitación.";
        }
        return objetoDisponible;
    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        return "Estás en " + description + ".\nSalidas: " + getExitString() + ".\nObjetos: " + getItemDescription();
    }
    
    public Item itemToTake(String item)
    {
        Item itemRecogido = null;
        Item itemABorrar = null;
        for (Item itemActual : items) {
            if (itemActual.getDescripcion().equals(item)) {
                itemRecogido = itemActual;
            }
        }
        items.remove(itemRecogido);
        return itemRecogido;
    }
    
    public void itemToDrop(Item item)
    {
        items.add(item);        
    }
}
