

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;

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

                Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                Rectangle shotArea = new Rectangle(800,600); //defaultToolkit.getScreenSize()
                BufferedImage img = r.createScreenCapture(shotArea);

                downloadImg(img);

                byte[] buffer = convertToJPG(img);

                DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, IpServidor, PORTA);
                clientSocket.send(sendPacket);

                System.out.println("Enviou " + buffer.length);
                sleep(800);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] convertToJPG(BufferedImage img) throws IOException {
        // Obtain writer for JPEG format 
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        
        // Configure JPEG compression: X% quality
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(0.25f);

        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        writer.setOutput(new MemoryCacheImageOutputStream(compressed));
        
        // Write image as JPEG w/configured settings to the in-memory stream
        // (the IIOImage is just an aggregator object, allowing you to associate
        // thumbnails and metadata to the image, it "does" nothing)
        writer.write(null, new IIOImage(img, null, null), iwp);
        writer.dispose();
        compressed.flush();
        
        return compressed.toByteArray();
    }

    public void downloadImg(BufferedImage img) throws IOException {
        String path = "C:\\Users\\adm\\Downloads\\Shot.jpg";
        ImageIO.write(img, "jpg", new File(path));
    } 

}
