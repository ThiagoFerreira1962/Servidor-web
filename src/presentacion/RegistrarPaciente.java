package presentacion;

import javax.swing.JInternalFrame;


import excepciones.UsuarioRepetidoException;
import logica.IControladorUsuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.awt.event.ActionEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@SuppressWarnings("serial")
public class RegistrarPaciente extends JInternalFrame {
	private IControladorUsuario controlUsr;
	
	private JTextField textFieldNick;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldCorreo;
    private JPasswordField textFieldContrasena;
    private JPasswordField textFieldConfirmarContrasena;
    private JTextField textFieldCI;
    private JTextField textFieldAntecedentes;
    private JTextField textFieldFechaNacimiento; 
    private JTextField textFieldFechaIngreso;
    private JTextField textFieldFoto;
   
    private static final String CARPETA_FOTOS = "src/img";
    private static final String fotoDefault = "defaultPaciente.jpg";
    private File archivoFoto;

    private JLabel lblIngreseTitulo;
    private JLabel lblIngreseNick;
    private JLabel lblIngreseNombre;
    private JLabel lblIngreseApellido;
    private JLabel lblIngreseCorreo;
    private JLabel lblContrasena;
    private JLabel lblConfirmarContrasena;
    private JLabel lblIngreseFechaNacimiento;
    private JLabel lblIngreseFechaIngreso;
    private JLabel lblIngreseCi;
    private JLabel lblIngreseAntecedentes;
    private JLabel lblFotoPerfil;
    
