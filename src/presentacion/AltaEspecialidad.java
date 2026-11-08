package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import excepciones.EspecialidadRepetidaException;
import logica.IControladorEspecialidad;

import java.awt.Dimension;

@SuppressWarnings("serial")
public class AltaEspecialidad extends JInternalFrame {

	private IControladorEspecialidad controlEsp;
	private JLabel lblIngreseNombre;
	private JLabel lblIngreseCosto;
	private	JTextField textFieldNombre;	
	private JTextField textFieldCosto;
	private JButton	btnAceptar;
	private JButton btnCancelar;
	
	protected void cmdAltaEspecialidadActionPerformed(ActionEvent arg0) {
		
		if (checkformulario()) {
		String nombre = textFieldNombre.getText();
		float costoAd;
		
		try {
			costoAd = Float.parseFloat(this.textFieldCosto.getText());
			if(costoAd<0) {
				JOptionPane.showMessageDialog(AltaEspecialidad.this,"El costo Adicional debe ser mayor o igual a cero.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "El costo Adicional debe ser un número ", "Error de formato", JOptionPane.ERROR_MESSAGE);
			return;
			}
		
		try {
			controlEsp.crearEspecialidad(nombre, costoAd);
			JOptionPane.showMessageDialog(this, "La especialidad se ha creado con éxito", "Alta Especialidad",JOptionPane.INFORMATION_MESSAGE);
	    	limpiarFormulario();
	    	setVisible(false);
		}catch (EspecialidadRepetidaException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error de formato", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	protected boolean checkformulario() {
		String nombre = textFieldNombre.getText();
		String costoAd = textFieldCosto.getText();
		
		if(nombre.isBlank() || costoAd.isBlank() ){
			JOptionPane.showMessageDialog(this, "No puede haber campos obligatiorios vacíos.", "Registrar Medico",
                    JOptionPane.ERROR_MESSAGE);
        	return false;}
		return true;
		}
	
	protected void limpiarFormulario() {
		textFieldNombre.setText("");
		textFieldCosto.setText("");
	}
		

	public AltaEspecialidad(IControladorEspecialidad ice) {
		controlEsp = ice;
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Alta de Especialidad");
        setBounds(10, 40, 300, 200);
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        getContentPane().setLayout(gridBagLayout);
        
        lblIngreseNombre = new JLabel("Nombre: ");
        lblIngreseNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseNombre = new GridBagConstraints();
        gbc_lblIngreseNombre.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseNombre.gridwidth = 1;
        gbc_lblIngreseNombre.gridx = 0;
        gbc_lblIngreseNombre.gridy = 1;
        gbc_lblIngreseNombre.insets = new Insets(0, 0, 5, 5);
        getContentPane().add(lblIngreseNombre, gbc_lblIngreseNombre);
        
        
        textFieldNombre = new JTextField();
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.gridwidth = 2;
        gbc_textFieldNombre.fill = GridBagConstraints.BOTH;
        gbc_textFieldNombre.gridx = 1;
        gbc_textFieldNombre.gridy = 1;
        getContentPane().add(textFieldNombre, gbc_textFieldNombre);
        textFieldNombre.setColumns(10);
        
        
        lblIngreseCosto = new JLabel("Costo Adicional: ");
        lblIngreseCosto.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_lblIngreseCosto = new GridBagConstraints();
        gbc_lblIngreseCosto.fill = GridBagConstraints.BOTH;
        gbc_lblIngreseCosto.gridwidth = 1;;
        gbc_lblIngreseCosto.gridx = 0;
        gbc_lblIngreseCosto.gridy = 2;
        gbc_lblIngreseCosto.insets = new Insets(10, 0, 5, 5);
        getContentPane().add(lblIngreseCosto, gbc_lblIngreseCosto);
        
        textFieldCosto = new JTextField();
        GridBagConstraints gbc_textFieldCosto = new GridBagConstraints();
        gbc_textFieldCosto.gridwidth = 2;
        gbc_textFieldCosto.fill = GridBagConstraints.BOTH;
        gbc_textFieldCosto.insets = new Insets(10, 0, 5, 0);
        gbc_textFieldCosto.gridx = 1;
        gbc_textFieldCosto.gridy = 2;
        getContentPane().add(textFieldCosto, gbc_textFieldCosto);
        textFieldCosto.setColumns(10);
        
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdAltaEspecialidadActionPerformed(arg0);
            }
        });
        
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.fill = GridBagConstraints.BOTH;
        gbc_btnAceptar.insets= new Insets(10,0,0,3);
        gbc_btnAceptar.gridx = 1;
        gbc_btnAceptar.gridy = 3;
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
        gbc_btnCancelar.insets = new Insets(10, 3, 0, 0);
        gbc_btnCancelar.gridx = 2;
        gbc_btnCancelar.gridy = 3;
        getContentPane().add(btnCancelar, gbc_btnCancelar);
        
        
        btnAceptar.setPreferredSize(new Dimension(95, 30));
        btnCancelar.setPreferredSize(new Dimension(95, 30));
	}

	
	
	
}