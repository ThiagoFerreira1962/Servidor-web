package presentacion;

import logica.IControladorUsuario;
import datatypes.DTUsuario;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class ConsultarReservaConsulta extends JInternalFrame {
	private IControladorUsuario controladorU;
	private ListarReservas ventanaListarReservas;
	
	private JComboBox<DTUsuario> comboPacientes;
	private JLabel lblPacientes;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JPanel panelContenido;
	
	
	public ConsultarReservaConsulta(IControladorUsuario icu) {
		controladorU = icu;
		
		setResizable(false);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consultar una Reserva de Consulta");
		setBounds(30, 30, 450, 180);
		
		panelContenido = new JPanel();
		panelContenido.setLayout(null);
		getContentPane().add(panelContenido);
		
		
		//PACIENTE
		lblPacientes = new JLabel("Paciente:");
		lblPacientes.setBounds(20, 30, 110, 20);
		panelContenido.add(lblPacientes);
		
		comboPacientes= new JComboBox<DTUsuario>();
		comboPacientes.setBounds(140, 28, 250, 25);
		panelContenido.add(comboPacientes);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(20, 90, 110, 25);
		panelContenido.add(btnAceptar);
		
		//CANCELAR
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(282, 90, 110, 25);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelContenido.add(btnCancelar);
		
		
	    btnAceptar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	DTUsuario u = (DTUsuario) comboPacientes.getSelectedItem();
	        	//se fija si se selecciona algún paciente, y si no tira un mensaje
	        	if (u == null) {
	        		JOptionPane.showMessageDialog(ConsultarReservaConsulta.this,
		                    "Debe seleccionar Paciente", "Error", JOptionPane.WARNING_MESSAGE);
		                return;
	        	}
	           if (ventanaListarReservas.cargarReservas(u)) {
	        	   ventanaListarReservas.setVisible(true);
	        	   ventanaListarReservas.toFront();
	           }
	        }
	    });
}
	   
	public void setVentanaListarReservas(ListarReservas v) {
		this.ventanaListarReservas = v;
	}
	
	public void cargarPacientes() {
	    comboPacientes.removeAllItems();
	    for (DTUsuario u : controladorU.listarPacientes()) {
	        comboPacientes.addItem(u);
	    }
	    comboPacientes.setSelectedIndex(-1);
	}

	
}