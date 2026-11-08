function volver(){
	window.history.back();
}

function validarAnio() {
	const input = document.getElementById("anio");
	const anio = parseInt(input.value, 10);
	const anioActual = new Date().getFullYear();
	const feedback = input.parentElement.querySelector(".invalid-feedback");

	if (anio > anioActual) {
		input.classList.add("is-invalid");
		feedback.textContent = "El año no debe ser posterior al actual";
		return false;
	}
	input.classList.remove("is-invalid");
	return true;
}