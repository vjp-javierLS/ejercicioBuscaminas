
import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author jlosas01
 */
public class VentanaJuego extends javax.swing.JFrame {
    Tiempo tiempo = new Tiempo();
    private static int filas;
    private static int columnas;
    private static int minas;
    private BotonCustom[] botones;
    private int minasRestantes;
    
    /**
     * Creates new form VentanaJuego
     */
    public VentanaJuego() {
        VentanaPedirDatos pedirDatos = new VentanaPedirDatos(this,true);
        pedirDatos.setVisible(true); // llamamos a la ventana de pedir los datos antes de mostrar la ventana principal
        initComponents();
        
        jPanel1.setLayout(new java.awt.GridLayout(filas,columnas)); // creamos un layout con las filas y columnas que nos pasó el usuario
        jPanel1.setMaximumSize(new Dimension(520,200));
        
        botones = new BotonCustom[(filas*columnas)]; // creamos un array de botones con todos los botones de la formula 'fil*col'
        for(int i = 0;i<botones.length;i++)
        {
            botones[i] = new BotonCustom(i); // creamos un boton custom y le pasamos el id de la posicion
            botones[i].setMaximumSize(new Dimension(40,40)); // le damos unas dimensiones concretas iguales a todos
            jPanel1.add(botones[i]);
        }
        jLabelNumMinas.setText(((Integer)minas).toString()); // mostramos las minas que hay
        minasRestantes=minas;
        generarMinas(botones); // generamos y colocamos las minas en posicione NO REPETIDAS
        tiempo.Contar(); // comenzamos a contar el tiempo

    }
    
