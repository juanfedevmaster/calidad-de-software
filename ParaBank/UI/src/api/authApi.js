import request from './httpClient';

export function register({ name, username, password }) {
  return request('/api/auth/register', {
    method: 'POST',
    body: { name, username, password },
  });
}

export function login({ username, password }) {
  return request('/api/auth/login', {
    method: 'POST',
    body: { username, password },
  });
}

export function getBalance(token) {
  return request('/api/users/me/balance', {
    method: 'GET',
    token,
  });
}

export function changePassword(token, { currentPassword, newPassword }) {
  return request('/api/users/me/password', {
    method: 'PUT',
    token,
    body: { currentPassword, newPassword },
  });
}
