package presentacion;
import logica.IControladorDiaConsulta;

import datatypes.DTConsulta;
import datatypes.DTDiaConsulta;

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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.BorderLayout;


@SuppressWarnings("serial")
public class ListarConsultasIngresadas extends JInternalFrame {
	private IControladorDiaConsulta controladorDC;
	private DetalleAceptarRechazarConsulta ventanaDetalleCons;
	private String nombreDiaActual;

	private JList<DTConsulta> listaConsultas;
	private DefaultListModel<DTConsulta> modeloLista;
	private JLabel lblConsulta;
	private JButton btnSeleccionar;

	public ListarConsultasIngresadas(IControladorDiaConsulta icd) {
		controladorDC = icd;

		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Listar Consultas");
		setBounds(500, 100, 380, 280);

		getContentPane().setLayout(new BorderLayout(0,0));
		((javax.swing.JComponent) getContentPane()).setBorder(
				javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
		);

		modeloLista = new DefaultListModel<DTConsulta>();
		listaConsultas = new JList<DTConsulta>(modeloLista);
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
		JScrollPane scrollPane = new JScrollPane(listaConsultas);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		lblConsulta = new JLabel("Consultas del Día de Consulta");
		lblConsulta.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblConsulta, BorderLayout.NORTH);

		btnSeleccionar = new JButton("Seleccionar");
		JPanel panelBtn = new JPanel();
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBtn.add(btnSeleccionar);
		getContentPane().add(panelBtn, BorderLayout.SOUTH);
		panelBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));

		//MOSTRAR EL DETALLE DE LA CONSULTA
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTConsulta seleccionada = listaConsultas.getSelectedValue();
				if (seleccionada == null) {
					JOptionPane.showMessageDialog(ListarConsultasIngresadas.this, "Seleccione una consulta", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				abrirDetalle(seleccionada);
			}
		});

		//DOBLE CLIC SOBRE LA CONSULTA
		listaConsultas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					DTConsulta seleccionada = listaConsultas.getSelectedValue();
					if (seleccionada != null) {
						abrirDetalle(seleccionada);
					}
				}
			}
		});
	}

	private void abrirDetalle(DTConsulta c) {
		DTConsulta detalle = controladorDC.seleccionarConsultaMedico(nombreDiaActual, c.getNombre());
		ventanaDetalleCons.mostrarConsulta(detalle, nombreDiaActual);
		ventanaDetalleCons.setVisible(true);
		ventanaDetalleCons.toFront();
	}

	public void setVentanaDetalleCons(DetalleAceptarRechazarConsulta v) {
	    this.ventanaDetalleCons = v;
	}
	public boolean cargarConsultas(DTDiaConsulta dia) {
		nombreDiaActual = dia.getNombre();
		lblConsulta.setText("Consultas de " + dia.getNombre());
		modeloLista.removeAllElements();
		List<DTConsulta> consultas = controladorDC.listarConsultasDiaConsultaIngresadas(dia.getNombre());
		if (consultas != null && !consultas.isEmpty()) {
			modeloLista.addAll(consultas);
			return true;
		} else {
			JOptionPane.showMessageDialog(this, "El dia de consulta no tiene consultas.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
}
	

