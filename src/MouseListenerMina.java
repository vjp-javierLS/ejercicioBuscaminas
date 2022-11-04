
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jlosas01
 */
public class MouseListenerMina implements MouseListener{
    private BotonCustom boton;
    private VentanaJuego padre;
    
    public MouseListenerMina(BotonCustom boton, VentanaJuego padre) {
        this.boton = boton;
        this.padre = padre;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getButton()==3)
        {
            padre.ponerImagenBandera(boton);
        }
        else
        {
            padre.sumarTurno();
            padre.ponerImagenMina(boton);
            padre.finDelJuego();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}