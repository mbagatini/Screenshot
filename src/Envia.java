
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author morgana
 */
public class Envia {

    private static final int PORTA = 5000;

    public static void main(String[] args) {
        new Envia().enviaScreenshot();
    }

    public void enviaScreenshot() {
        while (true) {
            try {
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress IpServidor = InetAddress.getByName("127.0.0.1");

                Robot r = new Robot();

                BufferedImage img = r.createScreenCapture(new Rectangle(Util.RESOLUCAO_X, Util.RESOLUCAO_Y)); // capturei a tela toda

                byte buffer[] = new byte[Util.BLOCK_X * Util.BLOCK_Y * 4 + 4 + 4];

                // divide a tela em quadros
                for (int posX = 0; posX < (Util.RESOLUCAO_X - Util.BLOCK_X); posX = posX + Util.BLOCK_X) {
                    for (int posY = 0; posY < (Util.RESOLUCAO_Y - Util.BLOCK_Y); posY = posY + Util.BLOCK_Y) {

                        // arqui poderia ser lancado uma Thread
                        int aux = 0;
                        for (int y = 0; y < Util.BLOCK_Y; y++) {
                            for (int x = 0; x < Util.BLOCK_X; x++) {

                                int cor = img.getRGB(posX + x, posY + y); //ARGB

                                byte auxBuffer[] = Util.integerToBytes(cor);

                                for (int j = 0; j < auxBuffer.length; j++) {
                                    buffer[aux++] = auxBuffer[j];
                                }
                            }
                        }

                        // bytes do posX
                        byte auxBufferPosX[] = Util.integerToBytes(posX);
                        for (int j = 0; j < auxBufferPosX.length; j++) {
                            buffer[aux++] = auxBufferPosX[j];
                        }

                        // bytes do posY
                        byte auxBufferPosY[] = Util.integerToBytes(posY);
                        for (int j = 0; j < auxBufferPosY.length; j++) {
                            buffer[aux++] = auxBufferPosY[j];
                        }

                        DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, IpServidor, PORTA);
                        clientSocket.send(sendPacket);

                        System.out.println("posX = " + posX + " posY = " + posY + " Enviou " + buffer.length);
                    }
                }

                Thread.sleep(10);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 
}
