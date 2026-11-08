package presentacion;

import javax.swing.JInternalFrame;



import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


import datatypes.DiaSemana;

import logica.IControladorDiaConsulta;
import logica.IControladorClinica;
import logica.IControladorEspecialidad;

import java.time.LocalTime;
import javax.swing.JOptionPane; //para ventanas emergentes cuando falla algo

import excepciones.DiaConsultaRepetidoException;

@SuppressWarnings("serial")
public class AltaDiaConsulta extends JInternalFrame  { //se comporta como una ventana q se puede abrir dentro de la ventana principal
	private IControladorDiaConsulta ICDC;
	private IControladorClinica ICC;
	private IControladorEspecialidad ICE;
	
    private JLabel lblNombre;
    private JLabel lblDescripcion;
    private JLabel lblDia;
    private JLabel lblHoraInicio;
    private JLabel lblEspecialidad;
    private JLabel lblClinica;
    private JLabel lblImagen;

    private JTextField textFieldNombre;
    private JTextField textFieldDescripcion;
    private JTextField textFieldHoraInicio;
    private JTextField textFieldImagen;
    
    private JComboBox<DiaSemana> comboDia;
    private JComboBox<String> comboClinica;
    private JComboBox<String> comboEspecialidad;
	
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JButton btnSeleccionarImagen;
    
    private File archivoImagen;
    private static final String CARPETA_FOTOS = "src/img";
    private static final String IMAGEN_DEFAULT = "defaultDiaConsulta.jpg";
    
    //actualizo clinicas q hayan sido dadas de alta durante la ejecucion del programa
    public void cargarClinicas() {
    	comboClinica.removeAllItems();
    	for(String nombre : ICC.listarNombreClinicas()) {
    		comboClinica.addItem(nombre); //additem para agregar una opcion en la combobox
    	}
    	comboClinica.setSelectedIndex(-1);
    }
    
