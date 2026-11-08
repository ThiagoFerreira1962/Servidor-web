package presentacion;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import logica.Fabrica;
import logica.IControladorUsuario;
import logica.IControladorDiaConsulta;
import logica.IControladorEspecialidad;
import logica.IControladorClinica;
import logica.CargaDatos;


import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Principal {

	private JFrame frmSaludUy;
	private RegistrarMedico regMedInternalFrame;
	private RegistrarPaciente regPacInternalFrame;
	private ConsultarMedico consMedInternalFrame;
	private ConsultarPaciente consPacInternalFrame;
	private ListarUsuarios listarUInternalFrame;
	private ConsultarDiaDeConsulta consDConsInternalFrame;
	private DetalleDiaConsulta detalleDiaInternalFrame;
	private DetalleConsulta detalleConsInternalFrame;
	private DetalleClinica detalleClinica;
	private DetalleReserva detalleResInternalFrame;
	private ConsultarConsulta consConsInternalFrame;
	private ListarConsultas listarConsInternalFrame;
	private ListarReservas listarResInternalFrame;
	private ConsultarReservaConsulta consResConsInternalFrame;
	private ListarPacientes listarPacInternalFrame;
	private IControladorUsuario ICU;
	private IControladorClinica ICC;
	private IControladorDiaConsulta ICDC;
	private IControladorEspecialidad ICE;
	private AltaBeneficioAnual altaBenAnInternalFrame;
	private AltaEspecialidad altaEspInternalFrame;
	private AltaDiaConsulta altaDiaConsulta;
	private AltaClinica altaClinica;
	private AltaConsulta altaConsulta;
	private ConsultarClinica consultaClinica;
	private ReservaConsulta resConsInternalFrame;
	private ConsultarBeneficioAnual consBAInternalFrame;
	private DetalleBeneficioAnual detBenAnualInternalFrame;
	private AceptarRechazarConsulta aceptarRechazarConsulta;
	private ListarConsultasIngresadas listarConsultasIngresadas;
	private DetalleAceptarRechazarConsulta detalleAceptarRechazarConsulta;
	
	//dimensiones
	private final int jframeX = 0;
    private final int jframeY = 0;
    private final int jframeW = 1300;
    private final int jframeH = 800;
    
    
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal window = new Principal();
                    window.frmSaludUy.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    
    public Principal() {

        Fabrica f = Fabrica.getInstance();
    	ICU = f.getIControladorUsuario();
    	ICDC = f.getIControladorDiaConsulta();
    	ICC = f.getIControladorClinica();
    	ICE = f.getIControladorEspecialidad();
    	
    	//Se crean las ventanas y se ocultan
    	regMedInternalFrame = new RegistrarMedico(ICU, ICE);
    	regMedInternalFrame.setVisible(false);
    	
    	regPacInternalFrame = new RegistrarPaciente(ICU);
    	regPacInternalFrame.setVisible(false);
    	
    	altaEspInternalFrame = new AltaEspecialidad(ICE);
    	altaEspInternalFrame.setVisible(false);
    	
    	altaBenAnInternalFrame = new AltaBeneficioAnual(ICU);
    	altaBenAnInternalFrame.setVisible(false);
    	
    	altaDiaConsulta = new AltaDiaConsulta(ICDC,ICC,ICE);
        altaDiaConsulta.setVisible(false);
        
        altaConsulta = new AltaConsulta(ICDC,ICU);
        altaConsulta.setVisible(false);
        
    	
    	altaClinica= new AltaClinica(ICC);
        altaClinica.setVisible(false);
        
        consultaClinica= new ConsultarClinica(ICC);
        consultaClinica.setVisible(false);
        
        detalleClinica = new DetalleClinica();
        detalleClinica.setVisible(false);
        consultaClinica.setVentanaDetalleClinica(detalleClinica);
    	
    	consDConsInternalFrame = new ConsultarDiaDeConsulta(ICDC);
    	consDConsInternalFrame.setVisible(false);
    	
    	detalleDiaInternalFrame = new DetalleDiaConsulta(ICDC);
    	detalleDiaInternalFrame.setVisible(false);
    	consDConsInternalFrame.setVentanaDetalleDia(detalleDiaInternalFrame);
    	
    	detalleResInternalFrame = new DetalleReserva();
    	detalleResInternalFrame.setVisible(false);
    	
    	detalleConsInternalFrame = new DetalleConsulta();
    	detalleConsInternalFrame.setVisible(false);
    	detalleDiaInternalFrame.setVentanaDetalleCons(detalleConsInternalFrame);
    	detalleConsInternalFrame.setVentanaReserva(detalleResInternalFrame);
    	
    	consMedInternalFrame = new ConsultarMedico(ICU,ICE);
    	consMedInternalFrame.setVisible(false);
    	consMedInternalFrame.setVentanaConsulta(detalleConsInternalFrame);
    	
    	consPacInternalFrame = new ConsultarPaciente(ICU);
    	consPacInternalFrame.setVisible(false);
    	consPacInternalFrame.setVentanaReserva(detalleResInternalFrame);
    	 
    	listarUInternalFrame = new ListarUsuarios(ICU);
    	listarUInternalFrame.setConsultaUsuarios(consMedInternalFrame, consPacInternalFrame);
    	listarUInternalFrame.setVisible(false);
    	
    	consConsInternalFrame = new ConsultarConsulta(ICDC);
    	consConsInternalFrame.setVisible(false);
    	
    	listarConsInternalFrame = new ListarConsultas(ICDC);
    	listarConsInternalFrame.setVisible(false);
    	listarConsInternalFrame.setVentanaDetalleCons(detalleConsInternalFrame);
    	consConsInternalFrame.setVentanaListarConsultas(listarConsInternalFrame);
    	
    	consResConsInternalFrame = new ConsultarReservaConsulta(ICU);
    	consResConsInternalFrame.setVisible(false);
    	
    	listarResInternalFrame = new ListarReservas(ICU);
    	listarResInternalFrame.setVisible(false);
    	listarResInternalFrame.setVentanaDetalleReserva(detalleResInternalFrame);
    	consResConsInternalFrame.setVentanaListarReservas(listarResInternalFrame);
    	
    	detBenAnualInternalFrame = new DetalleBeneficioAnual();
    	detBenAnualInternalFrame.setVisible(false); 
    	
    	consBAInternalFrame = new ConsultarBeneficioAnual(ICU);
    	consBAInternalFrame.setVisible(false);
    	consBAInternalFrame.setDetalleBeneficioAnual(detBenAnualInternalFrame);
    	
    	listarPacInternalFrame = new ListarPacientes(ICU);
    	listarPacInternalFrame.setVisible(false);
    	listarPacInternalFrame.setConsultarBeneficioAnual(consBAInternalFrame);
    	

    	aceptarRechazarConsulta = new AceptarRechazarConsulta(ICDC);
    	listarConsultasIngresadas = new ListarConsultasIngresadas(ICDC);
    	detalleAceptarRechazarConsulta = new DetalleAceptarRechazarConsulta(ICDC);
    	
    	aceptarRechazarConsulta.setVentanaListarConsultasIngresadas(listarConsultasIngresadas);
    	listarConsultasIngresadas.setVentanaDetalleCons(detalleAceptarRechazarConsulta);
    	detalleAceptarRechazarConsulta.setVentanaReserva(detalleResInternalFrame);
    	
        Initialize();
        
    }
    
    private void Initialize() {
    	
    	frmSaludUy = new JFrame();
        frmSaludUy.setTitle("SaludUy");
        frmSaludUy.setBounds(jframeX, jframeY, jframeW, jframeH);
        frmSaludUy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    	// Agregamos absolute layout
    	frmSaludUy.getContentPane().setLayout(null);
        
    	//Se agregan las ventanas
    	frmSaludUy.getContentPane().add(regMedInternalFrame);
    	frmSaludUy.getContentPane().add(regPacInternalFrame);
    	frmSaludUy.getContentPane().add(altaEspInternalFrame);
    	frmSaludUy.getContentPane().add(consDConsInternalFrame);
    	frmSaludUy.getContentPane().add(detalleDiaInternalFrame);
    	frmSaludUy.getContentPane().add(detalleResInternalFrame);
    	frmSaludUy.getContentPane().add(detalleConsInternalFrame);
    	frmSaludUy.getContentPane().add(consConsInternalFrame);
    	frmSaludUy.getContentPane().add(consResConsInternalFrame);
    	frmSaludUy.getContentPane().add(altaBenAnInternalFrame);
        frmSaludUy.getContentPane().add(altaClinica);
        frmSaludUy.getContentPane().add(altaConsulta);
        frmSaludUy.getContentPane().add(consultaClinica);
        frmSaludUy.getContentPane().add(detalleClinica);
    	frmSaludUy.getContentPane().add(listarUInternalFrame);
    	frmSaludUy.getContentPane().add(consMedInternalFrame);	
    	frmSaludUy.getContentPane().add(consPacInternalFrame);
    	frmSaludUy.getContentPane().add(listarPacInternalFrame);
    	frmSaludUy.getContentPane().add(consBAInternalFrame);
    	frmSaludUy.getContentPane().add(listarConsInternalFrame);
    	frmSaludUy.getContentPane().add(detBenAnualInternalFrame);
    	frmSaludUy.getContentPane().add(listarResInternalFrame);
    	frmSaludUy.getContentPane().add(aceptarRechazarConsulta);
    	frmSaludUy.getContentPane().add(listarConsultasIngresadas);
    	frmSaludUy.getContentPane().add(detalleAceptarRechazarConsulta);

    	//Agregamos internal frame de "Reserva consulta"
    	resConsInternalFrame = new ReservaConsulta(ICU, ICDC);
    	frmSaludUy.getContentPane().add(resConsInternalFrame);
    	resConsInternalFrame.setVisible(false);
    		
        JMenuBar menuBar = new JMenuBar();
        frmSaludUy.setJMenuBar(menuBar);
        
        JMenu menuSistema = new JMenu("Sistema");
        menuBar.add(menuSistema);
        
        JMenu menuAltas = new JMenu("Alta");
        menuBar.add(menuAltas);
        
        JMenu menuConsultas = new JMenu("Consultar");
        menuBar.add(menuConsultas);
        
        JMenu menuReservas = new JMenu("Reservar");
        menuBar.add(menuReservas);
       
        //Ventana AltaEspecialidad
        JMenuItem menuAltaEspecialidad = new JMenuItem("Alta Especialidad");
        menuAltas.add(menuAltaEspecialidad);
        menuAltaEspecialidad.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		altaEspInternalFrame.setVisible(true);
        	}
        });
        
        //Ventana AltaBeneficioAnual
        JMenuItem menuAltaBeneficioAnual = new JMenuItem("Alta Beneficio Anual");
        menuAltas.add(menuAltaBeneficioAnual);
        menuAltaBeneficioAnual.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		altaBenAnInternalFrame.cargarPacientes();
        		altaBenAnInternalFrame.setVisible(true);
        	}
        });
        
        //Ventana ConsultarDiaDeConsulta
        JMenuItem menuConsultarDiaConsulta = new JMenuItem("Consultar Día de Consulta");
        menuConsultas.add(menuConsultarDiaConsulta);
        menuConsultarDiaConsulta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		consDConsInternalFrame.cargarDias();
        		consDConsInternalFrame.setVisible(true);
        	}
        });
        
        //consultar clinica
        JMenuItem menuClinica = new JMenuItem("Consultar Clínica");
        menuConsultas.add(menuClinica);
        menuClinica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                consultaClinica.cargarClinicas();
                consultaClinica.setVisible(true);
            }
        });
        
        //Ventana ConsultarConsulta
        JMenuItem menuConsultarConsulta = new JMenuItem("Consultar Consulta");
        menuConsultas.add(menuConsultarConsulta);
        menuConsultarConsulta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		consConsInternalFrame.cargarDias();
        		consConsInternalFrame.setVisible(true);
        	}

        });
        JMenuItem menuAceptarRechazarConsulta = new JMenuItem("Aceptar/Rechazar Consulta");
        menuConsultas.add(menuAceptarRechazarConsulta);
        menuAceptarRechazarConsulta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		aceptarRechazarConsulta.cargarDiasConsulta();
        		aceptarRechazarConsulta.setVisible(true);
        	}
        });
        
        
        //Ventana ConsultarReservaConsulta
        JMenuItem menuConsultarReservaConsulta = new JMenuItem("Consultar Reserva de Consulta");
        menuConsultas.add(menuConsultarReservaConsulta);
        menuConsultarReservaConsulta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		consResConsInternalFrame.cargarPacientes();
        		consResConsInternalFrame.setVisible(true);
        	}
        });

              
        //Ventana AltaDiaConsulta:
        frmSaludUy.getContentPane().add(altaDiaConsulta);
        JMenuItem menuAltaDiaConsulta = new JMenuItem("Alta Día Consulta");
        menuAltas.add(menuAltaDiaConsulta);
        menuAltaDiaConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	altaDiaConsulta.cargarClinicas();
            	altaDiaConsulta.cargarEspecialidades();
                altaDiaConsulta.setVisible(true);
            }
        });
        
        //Ventana AltaConsulta:
        JMenuItem menuAltaConsulta= new JMenuItem("Alta Consulta");
        menuAltas.add(menuAltaConsulta);
        menuAltaConsulta.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		altaConsulta.cargarDiasConsulta();
        		altaConsulta.cargarMedicos();
        		altaConsulta.setVisible(true);
        	}
        });
        


        //Ventana AltaClinica
        JMenuItem menuAltaClinica= new JMenuItem("Alta Clínica");
        menuAltas.add(menuAltaClinica);
        menuAltaClinica.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		altaClinica.setVisible(true);
        	}
        });
        
        //Ventana AltaUsuario
        
        JMenuItem menuItemRegistrar = new JMenuItem("Alta Usuario");
        menuItemRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Muestro el InternalFrame para registrar un usuario
            	String[] opciones = {"Paciente", "Medico"};
            	int opcion = JOptionPane.showOptionDialog(frmSaludUy, "¿Qué usuario desea registrar?", "Registrar Usuario", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,opciones,null);
            	switch(opcion) {
            	case 0:
            		regPacInternalFrame.setVisible(true);
            		break;
            	case 1:
            		regMedInternalFrame.cargarEspecialidades();
            		regMedInternalFrame.setVisible(true);
            		break;
            	}
            }
        });
        menuAltas.add(menuItemRegistrar);
        
        
        //Item de cargar datos
        JMenuItem menuCargarDatos = new JMenuItem("Cargar Datos de Prueba");
        menuCargarDatos.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		CargaDatos.cargar();
        		menuCargarDatos.setEnabled(false);
        		JOptionPane.showMessageDialog(frmSaludUy, "Datos cargados con éxito", "Carga de Datos", JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        
        menuSistema.add(menuCargarDatos);
        
        
        
        JMenuItem menuSalir = new JMenuItem("Salir");
        menuSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // Salgo de la aplicación
                frmSaludUy.setVisible(false);
                frmSaludUy.dispose();
            }
        });
        
        menuSistema.add(menuSalir);
        
        //Consultar Usuario
        JMenuItem listarUMenuItem = new JMenuItem("Consultar Usuario");
        listarUMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(listarUInternalFrame.cargarUsuarios()) {
        		listarUInternalFrame.setVisible(true);
        		}
        	}
        });
        menuConsultas.add(listarUMenuItem);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Reserva de consulta");
        mntmNewMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		resConsInternalFrame.agregarDias();
        		resConsInternalFrame.setVisible(true);
        		resConsInternalFrame.agregarPacientes();
        	}
        });

        menuReservas.add(mntmNewMenuItem);
        
   
    
    	JMenuItem consBeneAnualMenuItem = new JMenuItem("Consultar Beneficio Anual");
    	consBeneAnualMenuItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (listarPacInternalFrame.cargarPacientes()) {
    			listarPacInternalFrame.setVisible(true);
    			listarPacInternalFrame.consultaBeneficioAnual();
    			}
    		}
    	});
    	menuConsultas.add(consBeneAnualMenuItem);
    }	


   }







