package presentacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import javax.swing.JComboBox;

import logica.IControladorDiaConsulta;



import datatypes.DTDiaConsulta;

import datatypes.DTConsulta;



@SuppressWarnings("serial")
public class AceptarRechazarConsulta extends JInternalFrame{
	private IControladorDiaConsulta ICDC;
	private ListarConsultasIngresadas ventanaListarConsultasIngresadas;

	private JComboBox<String> comboDiaConsulta;
	private JComboBox<String> comboConsulta;
	private JLabel lblDiaConsulta;

	private JButton btnAceptar;
    
	public void cargarDiasConsulta() {

	    comboDiaConsulta.removeAllItems();

	    for (DTDiaConsulta ddc : ICDC.listarDiasConsultas()) {
	        comboDiaConsulta.addItem(ddc.getNombre());
	    }
	}
    public void cargarConsultas() {
    	comboConsulta.removeAllItems();
    	String nombreDia = (String) comboDiaConsulta.getSelectedItem();
    	if(nombreDia==null) {
    		return;
    	}
    	for(DTConsulta dc : ICDC.listarConsultasDiaConsultaIngresadas(nombreDia)){
    		comboConsulta.addItem(dc.getNombre());
    	}
    	
    }
    public void setVentanaListarConsultasIngresadas(ListarConsultasIngresadas v) {
        this.ventanaListarConsultasIngresadas = v;
    }
    public AceptarRechazarConsulta(IControladorDiaConsulta icdc) {
    	this.ICDC=icdc;
        setTitle("Aceptar/Rechazar Consulta");
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setBounds(10, 40, 500, 200);
        getContentPane().setLayout(null);
        
        lblDiaConsulta= new JLabel("Dia Consulta:");
        lblDiaConsulta.setBounds(30, 40, 100, 25);
        getContentPane().add(lblDiaConsulta);
        
        
        comboDiaConsulta = new JComboBox<String>();
        comboDiaConsulta.setBounds(150, 40, 250, 25);
        getContentPane().add(comboDiaConsulta);
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(150, 90, 110, 25);
        getContentPane().add(btnAceptar);

        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String nombreDia = (String) comboDiaConsulta.getSelectedItem();
            	if (nombreDia == null) {JOptionPane.showMessageDialog(AceptarRechazarConsulta.this, "Seleccione un día de consulta.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            	DTDiaConsulta diaSeleccionado = null;
            	for (DTDiaConsulta dia : ICDC.listarDiasConsultas()) {
                    if (dia.getNombre().equals(nombreDia)) {
                        diaSeleccionado = dia;
                        break;
                    }
                }
                if (diaSeleccionado != null) {
                    if (ventanaListarConsultasIngresadas.cargarConsultas(diaSeleccionado)) {
                        ventanaListarConsultasIngresadas.setVisible(true);
                        ventanaListarConsultasIngresadas.toFront();
                    }
                }
            }
        });
    }
}
