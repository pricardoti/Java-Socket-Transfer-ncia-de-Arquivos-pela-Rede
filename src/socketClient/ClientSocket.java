package socketClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ClientSocket extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private long tamanhoPermitidoKB = 5120; // Igual a 5MB
	private Arquivo arquivo;
	private File fileSelected;

	public ClientSocket() {
		initComponents();
	}

	public void initComponents() {
		jLabel1 = new javax.swing.JLabel();
		jTextFieldNome = new javax.swing.JTextField();
		jButtonArquivo = new javax.swing.JButton();
		jLabelTamanho = new javax.swing.JLabel();
		jButtonEnviar = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jTextFieldIP = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		jTextFieldDiretorio = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		jTextFieldPorta = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jLabel1.setText("Arquivo:");

		jTextFieldNome.setEnabled(false);	// desabilitando input do nome do arquivo
		jTextFieldPorta.setEnabled(false);	// desabilitando input da porta de conexao
		jTextFieldPorta.setText("5566"); 	// setando como default
		
		jButtonArquivo.setText("Selecionar Arquivo");
		jButtonArquivo.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonArquivoActionPerformed(evt);
			}
		});

		jLabelTamanho.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabelTamanho.setText("Tamanho:");

		jButtonEnviar.setText("Enviar");
		jButtonEnviar.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButtonEnviarActionPerformed(evt);
			}
		});

		jLabel2.setText("IP");
		jLabel3.setText("Diret\u00f3rio Dest.");
		jLabel4.setText("Porta");
		
		// montagem do layout da interface
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jTextFieldNome, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
						.addComponent(jLabel1).addComponent(jButtonEnviar).addComponent(jButtonArquivo)
						.addComponent(jLabelTamanho)
						.addGroup(layout.createSequentialGroup().addComponent(jLabel2)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104,
										Short.MAX_VALUE)
								.addComponent(jTextFieldIP, javax.swing.GroupLayout.PREFERRED_SIZE, 162,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
										layout.createSequentialGroup().addComponent(jLabel4)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jTextFieldPorta, javax.swing.GroupLayout.PREFERRED_SIZE,
														162, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
										layout.createSequentialGroup().addComponent(jLabel3)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jTextFieldDiretorio,
														javax.swing.GroupLayout.PREFERRED_SIZE, 160,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addGap(37, 37, 37)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap().addComponent(jLabel1)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
						.addComponent(jTextFieldIP, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4)
						.addComponent(jTextFieldPorta, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3)
						.addComponent(jTextFieldDiretorio, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGap(16, 16, 16).addComponent(jButtonArquivo)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabelTamanho)
				.addGap(25, 25, 25).addComponent(jButtonEnviar).addGap(139, 139, 139)));
		pack();
	}

	private void jButtonEnviarActionPerformed(java.awt.event.ActionEvent evt) {
		enviarArquivoServidor();
	}

	private void jButtonArquivoActionPerformed(java.awt.event.ActionEvent evt) {
		FileInputStream fis;
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setDialogTitle("Escolha o arquivo");

			if (chooser.showOpenDialog(this) == JFileChooser.OPEN_DIALOG) {
				fileSelected = chooser.getSelectedFile();

				byte[] bFile = new byte[(int) fileSelected.length()];
				fis = new FileInputStream(fileSelected);
				fis.read(bFile);
				fis.close();

				long kbSize = fileSelected.length() / 1024;
				jTextFieldNome.setText(fileSelected.getName());	
				jLabelTamanho.setText(kbSize + " KB");

				arquivo = new Arquivo();
				arquivo.setConteudo(bFile);
				arquivo.setDataHoraUpload(new Date());
				arquivo.setNome(fileSelected.getName());
				arquivo.setTamanhoKB(kbSize);
				arquivo.setIpDestino(jTextFieldIP.getText());
				arquivo.setPortaDestino(jTextFieldPorta.getText());
				arquivo.setDiretorioDestino(jTextFieldDiretorio.getText().trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void enviarArquivoServidor() {
		/**
		 * Faz a validação do arquivo, checando se o mesmo possui o tamanho máximo permitido para envio; 
		 * Realiza a conexão com o servidor através do IP e Porta digitados;
		 **/
		if (validaArquivo()) {
			ObjectOutputStream out;
			try {
				/**
				 * Recebe o ip da interface e transforma em objeto
				 * InetAddress para poder levantar a conexao com o servidor
				 * */
				InetAddress IPAddress = InetAddress.getByName(jTextFieldIP.getText().trim());
				int port = Integer.parseInt(jTextFieldPorta.getText().trim());
				
				/**
				 * Abre a conexão com o servidor com o host e porta fornecidos.
				 * abra o canal de saida de stream (enviar).
				 * */
				socket = new Socket(IPAddress, port); 
		        out = new ObjectOutputStream(socket.getOutputStream());
		        
		        /**
		         * Escreve as informações para que no servidor, possamos ler e tornar 
		         * a gravação de forma mais dinamica sem que tenha que ser um diretorio e nome default.
		         * */
		        out.writeUTF(arquivo.getDiretorioDestino() + fileSelected.getName());
		        out.writeLong(fileSelected.length());
		        
		        /**
		         * Lendo o arquivo e transformando em bytes para transmissão na rede.
		         * */
		        file = new FileInputStream(fileSelected.getPath().toString());		        
		        byte[] buf = new byte[4096];    
		        
		        while (true){
		        	int len = file.read(buf);
		        	if (len == -1) 
		        		break;
		        	out.write(buf, 0, len);
		        }
		        
		        JOptionPane.showMessageDialog(null, "Arquivo enviado com sucesso!");	
		        System.exit(0);
			} catch (IOException e) {				
				if (e.getMessage().equals("Conexão recusada")) 
					JOptionPane.showMessageDialog(null, "Erro ao tentar abrir uma nova conexão!");
				else 
					JOptionPane.showMessageDialog(null, "Erro ao tentar enviar o arquivo!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean validaArquivo() {
		if (arquivo.getTamanhoKB() > tamanhoPermitidoKB) {
			JOptionPane.showMessageDialog(this,"Tamanho máximo permitido atingido (" + (tamanhoPermitidoKB / 1024) + ")");
			return false;
		}
		return true;
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ClientSocket().setVisible(true);
			}
		});
	}

	private javax.swing.JButton jButtonArquivo;
	private javax.swing.JButton jButtonEnviar;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabelTamanho;
	private javax.swing.JTextField jTextFieldDiretorio;
	private javax.swing.JTextField jTextFieldIP;
	private javax.swing.JTextField jTextFieldNome;
	private javax.swing.JTextField jTextFieldPorta;
	private FileInputStream file;
	private Socket socket;
}
