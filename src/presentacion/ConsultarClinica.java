package presentacion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JInternalFrame;

import logica.IControladorClinica;
import datatypes.DTClinica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ConsultarClinica extends JInternalFrame{
	private IControladorClinica ICC;
	
	private JLabel lblClinica;
	private JComboBox<String>clinicas;
	private JButton btnConsultar;
	private JButton btnCancelar;
	
	private DetalleClinica ventanaDetalleClinica; 
	
	public void cargarClinicas() {
	    clinicas.removeAllItems();

	    for (String nombre : ICC.listarNombreClinicas()) {
	        clinicas.addItem(nombre);
	    }
	    clinicas.setSelectedIndex(-1);
	}
	public ConsultarClinica(IControladorClinica icc) {
		this.ICC=icc;
		
        setTitle("Consultar Clínica");
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setBounds(10, 40, 450, 140);
        getContentPane().setLayout(null);
		
		//Clinincas:
		lblClinica=new JLabel("Clínica:");
		lblClinica.setBounds(20, 25, 120, 20);
		getContentPane().add(lblClinica);
		
		clinicas= new JComboBox<String>();
		clinicas.setBounds(150, 25, 240, 23);
		getContentPane().add(clinicas);
		
		btnConsultar = new JButton("Aceptar");
		btnConsultar.setBounds(10, 65, 120, 25);
		
		//Cancelar:
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(280, 65, 110, 25);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		getContentPane().add(btnCancelar);
		
		btnConsultar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        String nombreClinica = (String) clinicas.getSelectedItem();

		        if (nombreClinica == null) {
		        	JOptionPane.showMessageDialog(ConsultarClinica.this,"Debe seleccionar una Clinica","Error",JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        DTClinica clinica = ICC.seleccionarClinica(nombreClinica);

		        ventanaDetalleClinica.mostrarClinica(clinica);
		        ventanaDetalleClinica.setVisible(true);
		        ventanaDetalleClinica.toFront();
		    }
		});
		getContentPane().add(btnConsultar);

	
	}
		
	public void setVentanaDetalleClinica(DetalleClinica c) {
		this.ventanaDetalleClinica= c;
	}

}
