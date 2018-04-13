import java.util.HashMap;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    private HashMap<String, CommandWord> comandos;
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        comandos = new HashMap<>();
        comandos.put("go", CommandWord.GO);
        comandos.put("quit", CommandWord.QUIT);
        comandos.put("help", CommandWord.HELP);
        comandos.put("look", CommandWord.LOOK);
        comandos.put("eat", CommandWord.EAT);
        comandos.put("back", CommandWord.BACK);
        comandos.put("take", CommandWord.TAKE);
        comandos.put("drop", CommandWord.DROP);
        comandos.put("items", CommandWord.ITEMS);
        comandos.put("use", CommandWord.USE);
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        boolean aDevolver = false;
        for (String comando : comandos.keySet()) {
            if (aString.equals(comando)) {
                aDevolver = true;
            }
        }
        return aDevolver;
    }

    /**
     * Imprime por pantalla todos los comandos válidos
     */
    public String getCommandList()
    {
        String listaComandos = "";
        for (String comando : comandos.keySet()) {
            listaComandos += comando + " ";
        }
        return listaComandos;
    }

    /**
     * Return the CommandWord associated with a word.
     * @param commandWord The word to look up (as a string).
     * @return The CommandWord corresponding to the String commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord comando = CommandWord.UNKNOWN;
        for (int i = 0; i < comandos.size(); i++) {
            if (comandos.get(i).equals(commandWord)) {
                comandos.get(i);
            }
        }
        return comando;
    }
}
