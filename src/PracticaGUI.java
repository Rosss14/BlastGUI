import javax.swing.*;
import java.awt.event.*; 
import java.awt.FlowLayout;
import java.awt.Dimension;
import blast.BlastController;

public class PracticaGUI extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String dataBaseFile = new String("yeast.aa");
	private static final String dataBaseIndexes = new String("yeast.aa.indexs");
	
	private JButton bSearch;
	private JComboBox<String> cbSearch;
	private JTextField tPercentage;
	private JTextArea taResult;
	private JRadioButton rbProtein;
	private JRadioButton rbNucleotide;
	private JLabel lSearch;
	private JLabel lSecuence;
	private char type = 'p';
	private float similarity;
	
	public PracticaGUI() {
		
		
		
		setLayout(new FlowLayout());
		tPercentage = new JTextField("");
		tPercentage.setPreferredSize(new Dimension(50,25));
		lSearch = new JLabel("Similarity");
		lSecuence = new JLabel("Secuence");
		bSearch = new JButton("Search");
		bSearch.addActionListener(new BlastListener());
		cbSearch = new JComboBox<String>();
		cbSearch.setEditable(true);
		cbSearch.addActionListener(new BlastListener());
		rbProtein = new JRadioButton("Protein",true);
		rbNucleotide = new JRadioButton("Nucleotide");
		rbProtein.addActionListener(new BlastListener());
		rbNucleotide.addActionListener(new BlastListener());
		add(lSearch);
		add(tPercentage);
		add(bSearch);
		add(lSecuence);
		add(cbSearch);
		add(rbProtein);
		add(rbNucleotide);
		taResult = new JTextArea("");
		add(new JScrollPane(taResult) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
				return new Dimension(200,100);
			}
		});
		
	}
	private class BlastListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			try {
				if (e.getSource().equals(bSearch)) {
					BlastController bCont = new BlastController();
					similarity = Float.parseFloat(tPercentage.getText());
					taResult.setText("Search parameters: "+ type + " "+ cbSearch.getSelectedItem().toString()+" "+similarity);
					if (similarity < 0 || similarity > 1) {
						System.out.println("Similarity : introduce un numero entre 0.0 y 1.0");
					}
					taResult.setText(bCont.blastQuery(type, dataBaseFile, 
							dataBaseIndexes,similarity,cbSearch.getSelectedItem().toString()));
				}
				else if ((e.getSource().equals(cbSearch))&&(e.getActionCommand().equals("comboBoxEdited"))) {
					cbSearch.addItem((String) cbSearch.getSelectedItem());	 
				}
				else if ((e.getSource().equals(cbSearch))&&(e.getActionCommand().equals("comboBoxChanged"))) {
					cbSearch.setSelectedItem(cbSearch.getSelectedItem());
				}
				else if (e.getSource().equals(rbNucleotide)&&rbNucleotide.isSelected()) {
					rbProtein.setSelected(false);
					rbNucleotide.setSelected(true);
					type='n';
				}
				else if (e.getSource().equals(rbProtein)&&rbProtein.isSelected()) {
					rbProtein.setSelected(true);
					rbNucleotide.setSelected(false);
					type='p';
				}
			}catch(Exception exc) {
				System.out.println("Error: "+exc.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame myFrame = new JFrame("GUIBlast");
				myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
				myFrame.setContentPane(new PracticaGUI());
				myFrame.setVisible(true);
				myFrame.setSize(new Dimension(210,300));
				myFrame.setLocationRelativeTo(null);
			}
		});
	}
}
