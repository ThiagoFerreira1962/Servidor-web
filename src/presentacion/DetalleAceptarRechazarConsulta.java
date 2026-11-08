package presentacion;
import datatypes.DTConsulta;
import datatypes.DTReserva;

import java.time.format.DateTimeFormatter;

import javax.swing.DefaultListCellRenderer;
import javax.swing.BorderFactory;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import logica.IControladorDiaConsulta;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class DetalleAceptarRechazarConsulta extends JInternalFrame {
	private JTextField textFieldNombre;
	private JTextField textFieldMedico;
	private JTextField textFieldFecha;
	private JTextField textFieldNumSala;
	private JTextField textFieldNroDisp;
	private JTextField textFieldCosto;
	private JLabel lblNombre;
	private JLabel lblMedico;
	private JLabel lblFecha;
	private JLabel lblNumSala;
	private JLabel lblNroDisp;
	private JLabel lblCosto;
	private JLabel lblReservas;
	private JLabel lblImagen;
	private JButton btnCerrar;
	private JButton btnAceptar;
	private JButton btnRechazar;
	private JPanel panelContenido;
	private JList<DTReserva> listaReservas;
	private DefaultListModel<DTReserva> modeloLReservas;
	private DetalleReserva ventanaReserva;
	private IControladorDiaConsulta ICDC;
	private String nombreDiaActual;
	private String nombreConsultaActual;

	public DetalleAceptarRechazarConsulta(IControladorDiaConsulta icdc) {
		this.ICDC=icdc;
		setResizable(false);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Detalle de la Consulta");
		setBounds(560, 100, 660, 450);

		panelContenido = new JPanel();
		panelContenido.setLayout(null);
		getContentPane().add(panelContenido);


		//NOMBRE
		lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(20, 25, 140, 20);
		panelContenido.add(lblNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		textFieldNombre.setBounds(170, 23, 250, 25);
		panelContenido.add(textFieldNombre);

		//MEDICO
		lblMedico = new JLabel("Médico: ");
		lblMedico.setBounds(20, 65, 140, 20);
		panelContenido.add(lblMedico);

		textFieldMedico = new JTextField();
		textFieldMedico.setEditable(false);
		textFieldMedico.setBounds(170, 63, 250, 25);
		panelContenido.add(textFieldMedico);

		//FECHA
		lblFecha = new JLabel("Fecha: ");
		lblFecha.setBounds(20, 105, 140, 20);
		panelContenido.add(lblFecha);

		textFieldFecha = new JTextField();
		textFieldFecha.setEditable(false);
		textFieldFecha.setBounds(170, 103, 250, 25);
		panelContenido.add(textFieldFecha);

		//NUMSALA
		lblNumSala = new JLabel("Número de Sala: ");
		lblNumSala.setBounds(20, 145, 140, 20);
		panelContenido.add(lblNumSala);

		textFieldNumSala = new JTextField();
		textFieldNumSala.setEditable(false);
		textFieldNumSala.setBounds(170, 143, 250, 25);
		panelContenido.add(textFieldNumSala);

		//NRODISP
		lblNroDisp = new JLabel("Números Disponibles: ");
		lblNroDisp.setBounds(20, 185, 140, 20);
		panelContenido.add(lblNroDisp);

		textFieldNroDisp = new JTextField();
		textFieldNroDisp.setEditable(false);
		textFieldNroDisp.setBounds(170, 183, 250, 25);
		panelContenido.add(textFieldNroDisp);

		//COSTO
		lblCosto = new JLabel("Costo: ");
		lblCosto.setBounds(20, 225, 140, 20);
		panelContenido.add(lblCosto);

		textFieldCosto = new JTextField();
		textFieldCosto.setEditable(false);
		textFieldCosto.setBounds(170, 223, 250, 25);
		panelContenido.add(textFieldCosto);
		
		//RESERVAS
        lblReservas = new JLabel("Reservas:");
        lblReservas.setBounds(20, 265, 140, 20);
        panelContenido.add(lblReservas);
        
        listaReservas = new JList<DTReserva>();
        listaReservas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    
            	Component lineas = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);   
                ((javax.swing.JComponent) lineas).setBorder(
                        BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY), 
                        BorderFactory.createEmptyBorder(5, 8, 5, 8) 
                        )
                    );
                    return lineas;
                }
            });
        modeloLReservas = new DefaultListModel<>();
        listaReservas.setModel(modeloLReservas);
        
        JScrollPane scrollReservas = new JScrollPane(listaReservas);
        scrollReservas.setBounds(170, 263, 250, 100);
        panelContenido.add(scrollReservas);
        
        //IMAGEN
        lblImagen = new JLabel();
        lblImagen.setBounds(440, 25, 180, 190);
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setBorder(BorderFactory.createTitledBorder("Consulta"));
        panelContenido.add(lblImagen);
        
        listaReservas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DTReserva reservaSeleccionada = listaReservas.getSelectedValue();
                    if (reservaSeleccionada != null) {
                        ventanaReserva.mostrarReserva(reservaSeleccionada);
                        ventanaReserva.setVisible(true);
                        ventanaReserva.toFront();
                        try {
                            ventanaReserva.setSelected(true);
                        } catch (java.beans.PropertyVetoException ex) {
                            ex.printStackTrace();
                        }
                    }
                } 
            }
        });
         
        //ACEPTAR
        btnAceptar= new JButton("Aceptar");
        btnAceptar.setBounds(30, 377, 110, 25);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ICDC.aceptarConsulta(nombreConsultaActual, nombreDiaActual);
            	JOptionPane.showMessageDialog(DetalleAceptarRechazarConsulta.this,"La consulta fue aceptada correctamente.","Consulta aceptada",JOptionPane.INFORMATION_MESSAGE);
            	setVisible(false);
            }
        });
		panelContenido.add(btnAceptar);
		
        //Rechazar
        btnRechazar= new JButton("Rechazar");
        btnRechazar.setBounds(160, 377, 110, 25);
        btnRechazar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ICDC.rechazarConsulta(nombreConsultaActual, nombreDiaActual);
            	JOptionPane.showMessageDialog(DetalleAceptarRechazarConsulta.this,"La consulta fue rechazada correctamente.","Consulta rechazada",JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            }
        });
		panelContenido.add(btnRechazar);
		

		//CERRAR
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(290, 377, 110, 25);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panelContenido.add(btnCerrar);
	}
	
	public void setVentanaReserva(DetalleReserva dtr) {
		this.ventanaReserva = dtr;
	}


	public void mostrarConsulta(DTConsulta detalle,String nombreDia) {
	    this.nombreDiaActual = nombreDia;
	    this.nombreConsultaActual = detalle.getNombre();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		textFieldNombre.setText(detalle.getNombre());
		textFieldMedico.setText(detalle.getNombreMedico());
		textFieldFecha.setText(detalle.getFecha().format(formato));
		textFieldNumSala.setText(String.valueOf(detalle.getNumSala()));
		textFieldNroDisp.setText(String.valueOf(detalle.getNroDisp()));
		textFieldCosto.setText("$" + detalle.getCosto());
		modeloLReservas.clear();
		if (detalle.getReservas() != null) {
			modeloLReservas.addAll(detalle.getReservas());
		}
		lblImagen.setIcon(Imagenes.cargar(detalle.getImagen(), "defaultConsulta.jpg", 150, 150));
		lblImagen.repaint();
	}
}
