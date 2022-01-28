import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class ClientRobot extends JFrame implements ActionListener, KeyListener{

    private JButton bAvance;
    private JButton bRecule;
    private JButton bGauche;
    private JButton bDroite;
	private JButton bCarre;
	private JButton bStop;
	private JButton bZigzag;
    private JButton bTriangle;
    
    private JTextField textMessage1;
    private JTextField textMessage2;
    private JButton boutonEnvoyer;

    private String serverHostname = new String ("192.168.43.155"); // L'adresse du serveur
    private int portNumber = 5000; // Le port du serveur
    
    //Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize(); // full screen
    //int largeur = tailleMoniteur.width;
    //int hauteur = tailleMoniteur.height;
        
                
    public ClientRobot() {
		
        //super("Client - Araignée");
        addKeyListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);              // [NST]
		this.setLocation(300,300);
		int largeur = 600;
		int hauteur = 600;
		setSize(largeur,hauteur);
		setLayout(null);
		setVisible(true);
		
		//Jpanel télécommande
		JPanel pDir=new JPanel();
        pDir.setLayout(null);
        pDir.setBounds(10,20,largeur-40,(int)(hauteur/2-50));
        pDir.setBackground(Color.WHITE);
      
        bAvance = new JButton("Avance");
        bAvance.addActionListener(this);
        bRecule = new JButton("Recule");
        bRecule.addActionListener(this);
        bGauche = new JButton("Gauche");
        bGauche.addActionListener(this);
        bDroite = new JButton("Droite");
        bDroite.addActionListener(this);   
        bAvance.setBounds((int)(largeur/2)-105,50,160,60);
        bRecule.setBounds((int)(largeur/2)-105,140,160,60);
        bGauche.setBounds(10,90,160,60);
        bDroite.setBounds(380,90,160,60);
       
        pDir.add(bGauche);
        pDir.add(bDroite);
        pDir.add(bAvance);
        pDir.add(bRecule);
		
		//JPanel fonction de déplacement
		JPanel pFonction = new JPanel();
		pFonction.setBounds(10,(int)(hauteur/2-10),largeur-40,(int)(hauteur/2-50));
		pFonction.setLayout(null);
		pFonction.setBackground(Color.blue);
		
		bCarre = new JButton("Carre");
        bCarre.addActionListener(this);
        bStop = new JButton("Stop");
        bStop.addActionListener(this);
        bZigzag = new JButton("zigzag");
        bZigzag.addActionListener(this); 
        bTriangle = new JButton("Triangle");
        bTriangle.addActionListener(this); 
		bCarre.setBounds(280,50,130,70);
        bStop.setBounds(280,130,130,70);
        bZigzag.setBounds(420,50,130,70);
		bTriangle.setBounds(420,130,130,70);
	
		pFonction.add(bCarre);
        pFonction.add(bStop);
        pFonction.add(bZigzag);
		pFonction.add(bTriangle);
		
		ImageIcon icon = new ImageIcon(new ImageIcon("./photo2.png").getImage().getScaledInstance(300,300, Image.SCALE_DEFAULT));
		JLabel photo = new JLabel(icon);
		photo.setLayout(null);
		photo.setBounds(0,0,250,240);
		pFonction.add(photo);
		
		
		// JLabelMain
		JPanel pMain = new JPanel();
        pMain.setLayout(null);
		pMain.setBackground(new Color(0, 0, 153));
		pMain.add(pFonction);
        pMain.add(pDir);
        
        this.setContentPane(pMain);
        setVisible(true);
    }


    public static void main(String[] args) throws InterruptedException{
        new ClientRobot();
        String USAGE = "java MidiSource [-p] [<midifile.mid>]";
        String PLAY  = "-p";
        String VERSION = "MidiSource verison .3";
        boolean VERBOSE = true;
        MidiSource source = null;

        System.out.println(VERSION);
        // make this receiver listen for input from first MIDI input device found
        if (args.length == 0) {                               // java MidiSource
            source = new MidiSource(VERBOSE, false);
        }
        else if (args.length == 1) {
            if (args[0].equals(PLAY))                         // java MidiSource -p
                source = new MidiSource(VERBOSE, true);
            else {                                             // java MidiSource somefile.mid
                source = new MidiSource(args[0], VERBOSE, false);
                source.start();
            }
        }
        else if (args.length == 2) {
            if (args[0].equals(PLAY)) {                        // java MidiSource -p somefile.mid
                source = new MidiSource(args[1], VERBOSE, true);
                source.start();
            }
            else if (args[1].equals(PLAY)) {                   // java MidiSource somefile.mid -p
                source = new MidiSource(args[0], VERBOSE, true);
                source.start();
            }
            else
                System.out.println(USAGE);
        }
        else
            System.out.println(USAGE);
        
    }
    
     public void actionPerformed(ActionEvent e) {
                if(e.getSource()==boutonEnvoyer || e.getSource()==textMessage1) {
                        envoyerMessage("moteurDroit:"+ textMessage1.getText());
                        envoyerMessage("moteurGauche:"+ textMessage2.getText());
                        textMessage1.setText("");
                        textMessage2.setText("");
                } if (e.getSource()== bAvance) {
                        envoyerMessage("aller devant");
                } if (e.getSource()== bRecule) {
                        envoyerMessage("retourner en arrière");
                } if (e.getSource()== bDroite) {
                        envoyerMessage("tourner à droite");
                } if (e.getSource()== bGauche) {
                        envoyerMessage("tourner à gauche");
                } if (e.getSource()== bCarre) {
                        carre();
                }  if(e.getSource()== bStop) {
                        envoyerMessage("stop");
				}  if(e.getSource()== bZigzag) {
                        zigzag();
				} if(e.getSource()== bTriangle) {
                        triangle();
				}
    }
    
    public  void carre() {
		envoyerMessage("aller devant");
		try{ Thread.sleep(10000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
		try{ Thread.sleep(500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("tourner à droite");
		try{ Thread.sleep(5150);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
		try{ Thread.sleep(500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("aller devant");
		try{ Thread.sleep(15000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
		try{ Thread.sleep(500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("tourner à droite");
		try{ Thread.sleep(5100);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
		try{ Thread.sleep(500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("aller devant");
		try{ Thread.sleep(10000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
		try{ Thread.sleep(500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("tourner à droite");
		try{ Thread.sleep(5000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
		try{ Thread.sleep(500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("aller devant");
		try{ Thread.sleep(15000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
		try{ Thread.sleep(500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("tourner à droite");
		try{ Thread.sleep(5000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
	}
	
	public  void zigzag(){
		envoyerMessage("aller devant");
		try{ Thread.sleep(8000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("retourner en arrière");
		try{ Thread.sleep(8000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("aller devant");
		try{ Thread.sleep(8000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("retourner en arrière");
		try{ Thread.sleep(8000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
	}
	public  void triangle(){
		envoyerMessage("aller devant");
		try{ Thread.sleep(12000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("tourner à droite");
		try{ Thread.sleep(6500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("aller devant");
		try{ Thread.sleep(12000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("retourner en arrière");
		try{ Thread.sleep(6500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");
		try{ Thread.sleep(12000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("retourner en arrière");
		try{ Thread.sleep(6500);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("stop");	
		
	}
    public void envoyerMessage(String mess){
                
                try {
                        
                DatagramSocket socket = new DatagramSocket();
                byte[] b = mess.getBytes();

                DatagramPacket msg = new DatagramPacket(b, b.length, InetAddress.getByName(serverHostname), portNumber);
                socket.send(msg);
                socket.close();
                        
                } catch(Exception e) {
                        e.printStackTrace();
                        System.exit(0) ;
                }
        }
        
    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
				envoyerMessage("aller devant");
		}
		
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				envoyerMessage("retourner en arrière");
		}
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				envoyerMessage("tourner à droite");
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				envoyerMessage("tourner à gauche");
		}
                        
        if (e.getKeyChar()=='o' || e.getKeyChar()=='O') {
                        envoyerMessage("rond");
        }

        if (e.getKeyChar()=='c' || e.getKeyChar()=='C') {
                        envoyerMessage("carré");
        }
        
        if (e.getKeyChar()=='s' || e.getKeyChar()=='S') {
                        envoyerMessage("slalom");
        }
        
        if (e.getKeyChar()=='t' || e.getKeyChar()=='T') {
                        envoyerMessage("triangle");
        }
    }
        
        public void keyReleased(KeyEvent e) {
        }
        
        public void keyTyped(KeyEvent e) {
			bAvance.setText("lolo");
        }
    
}
