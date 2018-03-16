
/**
 * Crea objetos de la clase Item.
 *
 * @author (Jorge Jaular)
 * @version (14/03/2018)
 */
public class Item
{
    private String descripcion;
    private int peso;

    /**
     * Constructor de la clase Item
     */
    public Item(String descripcion, int peso)
    {
        this.descripcion = descripcion;
        this.peso = peso;
    }

    /**
     * Devuelve el peso del item en gramos.
     * @return peso - El peso del item
     */
    public int getPeso()
    {
        return peso;
    }
    
    /**
     * Devuelve una descripción del item.
     * @return descripcion - La descripción del item.
     */
    public String getDescripcion()
    {
        return descripcion;
    }
}
