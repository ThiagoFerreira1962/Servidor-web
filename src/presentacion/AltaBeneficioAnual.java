package presentacion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JInternalFrame;

import logica.IControladorUsuario;
import datatypes.DTUsuario;
import excepciones.BeneficioAnualRepetidoException;
import excepciones.BeneficioAnualAnioPosteriorException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class AltaBeneficioAnual extends JInternalFrame {
	private IControladorUsuario ICU;

	private JLabel lblPaciente;
	private JLabel lblAnio;
	private JLabel lblOrdenes;
	private JLabel lblTickets;

	private JComboBox<DTUsuario> comboPacientes;
	private JTextField textFieldAnio;
	private JTextField textFieldOrdenes;
	private JTextField textFieldTickets;

	private JButton btnAceptar;
	private JButton btnCancelar;


	public AltaBeneficioAnual(IControladorUsuario icu) {
		this.ICU = icu;

		setTitle("Alta de Beneficio Anual");
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setBounds(10, 40, 500, 300);
		getContentPane().setLayout(null);

		//PACIENTE
		lblPaciente = new JLabel("Paciente:");
		lblPaciente.setBounds(20, 25, 150, 20);
		getContentPane().add(lblPaciente);

		comboPacientes = new JComboBox<DTUsuario>();
		comboPacientes.setBounds(180, 25, 240, 23);
		getContentPane().add(comboPacientes);

		//AÑO
		lblAnio = new JLabel("Año:");
		lblAnio.setBounds(20, 65, 150, 20);
		getContentPane().add(lblAnio);

		textFieldAnio = new JTextField();
		textFieldAnio.setBounds(180, 65, 240, 23);
		getContentPane().add(textFieldAnio);

		//CANTIDADORDENES
		lblOrdenes = new JLabel("Órdenes de consulta:");
		lblOrdenes.setBounds(20, 105, 150, 20);
		getContentPane().add(lblOrdenes);

		textFieldOrdenes = new JTextField();
		textFieldOrdenes.setBounds(180, 105, 240, 23);
		getContentPane().add(textFieldOrdenes);

		//CANTIDADTICKETS
		lblTickets = new JLabel("Tickets de medicamento:");
		lblTickets.setBounds(20, 145, 160, 20);
		getContentPane().add(lblTickets);

		textFieldTickets = new JTextField();
		textFieldTickets.setBounds(180, 145, 240, 23);
		getContentPane().add(textFieldTickets);

		//ACEPTAR
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(180, 190, 110, 23);
		getContentPane().add(btnAceptar);

		//CANCELAR
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(310, 190, 110, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
				setVisible(false);
			}
		});
		getContentPane().add(btnCancelar);


		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTUsuario paciente = (DTUsuario) comboPacientes.getSelectedItem();
				String textoAnio = textFieldAnio.getText();
				String textoOrdenes = textFieldOrdenes.getText();
				String textoTickets = textFieldTickets.getText();

				//reviso que no falte nada
				if (paciente == null || textoAnio.isBlank() || textoOrdenes.isBlank() || textoTickets.isBlank()) {
					JOptionPane.showMessageDialog(AltaBeneficioAnual.this,
							"Debe completar todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}

				//convierto los textos a int
				int anio, ordenes, tickets;
				try {
					anio = Integer.parseInt(textoAnio);
					ordenes = Integer.parseInt(textoOrdenes);
					tickets = Integer.parseInt(textoTickets);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(AltaBeneficioAnual.this,
							"El año y las cantidades deben ser números enteros",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (anio < LocalDate.now().getYear()) {
					JOptionPane.showMessageDialog(AltaBeneficioAnual.this, "El año debe igual al año actual", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				

				if (ordenes < 0 || tickets < 0) {
					JOptionPane.showMessageDialog(AltaBeneficioAnual.this,
							"Las cantidades no pueden ser negativas",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (anio < 1000 || anio > 9999) {
					JOptionPane.showMessageDialog(AltaBeneficioAnual.this,
							"El año debe tener 4 dígitos", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					ICU.altaBeneficioAnual(paciente.getNickname(), anio, ordenes, tickets);
					JOptionPane.showMessageDialog(AltaBeneficioAnual.this,
							"El beneficio anual fue registrado con éxito",
							"Alta Beneficio Anual", JOptionPane.INFORMATION_MESSAGE);
					limpiarCampos();
					setVisible(false);

				} catch (BeneficioAnualRepetidoException ex) {
					//el paciente ya tenia beneficio para ese anio: puede editarlo o cancelar
					String[] opciones = {"Si", "No"};
					int opcion = JOptionPane.showOptionDialog(AltaBeneficioAnual.this,
							"El paciente ya tiene un beneficio anual para el año " + anio + ".\n¿Desea editarlo?",
							"Beneficio Anual Repetido", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

					if (opcion == 0) {
						ICU.editarBeneficioAnual(paciente.getNickname(), anio, ordenes, tickets);
						JOptionPane.showMessageDialog(AltaBeneficioAnual.this, "El beneficio anual fue actualizado con éxito", "Alta Beneficio Anual", JOptionPane.INFORMATION_MESSAGE);
						limpiarCampos();
						setVisible(false);
					}
				} catch (BeneficioAnualAnioPosteriorException exe) {
					JOptionPane.showMessageDialog(AltaBeneficioAnual.this, "El año no puede ser mayor a " + anio, "Alta Beneficio Anual", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}


	public void cargarPacientes() {
		comboPacientes.removeAllItems();
		for (DTUsuario u : ICU.listarPacientes()) {
			comboPacientes.addItem(u);
		}
		comboPacientes.setSelectedIndex(-1);
		limpiarCampos();
	}


	private void limpiarCampos() {
		textFieldAnio.setText("");
		textFieldOrdenes.setText("");
		textFieldTickets.setText("");
	}

}