package presentacion;

import logica.IControladorUsuario;
import datatypes.DTPaciente;
import datatypes.DTUsuario;
import java.util.ArrayList;
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
public class ListarPacientes extends JInternalFrame {
	private IControladorUsuario controladorU;
	
	private JList<String> listaP;
	private JLabel lblU;
	private JButton btnEleccion;
	private ActionListener ActListenerActual;
	private ConsultarBeneficioAnual consBA;
	
	public ListarPacientes(IControladorUsuario icu) {
		controladorU = icu;
		
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Listar Pacientes");
        setBounds(30, 30, 320, 280);
        
        getContentPane().setLayout(new BorderLayout(0,0));
        ((javax.swing.JComponent) getContentPane()).setBorder(
        	    javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
        	);
        
        listaP = new JList<String>();
        listaP.setCellRenderer(new DefaultListCellRenderer() {
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
        listaP.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaP);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        lblU = new JLabel("Pacientes Registrados");
        lblU.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblU, BorderLayout.NORTH);
        
        btnEleccion = new JButton("Seleccionar");
        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(btnEleccion);
        getContentPane().add(panelBtn, BorderLayout.SOUTH);
        panelBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));
	}
	
	
	//ACTIONLISTENER BENEFICIOANUAL
	public void consultaBeneficioAnual() {
		if (ActListenerActual != null) {
			btnEleccion.removeActionListener(ActListenerActual);
		}
		ActListenerActual = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pacienteSeleccionado = listaP.getSelectedValue();
                if (pacienteSeleccionado == null) {
                    JOptionPane.showMessageDialog(ListarPacientes.this, "Seleccione un paciente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                setVisible(false);
                if (consBA.cargarBeneficiosAnuales(pacienteSeleccionado)) {
                consBA.setVisible(true);
                }
            }
		};
			btnEleccion.addActionListener(ActListenerActual);
	}
	
	
	
	public boolean cargarPacientes() {
        DefaultListModel<String> modeloLista = new DefaultListModel<String>();
        List<DTPaciente> listaPacientes = controladorU.listarPacientes();
        List<String> pacientes = new ArrayList<>();
        for (DTUsuario dtpaciente : listaPacientes) {
        	pacientes.add(dtpaciente.toString());
        }
        
        if (pacientes != null && !pacientes.isEmpty()) {
            modeloLista.addAll(pacientes);
            listaP.setModel(modeloLista);
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "No hay pacientes para listar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
    }
	
	public void setConsultarBeneficioAnual(ConsultarBeneficioAnual cba) {
		consBA = cba;
	}

	
}