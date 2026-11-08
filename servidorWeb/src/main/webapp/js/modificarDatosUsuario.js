const form = document.getElementById("formPerfil");
const btnEditar = document.getElementById("btn-editar");
const btnCancelar = document.getElementById("btn-cancelar");
const btnConfirmar = document.getElementById("btn-confirmar");
const campos = document.querySelectorAll('.campoEdit');
btnCancelar.style.display = "none";
btnConfirmar.style.display = "none";

mostrarBotonesBorrar(false);


function mostrarBotonesBorrar(visible) {
	document.querySelectorAll(".btn-delete").forEach(function(b){
	b.style.display = visible ? "" : "none";
	});
}


btnEditar.addEventListener("click", editFunction);
btnCancelar.addEventListener("click", cancelarFunction);

function editFunction() {
  btnEditar.style.display = "none";
  btnCancelar.style.display = "block";
  btnConfirmar.style.display = "block";
  mostrarBotonesBorrar(true);
  campos.forEach(function (c) {
    c.readOnly = false;
    c.classList.add("editando");
  });
}

function bloquearCampos() {
  campos.forEach(function (c) {
    c.readOnly = true;
    c.classList.remove("editando");
	
  });
  mostrarBotonesBorrar(false);
  btnCancelar.style.display = "none";
  btnConfirmar.style.display = "none";
  btnEditar.style.display = "block";
}

function cancelarFunction() {
  form.reset();
  limpiarAlertas();
  bloquearCampos();
}

function validarCedula (){

	const input = document.getElementById("ci");
	if (!input) return true; //El perfil del médico no tiene cédula 
	var ci = Array.from(input.value.trim());

//solo numeros

const digitos = "0123456789";
for (let i = 0; i < ci.length; i++) {
    if (!digitos.includes(ci[i])) {
        mostrarAlerta("ci","Ingrese la cedula sin puntos ni guiones.")
        return false;
    }
}

// 7 u 8 digitos

if (ci.length < 7 || ci.length > 8 ){
    mostrarAlerta("ci","La cédula debe ser de 7 u 8 dígitos");
    return false;
}

//primer digito distinto de 0 

if (ci[0] === '0' ){
    mostrarAlerta("ci","El primer dígito no puede ser 0.");
    return false;
}

//digito verificador

var v = 0;
var i = 0;
var multiplicadores = [2,9,8,7,6,3,4];

if(ci.length <= 7){
    for(i = ci.length; i < 8; i++){
        ci.unshift('0');
    }
}

for(i = 0; i < 7; i++){
 v += multiplicadores[i] * ci[i] % 10;
}
if (v%10 === 0){
    v = 0;
}else{
    v = 10 - v % 10;
}

if (v != ci[7]){
    mostrarAlerta("ci","Ingrese una cédula válida.");
    return false;
}

return true;

}

function validarPerfil() {
  limpiarAlertas();
  let ok = true;

  // Cédula
  if (!validarCedula()) ok = false;

  // Fecha de nacimiento: no puede ser futura
  const nac = document.getElementById("fechaNacimiento");
  const h = new Date();
  const hoy = h.getFullYear() + "-"
            + String(h.getMonth() + 1).padStart(2, "0") + "-"
            + String(h.getDate()).padStart(2, "0");
  if (nac && nac.value > hoy) {
    mostrarAlerta("fechaNacimiento", "La fecha de nacimiento no puede ser futura.");
    ok = false;
  }

  // Fecha de ingreso (solo pacientes): posterior al nacimiento
  const ing = document.getElementById("fechaIngreso");
  if (ing && ing.value && nac && nac.value && ing.value < nac.value) {
    mostrarAlerta("fechaIngreso", "La fecha de ingreso no puede ser anterior a la de nacimiento.");
    ok = false;
  }

  return ok;
}

form.addEventListener("submit", function (e) {
  if (!validarPerfil()) {
    e.preventDefault();
  }
});

function limpiarAlertas() {
  campos.forEach(function (c) {
    c.classList.remove("is-invalid");
  });
}