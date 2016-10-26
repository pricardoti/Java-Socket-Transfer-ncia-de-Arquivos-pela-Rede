package socketServer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import Mp3.Jlayer;

public class SocketServer {
	public static void main(String args[]) throws Exception {
		new Server();
	}
	
	public static class Server implements Runnable {
		private ServerSocket srvSocket;

		public Server () throws Exception {
			new Thread(this).start();
		}
	
		@Override
		public void run() {
			try {
				/**
				 * Levantando a conexão via socket para escutar a porta definida como padrão, 
				 * e realiza o start da Thread na classe implicita para leitura e gravação do arquivo.
				 * */
				srvSocket = new ServerSocket(5566);
				Socket socket = srvSocket.accept();
				new getFileServer(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}				
		}
		
	}
	
	public static class getFileServer extends Thread {		
		private Socket socket;
		
		/**
		 * Construtor da classe que recebe o arquivo via Thread
		 * @param socket recebe o socket aceito na porta setada como padrão.
		 * */
		public getFileServer(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			FileOutputStream file;
			
			try {
				/**
				 * Instanciando o SeverSocket para escutar a porta como default 5566 
				 * O metodo accept() bloqueia a execução até que o servidor receba um pedido de conexão.
				 * */
		        ObjectInputStream out = new ObjectInputStream(socket.getInputStream());		
		        String dirFile = out.readUTF();

		        /**
		         * Leitura do arquivo em bytes, remonta e escreve o arquivo no diretorio
		         * passado pelo cliente. Informa ao cliente caso tudo aconteça corretamente.
		         * */
		        file = new FileOutputStream(dirFile);	        
		        byte[] buf = new byte[4096];	        
	        
		        while (true){
		        	int len =  out.read(buf);
		        	if (len == -1) 
		        		break;
		        	file.write(buf, 0, len);
		        }
		        
				JOptionPane.showMessageDialog(null, "Arquivo recebido com sucesso!");				
				new Jlayer(dirFile);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
}