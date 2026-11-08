function validarContrasena () {

const contrasena = document.getElementById("contrasena").value;
const confContrasena = document.getElementById("confContr").value;

if (contrasena !== confContrasena){
    mostrarAlerta("confContr", "Las contraseñas no coinciden.");
    return false;
}else{
    return true;
    }
}

function validarEspecialidades(){
const marcadas = document.querySelectorAll("input[name='especialidades']:checked");
const caja = document.getElementById("especialidades");
const feedback = caja.parentElement.querySelector(".invalid-feedback");

if (marcadas.length === 0) {
    caja.classList.add("is-invalid", "border-danger");
    feedback.style.display = "block";   
    mostrarAlerta("especialidades", "Elige al menos una especialidad.")
    return false;
}
caja.classList.remove("is-invalid", "border-danger");
feedback.style.display = "none";
return true;
}



function validarCedula (){

var ci = Array.from(document.getElementById("ci").value);

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


function validarFechaNacimiento (){

const fechaNac =  document.getElementById("fechaNac").valueAsDate;
const hoy = new Date()
if (fechaNac > hoy) {
    mostrarAlerta("fechaNac","La fecha de nacimiento debe ser anterior a la fecha de hoy.");
    return false;
}

const limite = hoy;
limite.setFullYear(hoy.getFullYear()-120);

if (fechaNac < limite){
    mostrarAlerta("fechaNac","Ingrese una fecha de nacimiento válida.")
    return false;
}

return true;
}


function mostrarAlerta(id, mensaje) {
  const input = document.getElementById(id);
  input.classList.add("is-invalid");
  input.parentElement.querySelector(".invalid-feedback").textContent = mensaje;
}


function limpiarErrores() {
  document.querySelectorAll(".is-invalid").forEach(elm => elm.classList.remove("is-invalid"));
}


function validarFormularioPaciente(){

const contraOK = validarContrasena();
const ciOK = validarCedula();
const fechaNacOK = validarFechaNacimiento();
const todoOK = contraOK && ciOK && fechaNacOK;

if (!todoOK) {
    document.getElementById("alertas").scrollIntoView({ behavior: "smooth", block: "center" });
  }
  return todoOK;
}


function validarFormularioMedico(){

const contraOK = validarContrasena();
const espOK = validarEspecialidades();
const todoOK = contraOK && espOK;

if (!todoOK) {
    document.getElementById("alertas").scrollIntoView({ behavior: "smooth", block: "center"});
}
 return todoOK;
}

function validarFormularioBeneficioAnual() {
	const anio = parseInt(document.getElementById("anioBA").value);
	const cantOrdenes = parseInt(document.getElementById("cantOrdenes").value); 
	const cantTickets = parseInt(document.getElementById("cantTickets").value);
	const anioActual = new Date().getFullYear();
	let anioOK = true;
	let cantTicketsOK = true;
	let cantOrdenesOK = true;
	 
	 if (anio !== anioActual) {
	 	mostrarAlerta("anioBA", "El año ingresado no puede ser distinto del año actual");
	 	anioOK = false;
	 }
	 
	 if (cantOrdenes < 0) {
		mostrarAlerta("cantOrdenes", "La cantidad de órdenes de consultas no puede ser negativa");
		cantOrdenes = false;
	 }
	 
	 if (cantTickets < 0) {
		mostrarAlerta("cantTickets", "La cantidad de tickets de medicamentos no puede ser negativa");
		cantTicketsOK = false;
	 }
	 
	 return (anioOK && cantOrdenesOK && cantTicketsOK);
}