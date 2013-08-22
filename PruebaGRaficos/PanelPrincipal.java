/*
 * Javier Abell�n. 13 de febrero de 2004
 *
 * Principal.java
 *
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * Panel con un Lienzo y un bot�n.
 * Pulsando el bot�n se cambia la l�nea a dibujar en el lienzo.
 */
public class PanelPrincipal extends JPanel
 {
     /**
      * Constructor por defecto. Pone el lienzo y el bot�n en el panel y
      * hace que se dibuje la primera l�nea.
      */
     public PanelPrincipal()
     {
    	 this.setSize(100, 100);
         ponComponentes();
         pintaLinea();
     }
     
     /**
      * Pone el lienzo y el bot�n en el panel.
      */
     private void ponComponentes()
     {
         this.add (lienzo);
         this.add (boton);

         /* Accion que se ejecuta al pulsar el bot�n, se hace que se pinte la
          * l�nea */
         boton.addActionListener (new ActionListener () {
             public void actionPerformed (ActionEvent e)
             {
                 pintaLinea();
             }
         });
     }
     
     /**
      * Dibuja una l�nea en el lienzo. Cada vez que se llama a este m�todo,
      * la l�nea se cambia para que se dibuje en distinto sitio dentro del
      * lienzo.
      */
     private void pintaLinea()
     {
         /* Se hace que la x cambie de 0 a 100 o de 100 a 0 en cada llamada. */
         if (x < 50.0) x = 100.0;
         else x = 0.0;
         
         /* Se crea una linea diagonal que puede ser / o \ */
         Line2D linea = new Double (x, 0.0, 100.0-x, 100.0);
         
         /* Se pasa la l�nea al lienzo y se hace que se redibuje */
         lienzo.tomaLinea (linea);
         lienzo.repaint();
     }
     

     /** Coordenada x del punto de origen de la l�nea que se dibuja en el
      * lienzo. Valdr� 0.0 o 100.0 */
     private double x = 0.0;
     
     /** El lienzo de dibujo */
     private Lienzo lienzo = new Lienzo();
     
     /** El bot�n que hace cambiar la l�nea dibujada en el lienzo */
     private JButton boton = new JButton ("Cambia dibujo");
}