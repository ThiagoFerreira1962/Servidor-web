package presentacion;

import logica.IControladorDiaConsulta;

import datatypes.DTDiaConsulta;
import datatypes.DTConsulta;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class DetalleDiaConsulta extends JInternalFrame {
	private IControladorDiaConsulta controladorDC;
	private String nombreDiaConsulta;
	
	private DetalleConsulta ventanaDetalleCons;
	private JTextField textFieldNombre;
	private JTextField textFieldDescripcion;
	private JTextField textFieldDia;
	private JTextField textFieldHoraInicio;
	private JTextField textFieldCosto;
	private JComboBox<DTConsulta> comboConsultas;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblDia;
	private JLabel lblHoraInicio;
	private JLabel lblCosto;
	private JLabel lblConsulta;
	private JLabel lblImagen;
	private JButton btnCerrar;
	private JButton btnConsultarConsulta;
	private JPanel panelContenido;
	
	
	public DetalleDiaConsulta(IControladorDiaConsulta icd) {
		controladorDC = icd;
		
		setResizable(false);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Detalle del Día de Consulta");
		setBounds(500, 100, 620, 390);
		
		panelContenido = new JPanel(); 
		panelContenido.setLayout(null);
		getContentPane().add(panelContenido);
		
		//NOMBRE
		lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(20, 25, 110, 20);
		panelContenido.add(lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		textFieldNombre.setBounds(140, 23, 250, 25);
		panelContenido.add(textFieldNombre);
		
		//DESCRIPCION
		lblDescripcion = new JLabel("Descripción: ");
		lblDescripcion.setBounds(20, 65, 110, 20);
		panelContenido.add(lblDescripcion);
		
		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setEditable(false);
		textFieldDescripcion.setBounds(140, 63, 250, 25);
		panelContenido.add(textFieldDescripcion);
		
		//DIA
		lblDia = new JLabel("Día: ");
		lblDia.setBounds(20, 105, 110, 20);
		panelContenido.add(lblDia);
		
		textFieldDia = new JTextField();
		textFieldDia.setEditable(false);
		textFieldDia.setBounds(140, 103, 250, 25);
		panelContenido.add(textFieldDia);
		
		//HORAINICIO
		lblHoraInicio = new JLabel("Hora de Inicio: ");
		lblHoraInicio.setBounds(20, 145, 110, 20);
		panelContenido.add(lblHoraInicio);
		
		textFieldHoraInicio = new JTextField();
		textFieldHoraInicio.setEditable(false);
		textFieldHoraInicio.setBounds(140, 143, 250, 25);
		panelContenido.add(textFieldHoraInicio);
		
		//COSTO
		lblCosto = new JLabel("Costo: ");
		lblCosto.setBounds(20, 185, 110, 20);
		panelContenido.add(lblCosto);
		
		textFieldCosto = new JTextField();
		textFieldCosto.setEditable(false);
		textFieldCosto.setBounds(140, 183, 250, 25);
		panelContenido.add(textFieldCosto);
		
		//CONSULTAS
		lblConsulta = new JLabel("Consultas: ");
		lblConsulta.setBounds(20, 235, 110, 20);
		panelContenido.add(lblConsulta);
		
		comboConsultas = new JComboBox<DTConsulta>();
		comboConsultas.setBounds(140, 233, 250, 25);
		panelContenido.add(comboConsultas);
		
		btnConsultarConsulta = new JButton("Consultar");
		btnConsultarConsulta.setBounds(20, 280, 110, 25);
		panelContenido.add(btnConsultarConsulta);
		
		//IMAGEN
		lblImagen = new JLabel();
		lblImagen.setBounds(420, 25, 180, 190);
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setBorder(BorderFactory.createTitledBorder("Día de Consulta"));
		panelContenido.add(lblImagen);
		
		//CERRAR
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(280, 280, 110, 25);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelContenido.add(btnCerrar);
		
		btnConsultarConsulta.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            DTConsulta c = (DTConsulta) comboConsultas.getSelectedItem();
	          //se fija si selecciona una consulta y sino tira un mensaje
	            if (c == null) {
	                JOptionPane.showMessageDialog(DetalleDiaConsulta.this,
	                    "Debe seleccionar una consulta", "Error", JOptionPane.WARNING_MESSAGE);
	                return;
	            }
	            DTConsulta detalle = controladorDC.seleccionarConsultaMedico(nombreDiaConsulta, c.getNombre());
	            ventanaDetalleCons.mostrarConsulta(detalle);
	            ventanaDetalleCons.setVisible(true);
	            ventanaDetalleCons.toFront();   
	        }
	    });
	}
	
	public void setVentanaDetalleCons(DetalleConsulta v) {
		this.ventanaDetalleCons = v;
	}
	
	public void mostrarDia(DTDiaConsulta dia) {
		nombreDiaConsulta = dia.getNombre();
		textFieldNombre.setText(dia.getNombre());
		textFieldDescripcion.setText(dia.getDescripcion());
		textFieldDia.setText(String.valueOf(dia.getDia()));
		textFieldHoraInicio.setText(String.valueOf(dia.getHoraInicio()));
		textFieldCosto.setText("$" + dia.getCosto());
		
		comboConsultas.removeAllItems();
		for (DTConsulta c : controladorDC.listarConsultasDiaConsulta(dia.getNombre())) {
			comboConsultas.addItem(c);
		}
		comboConsultas.setSelectedIndex(-1);
		lblImagen.setIcon(Imagenes.cargar(dia.getImagen(), "defaultDiaConsulta.jpg", 160, 160));
		lblImagen.repaint();
		
	}
}