package logica;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import datatypes.DTMedico;
import datatypes.DTPaciente;
import datatypes.DTUsuario;


public class HandlerUsuario {
private static HandlerUsuario instance;

private Map<String, Usuario> users;

private HandlerUsuario() {
    users = new HashMap<>();
}

public static HandlerUsuario getInstance() {
    if (instance == null) {
        instance = new HandlerUsuario();
    }
    return instance;
}

public int existeNicknameCorreo(String nickname, String correo) {	//0 = ok, 1 = nick repetido, 2 = correo repetido

    boolean existeNick = users.containsKey(nickname);
    boolean existeCorreo = false;

    for (Usuario u : users.values()) {
        if (u.getCorreo().equals(correo)) {
            existeCorreo = true;
        }
    }

    if (!existeNick && !existeCorreo)
        return 0;

    if (existeNick && !existeCorreo)
        return 1;

    if (!existeNick && existeCorreo)
        return 2;

    return 3;
}

public void agregarPaciente(Paciente pac) {
    users.put(pac.getNickname(), pac);
}

public void agregarMedico(Medico med) {
    users.put(med.getNickname(), med);
}

public List<DTPaciente> listarPacientes() {

    List<DTPaciente> pacientes = new ArrayList<>();

    for (Usuario usr : users.values()) {

        if (usr instanceof Paciente) {

            Paciente pac = (Paciente) usr;

            DTPaciente dtp = (DTPaciente) pac.obtenerDTUsuario();

            pacientes.add(dtp);
        }
    }

    return pacientes;
}

public List<DTMedico> listarMedicos(){

    List<DTMedico> medicos = new ArrayList<>();

    for (Usuario usr : users.values()) {

        if (usr instanceof Medico) {

            Medico med = (Medico) usr;

            DTMedico dtp = (DTMedico) med.obtenerDTUsuario();

            medicos.add(dtp);
        }
    }

    return medicos;
}

public List<DTUsuario> listarUsuarios(){
	List<DTUsuario> usuarios = new ArrayList<>();
	for (Usuario usr : users.values()) {
		usuarios.add(usr.obtenerDTUsuario());
	}
	return usuarios;
}

public Usuario obtenerUsuario(String nickname) {
    return users.get(nickname);
}


public Usuario obtenerPaciente(String nickname) {
	Usuario pac = users.get(nickname);
	return pac;
	
}


public Usuario obtenerMedico(String nickname) {
	Usuario med = users.get(nickname);
	return med;
}

public void limpiarInstancia() {
	instance = null;
}
}