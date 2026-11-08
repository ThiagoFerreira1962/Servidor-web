package presentacion;

import javax.swing.JInternalFrame;

import excepciones.UsuarioRepetidoException;
import logica.IControladorUsuario;
import logica.IControladorEspecialidad;
import datatypes.Mes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class RegistrarMedico extends JInternalFrame{
	
	private File archivoFoto;
	private static final String CARPETA_FOTOS = "src/img";
	private static final String fotoDefault = "defaultMedico.jpg";
	
	private IControladorUsuario controlUsr;
	private IControladorEspecialidad controlEsp;
	private JTextField textFieldNick;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldCorreo;
    private JPasswordField textFieldContrasena;
    private JTextField textFieldNroRegistro;
    private JPasswordField textFieldConfirmarContrasena;
    private JComboBox<Mes>  comboBoxMesLicencia;
    private JTextField textFieldWeb;
    private JPanel panelEspecialidades;
    private List<JCheckBox> checkBoxEspecialidades;
    private JTextField textFieldPorcentajeAdicional;
    private JTextField textFieldFoto;
    
    private JLabel lblFotoPerfil;
    private JLabel lblIngreseNroRegistro;
    private JLabel lblIngreseMesLicencia;
    private JLabel lblIngreseWeb;
    private JLabel lblIngreseEspecialidades;
    private	JLabel lblIngreseProcentajeAdicional;
    private JLabel lblOpcional;
    private JLabel lblIngreseTitulo;
    private JLabel lblIngreseNick;
    private JLabel lblIngreseNombre;
    private JLabel lblIngreseApellido;
    private JLabel lblIngreseCorreo;
    private JLabel lblContrasena;
    private JLabel lblConfirmarContrasena;
    private JButton btnSeleccionarFoto;
    private JButton btnAceptar;
    private JButton btnCancelar;

    	protected void cmdRegistroMedicoActionPerformed(ActionEvent arg0) {
    	
    	if (!checkFormulario()) {
    		return;
    	}
        // Obtengo datos de los controles Swing
    	float porAdicional;
    	String nickname = this.textFieldNick.getText();
        String nombre = this.textFieldNombre.getText();
        String apellido = this.textFieldApellido.getText();
        String correo = this.textFieldCorreo.getText();
        String contrasena= new String (this.textFieldContrasena.getPassword());
        String confirmacionContrasena = new String (this.textFieldConfirmarContrasena.getPassword());
        String nroRegistro = this.textFieldNroRegistro.getText();
        Mes mesLicencia = (Mes)this.comboBoxMesLicencia.getSelectedItem();
        String sitioWeb = this.textFieldWeb.getText();
        List<String> especialidades = getEspecialidadesSeleccionadas();
        
        //Verificar y guardar porcentajeAdicional
        try {
        porAdicional = Float.parseFloat(this.textFieldPorcentajeAdicional.getText());
		if(porAdicional < 0) {
			JOptionPane.showMessageDialog(null,"El porcentaje adicional debe ser mayor o igual a cero.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
        } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "El porcentaje adicional debe ser un número.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        return;
        }
        
        boolean contrasenaValida = contrasena.equals(confirmacionContrasena);

        if (!contrasenaValida) {
            JOptionPane.showMessageDialog(
                RegistrarMedico.this,
                "Las contraseñas no coinciden.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        String nombreFoto = fotoDefault;
        
        if (archivoFoto != null) {
	        try {
	        nombreFoto = copiarFoto (nickname);}
	        catch (IOException e) {
	        	JOptionPane.showMessageDialog(null,"Error al subir la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
	        }
        }

        //Verificar y crear el Medico
        try {
        	controlUsr.altaMedico(nickname, nombre, apellido, correo, nroRegistro, mesLicencia, porAdicional, 
        			sitioWeb, especialidades,contrasena,nombreFoto);
        	// Muestro éxito de la operación
        	JOptionPane.showMessageDialog(this, "El Usuario se ha creado con éxito", "Alta Usuario",JOptionPane.INFORMATION_MESSAGE);
        	limpiarFormulario();
        	setVisible(false);
        } catch (UsuarioRepetidoException e) {
        	// Muestro error de registro
        	JOptionPane.showMessageDialog(this, e.getMessage(), "Alta Usuario", JOptionPane.ERROR_MESSAGE);
        }
     
        }
    
    
    	protected boolean checkFormulario() {
    	
    	String nickname = this.textFieldNick.getText();
        String nombre = this.textFieldNombre.getText();
        String apellido = this.textFieldApellido.getText();
        String correo = this.textFieldCorreo.getText();
        String nroRegistro = this.textFieldNroRegistro.getText();
        Mes mesLicencia = (Mes)this.comboBoxMesLicencia.getSelectedItem();
        List<String> especialidades = this.getEspecialidadesSeleccionadas();
        String porAdicional = this.textFieldPorcentajeAdicional.getText();
        char [] contrasena = this.textFieldContrasena.getPassword();
        char []	conContrasena = this.textFieldConfirmarContrasena.getPassword();
        
        if (nickname.isBlank() || nombre.isBlank() || apellido.isBlank() 
        	|| correo.isBlank() || nroRegistro.isBlank() || especialidades.isEmpty() 
        	|| porAdicional.isBlank() || mesLicencia == null || contrasena.length == 0
        	|| conContrasena.length == 0) {
        	JOptionPane.showMessageDialog(this, "No puede haber campos obligatiorios vacíos.", "Alta Medico",
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
    	    if (archivoFoto == null) {
    	        return null; // la foto es opcional
    	    }
    	    String nombre = archivoFoto.getName();
    	    int punto = nombre.lastIndexOf('.');
    	    String extension = (punto >= 0) ? nombre.substring(punto) : "";

    	    Path carpeta = Paths.get(CARPETA_FOTOS);
    	    Files.createDirectories(carpeta); // crea "img" si no existe

    	    String nombreFinal = nickname + extension;
    	    Files.copy(archivoFoto.toPath(), carpeta.resolve(nombreFinal),
    	               StandardCopyOption.REPLACE_EXISTING);
    	    return nombreFinal;
    	}
    	
    	
    	
        private void limpiarFormulario() {
        textFieldNick.setText("");	
        textFieldNombre.setText("");
        textFieldApellido.setText("");
        textFieldCorreo.setText("");
        textFieldNroRegistro.setText("");
        comboBoxMesLicencia.setSelectedItem(null);
        textFieldWeb.setText("");
        textFieldPorcentajeAdicional.setText("");
        for (JCheckBox check : checkBoxEspecialidades) {
        	check.setSelected(false);
        }
        textFieldContrasena.setText("");
        textFieldConfirmarContrasena.setText("");
        textFieldFoto.setText("");
        archivoFoto = null;
    }
    
    public RegistrarMedico(IControladorUsuario icu, IControladorEspecialidad ice) {
        // Se inicializa con el controlador de usuarios
        controlUsr = icu;
        controlEsp = ice;
        

        // Propiedades del JInternalFrame como dimensión, posición dentro del frame,
        // etc.
        setResizable(false);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Alta de Médico");
        setBounds(10, 40, 550,600);
       
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 100, 120, 120, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 60, 30, 30, 30 };
        gridBagLayout.columnWeights = new double[] { 0.5, 0.5, 0.5, 0.5, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);
        
        lblIngreseTitulo = new JLabel("Datos del Médico:");
        lblIngreseTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblIngreseTitulo = new GridBagConstraints();
        gbc_lblIngreseTitulo.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseTitulo.insets = new Insets(10, 10, 20, 10);
        gbc_lblIngreseTitulo.gridwidth = 6;
        gbc_lblIngreseTitulo.gridx = 0;
        gbc_lblIngreseTitulo.gridy = 0;
        getContentPane().add(lblIngreseTitulo, gbc_lblIngreseTitulo);
        
        //Nick
        
        lblIngreseNick = new JLabel("Nickname:");
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
        
        lblIngreseNombre = new JLabel("Nombre:");
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
        
        lblIngreseApellido = new JLabel("Apellido:");
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
        
        lblIngreseCorreo= new JLabel("Correo:");
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
        
        //numero registro
        
        lblIngreseNroRegistro= new JLabel("Nro. de Registro:");
        lblIngreseNroRegistro.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseNroRegistro= new GridBagConstraints();
        gbc_lblIngreseNroRegistro.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseNroRegistro.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseNroRegistro.gridx = 0;
        gbc_lblIngreseNroRegistro.gridy = 5;
        getContentPane().add(lblIngreseNroRegistro, gbc_lblIngreseNroRegistro);
        
        
        textFieldNroRegistro = new JTextField();
        GridBagConstraints gbc_textFieldNroRegistro = new GridBagConstraints();
        gbc_textFieldNroRegistro.gridwidth = 2;
        gbc_textFieldNroRegistro.fill = GridBagConstraints.BOTH;
        gbc_textFieldNroRegistro.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNroRegistro.gridx = 1;
        gbc_textFieldNroRegistro.gridy = 5;
        getContentPane().add(textFieldNroRegistro, gbc_textFieldNroRegistro);
        textFieldCorreo.setColumns(10);
        
        //mes licencia
        
        lblIngreseMesLicencia= new JLabel("Mes de Licencia:");
        lblIngreseMesLicencia.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseMesLicencia= new GridBagConstraints();
        gbc_lblIngreseMesLicencia.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseMesLicencia.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseMesLicencia.gridx = 0;
        gbc_lblIngreseMesLicencia.gridy = 6;
        getContentPane().add(lblIngreseMesLicencia, gbc_lblIngreseMesLicencia);

        
        
        
        comboBoxMesLicencia = new JComboBox <> (Mes.values());
        GridBagConstraints gbc_comboBoxMesLicencia = new GridBagConstraints();
        gbc_comboBoxMesLicencia.gridwidth = 2;
        gbc_comboBoxMesLicencia.fill = GridBagConstraints.BOTH;
        gbc_comboBoxMesLicencia.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxMesLicencia.gridx = 1;
        gbc_comboBoxMesLicencia.gridy = 6;
        getContentPane().add(comboBoxMesLicencia, gbc_comboBoxMesLicencia);
        textFieldCorreo.setColumns(10);
        comboBoxMesLicencia.setSelectedIndex(-1);
        
        //lblIngres
        
        lblIngreseWeb= new JLabel("* Sitio web:");
        lblIngreseWeb.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseWeb= new GridBagConstraints();
        gbc_lblIngreseWeb.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseWeb.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseWeb.gridx = 0;
        gbc_lblIngreseWeb.gridy = 7;
        getContentPane().add(lblIngreseWeb, gbc_lblIngreseWeb);
        
        
        textFieldWeb = new JTextField();
        GridBagConstraints gbc_textFieldWeb= new GridBagConstraints();
        gbc_textFieldWeb.gridwidth = 2;
        gbc_textFieldWeb.fill = GridBagConstraints.BOTH;
        gbc_textFieldWeb.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldWeb.gridx = 1;
        gbc_textFieldWeb.gridy = 7;
        getContentPane().add(textFieldWeb, gbc_textFieldWeb);
        textFieldCorreo.setColumns(10);
        
        //Especialidades
        
        
        lblIngreseEspecialidades = new JLabel("Especialidades:");
        lblIngreseEspecialidades.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseEspecialidades= new GridBagConstraints();
        gbc_lblIngreseEspecialidades.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseEspecialidades.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseEspecialidades.gridx = 0;
        gbc_lblIngreseEspecialidades.gridy = 8;
        getContentPane().add(lblIngreseEspecialidades, gbc_lblIngreseEspecialidades);
        
        
        panelEspecialidades = new JPanel();
        panelEspecialidades.setLayout(new BoxLayout(panelEspecialidades, BoxLayout.Y_AXIS));
        checkBoxEspecialidades = new ArrayList<JCheckBox>();
        
        JScrollPane scrollEspecialidades = new JScrollPane(panelEspecialidades);
        GridBagConstraints gbc_textFieldEspecialidades= new GridBagConstraints();
        gbc_textFieldEspecialidades.gridwidth = 2;
        gbc_textFieldEspecialidades.fill = GridBagConstraints.BOTH;
        gbc_textFieldEspecialidades.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldEspecialidades.gridx = 1;
        gbc_textFieldEspecialidades.gridy = 8;
        getContentPane().add(scrollEspecialidades, gbc_textFieldEspecialidades);
        textFieldCorreo.setColumns(10);
        

        
        //Porcentaje Adicional

        
        lblIngreseProcentajeAdicional = new JLabel("Porcentaje Adicional:");
        lblIngreseProcentajeAdicional.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseProcentajeAdicional= new GridBagConstraints();
        gbc_lblIngreseProcentajeAdicional.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseProcentajeAdicional.insets = new Insets(0, 0, 5, 5);
        gbc_lblIngreseProcentajeAdicional.gridx = 0;
        gbc_lblIngreseProcentajeAdicional.gridy = 9;
        getContentPane().add(lblIngreseProcentajeAdicional, gbc_lblIngreseProcentajeAdicional);
        
        
        textFieldPorcentajeAdicional = new JTextField();
        GridBagConstraints gbc_textFieldPorcentajeAdicional = new GridBagConstraints();
        gbc_textFieldPorcentajeAdicional.gridwidth = 2;
        gbc_textFieldPorcentajeAdicional.fill = GridBagConstraints.BOTH;
        gbc_textFieldPorcentajeAdicional.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldPorcentajeAdicional.gridx = 1;
        gbc_textFieldPorcentajeAdicional.gridy = 9;
        getContentPane().add(textFieldPorcentajeAdicional, gbc_textFieldPorcentajeAdicional);
        textFieldCorreo.setColumns(10);
        
     // Contraseña

        lblContrasena = new JLabel("Contraseña: ");
        lblContrasena.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblContrasena = new GridBagConstraints();
        gbc_lblContrasena.fill = GridBagConstraints.BOTH;
        gbc_lblContrasena.insets = new Insets(0, 0, 5, 5);
        gbc_lblContrasena.gridx = 0;
        gbc_lblContrasena.gridy = 10;
        getContentPane().add(lblContrasena, gbc_lblContrasena);
        
        textFieldContrasena = new JPasswordField();
        GridBagConstraints gbc_textFieldContrasena = new GridBagConstraints();
        gbc_textFieldContrasena.gridwidth = 2;
        gbc_textFieldContrasena.fill = GridBagConstraints.BOTH;
        gbc_textFieldContrasena.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldContrasena.gridx = 1;
        gbc_textFieldContrasena.gridy = 10;
        getContentPane().add(textFieldContrasena, gbc_textFieldContrasena);
        textFieldContrasena.setColumns(10);


        // Confirmar contraseña

        lblConfirmarContrasena = new JLabel("Confirmar Contraseña: ");
        lblConfirmarContrasena.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblConfirmarContrasena = new GridBagConstraints();
        gbc_lblConfirmarContrasena.fill = GridBagConstraints.BOTH;
        gbc_lblConfirmarContrasena.insets = new Insets(0, 0, 5, 5);
        gbc_lblConfirmarContrasena.gridx = 0;
        gbc_lblConfirmarContrasena.gridy = 11;
        getContentPane().add(lblConfirmarContrasena, gbc_lblConfirmarContrasena);

        textFieldConfirmarContrasena = new JPasswordField();
        GridBagConstraints gbc_textFieldConfirmarContrasena = new GridBagConstraints();
        gbc_textFieldConfirmarContrasena.gridwidth = 2;
        gbc_textFieldConfirmarContrasena.fill = GridBagConstraints.BOTH;
        gbc_textFieldConfirmarContrasena.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldConfirmarContrasena.gridx = 1;
        gbc_textFieldConfirmarContrasena.gridy = 11;
        getContentPane().add(textFieldConfirmarContrasena, gbc_textFieldConfirmarContrasena);
        textFieldConfirmarContrasena.setColumns(10);
        
        //
        
        lblFotoPerfil = new JLabel("* Foto de Perfil:");
        lblFotoPerfil.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblFotoPerfil = new GridBagConstraints();
        gbc_lblFotoPerfil.fill = GridBagConstraints.BOTH;
        gbc_lblFotoPerfil.insets = new Insets(0, 0, 5, 5);
        gbc_lblFotoPerfil.gridx = 0;
        gbc_lblFotoPerfil.gridy = 12;
        getContentPane().add(lblFotoPerfil, gbc_lblFotoPerfil);

        textFieldFoto = new JTextField();
        textFieldFoto.setEditable(false);
        GridBagConstraints gbc_textFieldFoto = new GridBagConstraints();
        gbc_textFieldFoto.fill = GridBagConstraints.BOTH;
        gbc_textFieldFoto.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldFoto.gridx = 1;
        gbc_textFieldFoto.gridy = 12;
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
        gbc_btnSeleccionarFoto.gridy = 12;
        getContentPane().add(btnSeleccionarFoto, gbc_btnSeleccionarFoto);
        
        //opcional
        
    	lblOpcional = new JLabel("(*) opcional");
    	lblOpcional.setHorizontalAlignment(SwingConstants.CENTER);
    	GridBagConstraints gbc_lblOpcional= new GridBagConstraints();
    	gbc_lblOpcional.fill = GridBagConstraints.BOTH;
    	gbc_lblOpcional.insets = new Insets(10, 10, 20, 10);
    	gbc_lblOpcional.gridwidth = 2; 
    	gbc_lblOpcional.gridx = 1;
    	gbc_lblOpcional.gridy = 13;
    	getContentPane().add(lblOpcional, gbc_lblOpcional);
    	
    	
    	//botones
    	
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdRegistroMedicoActionPerformed(arg0);
            }
        });
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.fill = GridBagConstraints.BOTH;
        gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
        gbc_btnAceptar.gridx = 1;
        gbc_btnAceptar.gridy = 14;
        getContentPane().add(btnAceptar, gbc_btnAceptar);
      

        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
                setVisible(false);
            }
        });
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.fill = GridBagConstraints.BOTH;
        gbc_btnCancelar.gridx = 2;
        gbc_btnCancelar.gridy = 14;
        getContentPane().add(btnCancelar, gbc_btnCancelar);
        
        
    }
    
    public void cargarEspecialidades() {
    	panelEspecialidades.removeAll();
    	checkBoxEspecialidades.clear();
    	for (String nombre : controlEsp.getNombreEspecialidades()) {
    		JCheckBox check = new JCheckBox(nombre);
    		checkBoxEspecialidades.add(check);
    		panelEspecialidades.add(check);
    	}
    	panelEspecialidades.revalidate();
    	panelEspecialidades.repaint();
    }
    
    public List<String> getEspecialidadesSeleccionadas() {
    	List<String> res = new ArrayList<String>();
    	for (JCheckBox check : checkBoxEspecialidades) {
    		if (check.isSelected()) {
    			res.add(check.getText());
    		}
    	}
    	return res;
    }
}
