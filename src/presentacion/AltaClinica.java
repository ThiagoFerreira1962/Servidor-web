package presentacion;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import logica.IControladorClinica;
import excepciones.ClinicaRepetidaException;

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

@SuppressWarnings("serial")
public class AltaClinica extends JInternalFrame {
	private IControladorClinica ICC;
	
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblDireccion;
	private JLabel lblCostoTicketMedicamento;
	private JLabel lblCostoOrdenConsulta;
	private JLabel lblSitioWeb;
	private JLabel lblImagen;
	
	private JTextField textFieldNombre;
	private JTextField textFieldDescripcion;
	private JTextField textFieldDireccion;
	private JTextField textFieldCostoTicketMedicamento;
	private JTextField textFieldCostoOrdenConsulta;
	private JTextField textFieldSitioWeb;
	private JTextField textFieldImagen;
	
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JButton btnSeleccionarImagen;
    
    private File archivoImagen;
    private static final String CARPETA_FOTOS = "src/img";
    private static final String IMAGEN_DEFAULT = "defaultClinica.jpg";
    
    private void seleccionarImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (jpg, jpeg, png)", "jpg", "jpeg", "png"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            archivoImagen = chooser.getSelectedFile();
            textFieldImagen.setText(archivoImagen.getName());
        }
    }
    
    private String copiarImagen(String nombreClinica) throws IOException {
        String nombreOriginal = archivoImagen.getName();
        int punto = nombreOriginal.lastIndexOf('.');
        String extension = (punto >= 0) ? nombreOriginal.substring(punto) : "";

        Path carpeta = Paths.get(CARPETA_FOTOS);
        Files.createDirectories(carpeta);

        String nombreFinal = nombreClinica.replaceAll("[^a-zA-Z0-9]", "_") + extension;
        Files.copy(archivoImagen.toPath(), carpeta.resolve(nombreFinal), StandardCopyOption.REPLACE_EXISTING);
        return nombreFinal;
    }
    
    
    public AltaClinica(IControladorClinica icc) {
    	this.ICC=icc;
        setTitle("Alta de Clinica");
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setBounds(10, 40, 530, 395);
        getContentPane().setLayout(null);
        
        //Nombre:
        lblNombre= new JLabel("Nombre:");
        lblNombre.setBounds(20, 25, 120, 20);
        getContentPane().add(lblNombre);
        
        textFieldNombre= new JTextField();
        textFieldNombre.setBounds(240, 25, 240, 23);
        getContentPane().add(textFieldNombre);
        
        //Descripcion:
        lblDescripcion= new JLabel("Descripción:");
        lblDescripcion.setBounds(20, 65, 120, 20);
        getContentPane().add(lblDescripcion);
        
        textFieldDescripcion= new JTextField();
        textFieldDescripcion.setBounds(240, 65, 240, 23);
        getContentPane().add(textFieldDescripcion);
        
        //Direccion:
        lblDireccion= new JLabel("Dirección:");
        lblDireccion.setBounds(20, 105, 120, 20);
        getContentPane().add(lblDireccion);
        
        textFieldDireccion= new JTextField();
        textFieldDireccion.setBounds(240, 105, 240, 23);
        getContentPane().add(textFieldDireccion);
        
        //CostoTicket:
        lblCostoTicketMedicamento= new JLabel("Costo del ticket del Medicamento:");
        lblCostoTicketMedicamento.setBounds(20, 145, 225, 20);
        getContentPane().add(lblCostoTicketMedicamento);
        
        textFieldCostoTicketMedicamento= new JTextField();
        textFieldCostoTicketMedicamento.setBounds(240, 145, 240, 23);
        getContentPane().add(textFieldCostoTicketMedicamento);
        
        //CostoOrden:
        lblCostoOrdenConsulta= new JLabel("Costo de la orden de Consulta:");
        lblCostoOrdenConsulta.setBounds(20, 185, 180, 20);
        getContentPane().add(lblCostoOrdenConsulta);
        
        textFieldCostoOrdenConsulta= new JTextField();
        textFieldCostoOrdenConsulta.setBounds(240, 185, 240, 23);
        getContentPane().add(textFieldCostoOrdenConsulta);
        
        //SitioWeb:
        lblSitioWeb= new JLabel("Sitio Web (opcional) :");
        lblSitioWeb.setBounds(20, 225, 170, 20);
        getContentPane().add(lblSitioWeb);
        
        textFieldSitioWeb= new JTextField();
        textFieldSitioWeb.setBounds(240, 225, 240, 23);
        getContentPane().add(textFieldSitioWeb);
        
        //Imagen:
        lblImagen = new JLabel("Imagen (opcional) :");
        lblImagen.setBounds(20, 265, 170, 20);
        getContentPane().add(lblImagen);

        textFieldImagen = new JTextField();
        textFieldImagen.setEditable(false);
        textFieldImagen.setBounds(240, 265, 130, 23);
        getContentPane().add(textFieldImagen);

        btnSeleccionarImagen = new JButton("Seleccionar...");
        btnSeleccionarImagen.setBounds(375, 265, 105, 23);
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
        		String direccion= textFieldDireccion.getText();
        		String costoTicketTexto = textFieldCostoTicketMedicamento.getText(); //obtengo como texto y luego convierto a float
        		String costoOrdenTexto = textFieldCostoOrdenConsulta.getText(); //aca lo mismo
        		String Web= textFieldSitioWeb.getText();
        		//verifico que no falte lo necesario
        		if (nombre.isEmpty() || descripcion.isEmpty() || direccion.isEmpty() || costoTicketTexto.isEmpty() || costoOrdenTexto.isEmpty()) {
        			JOptionPane.showMessageDialog(AltaClinica.this, "Debe completar todos los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
        			return;        			
        		}
        		else { 
        			try {
        				//convierto a float
            			float costoTicket= Float.parseFloat(costoTicketTexto.replace(",", "."));
            			float costoOrden= Float.parseFloat(costoOrdenTexto.replace(",", "."));
                		if (costoTicket < 0 || costoOrden < 0) {
                			JOptionPane.showMessageDialog(AltaClinica.this, "Debe Ingresar costos mayores o iguales a cero", "Error", JOptionPane.ERROR_MESSAGE);
                			return;
                		}
                		String nombreArchivo = IMAGEN_DEFAULT;
                		if (archivoImagen != null) {
                		    nombreArchivo = copiarImagen(nombre);
                		}
        	        	ICC.altaClinica(nombre, descripcion, direccion, costoTicket, costoOrden, Web, nombreArchivo);
        	        	JOptionPane.showMessageDialog(AltaClinica.this, "La Clinica fue registrada con exito",
        	        		    "AltaClinica", JOptionPane.INFORMATION_MESSAGE);
        	        	limpiarCampos();
        	        	setVisible(false);
        	        	
        			} catch (ClinicaRepetidaException  ex) {
        	        	JOptionPane.showMessageDialog(AltaClinica.this, "Ya existe una Clinica con ese nombre",
        	        			"Error", JOptionPane.ERROR_MESSAGE); 
        	        } catch (NumberFormatException nf) {
        				JOptionPane.showMessageDialog(AltaClinica.this, "El formato del costo no es válido",
        	        			"Error", JOptionPane.ERROR_MESSAGE);
        			} catch (IOException ex) {
        			    JOptionPane.showMessageDialog(AltaClinica.this, "Error al copiar la imagen.",
        			            "Error", JOptionPane.ERROR_MESSAGE);
        			}
        		}        			
        	}
        });
        getContentPane().add(btnAceptar);
        
        
        
        
        //Cancelar:
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(295, 305, 100, 23);
        
        btnCancelar.addActionListener(new ActionListener() {
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
  		textFieldDireccion.setText("");
  		textFieldCostoTicketMedicamento.setText("");
  		textFieldCostoOrdenConsulta.setText("");
  		textFieldSitioWeb.setText("");
  		archivoImagen = null;
		textFieldImagen.setText("");
  	}
  	
}
