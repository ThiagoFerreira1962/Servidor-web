package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import datatypes.DTBeneficioAnual;
import logica.IControladorUsuario;

@SuppressWarnings("serial")
public class ConsultarBeneficioAnual extends JInternalFrame{
	
	private IControladorUsuario controladorU;
	private String nicknamePaciente;
	private JList<Integer> listaBA;
	private JLabel lblBA;
	private JButton btnEleccion;
	private DetalleBeneficioAnual detalleBAInternalFrame;


	public ConsultarBeneficioAnual(IControladorUsuario icu) {
		controladorU = icu;
		 
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Consulta Beneficio Anual");
        setBounds(30, 30, 320, 280);
        
        getContentPane().setLayout(new BorderLayout(0,0));
        ((javax.swing.JComponent) getContentPane()).setBorder(
        	    javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
        	);
        
        listaBA = new JList<Integer>();
        listaBA.setCellRenderer(new DefaultListCellRenderer() {
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
        listaBA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaBA);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        lblBA = new JLabel("Beneficios anuales");
        lblBA.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblBA, BorderLayout.NORTH);
        
        btnEleccion = new JButton("Seleccionar");
        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBtn.add(btnEleccion);
        getContentPane().add(panelBtn, BorderLayout.SOUTH);
        panelBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));
        btnEleccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listaBA.getSelectedValue() != null) {
					int seleccionado =(int)listaBA.getSelectedValue();
					detalleBAInternalFrame.mostrarDetalleBeneficioAnual(icu.getDTBeneficioAnualPacienteAnio(nicknamePaciente, seleccionado));
					detalleBAInternalFrame.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(ConsultarBeneficioAnual.this, "Seleccione un año.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
				}
			}
		});  
	}
	
	public boolean cargarBeneficiosAnuales(String nickPaciente){
		nicknamePaciente = nickPaciente;
		DefaultListModel<Integer> modeloLista = new DefaultListModel<Integer>();
		List <Integer> listaBenAnual = new ArrayList<>();
		List <DTBeneficioAnual> listaDTBA = controladorU.listarBeneficioAnuales(nickPaciente);
		for (DTBeneficioAnual dtba : listaDTBA) {
			listaBenAnual.add((Integer)dtba.getAnio());
		}
		if (listaBenAnual != null && !listaBenAnual.isEmpty()) {
	            modeloLista.addAll(listaBenAnual);
	            listaBA.setModel(modeloLista);
	            return true;
	        } else {
	            JOptionPane.showMessageDialog(this, "El pacienten no tiene beneficios anuales.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
	            return false;
	        }
	        
		
	}
	
	public void setDetalleBeneficioAnual(DetalleBeneficioAnual dba) {
		detalleBAInternalFrame = dba;
	}
}
