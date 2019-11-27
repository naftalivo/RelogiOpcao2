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

            //Acertar hora
//            if (Keyboard.isKeyDown(Keyboard.KEY_UP) && !Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
//                HoursAjust += 0.1;
//            }
//            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !Keyboard.isKeyDown(Keyboard.KEY_UP)) {
//                MinutAjust += 1;
//            }
            
            /*H - Metodo para aumetar o Tamanho do ponteir.. das Horas*/
            if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
                PonteiroHoras += 25;
            }
            
            /*M - Metodo para aumetar o Tamanho do ponteir.. dos Minutos*/
            if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
                PonteiroMinutos += 25;
            }
            
            /*S - Metodo para aumetar o Tamanho do ponteir.. dss Segundos*/
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                PonteiroSegundos += 25;
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

        // 60 - Pontos dos Segundos e Minutos no Relogio
        glColor3d(0.0, 0.0, 0.0);
        for (int i = 0; i < 60; i++) {
            glBegin(GL_POINTS);
            x = posX + r * Math.cos(2 * Math.PI * i / 60); /*Pontos ao redor do circulo*/
            y = posY + r * Math.sin(2 * Math.PI * i / 60);
            
            glVertex2d(x, y);
            x = posX + 0.040 * r * Math.cos(2 * Math.PI * i / 60); /*Pontos dentro do circulo*/
            y = posY + 0.045 * r * Math.sin(2 * Math.PI * i / 60);
            glVertex2d(x, y);
            glEnd();
        }

        // 12 Pontos das Horas no Relogio
        glColor3d(0.0, 0.0, 0.0);
        for (int i = 0; i < 12; i++) {
            glBegin(GL_LINE_LOOP);
            x = posX + r * Math.cos(2 * Math.PI * i / 12);
            y = posY + r * Math.sin(2 * Math.PI * i / 12);
            glVertex2d(x, y);
            x = posX + 0.9 * r * Math.cos(2 * Math.PI * i / 12);
            y = posY + 0.9 * r * Math.sin(2 * Math.PI * i / 12);
            glVertex2d(x, y);
            glEnd();
        }
        
        /*Pegando as Horas do sistema*/
        final LocalTime time = LocalTime.now();
        double hora = time.getHour() + time.getMinute() / 60.0 + time.getSecond() / 3600.0 + HoursAjust;
        double minuto = time.getMinute() + time.getSecond() / 60.0 + MinutAjust;
        double segundo = time.getSecond();

        // Ponteiro das Horas
        x = posX + 0.5 * r * Math.cos(5 * Math.PI / 2 - 2 * Math.PI * hora / 12);
        y = posY + 0.5 * r * Math.sin(5 * Math.PI / 2 - 2 * Math.PI * hora / 12);
        glLineWidth(PonteiroHoras);
        glBegin(GL_LINE_LOOP);
        glColor3d(1.0f, 0.5f, 0.0f);
        glVertex2d(posX, posY);
        glVertex2d(x, y);
        glEnd();
        
        // Ponteiro dos Minutos
        x = posX + 0.85 * r * Math.cos(5 * Math.PI / 2 - 2 * Math.PI * minuto / 60);
        y = posY + 0.85 * r * Math.sin(5 * Math.PI / 2 - 2 * Math.PI * minuto / 60);
        glLineWidth(PonteiroMinutos);
        glBegin(GL_LINE_LOOP);
        glColor3d(0.1, 0.75, 0.5);
        glVertex2d(posX, posY);
        glVertex2d(x, y);
        glEnd();
        
        // Ponteiro dos Segundos
        x = posX + 0.9 * r * Math.cos(5 * Math.PI / 2 - 2 * Math.PI * segundo / 60);
        y = posY + 0.9 * r * Math.sin(5 * Math.PI / 2 - 2 * Math.PI * segundo / 60);
        glLineWidth(PonteiroSegundos);
        glBegin(GL_LINE_LOOP);
        glColor3d(1.0, 0.9, 0.0);
        glVertex2d(posX, posY);
        glVertex2d(x, y);
        glEnd();
    }

}
