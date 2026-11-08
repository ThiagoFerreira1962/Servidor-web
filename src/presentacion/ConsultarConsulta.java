package presentacion;

import logica.IControladorDiaConsulta;
import datatypes.DTDiaConsulta;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

@SuppressWarnings("serial")
public class ConsultarConsulta extends JInternalFrame {
	private IControladorDiaConsulta controladorDC;
	private ListarConsultas ventanaListarConsultas;
	
	private JComboBox<DTDiaConsulta> comboDiasConsulta;
	private JLabel lblDiasConsulta;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JPanel panelContenido;
	
	
	public ConsultarConsulta(IControladorDiaConsulta icd) {
		controladorDC = icd;
		
		setResizable(false);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consultar una Consulta");
		setBounds(30, 30, 450, 180);
		
		panelContenido = new JPanel();
		panelContenido.setLayout(null);
		getContentPane().add(panelContenido);
		
		
		//DIASCONSULTAS
		lblDiasConsulta = new JLabel("Días de Consulta:");
		lblDiasConsulta.setBounds(20, 30, 110, 20);
		panelContenido.add(lblDiasConsulta);
		
		comboDiasConsulta = new JComboBox<DTDiaConsulta>();
		comboDiasConsulta.setBounds(140, 28, 250, 25);
		panelContenido.add(comboDiasConsulta);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(20, 62, 110, 25);
		panelContenido.add(btnAceptar);
				
		//CANCELAR
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(280, 62, 110, 25);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelContenido.add(btnCancelar);

		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTDiaConsulta dia = (DTDiaConsulta) comboDiasConsulta.getSelectedItem();
				//se fija si selecciona un dia de consulta y sino tira un mensaje
				if (dia == null) {
					JOptionPane.showMessageDialog(ConsultarConsulta.this, "Debe seleccionar un día de consulta", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (ventanaListarConsultas.cargarConsultas(dia)) {
					ventanaListarConsultas.setVisible(true);
					ventanaListarConsultas.toFront();
				}
			}
		});
		
	}
	
	public void setVentanaListarConsultas(ListarConsultas v) {
		this.ventanaListarConsultas = v;
	}
	
	
	public void cargarDias() {
		comboDiasConsulta.removeAllItems();
		
		//Para que los días de consulta se muestren en el orden de la semana
		List<DTDiaConsulta> dias = new ArrayList<>(controladorDC.listarDiasConsultas());
		dias.sort(Comparator.comparing(DTDiaConsulta::getDia));
		
		for (DTDiaConsulta dtdc : dias) {
			comboDiasConsulta.addItem(dtdc);
		}
		comboDiasConsulta.setSelectedIndex(-1);
	}
	
	
}