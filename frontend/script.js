// =========================================================
// CONFIGURACIÓN
// =========================================================
const API_BASE_URL = 'http://localhost:8080/api';

// =========================================================
// REFERENCIAS AL DOM
// =========================================================
const form = document.getElementById('formRegistro');
const btnSubmit = document.getElementById('btnSubmit');
const btnSubmitLabel = document.getElementById('btnSubmitLabel');
const alertBox = document.getElementById('alertBox');

const selectTipoIdentificacion = document.getElementById('tipoIdentificacion');
const inputNumeroIdentificacion = document.getElementById('numeroIdentificacion');
const inputNombres = document.getElementById('nombres');
const inputApellidos = document.getElementById('apellidos');
const inputFechaNacimiento = document.getElementById('fechaNacimiento');
const inputDireccion = document.getElementById('direccion');

const selectPais = document.getElementById('pais');
const selectDepartamento = document.getElementById('departamento');
const selectCiudad = document.getElementById('ciudad');

const brandGrid = document.getElementById('brandGrid');
const brandsPreview = document.getElementById('brandsPreview');

const cardNombre = document.getElementById('cardNombre');
const cardId = document.getElementById('cardId');
const cardMarca = document.getElementById('cardMarca');

// =========================================================
// ARRANQUE: cargar catálogos al abrir la página
// =========================================================
document.addEventListener('DOMContentLoaded', () => {
  cargarTiposIdentificacion();
  cargarPaises();
  cargarMarcas();
  configurarListenersCascada();
  configurarPreviewTarjeta();
  form.addEventListener('submit', manejarEnvioFormulario);
});

// =========================================================
// UTILIDAD GENÉRICA DE FETCH
// =========================================================
async function obtenerJSON(url) {
  const respuesta = await fetch(url);
  if (!respuesta.ok) {
    throw new Error(`Error al consultar ${url}: HTTP ${respuesta.status}`);
  }
  return respuesta.json();
}

function llenarSelect(selectEl, items, { valueKey = 'id', labelKey = 'nombre', placeholder }) {
  selectEl.innerHTML = '';
  const optPlaceholder = document.createElement('option');
  optPlaceholder.value = '';
  optPlaceholder.disabled = true;
  optPlaceholder.selected = true;
  optPlaceholder.textContent = placeholder;
  selectEl.appendChild(optPlaceholder);

  items.forEach(item => {
    const opt = document.createElement('option');
    opt.value = item[valueKey];
    opt.textContent = item[labelKey];
    selectEl.appendChild(opt);
  });
}

// =========================================================
// CARGA DE CATÁLOGOS
// =========================================================
async function cargarTiposIdentificacion() {
  try {
    const data = await obtenerJSON(`${API_BASE_URL}/tipos-identificacion`);
    llenarSelect(selectTipoIdentificacion, data, { placeholder: 'Selecciona...' });
  } catch (err) {
    mostrarAlerta('No se pudo cargar la lista de tipos de identificación. Verifica que el backend esté corriendo en el puerto 8080.', 'error');
    console.error(err);
  }
}

async function cargarPaises() {
  try {
    const data = await obtenerJSON(`${API_BASE_URL}/paises`);
    llenarSelect(selectPais, data, { placeholder: 'Selecciona...' });
  } catch (err) {
    mostrarAlerta('No se pudo cargar la lista de países.', 'error');
    console.error(err);
  }
}

async function cargarMarcas() {
  try {
    const data = await obtenerJSON(`${API_BASE_URL}/marcas`);
    renderizarTarjetasMarca(data);
    renderizarPreviewMarcas(data);
  } catch (err) {
    mostrarAlerta('No se pudo cargar la lista de marcas.', 'error');
    console.error(err);
  }
}

