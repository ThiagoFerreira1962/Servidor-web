package presentacion;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import logica.IControladorDiaConsulta;
import logica.IControladorUsuario;

import excepciones.ConsultaRepetidaException;
import excepciones.MedicoNoTieneEspecialidadException;
import excepciones.FechaNoCoincideException;

import datatypes.DTDiaConsulta;
import datatypes.DTUsuario;

@SuppressWarnings("serial")
public class AltaConsulta extends JInternalFrame {
	private IControladorDiaConsulta ICDC;
	private IControladorUsuario ICU;
	
	private JLabel lblDiasConsulta;
	private JLabel lblMedicos;
	private JLabel lblNombre;
	private JLabel lblFecha;
	private JLabel lblNumSala;
	private JLabel lblCantNums;
	private JLabel lblImagen;
	
	private JComboBox<String>diasConsulta;
	private JComboBox<String>medicos;
	
	private JTextField textFieldNombre;
	private JTextField textFieldFecha;
	private JTextField textFieldNumSala;
	private JTextField textFieldCantNums;
	private JTextField textFieldImagen;
	
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JButton btnSeleccionarImagen;
    
    private File archivoImagen;
    private static final String CARPETA_FOTOS = "src/img";
    private static final String IMAGEN_DEFAULT = "defaultConsulta.jpg";
    
