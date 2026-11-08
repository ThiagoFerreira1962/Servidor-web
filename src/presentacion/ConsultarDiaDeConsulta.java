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
public class ConsultarDiaDeConsulta extends JInternalFrame {
	private IControladorDiaConsulta controladorDC;
	private DetalleDiaConsulta ventanaDetalleDia;
	
	private JComboBox<DTDiaConsulta> comboDiasConsulta;
	private JLabel lblDiasConsulta;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JPanel panelContenido;
	
	
	public ConsultarDiaDeConsulta(IControladorDiaConsulta icd) {
		controladorDC = icd;
		
		setResizable(false);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consultar un Día de Consulta");
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
		btnAceptar.setBounds(20, 90, 110, 25);
		panelContenido.add(btnAceptar);
		
		//CANCELAR
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(280, 90, 110, 25);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelContenido.add(btnCancelar);
		
	    btnAceptar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            DTDiaConsulta dia = (DTDiaConsulta) comboDiasConsulta.getSelectedItem();
	            //se fija si se selecciona algún día de consulta, y si no tira un mensaje
	            if (dia == null) {
	                JOptionPane.showMessageDialog(ConsultarDiaDeConsulta.this,
	                    "Debe seleccionar un día de consulta", "Error", JOptionPane.WARNING_MESSAGE);
	                return;
	            }
	            
	            ventanaDetalleDia.mostrarDia(dia);
	            ventanaDetalleDia.setVisible(true);
	            ventanaDetalleDia.toFront();
	        }
	    });
	    
	}
	    
	
	public void setVentanaDetalleDia(DetalleDiaConsulta v) {
		this.ventanaDetalleDia = v;
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