function renderizarTarjetasMarca(marcas) {
  brandGrid.innerHTML = '';
  marcas.forEach(marca => {
    const wrapper = document.createElement('div');
    wrapper.className = 'brandcard';

    const input = document.createElement('input');
    input.type = 'radio';
    input.name = 'marca';
    input.id = `marca-${marca.id}`;
    input.value = marca.id;

    const label = document.createElement('label');
    label.setAttribute('for', `marca-${marca.id}`);
    label.textContent = marca.nombre;

    input.addEventListener('change', () => {
      cardMarca.textContent = marca.nombre;
    });

    wrapper.appendChild(input);
    wrapper.appendChild(label);
    brandGrid.appendChild(wrapper);
  });
}

function renderizarPreviewMarcas(marcas) {
  brandsPreview.innerHTML = '';
  marcas.forEach(marca => {
    const li = document.createElement('li');
    li.textContent = marca.nombre;
    brandsPreview.appendChild(li);
  });
}

// =========================================================
// LÓGICA EN CASCADA: País -> Departamento -> Ciudad
// =========================================================
function configurarListenersCascada() {
  selectPais.addEventListener('change', async () => {
    const paisId = selectPais.value;

    // Reiniciar departamento y ciudad
    selectDepartamento.disabled = true;
    selectDepartamento.innerHTML = '<option value="" disabled selected>Cargando...</option>';
    selectCiudad.disabled = true;
    selectCiudad.innerHTML = '<option value="" disabled selected>Elige un departamento primero</option>';

    if (!paisId) return;

    try {
      const departamentos = await obtenerJSON(`${API_BASE_URL}/departamentos/${paisId}`);
      llenarSelect(selectDepartamento, departamentos, { placeholder: 'Selecciona...' });
      selectDepartamento.disabled = false;
    } catch (err) {
      mostrarAlerta('No se pudo cargar la lista de departamentos.', 'error');
      console.error(err);
    }
  });

  selectDepartamento.addEventListener('change', async () => {
    const departamentoId = selectDepartamento.value;

    selectCiudad.disabled = true;
    selectCiudad.innerHTML = '<option value="" disabled selected>Cargando...</option>';

    if (!departamentoId) return;

    try {
      const ciudades = await obtenerJSON(`${API_BASE_URL}/ciudades/${departamentoId}`);
      llenarSelect(selectCiudad, ciudades, { placeholder: 'Selecciona...' });
      selectCiudad.disabled = false;
    } catch (err) {
      mostrarAlerta('No se pudo cargar la lista de ciudades.', 'error');
      console.error(err);
    }
  });
}

// =========================================================
// PREVIEW EN VIVO DE LA TARJETA DE MIEMBRO
// =========================================================
function configurarPreviewTarjeta() {
  const actualizarNombre = () => {
    const nombres = inputNombres.value.trim();
    const apellidos = inputApellidos.value.trim();
    const nombreCompleto = `${nombres} ${apellidos}`.trim();
    cardNombre.textContent = nombreCompleto || 'Tu nombre aquí';
  };

  const actualizarId = () => {
    const numero = inputNumeroIdentificacion.value.trim();
    cardId.textContent = numero ? `N.° ${numero}` : 'N.° ---- ---- ----';
  };

  inputNombres.addEventListener('input', actualizarNombre);
  inputApellidos.addEventListener('input', actualizarNombre);
  inputNumeroIdentificacion.addEventListener('input', actualizarId);
}

