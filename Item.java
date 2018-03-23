
/**
 * Crea objetos de la clase Item.
 *
 * @author (Jorge Jaular)
 * @version (14/03/2018)
 */
public class Item
{
    private String id;
    private String descripcion;
    private int peso;
    /**
     * Constructor de la clase Item
     */
    public Item(String id, String descripcion, int peso)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.peso = peso;
    }
    
    /**
     * Devuelve el item en cuestión.
     * @return id - El item.
     */
    public String getId()
    {
        return id;
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
    
    /**
     * Permite modificar el item.
     * @param id - El nuevo item.
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * Permite modificar la descripción de un item.
     * @param descripcion - La nueva descripción del item.
     */
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
    
    /**
     * Permite modificar el peso de un item.
     * @param peso - El nuevo peso del item.
     */
    public void setPeso(int peso)
    {
        this.peso = peso;
    }
}
