package presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import datatypes.DTClinica;

@SuppressWarnings("serial")
public class DetalleClinica extends JInternalFrame {
	
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblDireccion;
	private JLabel lblCostoTicket;
	private JLabel lblCostoOrden;
	private JLabel lblSitioWeb;
	private JLabel lblImagen;
	
	private JTextField textFieldNombre;
	private JTextField textFieldDescripcion;
	private JTextField textFieldDireccion;
	private JTextField textFieldCostoTicket;
	private JTextField textFieldCostoOrden;
	private JTextField textFieldSitioWeb;
	
	private JButton btnCerrar;
	
	public DetalleClinica() {
		setResizable(false);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Detalle de la Clínica");
		setBounds(560, 100, 720, 380);
		getContentPane().setLayout(null);
		
        //Nombre:
        lblNombre= new JLabel("Nombre:");
        lblNombre.setBounds(20, 25, 120, 20);
        getContentPane().add(lblNombre);
        
        textFieldNombre= new JTextField();
        textFieldNombre.setBounds(240, 25, 240, 23);
        textFieldNombre.setEditable(false);
        textFieldNombre.setFocusable(false);
        getContentPane().add(textFieldNombre);
        
        //Descripcion:
        lblDescripcion= new JLabel("Descripción:");
        lblDescripcion.setBounds(20, 65, 120, 20);
        getContentPane().add(lblDescripcion);
        
        textFieldDescripcion= new JTextField();
        textFieldDescripcion.setBounds(240, 65, 240, 23);
        textFieldDescripcion.setEditable(false);
        textFieldDescripcion.setFocusable(false);
        getContentPane().add(textFieldDescripcion);
        
        //Direccion:
        lblDireccion= new JLabel("Dirección:");
        lblDireccion.setBounds(20, 105, 120, 20);
        getContentPane().add(lblDireccion);
        
        textFieldDireccion= new JTextField();
        textFieldDireccion.setBounds(240, 105, 240, 23);
        textFieldDireccion.setEditable(false);
        textFieldDireccion.setFocusable(false);
        getContentPane().add(textFieldDireccion);
        
        //CostoTicket:
        lblCostoTicket= new JLabel("Costo del ticket del Medicamento:");
        lblCostoTicket.setBounds(20, 145, 225, 20);
        getContentPane().add(lblCostoTicket);
        
        textFieldCostoTicket= new JTextField();
        textFieldCostoTicket.setBounds(240, 145, 240, 23);
        textFieldCostoTicket.setEditable(false);
        textFieldCostoTicket.setFocusable(false);
        getContentPane().add(textFieldCostoTicket);
        
        //CostoOrden:
        lblCostoOrden= new JLabel("Costo de la orden de Consulta:");
        lblCostoOrden.setBounds(20, 185, 180, 20);
        getContentPane().add(lblCostoOrden);
        
        textFieldCostoOrden= new JTextField();
        textFieldCostoOrden.setBounds(240, 185, 240, 23);
        textFieldCostoOrden.setEditable(false);
        textFieldCostoOrden.setFocusable(false);
        getContentPane().add(textFieldCostoOrden);
        
        //SitioWeb:
        lblSitioWeb= new JLabel("Sitio Web (opcional) :");
        lblSitioWeb.setBounds(20, 225, 170, 20);
        getContentPane().add(lblSitioWeb);
        
        textFieldSitioWeb= new JTextField();
        textFieldSitioWeb.setBounds(240, 225, 240, 23);
        textFieldSitioWeb.setEditable(false);
        textFieldSitioWeb.setFocusable(false);
        getContentPane().add(textFieldSitioWeb);
        
        //Imagen:
        lblImagen = new JLabel();
        lblImagen.setBounds(500, 25, 180, 190);
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setBorder(BorderFactory.createTitledBorder("Clínica"));
        getContentPane().add(lblImagen);
        
        //Cerrar:
        btnCerrar= new JButton("Cerrar");
        btnCerrar.setBounds(295, 300, 100, 23);
        
        btnCerrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setVisible(false);
        	}
        });
        getContentPane().add(btnCerrar);     
    }
	public void mostrarClinica(DTClinica detalle) {
		textFieldNombre.setText(detalle.getNombre());
		textFieldDescripcion.setText(detalle.getDescripcion());
		textFieldDireccion.setText(detalle.getDireccion());
		textFieldCostoOrden.setText("$" + detalle.getCostoConsulta());
		textFieldCostoTicket.setText("$" + detalle.getCostoTicket());
		textFieldSitioWeb.setText(detalle.getSitioWeb());
		lblImagen.setIcon(Imagenes.cargar(detalle.getFoto(), "defaultClinica.jpg", 150, 150));
		lblImagen.repaint();
	}
		
}
	