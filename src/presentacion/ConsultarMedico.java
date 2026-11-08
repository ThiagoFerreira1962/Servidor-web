package presentacion;

import datatypes.DTMedico;
import datatypes.DTConsulta;
import logica.IControladorUsuario;
import logica.IControladorEspecialidad;
import datatypes.Mes;

import javax.swing.JOptionPane;
import javax.swing.DefaultListCellRenderer;
import java.awt.Component;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import java.util.ArrayList;
import java.util.List;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class ConsultarMedico extends JInternalFrame {
	private DetalleConsulta ventanaConsulta;

    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldTipo;
    private JTextField textFieldCorreo;
    private JTextField textFieldRegistro;
    private JTextField textFieldLicencia;
    private JTextField textFieldCosto;
    private JTextField textFieldWeb;
    private JLabel lblCorreo;
    private JLabel lblEspecialidad;
    private JLabel lblRegistro;
    private JLabel lblLicencia;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblTipo;
    private JLabel lblConsulta;
    private JList<DTConsulta> listaConsultas;
    private DefaultListModel<DTConsulta> modeloLConsulta;
    private JLabel lblInfoUsuario;
    private JLabel lblCosto;
    private JLabel lblWeb;
    private JPanel panelEspecialidades;
    private List<JCheckBox> checkBoxEspecialidades;
    private IControladorEspecialidad controladorE;
    private JPanel panelContenido;
    private IControladorUsuario controladorU;
    private JButton btnEditar;
    private JButton btnCancelar;
    private boolean modoEdicion;
    private JLabel lblImagenFuente;
    private DTMedico medicoActual;


    public ConsultarMedico(IControladorUsuario ICU, IControladorEspecialidad ICE){
    	controladorU = ICU;
    	controladorE = ICE;

    	setResizable(false);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Consultar un Usuario");
        setBounds(30, 30, 769, 840);


        panelContenido = new JPanel();
        panelContenido.setLayout(null);
        panelContenido.setPreferredSize(new java.awt.Dimension(420, 800));


        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        setContentPane(scrollPane);


        lblInfoUsuario = new JLabel("Información de Usuario");
        lblInfoUsuario.setBounds(165, 17, 180, 14);
        panelContenido.add(lblInfoUsuario);


        //NOMBRE
        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(10, 61, 65, 14);
        panelContenido.add(lblNombre);

        textFieldNombre = new JTextField();
        textFieldNombre.setEditable(false);
        textFieldNombre.setBounds(110, 54, 271, 30);
        panelContenido.add(textFieldNombre);


        //APELLIDO
        lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(10, 103, 65, 14);
        panelContenido.add(lblApellido);

        textFieldApellido = new JTextField();
        textFieldApellido.setEditable(false);
        textFieldApellido.setBounds(110, 96, 271, 30);
        panelContenido.add(textFieldApellido);


        //CORREO
        lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(10, 144, 65, 14);
        panelContenido.add(lblCorreo);

        textFieldCorreo = new JTextField();
        textFieldCorreo.setEditable(false);
        textFieldCorreo.setBounds(110, 137, 271, 30);
        panelContenido.add(textFieldCorreo);


        //TIPO
        lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(10, 186, 65, 14);
        panelContenido.add(lblTipo);

        textFieldTipo = new JTextField();
        textFieldTipo.setEditable(false);
        textFieldTipo.setBounds(110, 179, 271, 30);
        panelContenido.add(textFieldTipo);


        //REGISTRO
        lblRegistro = new JLabel("Registro:");
        lblRegistro.setBounds(10, 228, 65, 14);
        panelContenido.add(lblRegistro);

        textFieldRegistro = new JTextField();
        textFieldRegistro.setEditable(false);
        textFieldRegistro.setBounds(110, 221, 271, 30);
        panelContenido.add(textFieldRegistro);


        //LICENCIA
        lblLicencia = new JLabel("Licencia:");
        lblLicencia.setBounds(10, 270, 65, 14);
        panelContenido.add(lblLicencia);

        textFieldLicencia = new JTextField();
        textFieldLicencia.setEditable(false);
        textFieldLicencia.setBounds(110, 263, 271, 30);
        panelContenido.add(textFieldLicencia);


        //ESPECIALIDADES
        lblEspecialidad = new JLabel("Especialidades:");
        lblEspecialidad.setBounds(10, 312, 100, 14);
        panelContenido.add(lblEspecialidad);

        panelEspecialidades = new JPanel();
        panelEspecialidades.setLayout(new BoxLayout(panelEspecialidades, BoxLayout.Y_AXIS));
        checkBoxEspecialidades = new ArrayList<JCheckBox>();

        JScrollPane scrollEspecialidad = new JScrollPane(panelEspecialidades);
        scrollEspecialidad.setBounds(110, 305, 271, 100);
        panelContenido.add(scrollEspecialidad);


        //COSTO
        lblCosto = new JLabel("Costo:");
        lblCosto.setBounds(10, 424, 65, 14);
        panelContenido.add(lblCosto);

        textFieldCosto = new JTextField();
        textFieldCosto.setEditable(false);
        textFieldCosto.setBounds(110, 417, 271, 30);
        panelContenido.add(textFieldCosto);


        //WEB
        lblWeb = new JLabel("Web:");
        lblWeb.setBounds(10, 466, 65, 14);
        panelContenido.add(lblWeb);

        textFieldWeb = new JTextField();
        textFieldWeb.setEditable(false);
        textFieldWeb.setBounds(110,459, 271, 30);
        panelContenido.add(textFieldWeb);


        //CONSULTAS
        lblConsulta = new JLabel("Consultas:");
        lblConsulta.setBounds(10, 508, 65, 14);
        panelContenido.add(lblConsulta);

        listaConsultas = new JList<DTConsulta>();
        listaConsultas.setCellRenderer(new DefaultListCellRenderer() {
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
        modeloLConsulta = new DefaultListModel<>();
        listaConsultas.setModel(modeloLConsulta);

        JScrollPane scrollConsultas = new JScrollPane(listaConsultas);
        scrollConsultas.setBounds(110, 501, 271, 100);
        panelContenido.add(scrollConsultas);

       listaConsultas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DTConsulta consultaSeleccionada = listaConsultas.getSelectedValue();
                    if (consultaSeleccionada != null) {
                        ventanaConsulta.mostrarConsulta(consultaSeleccionada);
                        ventanaConsulta.setVisible(true);
                    }
                }
            }
        });

       btnEditar = new JButton("Editar");
       btnEditar.setBounds(110, 610, 100, 25);
       panelContenido.add(btnEditar);

       btnCancelar = new JButton("Cancelar");
       btnCancelar.setBounds(220, 610, 100, 25);
       btnCancelar.setVisible(false);
       panelContenido.add(btnCancelar);

       //IMAGEN
       JLabel lblImagen = new JLabel("Imagen:");
       lblImagen.setBounds(10, 664, 65, 14);
       panelContenido.add(lblImagen);

       // Sin "JLabel" adelante: se asigna al atributo de la clase
       lblImagenFuente = new JLabel();
       lblImagenFuente.setBounds(110, 650, 100, 100);
       lblImagenFuente.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
       panelContenido.add(lblImagenFuente);

       btnEditar.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (!modoEdicion) {
                   entrarModoEdicion();
               } else {
                   confirmarEdicion();
               }
           }
       });

       btnCancelar.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               llenarCampos(medicoActual);
           }
       });

    }

    public void setVentanaConsulta (DetalleConsulta dtc) {
    	this.ventanaConsulta = dtc;
	}

    private void cargarImagen(String ruta) {
        lblImagenFuente.setIcon(null);
        lblImagenFuente.setText("");
        lblImagenFuente.setHorizontalAlignment(JLabel.CENTER);
        if (ruta == null || ruta.trim().isEmpty()) {
            lblImagenFuente.setText("Sin imagen");
            return;
        }
        File archivo = new File("src/img", ruta);
        if (!archivo.exists()) {
            archivo = new File(ruta);
        }
        if (!archivo.exists()) {
            lblImagenFuente.setText("Sin imagen");
            return;
        }

        ImageIcon original = new ImageIcon(archivo.getPath());
        Image escalada = original.getImage().getScaledInstance(
                lblImagenFuente.getWidth() - 2,
                lblImagenFuente.getHeight() - 2,
                Image.SCALE_SMOOTH);
        lblImagenFuente.setIcon(new ImageIcon(escalada));
    }

    public void cargarEspecialidades(List<String> especialidadesDelMedico) {
        panelEspecialidades.removeAll();
        checkBoxEspecialidades.clear();
        for (String nombre : controladorE.getNombreEspecialidades()) {
            JCheckBox check = new JCheckBox(nombre);
            check.setSelected(especialidadesDelMedico != null && especialidadesDelMedico.contains(nombre));
            check.setEnabled(modoEdicion);
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

    private void setEspecialidadesEditables(boolean editable) {
        for (JCheckBox check : checkBoxEspecialidades) {
            check.setEnabled(editable);
        }
    }

    private void entrarModoEdicion() {
        modoEdicion = true;
        btnEditar.setText("Confirmar");
        btnCancelar.setVisible(true);

        textFieldNombre.setEditable(true);
        textFieldApellido.setEditable(true);
        textFieldRegistro.setEditable(true);
        textFieldLicencia.setEditable(true);
        textFieldCosto.setEditable(true);
        textFieldWeb.setEditable(true);
        setEspecialidadesEditables(true);
    }

    private void confirmarEdicion() {
        try {
            Mes nuevaLicencia = Mes.valueOf(textFieldLicencia.getText().trim());
            String costoTexto = textFieldCosto.getText().trim().replace(",", ".");
            float nuevoCosto = Float.parseFloat(costoTexto);
            List<String> especialidadesNuevas = getEspecialidadesSeleccionadas();
            // Se conserva la foto actual (antes se mandaba null)
            String foto = medicoActual.getFoto();
            controladorU.editarMedico(
                medicoActual.getNickname(),
                textFieldNombre.getText(),
                textFieldApellido.getText(),
                textFieldRegistro.getText(),
                nuevaLicencia,
                nuevoCosto,
                textFieldWeb.getText(),
                especialidadesNuevas, foto
            );

            modoEdicion = false;
            btnEditar.setText("Editar");
            btnCancelar.setVisible(false);

            textFieldNombre.setEditable(false);
            textFieldApellido.setEditable(false);
            textFieldRegistro.setEditable(false);
            textFieldLicencia.setEditable(false);
            textFieldCosto.setEditable(false);
            textFieldWeb.setEditable(false);
            setEspecialidadesEditables(false);

            JOptionPane.showMessageDialog(this, "Datos actualizados con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException iae) {
            JOptionPane.showMessageDialog(this, "Mes de licencia o costo inválido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void llenarCampos(DTMedico dtm) {
        this.medicoActual = dtm;
        textFieldNombre.setText(dtm.getNombre());
        textFieldApellido.setText(dtm.getApellido());
        textFieldTipo.setText("Médico");
        textFieldCorreo.setText(dtm.getCorreo());
        textFieldRegistro.setText(dtm.getNroRegistro());
        textFieldLicencia.setText(dtm.getLicencia().toString());
        textFieldCosto.setText(String.format("%.1f", dtm.getCostoAdicional()));
        textFieldWeb.setText(dtm.getSitioWeb());
        cargarImagen(dtm.getFoto());
        cargarEspecialidades(dtm.getEspecialidades());

        modeloLConsulta.clear();
        if (dtm.getConsultas() != null) {
            modeloLConsulta.addAll(dtm.getConsultas());
        }

        modoEdicion = false;
        btnEditar.setText("Editar");
        btnCancelar.setVisible(false);
        textFieldNombre.setEditable(false);
        textFieldApellido.setEditable(false);
        textFieldRegistro.setEditable(false);
        textFieldLicencia.setEditable(false);
        textFieldCosto.setEditable(false);
        textFieldWeb.setEditable(false);
    }
}