// =========================================================
// ENVÍO DEL FORMULARIO (POST /api/clientes)
// =========================================================
async function manejarEnvioFormulario(event) {
  event.preventDefault();
  limpiarErrores();
  ocultarAlerta();

  const marcaSeleccionada = form.querySelector('input[name="marca"]:checked');

  const payload = {
    tipoIdentificacion: { id: parseInt(selectTipoIdentificacion.value, 10) || null },
    numeroIdentificacion: inputNumeroIdentificacion.value.trim(),
    nombres: inputNombres.value.trim(),
    apellidos: inputApellidos.value.trim(),
    fechaNacimiento: inputFechaNacimiento.value || null,
    direccion: inputDireccion.value.trim(),
    pais: { id: parseInt(selectPais.value, 10) || null },
    departamento: { id: parseInt(selectDepartamento.value, 10) || null },
    ciudad: { id: parseInt(selectCiudad.value, 10) || null },
    marca: { id: marcaSeleccionada ? parseInt(marcaSeleccionada.value, 10) : null }
  };

  // Validación manual: verificar que ningún id sea null
  const camposRequeridos = [
    payload.tipoIdentificacion.id,
    payload.numeroIdentificacion,
    payload.nombres,
    payload.apellidos,
    payload.fechaNacimiento,
    payload.direccion,
    payload.pais.id,
    payload.departamento.id,
    payload.ciudad.id,
    payload.marca.id
  ];

  if (camposRequeridos.some(v => !v)) {
    mostrarAlerta('Por favor completa todos los campos obligatorios.', 'error');
    return;
  }

  establecerCargando(true);

  try {
    const respuesta = await fetch(`${API_BASE_URL}/clientes`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (respuesta.ok) {
      const data = await respuesta.json();
      const nombreMarca = data.marca ? data.marca.nombre : '';
      mostrarAlerta(`¡Registro exitoso! Bienvenido ${data.nombres}. Marca seleccionada: ${nombreMarca}.`, 'success');
      form.reset();
      selectDepartamento.disabled = true;
      selectDepartamento.innerHTML = '<option value="" disabled selected>Elige un país primero</option>';
      selectCiudad.disabled = true;
      selectCiudad.innerHTML = '<option value="" disabled selected>Elige un departamento primero</option>';
      cardNombre.textContent = 'Tu nombre aquí';
      cardId.textContent = 'N.° ---- ---- ----';
      cardMarca.textContent = 'Selecciona una marca';
    } else {
      const data = await respuesta.json();
      mostrarErroresDeCampos(data);
      mostrarAlerta(data.error || 'Revisa los campos marcados en el formulario.', 'error');
    }
  } catch (err) {
    mostrarAlerta('No fue posible conectar con el servidor. Verifica que el backend esté corriendo en http://localhost:8080.', 'error');
    console.error(err);
  } finally {
    establecerCargando(false);
  }
}

function validarPayload(payload) {
  return Object.entries(payload).every(([_, value]) => value !== null && value !== '');
}

function establecerCargando(cargando) {
  btnSubmit.disabled = cargando;
  btnSubmitLabel.textContent = cargando ? 'Enviando...' : 'Registrarme en el club';
}

// =========================================================
// MANEJO DE ALERTAS Y ERRORES DE CAMPO
// =========================================================
function mostrarAlerta(mensaje, tipo) {
  alertBox.textContent = mensaje;
  alertBox.className = `alert alert--${tipo}`;
  alertBox.hidden = false;
  alertBox.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

function ocultarAlerta() {
  alertBox.hidden = true;
  alertBox.textContent = '';
}

function mostrarErroresDeCampos(errores) {
  Object.entries(errores).forEach(([campo, mensaje]) => {
    const spanId = mapearCampoAId(campo);
    const span = document.querySelector(`[data-error-for="${spanId}"]`);
    if (span) span.textContent = mensaje;
  });
}

function mapearCampoAId(campoBackend) {
  const mapa = {
    tipoIdentificacionId: 'tipoIdentificacion',
    numeroIdentificacion: 'numeroIdentificacion',
    nombres: 'nombres',
    apellidos: 'apellidos',
    fechaNacimiento: 'fechaNacimiento',
    direccion: 'direccion',
    paisId: 'pais',
    departamentoId: 'departamento',
    ciudadId: 'ciudad',
    marcaId: 'marca'
  };
  return mapa[campoBackend] || campoBackend;
}

function limpiarErrores() {
  document.querySelectorAll('.field__error').forEach(span => (span.textContent = ''));
}
