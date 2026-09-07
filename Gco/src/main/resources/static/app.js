const API_BASE = 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', () => {
    cargarSelect('tipoIdentificacion', '/tipos-identificacion');
    cargarSelect('pais', '/paises');
    cargarSelect('marca', '/marcas');

    document.getElementById('pais').addEventListener('change', async (e) => {
        const paisId = e.target.value;
        const deptoSelect = document.getElementById('departamento');
        const ciudadSelect = document.getElementById('ciudad');
        
        deptoSelect.innerHTML = '<option value="">Seleccione...</option>';
        ciudadSelect.innerHTML = '<option value="">Seleccione primero el depto...</option>';
        ciudadSelect.disabled = true;

        if (paisId) {
            deptoSelect.disabled = false;
            await cargarSelect('departamento', `/departamentos/${paisId}`);
        } else {
            deptoSelect.disabled = true;
        }
    });

    document.getElementById('departamento').addEventListener('change', async (e) => {
        const deptoId = e.target.value;
        const ciudadSelect = document.getElementById('ciudad');
        
        ciudadSelect.innerHTML = '<option value="">Seleccione...</option>';

        if (deptoId) {
            ciudadSelect.disabled = false;
            await cargarSelect('ciudad', `/ciudades/${deptoId}`);
        } else {
            ciudadSelect.disabled = true;
        }
    });

    document.getElementById('registroForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const btnSubmit = document.getElementById('btnSubmit');
        const mensajeDiv = document.getElementById('mensaje');
        
        btnSubmit.disabled = true;
        btnSubmit.textContent = 'Registrando...';

        const clienteData = {
            tipoIdentificacion: { id: document.getElementById('tipoIdentificacion').value },
            numeroIdentificacion: document.getElementById('numeroIdentificacion').value,
            nombres: document.getElementById('nombres').value,
            apellidos: document.getElementById('apellidos').value,
            fechaNacimiento: document.getElementById('fechaNacimiento').value,
            direccion: document.getElementById('direccion').value,
            pais: { id: document.getElementById('pais').value },
            departamento: { id: document.getElementById('departamento').value },
            ciudad: { id: document.getElementById('ciudad').value },
            marca: { id: document.getElementById('marca').value }
        };

        try {
            const response = await fetch(`${API_BASE}/clientes`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(clienteData)
            });

            if (response.ok) {
                mostrarMensaje(mensajeDiv, '¡Registro exitoso! Bienvenido a nuestro programa de fidelidad.', 'success');
                e.target.reset(); 
                document.getElementById('departamento').disabled = true;
                document.getElementById('ciudad').disabled = true;
            } else {
                mostrarMensaje(mensajeDiv, 'Ocurrió un error al registrar el cliente.', 'error');
            }
        } catch (error) {
            mostrarMensaje(mensajeDiv, 'Error de conexión con el servidor.', 'error');
            console.error(error);
        } finally {
            btnSubmit.disabled = false;
            btnSubmit.textContent = 'Completar Registro';
        }
    });
});

async function cargarSelect(elementId, endpoint) {
    try {
        const response = await fetch(API_BASE + endpoint);
        const data = await response.json();
        
        const select = document.getElementById(elementId);
        data.forEach(item => {
            const option = document.createElement('option');
            option.value = item.id;
            option.textContent = item.nombre;
            select.appendChild(option);
        });
    } catch (error) {
        console.error(`Error cargando ${endpoint}:`, error);
    }
}

function mostrarMensaje(element, texto, tipo) {
    element.textContent = texto;
    element.className = `mensaje ${tipo}`;
    setTimeout(() => {
        element.className = 'mensaje'; 
    }, 5000);
}

