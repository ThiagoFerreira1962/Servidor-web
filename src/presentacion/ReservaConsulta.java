package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import logica.IControladorUsuario;
import datatypes.DTConsulta;
import datatypes.DTDiaConsulta;
import datatypes.DTPaciente;
import javax.swing.JComboBox;
import logica.IControladorDiaConsulta;
import datatypes.DTUsuario;
import excepciones.PacienteYaTieneReservaException;
import excepciones.SinCuposDisponiblesException;

@SuppressWarnings("serial")
public class ReservaConsulta extends JInternalFrame {

	private IControladorUsuario ICU;
    private IControladorDiaConsulta ICDC;
    private JLabel lblSeleccioneDia;
    private JLabel lblSeleccionePaciente;
    private JButton btnCancelar;
    private JButton btnAceptar;
    private JComboBox<DTDiaConsulta> comboBoxDiaConsulta;
    private JComboBox<DTConsulta> comboBoxConsulta;
    private JComboBox<DTUsuario> comboBoxPaciente;

    public ReservaConsulta(IControladorUsuario icu, IControladorDiaConsulta icdc) {
        this.ICU = icu;
        ICDC = icdc;
        setTitle("Reserva consulta");
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setBounds(10, 40, 570, 217);
        getContentPane().setLayout(null);
        lblSeleccioneDia = new JLabel("Seleccione día consulta:");
        lblSeleccioneDia.setBounds(10, 28, 171, 14);
        getContentPane().add(lblSeleccioneDia);

        lblSeleccionePaciente = new JLabel("Seleccione paciente:");
        lblSeleccionePaciente.setBounds(10, 106, 158, 14);
        getContentPane().add(lblSeleccionePaciente);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(455, 152, 89, 23);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//comboBoxConsulta.clear();
                setVisible(false);
            }
        });
        getContentPane().add(btnCancelar);

        btnAceptar= new JButton("Aceptar");
        btnAceptar.setBounds(306, 152, 140, 23);
        btnAceptar.setEnabled(false);
        

    	
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DTUsuario dt = getPacienteSeleccionado();
                DTConsulta dtc = getConsultaSeleccionada();
                try {
                    boolean altaExitosa = ICU.altaReserva(dt.getNickname(), dtc.getNombre(), dtc.getFecha(), dtc.getCosto(), 0);
                    if (altaExitosa) {
                        JOptionPane.showMessageDialog(ReservaConsulta.this, "Alta dada con éxito", "Alta reserva", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                    }
                } catch (PacienteYaTieneReservaException ex) {
                    JOptionPane.showMessageDialog(ReservaConsulta.this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                catch (SinCuposDisponiblesException sc) {
                	JOptionPane.showMessageDialog(ReservaConsulta.this, sc.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        ActionListener verificarBoton = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean consultaSeleccionada = comboBoxConsulta.getSelectedItem() != null;
                boolean pacienteSeleccionado = comboBoxPaciente.getSelectedItem() != null;
                btnAceptar.setEnabled(consultaSeleccionada && pacienteSeleccionado);
            }
        };
        getContentPane().add(btnAceptar);
        
        comboBoxDiaConsulta = new JComboBox<DTDiaConsulta>();
        comboBoxDiaConsulta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DTDiaConsulta seleccionado = (DTDiaConsulta) comboBoxDiaConsulta.getSelectedItem();
        		if (seleccionado != null) {
        		  comboBoxConsulta.setEnabled(true);
        		  añadirConsultas(seleccionado.getNombre());
        		}
        	}
        });
        comboBoxDiaConsulta.setBounds(178, 24, 366, 22);
        getContentPane().add(comboBoxDiaConsulta);
        
        JLabel lblSeleccioneConsulta = new JLabel("Seleccione consulta:");
        lblSeleccioneConsulta.setBounds(10, 68, 158, 14);
        getContentPane().add(lblSeleccioneConsulta);
        
        comboBoxConsulta = new JComboBox<DTConsulta>();
        comboBoxConsulta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
        comboBoxConsulta.setEnabled(false);
        comboBoxConsulta.setBounds(178, 64, 366, 22);
        comboBoxConsulta.addActionListener(verificarBoton);
        getContentPane().add(comboBoxConsulta);
        
        comboBoxPaciente = new JComboBox<DTUsuario>();
        
        comboBoxPaciente.setBounds(178, 102, 366, 22);
        comboBoxPaciente.addActionListener(verificarBoton);
        getContentPane().add(comboBoxPaciente);
        
        
    }
    
    public void agregarDias() {
    	List<DTDiaConsulta> DTDia = ICDC.listarDiasConsultas();
    	DefaultComboBoxModel<DTDiaConsulta> dias = new DefaultComboBoxModel<>();
    	dias.addAll(DTDia);
    	comboBoxDiaConsulta.setModel(dias);
    }    

    public String DiaSeleccionado(){
    	DTDiaConsulta DT = (DTDiaConsulta) comboBoxDiaConsulta.getSelectedItem();
    	if (DT != null) {
    	return DT.getNombre();
    	}
    	return null;
    	
    }

    public void agregarPacientes() {
    	List<DTPaciente> pacientes = ICU.listarPacientes();
    	DefaultComboBoxModel<DTUsuario> listapacientes = new DefaultComboBoxModel<DTUsuario>();
    	listapacientes.addAll(pacientes);
    	comboBoxPaciente.setModel(listapacientes);
    	
    	comboBoxConsulta.setModel(new DefaultComboBoxModel<DTConsulta>());
    	comboBoxConsulta.setEnabled(false);
    	btnAceptar.setEnabled(false);
    	
    }

    public DTPaciente getPacienteSeleccionado() {
    	DTPaciente dt = (DTPaciente) comboBoxPaciente.getSelectedItem();
    	return dt;
    }

    public void añadirConsultas(String dia) {
    	List<DTConsulta> ListaConsultas = ICDC.listarConsultasDiaConsulta(dia);
    	DefaultComboBoxModel<DTConsulta> consultas = new DefaultComboBoxModel<>();
    	consultas.addAll(ListaConsultas);
    	comboBoxConsulta.setModel(consultas);
    }

    public DTConsulta getConsultaSeleccionada() {
    	DTConsulta dtc = (DTConsulta) comboBoxConsulta.getSelectedItem();
    	return dtc;
    }

}