    public void ponerImagenBandera(BotonCustom boton) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("bandera.jpg"));
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);

        if(imageIcon != null)
        {
            boton.setIcon(imageIcon);
        }
    }
    
    public void ponerImagenMina(BotonCustom boton) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("mina2.png"));
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);
        
        if(imageIcon != null)
        {
            boton.setIcon(imageIcon);
        }
    }
    
    public void finDelJuego() {
        tiempo.Detener();
        jLabelTiempo.setText(((Integer)tiempo.getSegundos()).toString()+"s");
        System.out.println("FIN DEL JUEGO");
        JOptionPane.showMessageDialog(this, "HAS PERDIDO", "FIN DEL JUEGO", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
    
    /**
     * Devuelve el boton con la id designada. Si no existe devuelve un botón sin nada
     * @param id
     * @return 
     */
    public BotonCustom devolverBotonPorId(int id) {
        if(id>0 && id< (filas*columnas)) // comprueba que la id esté dentro de los parámetros del mapa
        {
            return botones[id]; // devuelve el boton en la posicion 'id' porque al insertar en la lista los botones, la posicion coincidía con el id
        }
        else
        {
            return new BotonCustom(); // sino, devuelve un botón sin mina, osea que no suma al numero que aparece ni salta un error
        }
    }
    
    /**
     * Devuelve las casillas adyacentes al botón designado
     * @param boton
     * @return 
     */
    public BotonCustom[] comprobarCeldasAdy(BotonCustom boton) {
        int id = boton.getId(); // guarda el id de la casilla a comprobar
        BotonCustom[] listaBotAdyac = new BotonCustom[8];
        // comprobamos las casillas adyacentes en sentido horario
        listaBotAdyac[0] = devolverBotonPorId(id-columnas);
        listaBotAdyac[1] = devolverBotonPorId(id-columnas+1);
        listaBotAdyac[2] = devolverBotonPorId(id+1);
        listaBotAdyac[3] = devolverBotonPorId(id+columnas+1);
        listaBotAdyac[4] = devolverBotonPorId(id+columnas);
        listaBotAdyac[5] = devolverBotonPorId(id+columnas-1);
        listaBotAdyac[6] = devolverBotonPorId(id-1);
        listaBotAdyac[7] = devolverBotonPorId(id-columnas-1);
        
        return listaBotAdyac;
    }
    
    /**
     * Comprueba las minas de alrededor del botón y muestra el número de ellas que hay
     * @param boton 
     */
    public void comprobarMinasAdyacentes(BotonCustom boton) {
        BotonCustom[] listaBotAdyac = comprobarCeldasAdy(boton);
        int contMinas = 0;
        for(int i = 0;i<listaBotAdyac.length;i++) // recorremos la lista de botones para contar cuantas minas hay
        {
            if(listaBotAdyac[i].isEsMina())
            {
                contMinas++;
            }
        }
        boton.setText(((Integer)contMinas).toString()); // el boton muestra el numero de minas que tiene alrededor
    }
    
    /**
     * Elimina las celdas que no tengan minas adyacentes 
     * @param boton 
     */
    /*public void eliminarCeldasRecursividad(BotonCustom boton) {
        BotonCustom[] listaBotAdyac = comprobarCeldasAdy(boton);
        
        boton.setEnabled(false);
        
        for(int i = 0;i<listaBotAdyac.length;i++)
        {
            if(!comprobarMinasAdyacentes(listaBotAdyac[i]))
            {
                eliminarCeldasRecursividad(listaBotAdyac[i]);
            }
        }
    }*/ 
    
    /*public void restarMinas() {
        minasRestantes--;
        int num = Integer.parseInt(jLabelNumMinas.getText());
        if(minasRestantes==0)
        {
            tiempo.Detener();
            System.out.println("FIN DEL JUEGO!!!");
            jLabelNumMinas.setText(((Integer)(num-1)).toString());
            jLabelTiempo.setText(((Integer)tiempo.getSegundos()).toString()+"s");
        }
        else
        {
            num--;
            jLabelNumMinas.setText(((Integer)num).toString());
        }
    }*/
    
    /**
     * Suma uno al JLabel de turnos
     */
    public void sumarTurno() {
        int num = Integer.parseInt(jLabelTurno.getText());
        num++;
        jLabelTurno.setText(((Integer)num).toString());
    }
    
    
    /**
     * Genera las minas en las posiciones correspondientes
     * @param botones 
     */
    public void generarMinas(BotonCustom[] botones) {
        MouseListenerMina listenerMina;
        MouseListenerBlanco listenerBlanco;
        int[] posicionMinas = new int[minas];
        
        // generamos un array de aleatorios SIN REPETICION con las posiciones de dónde van a estar las minas
        for(int i=0;i<posicionMinas.length;i++){
            boolean encontrado = false;
            int pos=(int)(Math.random()*(filas*columnas));
            for (int j=0;j<i ;j++){
                if(posicionMinas[j]==pos){
                    encontrado=true;
                }
            }
            if(!encontrado){
                posicionMinas[i]=pos;
            }else{
                i--;
            }
        }
        
        // le asignamos al botón correspondiente si es una mina o no
        for(int k = 0;k<botones.length;k++)
        {
            boolean mina=false;
            for(int m = 0;m<posicionMinas.length;m++)
            {
                if(k==posicionMinas[m]) // comprobamos que la posicion del boton sea igual a la de la mina
                {
                    listenerMina = new MouseListenerMina(botones[k],this);
                    botones[k].addMouseListener(listenerMina); // si es una mina le pasamos el MouseListener de las minas
                    botones[k].setEsMina(true);
                    mina=true;
                }
            }
            if(!mina)
            {
                listenerBlanco = new MouseListenerBlanco(botones[k],this);
                botones[k].addMouseListener(listenerBlanco); // si no es una mina le pasamos el MouseListener de los botones en blanco
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNumMinas = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelTurno = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelTiempo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        jLabel1.setText("MINAS RESTANTES: ");

        jLabelNumMinas.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        jLabel2.setText("TURNO:");

        jLabelTurno.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        jLabelTurno.setText("0");

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N
        jLabel3.setText("TIEMPO:");

        jLabelTiempo.setFont(new java.awt.Font("DejaVu Sans", 1, 24)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelNumMinas, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelNumMinas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Recoge los datos de flias columnas y minas introducidos por el usuario en el JDialog
     * @param datos 
     */
    public static void recogerDatos(int[] datos) {
        filas = datos[0];
        columnas = datos[1];
        minas = datos[2];
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelNumMinas;
    private javax.swing.JLabel jLabelTiempo;
    private javax.swing.JLabel jLabelTurno;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
