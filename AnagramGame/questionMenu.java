import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class questionMenu extends JFrame {
	private JTextField txtTypeInUsername;
	private JTextField txtAnswer;
	private JButton btnNext;
	private JLabel lblNewLabel;
	private JLabel lblAnagram;
	private int playerNum;
	Question currentQ;
	private Iterator<Question> e;
	public questionMenu(int name, ArrayList<Question> database, ArrayList<Player> listPlayers) throws IOException{
		super("Player " + name);
		e = database.iterator();
		getContentPane().setLayout(null);
		this.playerNum = name;
		btnNext = new JButton("next");
		btnNext.setFont(new Font("Impact", Font.PLAIN, 16));
		btnNext.setBounds(247, 212, 195, 41);
		getContentPane().add(btnNext);
		
		txtTypeInUsername = new JTextField();
		txtTypeInUsername.setBounds(0, 0, 432, 41);
		txtTypeInUsername.setText("Type in username and press enter to save");
		txtTypeInUsername.selectAll();
		txtTypeInUsername.setColumns(17);
		getContentPane().add(txtTypeInUsername);
		
		
		currentQ = e.next();
		String path = currentQ.getJPEG();
		File file = new File(path);
		if(file.exists()){
			BufferedImage image;
				try {
				image = ImageIO.read(file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				image = null;
			}		
			lblNewLabel = new JLabel(new ImageIcon(image));
			lblNewLabel.setBounds(0, 36, 239, 217);
			getContentPane().add(lblNewLabel);
		}
		//System.out.println(currentQ.answer);
		//load the anagram
		String anagram = currentQ.getAnagram();
		lblAnagram = new JLabel(anagram);
		lblAnagram.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnagram.setFont(new Font("Wide Latin", Font.PLAIN, 24));
		lblAnagram.setBounds(247, 36, 206, 63);
		getContentPane().add(lblAnagram);
		
		txtAnswer = new JTextField();
		txtAnswer.setBounds(247, 100, 195, 63);
		txtAnswer.setHorizontalAlignment(SwingConstants.CENTER);
		txtAnswer.setText("Answer");
		txtAnswer.selectAll();
		getContentPane().add(txtAnswer);
		txtAnswer.setColumns(10);
		
		thehandler handler = new thehandler(listPlayers);
		txtTypeInUsername.addActionListener(handler);
		btnNext.addActionListener(handler);
		txtAnswer.addActionListener(handler);
	}
	
	private class thehandler implements ActionListener{
		private ArrayList<Player> listPlayers;
		private Player player;
		int counter;
		int counter2;
		public thehandler(ArrayList<Player> listPlayers) {
			this.listPlayers = listPlayers;
		}
		//issued automatically it's built in
		public void actionPerformed(ActionEvent event){
			String userName = "";
			if(event.getSource() == txtTypeInUsername){
				counter2++;
				//getActionCommand gets the text from that item
				userName = String.format("%s", event.getActionCommand()); 
				if(counter2 == 1){
					player = new Player(userName, 0);
					listPlayers.add(player);
					txtTypeInUsername.setEditable(false);
				}
			} else if(event.getSource() == txtAnswer){
				counter++;
				String answer = String.format("%s", event.getActionCommand());
				JOptionPane.showMessageDialog(null, "Your answer has been recorded");
				
				if(answer.equals(currentQ.word)){
					if(counter == 1){
						player.score++;
						//System.out.println(player.score);
					}
				}
			} else if(event.getSource() == btnNext){
				try {
					loadNewQuestions(e, lblNewLabel, lblAnagram);
					counter = 0;
					counter2 = 0;
					txtAnswer.setText("Answer");
					txtAnswer.selectAll();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void loadNewQuestions(Iterator<Question> e, JLabel lblNewLabel2, JLabel lblAnagram2) throws FileNotFoundException {
		// TODO Auto-generated method stub
		if(!e.hasNext()){
			JOptionPane.showMessageDialog(null,"You have finished your turn!");
			dispose();
		} else{
			currentQ = e.next();
			String path = currentQ.getJPEG();
			File file = new File(path);
			if(file.exists()){
				BufferedImage image;
				try {
					image = ImageIO.read(file);
					lblNewLabel2.setIcon(new ImageIcon(image));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					lblNewLabel2.setIcon(new ImageIcon("images/sadface.jpg"));
				}
			} else{
				lblNewLabel2.setIcon(new ImageIcon("images/sadface.jpg"));
			}
			//load the anagram
			String anagram = currentQ.getAnagram();
			lblAnagram2.setText(anagram);
		}
	}
}