    public void cargarDiasConsulta() {
    	diasConsulta.removeAllItems();
    	for(DTDiaConsulta ddc : ICDC.listarDiasConsultas()) {
       		diasConsulta.addItem(ddc.getNombre());
    	}
    }
    public void cargarMedicos() {
    	medicos.removeAllItems();
    	for(DTUsuario med : ICU.listarMedicos()) {
    		medicos.addItem(med.getNickname());
    	}
    }
    
    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (jpg, jpeg, png)", "jpg", "jpeg", "png"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            archivoImagen = chooser.getSelectedFile();
            textFieldImagen.setText(archivoImagen.getName());
        }
    }
    
    private String copiarImagen(String nombreConsulta) throws IOException {
        String nombreOriginal = archivoImagen.getName();
        int punto = nombreOriginal.lastIndexOf('.');
        String extension = (punto >= 0) ? nombreOriginal.substring(punto) : "";

        Path carpeta = Paths.get(CARPETA_FOTOS);
        Files.createDirectories(carpeta);

        String nombreFinal = nombreConsulta.replaceAll("[^a-zA-Z0-9]", "_") + extension;
        Files.copy(archivoImagen.toPath(), carpeta.resolve(nombreFinal), StandardCopyOption.REPLACE_EXISTING);
        return nombreFinal;
    }
	
	public AltaConsulta(IControladorDiaConsulta icdc, IControladorUsuario icu) {
		this.ICDC=icdc;
		this.ICU=icu;
		
        setTitle("Alta Consulta");
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setBounds(10, 40, 500, 395);
        getContentPane().setLayout(null);
        
        //DiasConsulta:
        lblDiasConsulta= new JLabel("Día Consulta:");
        lblDiasConsulta.setBounds(20, 25, 120, 20);
        getContentPane().add(lblDiasConsulta);
        
        diasConsulta= new JComboBox<String>();
        diasConsulta.setSelectedIndex(-1);
        diasConsulta.setBounds(200, 25, 240, 23);
        getContentPane().add(diasConsulta);
        
        //Medicos:
        lblMedicos= new JLabel("Médico:");
        lblMedicos.setBounds(20, 65, 120, 20);
        getContentPane().add(lblMedicos);
        
        medicos= new JComboBox<String>();
        medicos.setSelectedIndex(-1);
        medicos.setBounds(200, 65, 240, 23);
        getContentPane().add(medicos);
        
        //Nombre:
        lblNombre= new JLabel("Nombre:");
        lblNombre.setBounds(20, 105, 120, 20);
        getContentPane().add(lblNombre);
        
        textFieldNombre= new JTextField();
        textFieldNombre.setBounds(200, 105, 240, 23);
        getContentPane().add(textFieldNombre);
        
        //Fecha:
        lblFecha= new JLabel("Fecha :");
        lblFecha.setBounds(20, 145, 140, 20);
        getContentPane().add(lblFecha);
        
        textFieldFecha= new JTextField();
        textFieldFecha.setBounds(200,145,240,23);
        getContentPane().add(textFieldFecha);
        
        //NumSala:
        lblNumSala= new JLabel("Número de Sala:");
        lblNumSala.setBounds(20,185,120,20);
        getContentPane().add(lblNumSala);
        
        textFieldNumSala= new JTextField();
        textFieldNumSala.setBounds(200,185,240,23);
        getContentPane().add(textFieldNumSala);
        
        //CantNums:
        lblCantNums= new JLabel("Cantidad de Números:");
        lblCantNums.setBounds(20,225,160,20);
        getContentPane().add(lblCantNums);
        
        textFieldCantNums= new JTextField();
        textFieldCantNums.setBounds(200,225,240,23);
        getContentPane().add(textFieldCantNums);
        
        //Imagen:
        lblImagen = new JLabel("* Imagen:");
        lblImagen.setBounds(20, 265, 160, 20);
        getContentPane().add(lblImagen);

        textFieldImagen = new JTextField();
        textFieldImagen.setEditable(false);
        textFieldImagen.setBounds(200, 265, 130, 23);
        getContentPane().add(textFieldImagen);

        btnSeleccionarImagen = new JButton("Seleccionar...");
        btnSeleccionarImagen.setBounds(335, 265, 105, 23);
        btnSeleccionarImagen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
            }
        });
        getContentPane().add(btnSeleccionarImagen);
        
        //Aceptar:
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(210, 310, 100, 23);
        
        btnAceptar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//obtengo datos:
        		String diaConsulta= (String)diasConsulta.getSelectedItem();
        		String medico= (String)medicos.getSelectedItem();
        		String nombre= textFieldNombre.getText();
        		String textoFecha= textFieldFecha.getText();
        		String textoNumSala= textFieldNumSala.getText();
        		String textoCantNums= textFieldCantNums.getText();
        		
        		//reviso que no falte nada:
        		if(diaConsulta==null||medico==null||nombre.isEmpty() ||textoFecha.isEmpty()||textoNumSala.isEmpty()||textoCantNums.isEmpty()) {
        			JOptionPane.showMessageDialog(AltaConsulta.this, "Debe completar todos los campos");
        			return;
        		}
        	
        		
        		try {
               		//convierto los textos a int 
            		int numSala= Integer.parseInt(textoNumSala);
            		int cantNums= Integer.parseInt(textoCantNums);
            		
            		//convierto el texto de fecha a localDate
            		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
            		LocalDate fecha = LocalDate.parse(textoFecha, formato);
            		
            		if (numSala < 0 ) {
            			JOptionPane.showMessageDialog(AltaConsulta.this, "Número de sala debe ser mayor a igual a cero",
            					"Error", JOptionPane.WARNING_MESSAGE);
            			return;
            		}
            		
            		if (cantNums < 0) {
            			JOptionPane.showMessageDialog(AltaConsulta.this, "Cantidad de Núemeros debe ser mayor o igual a cero",
            					"Error", JOptionPane.WARNING_MESSAGE);
            			return;
            		}
            		
            		String nombreArchivo = IMAGEN_DEFAULT;
            		if (archivoImagen != null) {
            		    nombreArchivo = copiarImagen(nombre);
            		}
            		
        			ICDC.altaConsulta(nombre, fecha, numSala, cantNums, medico, diaConsulta, nombreArchivo);
        			JOptionPane.showMessageDialog(AltaConsulta.this,"La consulta se ha registrado con éxito",
        					"Alta Consulta",JOptionPane.INFORMATION_MESSAGE);
        			limpiarCampos();
        			setVisible(false);
        			
        		} catch(ConsultaRepetidaException ex){
        			JOptionPane.showMessageDialog(AltaConsulta.this, "Ya existe una consulta con ese nombre", 
        					"Error",JOptionPane.ERROR_MESSAGE);
        			
        		}
        			catch(DateTimeParseException ex){
        				JOptionPane.showMessageDialog(AltaConsulta.this, "La fecha ingresada no es válida", 
    					"Error",JOptionPane.ERROR_MESSAGE);
    			
    		}
        		catch(MedicoNoTieneEspecialidadException ex){
        			JOptionPane.showMessageDialog(AltaConsulta.this, "EL médico no posee la especialidad seleccionada",
        					"Error",JOptionPane.ERROR_MESSAGE);
        			
        		}
        		catch(FechaNoCoincideException ex) {
        			JOptionPane.showMessageDialog(AltaConsulta.this, ex.getMessage(),
        					"Error",JOptionPane.ERROR_MESSAGE);
        		}
        		catch(NumberFormatException ex) {
        			JOptionPane.showMessageDialog(AltaConsulta.this, "Número de sala y cantidad de números deben ser enteros",
        					"Error",JOptionPane.ERROR_MESSAGE);	
        		}
        		
        		catch (IOException ex) {
        		    JOptionPane.showMessageDialog(AltaConsulta.this, "Error al copiar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
        		}
      
        		
        	}
        	
        });
        getContentPane().add(btnAceptar);
               
        //Cancelar:
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(325, 310, 100, 23);
        
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
  		textFieldFecha.setText("");
  		textFieldNumSala.setText("");
  		textFieldCantNums.setText("");
  		diasConsulta.setSelectedIndex(-1);
  		medicos.setSelectedIndex(-1);
  		archivoImagen = null;
		textFieldImagen.setText("");
  	}
}
