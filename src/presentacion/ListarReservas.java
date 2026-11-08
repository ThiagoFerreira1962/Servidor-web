package presentacion;

import logica.IControladorUsuario;
import datatypes.DTReserva;
import datatypes.DTUsuario;

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
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ListarReservas extends JInternalFrame {
	private IControladorUsuario controladorU;
	private DetalleReserva ventanaDetalleReserva;

	private JList<DTReserva> listaReservas;
	private DefaultListModel<DTReserva> modeloLista;
	private JLabel lblReserva;
	private JButton btnSeleccionar;

	public ListarReservas(IControladorUsuario icu) {
		controladorU = icu;

		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Listar Reservas");
		setBounds(500, 100, 380, 280);

		getContentPane().setLayout(new BorderLayout(0,0));
		((javax.swing.JComponent) getContentPane()).setBorder(
				javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
		);

		modeloLista = new DefaultListModel<DTReserva>();
		listaReservas = new JList<DTReserva>(modeloLista);
		listaReservas.setCellRenderer(new DefaultListCellRenderer() {
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
		JScrollPane scrollPane = new JScrollPane(listaReservas);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		lblReserva = new JLabel("Reservas del Paciente");
		lblReserva.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblReserva, BorderLayout.NORTH);

		btnSeleccionar = new JButton("Seleccionar");
		JPanel panelBtn = new JPanel();
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBtn.add(btnSeleccionar);
		getContentPane().add(panelBtn, BorderLayout.SOUTH);
		panelBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 5, 0));

		//MOSTRAR EL DETALLE DE LA RESERVA
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DTReserva seleccionada = listaReservas.getSelectedValue();
				if (seleccionada == null) {
					JOptionPane.showMessageDialog(ListarReservas.this, "Seleccione una reserva", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				abrirDetalle(seleccionada);
			}
		});

		//DOBLE CLIC SOBRE LA RESERVA
		listaReservas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					DTReserva seleccionada = listaReservas.getSelectedValue();
					if (seleccionada != null) {
						abrirDetalle(seleccionada);
					}
				}
			}
		});
	}

	private void abrirDetalle(DTReserva r) {
		ventanaDetalleReserva.mostrarReserva(r);
		ventanaDetalleReserva.setVisible(true);
		ventanaDetalleReserva.toFront();
	}

	public void setVentanaDetalleReserva(DetalleReserva v) {
		this.ventanaDetalleReserva = v;
	}

	public boolean cargarReservas(DTUsuario paciente) {
		lblReserva.setText("Reservas de " + paciente.getNickname());
		modeloLista.removeAllElements();
		List<DTReserva> reservas = controladorU.listarReservas(paciente.getNickname());
		if (reservas != null && !reservas.isEmpty()) {
			modeloLista.addAll(reservas);
			return true;
		} else {
			JOptionPane.showMessageDialog(this, "El paciente no tiene reservas.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
}