    public void cargarEspecialidades() {
    	comboEspecialidad.removeAllItems();
    	for(String nombre : ICE.getNombreEspecialidades()) {
    		comboEspecialidad.addItem(nombre);
    	}
    	comboEspecialidad.setSelectedIndex(-1);
    }
    
    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (jpg, jpeg, png)", "jpg", "jpeg", "png"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            archivoImagen = chooser.getSelectedFile();
            textFieldImagen.setText(archivoImagen.getName());
        }
    }
    
    private String copiarImagen(String nombreDia) throws IOException {
        String nombreOriginal = archivoImagen.getName();
        int punto = nombreOriginal.lastIndexOf('.');
        String extension = (punto >= 0) ? nombreOriginal.substring(punto) : "";

        Path carpeta = Paths.get(CARPETA_FOTOS);
        Files.createDirectories(carpeta);

        String nombreFinal = nombreDia.replaceAll("[^a-zA-Z0-9]", "_") + extension;
        Files.copy(archivoImagen.toPath(), carpeta.resolve(nombreFinal), StandardCopyOption.REPLACE_EXISTING);
        return nombreFinal;
    }
    
	public AltaDiaConsulta(IControladorDiaConsulta icdc, IControladorClinica icc, IControladorEspecialidad ice) {
		this.ICDC=icdc;
		this.ICC=icc;
		this.ICE=ice;
		
        setTitle("Alta de Día Consulta");
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setBounds(10, 40, 430, 390);
        getContentPane().setLayout(null);
        
        //Nombre:
        lblNombre= new JLabel("Nombre:");
        lblNombre.setBounds(20, 25, 120, 20);
        getContentPane().add(lblNombre);
        
        textFieldNombre= new JTextField();
        textFieldNombre.setBounds(150, 25, 240, 23);
        getContentPane().add(textFieldNombre);
        
        //Descripcion:
        lblDescripcion= new JLabel("Descripción:");
        lblDescripcion.setBounds(20, 65, 120, 20);
        getContentPane().add(lblDescripcion);
        
        textFieldDescripcion= new JTextField();
        textFieldDescripcion.setBounds(150, 65, 240, 23);
        getContentPane().add(textFieldDescripcion);
        
        //Dia:
        lblDia= new JLabel("Día:");
        lblDia.setBounds(20, 105, 120, 20);
        getContentPane().add(lblDia);
        
        comboDia= new JComboBox<DiaSemana>(DiaSemana.values());
        comboDia.setSelectedIndex(-1);  //para q no aparezca el primer valor al inicializar (lunes)
        comboDia.setBounds(150, 105, 240, 23);
        getContentPane().add(comboDia);
       
        //Hora Inicio:
        lblHoraInicio= new JLabel ("Hora de inicio:");
        lblHoraInicio.setBounds(20, 145, 120, 20);
        getContentPane().add(lblHoraInicio);
        
        textFieldHoraInicio= new JTextField();
        textFieldHoraInicio.setBounds(150,145,240,23);
        getContentPane().add(textFieldHoraInicio);
        
        //Especialidad
        lblEspecialidad= new JLabel("Especialidad:");
        lblEspecialidad.setBounds(20,185,120,20);
        getContentPane().add(lblEspecialidad);
        
        comboEspecialidad= new JComboBox<String>();
        comboEspecialidad.setBounds(150,185,240,23);
        getContentPane().add(comboEspecialidad);
        
        
        //Clinica:
        lblClinica = new JLabel("Clínica:");
        lblClinica.setBounds(20,225,120,20);
        getContentPane().add(lblClinica);
        
        comboClinica= new JComboBox<String>();
        comboClinica.setBounds(150,225,240,23);
        getContentPane().add(comboClinica);
        
        //Imagen:
        lblImagen = new JLabel("* Imagen:");
        lblImagen.setBounds(20, 265, 120, 20);
        getContentPane().add(lblImagen);

        textFieldImagen = new JTextField();
        textFieldImagen.setEditable(false);
        textFieldImagen.setBounds(150, 265, 130, 23);
        getContentPane().add(textFieldImagen);

        btnSeleccionarImagen = new JButton("Seleccionar...");
        btnSeleccionarImagen.setBounds(285, 265, 105, 23);
        btnSeleccionarImagen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
            }
        });
        getContentPane().add(btnSeleccionarImagen);

        
        
        //Aceptar:
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(150, 305, 100, 23);
        
        btnAceptar.addActionListener(new ActionListener(){
        	  public void actionPerformed(ActionEvent e) {
        		  //obtengo los datos:
        		  String nombre= textFieldNombre.getText();
        		  String descripcion= textFieldDescripcion.getText();
        		  DiaSemana dia= (DiaSemana) comboDia.getSelectedItem();
        		  String horaInicio= textFieldHoraInicio.getText();
        		  String especialidad= (String) comboEspecialidad.getSelectedItem();
        		  String clinica= (String) comboClinica.getSelectedItem();
        		  //verifico que no falten datos:
        		  if(nombre.isEmpty()||descripcion.isEmpty()||horaInicio.isEmpty()||dia==null||especialidad==null||clinica==null) {
        			  JOptionPane.showMessageDialog(AltaDiaConsulta.this , "Debe completar todos los campos"); 
        			  return;
        		  }
        		  //convertir la hora:
        		  LocalTime hora;
        	        try {
        	        	hora = LocalTime.parse(textFieldHoraInicio.getText());
        	        } 
        	        catch (Exception ex) {
        	        	JOptionPane.showMessageDialog(AltaDiaConsulta.this , "La hora debe tener formato HH:mm",
        	        			"Error",JOptionPane.ERROR_MESSAGE);
        	            return;
        	        }
        	        
        	        String nombreArchivo = IMAGEN_DEFAULT;
        	        if (archivoImagen != null) {
        	            try {
        	                nombreArchivo = copiarImagen(nombre);
        	            } catch (IOException ex) {
        	                JOptionPane.showMessageDialog(AltaDiaConsulta.this, "Error al copiar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
        	                return;
        	            }
        	        }
        	        try {
        	        	ICDC.altaDiaConsulta(nombre, descripcion, dia, hora, clinica, especialidad, nombreArchivo);
        	        	JOptionPane.showMessageDialog(AltaDiaConsulta.this, "El Día Consulta fue registrado con éxito",
        	        			"Alta Dia Consulta",JOptionPane.INFORMATION_MESSAGE);
        	        	limpiarCampos();
        	        	setVisible(false);
        	        	
        	        }
        	        catch(DiaConsultaRepetidoException  ex){
        	        	JOptionPane.showMessageDialog(AltaDiaConsulta.this, "Ya existe un Día Consulta con ese nombre",
        	        			"Error",JOptionPane.ERROR_MESSAGE);
        	        	
        	        }
        	  }
        	
        });
        getContentPane().add(btnAceptar);
        
        
        //Cancelar:
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(285, 305, 100, 23);
        
        btnCancelar.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		limpiarCampos();
        		setVisible(false);
        		
        	}
        });
        getContentPane().add(btnCancelar);
	}
	
	//Limpiar los campos de texto
	public void limpiarCampos() {
		textFieldNombre.setText("");
		textFieldDescripcion.setText("");
		textFieldHoraInicio.setText("");
		comboDia.setSelectedIndex(-1);
		comboEspecialidad.setSelectedIndex(-1);
		comboClinica.setSelectedIndex(-1);
		archivoImagen = null;
		textFieldImagen.setText("");
	}
	

}
