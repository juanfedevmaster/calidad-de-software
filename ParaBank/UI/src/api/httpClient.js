const BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

/**
 * Wraps fetch against the ParaBank backend: builds the URL, sets JSON
 * headers, attaches the bearer token when present, and turns non-2xx
 * responses (using the backend's ApiErrorResponse shape) into a plain
 * Error with a user-facing message.
 */
async function request(path, { method = 'GET', body, token } = {}) {
  const headers = { 'Content-Type': 'application/json' };
  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  let response;
  try {
    response = await fetch(`${BASE_URL}${path}`, {
      method,
      headers,
      body: body ? JSON.stringify(body) : undefined,
    });
  } catch (networkError) {
    throw new Error('No se pudo conectar con el servidor. Verifica que el backend esté disponible.');
  }

  if (response.status === 204) {
    return null;
  }

  const text = await response.text();
  let data = null;
  if (text) {
    try {
      data = JSON.parse(text);
    } catch {
      data = null;
    }
  }

  if (!response.ok) {
    const detailMessage = data && data.details && data.details.length ? data.details.join(' ') : null;
    const message = (data && data.message) || detailMessage || 'Ocurrió un error inesperado.';
    throw new Error(message);
  }

  return data;
}

export default request;