    private JButton btnSeleccionarFoto;
    private JButton btnAceptar;
    private JButton btnCancelar;

	

	
   
    
    protected void cmdRegistroPacienteActionPerformed(ActionEvent arg0) {
		
    	if (checkFormulario()){
        // Obtengo datos de los controles Swing
    	String nickname = this.textFieldNick.getText();
        String nombre = this.textFieldNombre.getText();
        String apellido = this.textFieldApellido.getText();
        String correo = this.textFieldCorreo.getText();
        String contrasena = new String (this.textFieldContrasena.getPassword());
        String confirmacionContrasena = new String (this.textFieldConfirmarContrasena.getPassword());
        String ci = this.textFieldCI.getText();
        String antecedentes = this.textFieldAntecedentes.getText();
        LocalDate fechaNacimiento;
        LocalDate fechaIngreso;
        
        
        //Validar y guardar fechas
        try{
        	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        	fechaNacimiento = LocalDate.parse(textFieldFechaNacimiento.getText(), formato);
        	fechaIngreso = LocalDate.parse(textFieldFechaIngreso.getText(), formato);
        } catch (DateTimeParseException e) {
        	JOptionPane.showMessageDialog(null, "Las fechas deben tener el formato dd/mm/aaaa y ser válidas.", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        
        //Validar que la fecha de ingreso sea posterior a la fecha de nacimiento
        if (fechaIngreso.isBefore(fechaNacimiento)) {
        	JOptionPane.showMessageDialog(this, "La fecha de ingreso debe ser posterior o igual a la fecha de nacimiento.", "Error", JOptionPane.WARNING_MESSAGE);
        	return;
        }
        
        //Validar que la fecha de nacimiento y la de ingresos no sean futuras al día presente
        if (fechaNacimiento.isAfter(LocalDate.now()) || fechaIngreso.isAfter(LocalDate.now())) {
        	JOptionPane.showMessageDialog(this, "Las fechas ingresadas no pueden ser futuras.", "Error", JOptionPane.WARNING_MESSAGE);
        	return;
        }
        
        //Validar CI
        try {
            Integer.parseInt(ci);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La CI debe ser un número.", "Alta Usuario",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Validar contraseñas
        boolean contrasenaValida = contrasena.equals(confirmacionContrasena);

        if (!contrasenaValida) {
            JOptionPane.showMessageDialog(
                RegistrarPaciente.this,
                "Las contraseñas no coinciden.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        String nombreFoto = fotoDefault;
        if (archivoFoto != null) {
            try {
                nombreFoto = copiarFoto(nickname);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al subir la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        //Validar y crear paciente
        try {
        	
        	controlUsr.altaPaciente(nickname, nombre, apellido, correo, fechaNacimiento, ci , 
        			fechaIngreso, antecedentes,contrasena, nombreFoto);
            JOptionPane.showMessageDialog(this, "El Usuario se ha creado con éxito", "Alta Usuario", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            setVisible(false);
        
        } catch (UsuarioRepetidoException e) {
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Alta Usuario", JOptionPane.ERROR_MESSAGE);

    	}

    	}
    	
    }
    
    	protected boolean checkFormulario() {
    	
    	String nickname = this.textFieldNick.getText();
        String nombre = this.textFieldNombre.getText();
        String apellido = this.textFieldApellido.getText();
        String correo = this.textFieldCorreo.getText();
        char [] contrasena = this.textFieldContrasena.getPassword();
        char [] confirmacionContrasena = this.textFieldConfirmarContrasena.getPassword();
        String ci = this.textFieldCI.getText();
        String antecedentes = this.textFieldAntecedentes.getText();
        String fechaNacimiento = this.textFieldFechaNacimiento.getText();
    	String fechaIngreso =this.textFieldFechaIngreso.getText();
        
    	if (nickname.isBlank() || nombre.isBlank() || apellido.isBlank()
    	        || correo.isBlank() || ci.isBlank() || antecedentes.isEmpty()
    	        || fechaNacimiento.isBlank() || fechaIngreso.isBlank()
    	        || contrasena.length == 0|| confirmacionContrasena.length == 0) {
        	JOptionPane.showMessageDialog(this, "No puede haber campos obligatiorios vacíos", "Alta Usuario",
                    JOptionPane.ERROR_MESSAGE);
        	return false;}
        return true;
    }	
    	
    	
    	private void seleccionarFoto() {
    	    JFileChooser chooser = new JFileChooser();
    	    chooser.setFileFilter(new FileNameExtensionFilter(
    	            "Imágenes (jpg, jpeg, png)", "jpg", "jpeg", "png"));

    	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
    	        archivoFoto = chooser.getSelectedFile();
    	        textFieldFoto.setText(archivoFoto.getName());
    	    }
    	}
    	
    	private String copiarFoto(String nickname) throws IOException {
    	    String nombreOriginal = archivoFoto.getName();
    	    int punto = nombreOriginal.lastIndexOf('.');
    	    String extension = (punto >= 0) ? nombreOriginal.substring(punto) : "";

    	    Path carpeta = Paths.get(CARPETA_FOTOS);
    	    Files.createDirectories(carpeta);

    	    String nombreFinal = nickname + extension;
    	    Files.copy(archivoFoto.toPath(), carpeta.resolve(nombreFinal), StandardCopyOption.REPLACE_EXISTING);
    	    return nombreFinal;
    	}

    
    	
        private void limpiarFormulario() {
        textFieldNick.setText("");
        textFieldNombre.setText("");
        textFieldApellido.setText("");
        textFieldCorreo.setText("");
        textFieldCI.setText("");
        textFieldAntecedentes.setText("");
        textFieldFechaNacimiento.setText("");
        textFieldFechaIngreso.setText("");
        textFieldContrasena.setText("");
        textFieldConfirmarContrasena.setText("");
        textFieldFoto.setText("");
        archivoFoto = null;
        
    }
   
    


    public RegistrarPaciente(IControladorUsuario icu) {
        // Se inicializa con el controlador de usuarios
        controlUsr = icu;

        // Propiedades del JInternalFrame como dimensión, posición dentro del frame,
        // etc.
        setResizable(false);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Alta de Paciente");
        setBounds(10, 40, 600, 490);
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 100, 120, 120, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 ,30, 30, 30};
        gridBagLayout.columnWeights = new double[] { 0.5, 0.5, 0.5, 0.5, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);
        
        lblIngreseTitulo = new JLabel("Datos del Paciente:");
        lblIngreseTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblIngreseTitulo = new GridBagConstraints();
        gbc_lblIngreseTitulo.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseTitulo.insets = new Insets(10, 10, 20, 10);
        gbc_lblIngreseTitulo.gridwidth = 2; 
        gbc_lblIngreseTitulo.gridx = 1;
        gbc_lblIngreseTitulo.gridy = 0;
        getContentPane().add(lblIngreseTitulo, gbc_lblIngreseTitulo);
        
        //Nick
        
        lblIngreseNick = new JLabel("Nickname: ");
        lblIngreseNick.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseNick = new GridBagConstraints();
        gbc_lblIngreseNick.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseNick.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseNick.gridx = 0;
        gbc_lblIngreseNick.gridy = 1;
        getContentPane().add(lblIngreseNick, gbc_lblIngreseNick);
        
        textFieldNick = new JTextField();
        GridBagConstraints gbc_textFieldNick = new GridBagConstraints();
        gbc_textFieldNick.gridwidth = 2;
        gbc_textFieldNick.fill = GridBagConstraints.BOTH;
        gbc_textFieldNick.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNick.gridx = 1;
        gbc_textFieldNick.gridy = 1;
        getContentPane().add(textFieldNick, gbc_textFieldNick);
        textFieldNick.setColumns(10);
        
        //Nombre
        
        lblIngreseNombre = new JLabel("Nombre: ");
        lblIngreseNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseNombre = new GridBagConstraints();
        gbc_lblIngreseNombre.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseNombre.gridx = 0;
        gbc_lblIngreseNombre.gridy = 2;
        getContentPane().add(lblIngreseNombre, gbc_lblIngreseNombre);
        
        textFieldNombre = new JTextField();
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.gridwidth = 2;
        gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
        gbc_textFieldNombre.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNombre.gridx = 1;
        gbc_textFieldNombre.gridy = 2;
        getContentPane().add(textFieldNombre, gbc_textFieldNombre);
        textFieldNick.setColumns(10);
        
        //Apellido
        
        lblIngreseApellido = new JLabel("Apellido: ");
        lblIngreseApellido.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseApellido = new GridBagConstraints();
        gbc_lblIngreseApellido.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseApellido.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseApellido.gridx = 0;
        gbc_lblIngreseApellido.gridy = 3;
        getContentPane().add(lblIngreseApellido, gbc_lblIngreseApellido);
        
        textFieldApellido = new JTextField();
        GridBagConstraints gbc_textFieldApellido= new GridBagConstraints();
        gbc_textFieldApellido.gridwidth = 2;
        gbc_textFieldApellido.fill = GridBagConstraints.BOTH;
        gbc_textFieldApellido.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldApellido.gridx = 1;
        gbc_textFieldApellido.gridy = 3;
        getContentPane().add(textFieldApellido, gbc_textFieldApellido);
        textFieldNick.setColumns(10);
        
        //Correo
        
        lblIngreseCorreo= new JLabel("Correo: ");
        lblIngreseCorreo.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseCorreo = new GridBagConstraints();
        gbc_lblIngreseCorreo.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseCorreo.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseCorreo.gridx = 0;
        gbc_lblIngreseCorreo.gridy = 4;
        getContentPane().add(lblIngreseCorreo, gbc_lblIngreseCorreo);
        
        
        textFieldCorreo = new JTextField();
        GridBagConstraints gbc_textFieldCorreo = new GridBagConstraints();
        gbc_textFieldCorreo.gridwidth = 2;
        gbc_textFieldCorreo.fill = GridBagConstraints.BOTH;
        gbc_textFieldCorreo.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldCorreo.gridx = 1;
        gbc_textFieldCorreo.gridy = 4;
        getContentPane().add(textFieldCorreo, gbc_textFieldCorreo);
        textFieldCorreo.setColumns(10);
        
        //FechaNacimiento
        
        lblIngreseFechaNacimiento = new JLabel("Fecha de Nacimiento: ");
        lblIngreseFechaNacimiento.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseFechaNacimiento = new GridBagConstraints();
        gbc_lblIngreseFechaNacimiento.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseFechaNacimiento.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseFechaNacimiento.gridx = 0;
        gbc_lblIngreseFechaNacimiento.gridy = 5;
        getContentPane().add(lblIngreseFechaNacimiento, gbc_lblIngreseFechaNacimiento);
        
        textFieldFechaNacimiento = new JTextField();
        GridBagConstraints gbc_textFieldFechaNacimiento = new GridBagConstraints();
        gbc_textFieldFechaNacimiento.gridwidth = 2;
        gbc_textFieldFechaNacimiento.fill = GridBagConstraints.BOTH;
        gbc_textFieldFechaNacimiento.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldFechaNacimiento.gridx = 1;
        gbc_textFieldFechaNacimiento.gridy = 5;
        getContentPane().add(textFieldFechaNacimiento , gbc_textFieldFechaNacimiento);
        textFieldFechaNacimiento.setColumns(10);
        
        
        //CI
        
        lblIngreseCi = new JLabel("C.I.: ");
        lblIngreseCi.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseCi = new GridBagConstraints();
        gbc_lblIngreseCi.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseCi.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseCi.gridx = 0;
        gbc_lblIngreseCi.gridy = 6;
        getContentPane().add(lblIngreseCi, gbc_lblIngreseCi);
        
        textFieldCI = new JTextField();
        GridBagConstraints gbc_textFieldCI = new GridBagConstraints();
        gbc_textFieldCI.gridwidth = 2;
        gbc_textFieldCI.fill = GridBagConstraints.BOTH;
        gbc_textFieldCI.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldCI.gridx = 1;
        gbc_textFieldCI.gridy = 6;
        getContentPane().add(textFieldCI, gbc_textFieldCI);
        textFieldCI.setColumns(10);
        
        //Antecedentes
        
        lblIngreseAntecedentes= new JLabel("Antecedentes: ");
        lblIngreseAntecedentes.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseAntecedentes= new GridBagConstraints();
        gbc_lblIngreseAntecedentes.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseAntecedentes.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseAntecedentes.gridx = 0;
        gbc_lblIngreseAntecedentes.gridy = 7;
        getContentPane().add(lblIngreseAntecedentes, gbc_lblIngreseAntecedentes);
        
        textFieldAntecedentes= new JTextField();
        GridBagConstraints gbc_textFieldAntecedentes= new GridBagConstraints();
        gbc_textFieldAntecedentes.gridwidth = 2;
        gbc_textFieldAntecedentes.fill = GridBagConstraints.BOTH;
        gbc_textFieldAntecedentes.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldAntecedentes.gridx = 1;
        gbc_textFieldAntecedentes.gridy = 7;
        getContentPane().add(textFieldAntecedentes, gbc_textFieldAntecedentes);
        textFieldAntecedentes.setColumns(10);
        
        //FechaIngerso
        
        lblIngreseFechaIngreso = new JLabel("Fecha de Ingreso: ");
        lblIngreseFechaIngreso.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseFechaIngreso = new GridBagConstraints();
        gbc_lblIngreseFechaIngreso.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseFechaIngreso.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseFechaIngreso.gridx = 0;
        gbc_lblIngreseFechaIngreso.gridy = 8;
        getContentPane().add(lblIngreseFechaIngreso, gbc_lblIngreseFechaIngreso);
        
        textFieldFechaIngreso = new JTextField();
        GridBagConstraints gbc_textFieldFechaIngreso = new GridBagConstraints();
        gbc_textFieldFechaIngreso.gridwidth = 2;
        gbc_textFieldFechaIngreso.fill = GridBagConstraints.BOTH;
        gbc_textFieldFechaIngreso.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldFechaIngreso.gridx = 1;
        gbc_textFieldFechaIngreso.gridy = 8;
        getContentPane().add(textFieldFechaIngreso , gbc_textFieldFechaIngreso);
        textFieldFechaIngreso.setColumns(10);
        
     // Contraseña

        lblContrasena = new JLabel("Contraseña: ");
        lblContrasena.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblContrasena = new GridBagConstraints();
        gbc_lblContrasena.fill = GridBagConstraints.BOTH;
        gbc_lblContrasena.insets = new Insets(0, 0, 5, 5);
        gbc_lblContrasena.gridx = 0;
        gbc_lblContrasena.gridy = 9;
        getContentPane().add(lblContrasena, gbc_lblContrasena);
        
        textFieldContrasena = new JPasswordField();
        GridBagConstraints gbc_textFieldContrasena = new GridBagConstraints();
        gbc_textFieldContrasena.gridwidth = 2;
        gbc_textFieldContrasena.fill = GridBagConstraints.BOTH;
        gbc_textFieldContrasena.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldContrasena.gridx = 1;
        gbc_textFieldContrasena.gridy = 9;
        getContentPane().add(textFieldContrasena, gbc_textFieldContrasena);
        textFieldContrasena.setColumns(10);


        // Confirmar contraseña

        lblConfirmarContrasena = new JLabel("Confirmar Contraseña: ");
        lblConfirmarContrasena.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblConfirmarContrasena = new GridBagConstraints();
        gbc_lblConfirmarContrasena.fill = GridBagConstraints.BOTH;
        gbc_lblConfirmarContrasena.insets = new Insets(0, 0, 5, 5);
        gbc_lblConfirmarContrasena.gridx = 0;
        gbc_lblConfirmarContrasena.gridy = 10;
        getContentPane().add(lblConfirmarContrasena, gbc_lblConfirmarContrasena);

        textFieldConfirmarContrasena = new JPasswordField();
        GridBagConstraints gbc_textFieldConfirmarContrasena = new GridBagConstraints();
        gbc_textFieldConfirmarContrasena.gridwidth = 2;
        gbc_textFieldConfirmarContrasena.fill = GridBagConstraints.BOTH;
        gbc_textFieldConfirmarContrasena.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldConfirmarContrasena.gridx = 1;
        gbc_textFieldConfirmarContrasena.gridy = 10;
        getContentPane().add(textFieldConfirmarContrasena, gbc_textFieldConfirmarContrasena);
        textFieldConfirmarContrasena.setColumns(10);
        
        //Foto
        
        lblFotoPerfil = new JLabel("* Foto de Perfil:");
        lblFotoPerfil.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
        gbc_lblFotoPerfil.fill = GridBagConstraints.BOTH;
        gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
        gbc_lblFotoPerfil.gridx = 0;
        gbc_lblFotoPerfil.gridy = 11;
        getContentPane().add(lblFotoPerfil, gbc_lblFotoPerfil);

        textFieldFoto = new JTextField();
        textFieldFoto.setEditable(false);
        GridBagConstraints gbc_textFieldFoto = new GridBagConstraints();
        gbc_textFieldFoto.fill = GridBagConstraints.BOTH;
        gbc_textFieldFoto.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldFoto.gridx = 1;
        gbc_textFieldFoto.gridy = 11;
        getContentPane().add(textFieldFoto, gbc_textFieldFoto);

        btnSeleccionarFoto = new JButton("Seleccionar...");
        btnSeleccionarFoto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seleccionarFoto();
            }
        });
        GridBagConstraints gbc_btnSeleccionarFoto = new GridBagConstraints();
        gbc_btnSeleccionarFoto.fill = GridBagConstraints.BOTH;
        gbc_btnSeleccionarFoto.insets = new Insets(0, 0, 5, 0);
        gbc_btnSeleccionarFoto.gridx = 2;
        gbc_btnSeleccionarFoto.gridy = 11;
        getContentPane().add(btnSeleccionarFoto, gbc_btnSeleccionarFoto);
        
        
        btnAceptar = new JButton("Aceptar");
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.fill = GridBagConstraints.BOTH;
        gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
        gbc_btnAceptar.gridx = 1;
        gbc_btnAceptar.gridy = 13;
        getContentPane().add(btnAceptar, gbc_btnAceptar);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdRegistroPacienteActionPerformed(arg0);
            }
        });
        
        
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                setVisible(false);
            }
        });
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.fill = GridBagConstraints.BOTH;
        gbc_btnCancelar.insets = new Insets(0,0,5,5);
        gbc_btnCancelar.gridx = 2;
        gbc_btnCancelar.gridy = 13;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        
    	}
}
