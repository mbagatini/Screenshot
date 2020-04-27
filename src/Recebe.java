

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author morgana
 */
public class Recebe extends JFrame implements Runnable {

    public static final int PORTA = 5000;

    DatagramSocket clientsocket;

    private BufferedImage bi = new BufferedImage(Util.RESOLUCAO_X, Util.RESOLUCAO_Y, BufferedImage.TYPE_INT_ARGB);
    private byte buffer[] = new byte[Util.BLOCK_X * Util.BLOCK_Y * 4 + 4 + 4]; // pegar R, G, B, e alfa + 4 pois quero informar o posX e + 4 posY (posicão sorteada)

    public static void main(String[] args) {
        new Recebe();
    } 
    
    public Recebe() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("Visualização da tela");
        setSize(800, 600);
        
        try {
            // socket
            clientsocket = new DatagramSocket(PORTA);

            Thread t = new Thread(this);
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
        
    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                clientsocket.receive(receivePacket);
                
                System.out.println("Receiving..." + buffer.length);

                // int do posX
                byte auxPosX[] = new byte[4];
                for (int i = 0; i < 4; i++) {
                    auxPosX[i] = buffer[buffer.length - 8 + i]; // posX estava a 8 bytes atras no fim do pacote
                }
                int posX = Util.bytesToInteger(auxPosX);

                // int do posY
                byte auxPosY[] = new byte[4];
                for (int i = 0; i < 4; i++) {
                    auxPosY[i] = buffer[buffer.length - 4 + i]; // posy estava a 4 bytes atras no fim do pacote
                }
                int posY = Util.bytesToInteger(auxPosY);

                int aux = 0;
                for (int y = 0; y < Util.BLOCK_Y; y++) {
                    for (int x = 0; x < Util.BLOCK_X; x++) {

                        // int do posY
                        byte auxCor[] = new byte[4];
                        for (int i = 0; i < 4; i++) {
                            auxCor[i] = buffer[aux++]; // posy estava a 4 bytes atras no fim do pacote
                        }
                        int cor = Util.bytesToInteger(auxCor);

                        bi.setRGB(posX + x, posY + y, cor);
                        
                    }
                }

                //Thread.sleep(10);
                repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(bi, 0, 0, this);
    }
    
}
