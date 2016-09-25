package Mp3;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Jlayer {
	
	public void playerMp3(String pathFile){
		/**
		 * @author pauloricardo
		 * @param pathFile recebe o caminho do arquivo a ser executado
		 * 
		 * quando recebe, cria uma instancia de file para que possa ler o arquivo,
		 * passa para class mp3 e chama o metodo de reproduzir.
		 * */
		File mp3File = new File(pathFile);		
		Mp3 musica = new Mp3();
		
		musica.play(mp3File);
		musica.start();		
	}
	
	public class Mp3 extends Thread {
		/**
		 * @param file arquivo a ser executado.
		 * @param player manipula a execução do arquivo.
		 * */
		private File mp3;
		private Player player;
		
		public void play(File File){
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
