/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RelogiOpc2;

import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author Grupo | Gomes, Shazia, Ivo
 */
public class RelogiOpc2 {

    /**
     * @param args the command line arguments
     */
    public static final int DISPLAY_WIDTH = 600; /*Tamanho da tela a apresentar*/
    public static final int DISPLAY_HEIGHT = 600; /*Tamanho da tela a apresentar*/

    public static double HoursAjust = 0, MinutAjust = 0;
    public static float PonteiroHoras = 3, PonteiroMinutos = 5, PonteiroSegundos = 8; /*Tamanho dos Ponteiros*/

    public static void main(String[] args) throws LWJGLException {
        // TODO code application logic here
        Layout();
        run();
        sairPrograma();
    }

    public static void Layout() throws LWJGLException {
        /*Criacao da Tela de Apresentacao*/
        Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        Display.setTitle("Relogio");
        Display.setIcon(null);
        Display.create();
        glViewport(0, 2, DISPLAY_WIDTH, DISPLAY_HEIGHT);
        GLU.gluOrtho2D(0, DISPLAY_WIDTH, 0, DISPLAY_WIDTH);

        /*Cor do Fundo*/
        glClearColor(255f, 255f, 255f, 255f); 
//        glClearColor(0.75f, 0.75f, 0.75f, 15f);
        glClear(GL_COLOR);
    }

    public static void sairPrograma() {
        Display.destroy();
    }

    public static void run() {
        while (!Display.isCloseRequested()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(RelogiOpc2.class.getName()).log(Level.SEVERE, null, ex);
            }

            DesenhoRelogio();
            
            /*H - Metodo para aumetar o Tamanho do ponteir.. das Horas*/
            if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
                PonteiroHoras += 15;
            }
            
            /*M - Metodo para aumetar o Tamanho do ponteir.. dos Minutos*/
            if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
                PonteiroMinutos += 15;
            }
            
            /*S - Metodo para aumetar o Tamanho do ponteir.. dss Segundos*/
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                PonteiroSegundos += 15;
            }
            
            //Metodo para sair do sistema com a tecla - SHIFT
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))){
                sairPrograma();
                System.exit(0);
            }
            Display.update();
            Display.sync(60);
        }
    }

    public static void DesenhoRelogio() {
        //Posicao do Relogio no Output
        double x, y;
        double r = 0.8 * Integer.min(DISPLAY_WIDTH, DISPLAY_HEIGHT) / 2; /*Tamanho do Circulo | r = raio*/
        double posX = DISPLAY_WIDTH / 2;
        double posY = DISPLAY_HEIGHT / 2;
        glClear(GL_COLOR_BUFFER_BIT);

        // Circulo do Relogio
//        int segments = Integer.min(DISPLAY_WIDTH, DISPLAY_HEIGHT) / 2; /*Cria o Circulo | Contorno com os pontos*/
        int segments = Integer.min(DISPLAY_WIDTH, DISPLAY_HEIGHT) / 500;
        glLineWidth(2);

        /*Circunferencia*/
        glBegin(GL_LINE_LOOP);
        glColor3d(0.0, 0.0, 0.0);
        for (int i = 0; i <= segments; i++) {
            x = posX + r * Math.cos(2 * Math.PI * i / segments);
            y = posY + r * Math.sin(2 * Math.PI * i / segments);
            glVertex2d(x, y);
        }
        glEnd();      

}
