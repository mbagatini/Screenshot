

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

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

    JPanel panel;
    JLabel label;

    public Recebe() {
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
        try {
            while (true) {
                repaint();
                Thread.sleep(1000 / 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {

        DatagramPacket pacote = meu();
        BufferedImage img = retornaImg(pacote); 

        if (img != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(img, 10, 30, img.getWidth(), img.getHeight(), this);
        }

    }

    public DatagramPacket meu() {
        try {
            byte[] receivedata = new byte[37000];

            DatagramPacket recv_packet = new DatagramPacket(receivedata, receivedata.length);

            clientsocket.receive(recv_packet);

            byte[] buff = recv_packet.getData();

            System.out.println("Receiving..." + buff.length);

            return recv_packet;

            //label.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage retornaImg(DatagramPacket recv_packet) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(recv_packet.getData()));
            return img;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Recebe tela = new Recebe();
        tela.setTitle("Visualização da tela");
        tela.setSize(new Dimension(800, 600));
        tela.setLocationRelativeTo(null);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLayout(new FlowLayout());
        tela.setVisible(true);
    }
}
