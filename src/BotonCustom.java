
import javax.swing.JButton;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jlosas01
 */
public class BotonCustom extends JButton{
    
    // ATRIBUTOS
    private int id;
    private boolean esMina;
    
    // CONSTRUCTORES
    public BotonCustom() {
        super();
        this.id = 0;
        this.esMina = false;
    }
    
    public BotonCustom(int id) {
        super();
        this.id = id;
        this.esMina = false;
    }

    // GETTERS Y SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEsMina() {
        return esMina;
    }

    public void setEsMina(boolean esMina) {
        this.esMina = esMina;
    }
    
    
}
