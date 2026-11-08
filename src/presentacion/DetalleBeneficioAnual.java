package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import datatypes.DTBeneficioAnual;

@SuppressWarnings("serial")
public class DetalleBeneficioAnual extends JInternalFrame{
	
	private JPanel panelContenido;
	private JLabel lblAnio;
	private JLabel lblCantMed;
	private JLabel lblCantCons;
	private JTextField textFieldAnio;
	private JTextField textFieldCantMed;
	private JTextField textFieldCantCons;
	

	public DetalleBeneficioAnual() {
		
		setResizable(false);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Detalle del Beneficio Anual");
		setBounds(560, 100, 300, 200);

		panelContenido = new JPanel();
		panelContenido.setLayout(null);
		getContentPane().add(panelContenido);

		//AÑO
		lblAnio = new JLabel("Año: ");
		lblAnio.setBounds(20, 25, 200, 20);
		panelContenido.add(lblAnio);

		textFieldAnio = new JTextField();
		textFieldAnio.setEditable(false);
		textFieldAnio.setFocusable(false);
		textFieldAnio.setBounds(175, 23, 75, 25);
		panelContenido.add(textFieldAnio);

		//CANT MEDICAMENTOS
		lblCantCons = new JLabel("Consultas gratuitas: ");
		lblCantCons.setBounds(20, 65, 200, 20);
		panelContenido.add(lblCantCons);

		textFieldCantCons= new JTextField();
		textFieldCantCons.setEditable(false);
		textFieldCantCons.setFocusable(false);
		textFieldCantCons.setBounds(175, 63, 75, 25);
		panelContenido.add(textFieldCantCons);

		//CANT CONS
		lblCantMed = new JLabel("Medicamentos gratuitos:");
		lblCantMed.setBounds(20, 105, 200, 20);
		panelContenido.add(lblCantMed);

		textFieldCantMed = new JTextField();
		textFieldCantMed.setEditable(false);
		textFieldCantMed.setFocusable(false);
		textFieldCantMed.setBounds(175, 103, 75, 25);
		panelContenido.add(textFieldCantMed);


	}
	
	public void mostrarDetalleBeneficioAnual(DTBeneficioAnual dtba) {
		textFieldAnio.setText(String.valueOf(dtba.getAnio()));
		textFieldCantCons.setText(String.valueOf(dtba.getCantidadOrdenes()));
		textFieldCantMed.setText(String.valueOf(dtba.getCantidadTickets()));
	}
}
