package presentacion;

import logica.IControladorUsuario;
import datatypes.DTUsuario;
import datatypes.DTMedico;
import datatypes.DTPaciente;


import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.BorderFactory;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class ListarUsuarios extends JInternalFrame {
	private IControladorUsuario controladorU;
	
	private JList<String> listaU;
	private JLabel lblU;
	private JButton btnEleccion;
	private ConsultarMedico cnsMedico;
	private ConsultarPaciente cnsPaciente; 

	public ListarUsuarios(IControladorUsuario icu) {
		controladorU = icu;
		 
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Listar Usuarios");
        setBounds(30, 30, 320, 280);
        
        getContentPane().setLayout(new BorderLayout(0,0));
        ((javax.swing.JComponent) getContentPane()).setBorder(
        	    javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
        	);
        
        listaU = new JList<String>();
        listaU.setCellRenderer(new DefaultListCellRenderer() {
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
        listaU.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaU);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        lblU = new JLabel("Usuarios Registrados");
        lblU.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblU, BorderLayout.NORTH);
        
        btnEleccion = new JButton("Seleccionar");
        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(btnEleccion);
        getContentPane().add(panelBtn, BorderLayout.SOUTH);
        panelBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        //MOSTRAR DTUSUARIO
        btnEleccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuarioSeleccionado = listaU.getSelectedValue();
                if (usuarioSeleccionado == null) {
                    JOptionPane.showMessageDialog(ListarUsuarios.this, "Seleccione un usuario.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                setVisible(false);
                DTUsuario dtu = icu.seleccionarUsuario(usuarioSeleccionado);
                if (dtu instanceof DTMedico) {
                	DTMedico dtm = (DTMedico) dtu;
                	cnsMedico.llenarCampos(dtm);
                	cnsMedico.setVisible(true);
                	return;
                } else {
                	DTPaciente dtp = (DTPaciente) dtu;
                	cnsPaciente.llenarCampos(dtp);
                	cnsPaciente.setVisible(true);
                }
            }
        });     
	}
	
	public void setConsultaUsuarios(ConsultarMedico cnsMedico, ConsultarPaciente cnsPaciente) {
		this.cnsMedico = cnsMedico;
		this.cnsPaciente = cnsPaciente;
	}
	
	public boolean cargarUsuarios() {
        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
        List<DTUsuario> usuarios = controladorU.listarUsuarios();
        if (usuarios != null && !usuarios.isEmpty()) {
        	for (DTUsuario u : usuarios) {
        		modeloLista.addElement(u.getNickname());
        	} 
            listaU.setModel(modeloLista);
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "No hay usuarios para listar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
    }
	 
	 
	
}