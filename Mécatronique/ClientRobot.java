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

	private JLabel titre; 
    private JButton bAvance;
    private JButton bRecule;
    private JButton bGauche;
    private JButton bDroite;
	private JButton bCarre;
	private JButton bStop;
	private JButton bZigzag;
    private JButton bTriangle;
    
    private String serverHostname = new String ("192.168.43.155"); // L'adresse du serveur
    private int portNumber = 5000; // Le port du serveur
    
    Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize(); // full screen
    int largeur = tailleMoniteur.width;
    int hauteur = tailleMoniteur.height;
        
                
    public ClientRobot() {
		
        //super("Client - Araignée");
        addKeyListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);              // [NST]
		this.setLocation(300,300);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLayout(null);
		setVisible(true);
		
		Font police1 = new Font("Bookman Old Style",Font.BOLD,80); //police pour le titre
		Font police2 = new Font("Bookman Old Style",Font.BOLD,20); //police pour les boutons
		
		//Jpanel télécommande
		
		titre = new JLabel(); 
        titre.setFont(police1);
        titre.setText("INS'ARAIGNEE");
        titre.setBounds((int)(getWidth()/3.3), -40, getWidth(), getHeight()/5);
		titre.setForeground(Color.BLACK);
		
		
		JPanel pDir = new JPanel();
        pDir.setLayout(null);
        pDir.setBounds(10,20,largeur-40,(int)(hauteur/2-50));
        pDir.setBackground(Color.WHITE);
      
        bAvance = new JButton("Avance");
        bAvance.setFont(police2); 
        bAvance.addActionListener(this);
        bRecule = new JButton("Recule");
        bRecule.setFont(police2);
        bRecule.addActionListener(this);
        bGauche = new JButton("Gauche");
        bGauche.setFont(police2);
        bGauche.addActionListener(this);
        bDroite = new JButton("Droite");
        bDroite.setFont(police2);
        bDroite.addActionListener(this);   
        bAvance.setBounds((int)(getWidth()/2.8),(int)(getWidth()/11),250,70); 
        bRecule.setBounds((int)(getWidth()/2.8),(int)(getWidth()/6),250,70);
        bGauche.setBounds((int)(getWidth()/1.7),(int)(getWidth()/8),250,70);
        bDroite.setBounds((int)(getWidth()/7),(int)(getWidth()/8),250,70);
        
        ImageIcon toile = new ImageIcon(new ImageIcon("./toile.png").getImage().getScaledInstance(500,500, Image.SCALE_DEFAULT)); 
		JLabel photo1 = new JLabel(toile);
		photo1.setLayout(null);
		photo1.setBounds(largeur-500,-20,500,500); 
       
        pDir.add(bGauche);
        pDir.add(bDroite);
        pDir.add(bAvance);
        pDir.add(bRecule);
        pDir.add(titre); 
        pDir.add(photo1);
		
		//JPanel fonction de déplacement
        ImageIcon m = new ImageIcon(new ImageIcon("./fond1.jpg").getImage().getScaledInstance(largeur +100,hauteur+100, Image.SCALE_DEFAULT)); 
        Image monImage = m.getImage();
		JPanel pFonction = new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(monImage, -100, -100,this);
                
            }
        };
		pFonction.setBounds(10,(int)(hauteur/2-10),largeur-40,(int)(hauteur/2-50));
		pFonction.setLayout(null);
		//pFonction.setBackground(Color.blue);
	
		
		
		bCarre = new JButton("Carre");
		bCarre.setFont(police2);
        bCarre.addActionListener(this);
        bStop = new JButton("Stop");
        bStop.setFont(police2);
        bStop.addActionListener(this);
        bZigzag = new JButton("Zigzag");
        bZigzag.setFont(police2);
        bZigzag.addActionListener(this); 
        bTriangle = new JButton("Triangle");
        bTriangle.setFont(police2);
        bTriangle.addActionListener(this); 
		bCarre.setBounds((int)(getWidth()/2),(int)(getWidth()/17),250,70); 
        bStop.setBounds((int)(getWidth()/2),(int)(getWidth()/8),250,70);
        bZigzag.setBounds((int)(getWidth()/1.4),(int)(getWidth()/17),250,70);
		bTriangle.setBounds((int)(getWidth()/1.4),(int)(getWidth()/8),250,70);
	
		pFonction.add(bCarre);
        pFonction.add(bStop);
        pFonction.add(bZigzag);
		pFonction.add(bTriangle);
		
		ImageIcon icon = new ImageIcon(new ImageIcon("./photo2.png").getImage().getScaledInstance(550,420, Image.SCALE_DEFAULT)); 
		JLabel photo = new JLabel(icon); 
		photo.setLayout(null);
		photo.setBounds(80,-120,500,500); 
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
                if (e.getSource()== bAvance) {
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
		try{ Thread.sleep(5000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
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
		envoyerMessage("tourner à droite");
		try{ Thread.sleep(2000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("aller devant");
		try{ Thread.sleep(12000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("tourner à droite");
		try{ Thread.sleep(6000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("aller devant");
		try{ Thread.sleep(12000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("tourner à droite");
		try{ Thread.sleep(6000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("aller devant");
		try{ Thread.sleep(12000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
		envoyerMessage("tourner à droite");
		try{ Thread.sleep(4000);}catch(InterruptedException ex){ Thread.currentThread().interrupt();}
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
		
        if (e.getKeyChar()=='c' || e.getKeyChar()=='C') {
                        carre();
        }
        
        if (e.getKeyChar()=='z' || e.getKeyChar()=='Z') {
                        zigzag();
        }
        
        if (e.getKeyChar()=='t' || e.getKeyChar()=='T') {
                        triangle();
        }
    }
        
        public void keyReleased(KeyEvent e) {
        }
        
        public void keyTyped(KeyEvent e) {
        }
    
}
