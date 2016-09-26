package Mp3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import javazoom.jl.player.Player;

public class Jlayer extends JFrame {
	private static final long serialVersionUID = 1L;
	Mp3 musica;
	JFrame jtfMainFrame;
	JButton jbnButton1, jbnButton2, jbnButton3;
	JPanel jplPanel;
	
	public Jlayer(String pathFile){
		/**
		 * @author pauloricardo
		 * @param pathFile recebe o caminho do arquivo a ser executado
		 * 
		 * quando recebe, cria uma instancia de file para que possa ler o arquivo,
		 * passa para class mp3 e chama o metodo de reproduzir.
		 * */
		File mp3File = new File(pathFile);
		
		jtfMainFrame = new JFrame("Player music!");
		jtfMainFrame.setSize(0, 50);
		jbnButton1 = new JButton("play");
		jbnButton2 = new JButton("stop");
		jplPanel = new JPanel();
		jbnButton1.setMnemonic(KeyEvent.VK_I); //Set ShortCut Keys
		jbnButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jbnButton1.setEnabled(false);
				jbnButton2.setEnabled(true);				
				musica = new Mp3(mp3File);
				musica.start();	
			}
		});
		jbnButton2.setMnemonic(KeyEvent.VK_I);
		jbnButton2.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				jbnButton1.setEnabled(true);
				jbnButton2.setEnabled(false);
				musica.stop();
			}
		});
		jplPanel.setLayout(new FlowLayout());		
		jplPanel.add(jbnButton1);
		jplPanel.add(jbnButton2);
		jtfMainFrame.getContentPane().add(jplPanel, BorderLayout.CENTER);
		jtfMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jtfMainFrame.pack();
		jtfMainFrame.setVisible(true);
		
		// iniciando a musica, apenas para quando abrir o player tocar automaticamente. 
		jbnButton1.setEnabled(false);
		musica = new Mp3(mp3File);
		musica.start();	
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public class Mp3 extends Thread {
		/**
		 * @param file arquivo a ser executado.
		 * @param player manipula a execução do arquivo.
		 * */
		private File mp3;
		private Player player;
				
		public Mp3(File File){
			this.mp3 = File;
		}
		
		@Override
		public void run() {
			try {
				/**
				 * Carrega o arquivo para leitura e executa.
				 * */
				FileInputStream fis = new FileInputStream(mp3);
				BufferedInputStream bis = new BufferedInputStream(fis);

				this.player = new Player(bis);
				this.player.play();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
