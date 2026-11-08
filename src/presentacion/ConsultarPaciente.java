package presentacion;

import datatypes.DTPaciente;
import datatypes.DTReserva;
import logica.IControladorUsuario;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class ConsultarPaciente extends JInternalFrame {
	private DetalleReserva ventanaReserva;
	private IControladorUsuario controladorU;
	private DTPaciente pacienteActual;
	
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldTipo;
    private JTextField textFieldCorreo;
    private JTextField textFieldFechaNacimiento;
    private JTextField textFieldFechaIngreso;
    private JTextField textFieldCI;
    private JTextArea textAreaAntecedentes;
    private JLabel lblCorreo;
    private JLabel lblCI;
    private JLabel lblFechaNacimiento;
    private JLabel lblFechaIngreso;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblTipo;
    private JLabel lblReservas;
    private JList<DTReserva> listaReservas;
    private JLabel lblInfoUsuario;
    private JLabel lblAntecedentes;
    private JPanel panelContenido;
    private DefaultListModel<DTReserva> modeloLReservas; 
    private JButton btnEditar;
    private JButton btnCancelar;
    private Boolean modoEdicion;
    private JLabel lblImagenFuente;
    
    
    public ConsultarPaciente(IControladorUsuario ICU){
    	controladorU = ICU;
    	modoEdicion = false;
    	
    	
    	
    	setResizable(false);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Consultar un Usuario");
        setBounds(30, 30, 500, 500); 
        
        
        panelContenido = new JPanel();
        panelContenido.setLayout(null);
        panelContenido.setPreferredSize(new java.awt.Dimension(420, 700)); 
        
       
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        setContentPane(scrollPane);
    
        
        lblInfoUsuario = new JLabel("Información de Usuario");
        lblInfoUsuario.setBounds(185, 17, 180, 14);
        panelContenido.add(lblInfoUsuario);
        
        
        //NOMBRE
        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(10, 61, 65, 14);
        panelContenido.add(lblNombre);

        textFieldNombre = new JTextField();
        textFieldNombre.setEditable(false);
        textFieldNombre.setBounds(125, 54, 271, 30);
        panelContenido.add(textFieldNombre);
        
        
        //APELLIDO
        lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(10, 103, 65, 14);
        panelContenido.add(lblApellido);
        
        textFieldApellido = new JTextField();
        textFieldApellido.setEditable(false);
        textFieldApellido.setBounds(125, 96, 271, 30);
        panelContenido.add(textFieldApellido);
        
        
        //CORREO
        lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(10, 144, 65, 14);
        panelContenido.add(lblCorreo);
        
        textFieldCorreo = new JTextField();
        textFieldCorreo.setEditable(false);
        textFieldCorreo.setBounds(125, 137, 271, 30);
        panelContenido.add(textFieldCorreo);
        
        
        //TIPO
        lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(10, 186, 65, 14);
        panelContenido.add(lblTipo);
        
        textFieldTipo = new JTextField();
        textFieldTipo.setEditable(false);
        textFieldTipo.setBounds(125, 179, 271, 30);
        panelContenido.add(textFieldTipo);
        
        
        //FECHA NACIMIENTO
        lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
        lblFechaNacimiento.setBounds(10, 228, 200, 14);
        panelContenido.add(lblFechaNacimiento);
        
        textFieldFechaNacimiento = new JTextField();
        textFieldFechaNacimiento.setEditable(false);
        textFieldFechaNacimiento.setBounds(125, 221, 271, 30);
        panelContenido.add(textFieldFechaNacimiento);
        
        //IMAGEN
        
        JLabel lblImagen = new JLabel("Imagen:");
        lblImagen.setBounds(10, 664, 65, 14);
        panelContenido.add(lblImagen);
        lblImagenFuente = new JLabel();
        lblImagenFuente.setBounds(110, 650, 100, 100);
        lblImagenFuente.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelContenido.add(lblImagenFuente);
        
        //FECHA INGRESO
        lblFechaIngreso = new JLabel("Fecha Ingreso:");
        lblFechaIngreso.setBounds(10, 270, 100, 14);
        panelContenido.add(lblFechaIngreso);
        
        textFieldFechaIngreso = new JTextField();
        textFieldFechaIngreso.setEditable(false);
        textFieldFechaIngreso.setBounds(125, 263, 271, 30);
        panelContenido.add(textFieldFechaIngreso);
        

        
        
        //CI
        lblCI = new JLabel("CI:");
        lblCI.setBounds(10, 312, 100, 14);
        panelContenido.add(lblCI);
         
        textFieldCI = new JTextField();
        textFieldCI.setEditable(false);
        textFieldCI.setBounds(125, 305, 271, 30);
        panelContenido.add(textFieldCI);
        
        
        //RESERVAS
        lblReservas = new JLabel("Reservas:");
        lblReservas.setBounds(10, 354, 65, 14);
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
        listaReservas.setBounds(125, 347, 271, 100);
        panelContenido.add(listaReservas);
        modeloLReservas = new DefaultListModel<>();
        listaReservas.setModel(modeloLReservas);
        
        JScrollPane scrollReservas = new JScrollPane(listaReservas);
        scrollReservas.setBounds(125, 347, 271, 100);
        panelContenido.add(scrollReservas);
        
        listaReservas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DTReserva reservaSeleccionada = listaReservas.getSelectedValue();
                    if (reservaSeleccionada != null) {
                        ventanaReserva.mostrarReserva(reservaSeleccionada);
                        ventanaReserva.setVisible(true);
                    }
                }
            }
        });
        
      //ANTECEDENTES
        lblAntecedentes = new JLabel("Antecedentes:");
        lblAntecedentes.setBounds(10, 459, 100, 14);
        panelContenido.add(lblAntecedentes);
        
        textAreaAntecedentes = new JTextArea();
        textAreaAntecedentes.setEditable(false);
        textAreaAntecedentes.setLineWrap(true);       
        textAreaAntecedentes.setWrapStyleWord(true);
        
        JScrollPane scrollAntecedentes = new JScrollPane(textAreaAntecedentes);
        scrollAntecedentes.setBounds(125, 459, 271, 80);
        panelContenido.add(scrollAntecedentes);
        
        btnEditar = new JButton("Editar");
        btnEditar.setBounds(125, 550, 100, 25);
        panelContenido.add(btnEditar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(235, 550, 100, 25);
        btnCancelar.setVisible(false); 
        panelContenido.add(btnCancelar);

        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!modoEdicion) {
                    entrarModoEdicion();
                } else {
                    confirmarEdicion();
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                llenarCampos(pacienteActual); 
            }
        });
         	
    }
    
    public void setVentanaReserva(DetalleReserva dtr) {
    	this.ventanaReserva = dtr;
    }
    
    private void entrarModoEdicion() {
        modoEdicion = true;
        btnEditar.setText("Confirmar");
        btnCancelar.setVisible(true);
        
        textFieldNombre.setEditable(true);
        textFieldApellido.setEditable(true);
        textFieldFechaNacimiento.setEditable(true);
        textFieldFechaIngreso.setEditable(true);
        textFieldCI.setEditable(true);
        textAreaAntecedentes.setEditable(true);
    }

    private void confirmarEdicion() {
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate nuevaFechaNacimiento = LocalDate.parse(textFieldFechaNacimiento.getText(), formato);
            LocalDate nuevaFechaIngreso = LocalDate.parse(textFieldFechaIngreso.getText(), formato);
            String foto = pacienteActual.getFoto();
            controladorU.editarPaciente(
                pacienteActual.getNickname(),
                textFieldNombre.getText(),
                textFieldApellido.getText(),
                nuevaFechaNacimiento,
                nuevaFechaIngreso,
                textFieldCI.getText(),
                textAreaAntecedentes.getText(), foto
            );
            
            modoEdicion = false;
            btnEditar.setText("Editar");
            btnCancelar.setVisible(false);
            
            textFieldNombre.setEditable(false);
            textFieldApellido.setEditable(false);
            textFieldFechaNacimiento.setEditable(false);
            textFieldFechaIngreso.setEditable(false);
            textFieldCI.setEditable(false);
            textAreaAntecedentes.setEditable(false);
            
            JOptionPane.showMessageDialog(this, "Datos actualizados con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (java.time.format.DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarImagen(String ruta) {
        lblImagenFuente.setIcon(null);
        lblImagenFuente.setText("");
        lblImagenFuente.setHorizontalAlignment(JLabel.CENTER);

        if (ruta == null || ruta.trim().isEmpty()) {
            lblImagenFuente.setText("Sin imagen");
            return;
        }

        File archivo = new File("src/img", ruta);
        if (!archivo.exists()) {
            archivo = new File(ruta);
        }

        if (!archivo.exists()) {
            lblImagenFuente.setText("Sin imagen");
            return;
        }

        ImageIcon original = new ImageIcon(archivo.getPath());
        Image escalada = original.getImage().getScaledInstance(
                lblImagenFuente.getWidth() - 2,
                lblImagenFuente.getHeight() - 2,
                Image.SCALE_SMOOTH);
        lblImagenFuente.setIcon(new ImageIcon(escalada));
    }
    
    public void llenarCampos(DTPaciente dtp) {
    	this.pacienteActual = dtp;
    	textFieldNombre.setText(dtp.getNombre());
    	textFieldApellido.setText(dtp.getApellido());
    	textFieldTipo.setText("Paciente");
    	textFieldCorreo.setText(dtp.getCorreo());
    	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	textFieldFechaNacimiento.setText(dtp.getFechaNacimiento().format(formato));
    	textFieldFechaIngreso.setText(dtp.getFechaIngreso().format(formato));
    	textFieldCI.setText(dtp.getCi());
    	textAreaAntecedentes.setText(dtp.getAntecedentes());    	
    	modeloLReservas.clear();
    	cargarImagen(dtp.getFoto());
    	if(dtp.getReservas() != null) {
    		modeloLReservas.addAll(dtp.getReservas());
    	}
    	
        modoEdicion = false;
        btnEditar.setText("Editar");
        btnCancelar.setVisible(false);
        textFieldNombre.setEditable(false);
        textFieldApellido.setEditable(false);
        textFieldFechaNacimiento.setEditable(false);
        textFieldFechaIngreso.setEditable(false);
        textFieldCI.setEditable(false);
        textAreaAntecedentes.setEditable(false);
    }
    
}