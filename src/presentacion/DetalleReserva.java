package presentacion;

import datatypes.DTReserva;

import java.time.format.DateTimeFormatter;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class DetalleReserva extends JInternalFrame {

	private JTextField textFieldNickPaciente;
	private JTextField textFieldNomConsulta;
	private JTextField textFieldFecha;
	private JTextField textFieldNroPaciente;
	private JTextField textFieldCosto;
	private JTextField textFieldBeneficio;
	private JLabel lblNickPaciente;
	private JLabel lblNomConsulta;
	private JLabel lblFecha;
	private JLabel lblNroPaciente;
	private JLabel lblCosto;
	private JLabel lblBeneficio;
	private JButton btnCerrar;
	private JPanel panelContenido;


	public DetalleReserva() {
		setResizable(false);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Detalle de la Reserva");
		setBounds(620, 140, 460, 330);

		panelContenido = new JPanel();
		panelContenido.setLayout(null);
		getContentPane().add(panelContenido);
		
		//NICKNAME PACIENTE
		lblNickPaciente = new JLabel("Paciente: ");
		lblNickPaciente.setBounds(20, 25, 140, 20);
		panelContenido.add(lblNickPaciente);
		
		textFieldNickPaciente = new JTextField();
		textFieldNickPaciente.setEditable(false);
		textFieldNickPaciente.setBounds(170, 23, 250, 25);
		panelContenido.add(textFieldNickPaciente);
		
		//NOMBRE CONSULTA
		lblNomConsulta = new JLabel("Consulta: ");
		lblNomConsulta.setBounds(20, 65, 140, 20);
		panelContenido.add(lblNomConsulta);
		
		textFieldNomConsulta = new JTextField();
		textFieldNomConsulta.setEditable(false);
		textFieldNomConsulta.setBounds(170, 63, 250, 25);
		panelContenido.add(textFieldNomConsulta);
		
		//FECHA
		lblFecha = new JLabel("Fecha: ");
		lblFecha.setBounds(20, 105, 140, 20);
		panelContenido.add(lblFecha);

		textFieldFecha = new JTextField();
		textFieldFecha.setEditable(false);
		textFieldFecha.setBounds(170, 103, 250, 25);
		panelContenido.add(textFieldFecha);

		//NROPACIENTE
		lblNroPaciente = new JLabel("Número de Paciente: ");
		lblNroPaciente.setBounds(20, 145, 140, 20);
		panelContenido.add(lblNroPaciente);

		textFieldNroPaciente = new JTextField();
		textFieldNroPaciente.setEditable(false);
		textFieldNroPaciente.setBounds(170, 143, 250, 25);
		panelContenido.add(textFieldNroPaciente);

		//COSTO
		lblCosto = new JLabel("Costo: ");
		lblCosto.setBounds(20, 185, 140, 20);
		panelContenido.add(lblCosto);

		textFieldCosto = new JTextField();
		textFieldCosto.setEditable(false);
		textFieldCosto.setBounds(170, 183, 250, 25);
		panelContenido.add(textFieldCosto);

		//USABENEFICIO
		lblBeneficio = new JLabel("Usa Beneficio: ");
		lblBeneficio.setBounds(20, 225, 140, 20);
		panelContenido.add(lblBeneficio);

		textFieldBeneficio = new JTextField();
		textFieldBeneficio.setEditable(false);
		textFieldBeneficio.setBounds(170, 223, 250, 25);
		panelContenido.add(textFieldBeneficio);

		//CERRAR
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(310, 262, 110, 25);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelContenido.add(btnCerrar);
	}


	public void mostrarReserva(DTReserva r) {
		textFieldNickPaciente.setText(r.getNicknamePaciente());
		textFieldNomConsulta.setText(r.getNombreConsulta());
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		textFieldFecha.setText(r.getFecha().format(formato));
		textFieldNroPaciente.setText(String.valueOf(r.getNroPaciente()));
		textFieldCosto.setText("$" + String.format("%.2f", r.getCosto()));
		textFieldBeneficio.setText(r.getUsaBeneficio() ? "Si" : "No");
	}
}