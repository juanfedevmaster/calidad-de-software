import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';

export default function Login({ onSwitchToRegister }) {
  const { login } = useAuth();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();

    if (!username.trim() || !password) {
      setError('Completa usuario y contraseña.');
      return;
    }

    setSubmitting(true);
    const result = await login({ username, password });
    setSubmitting(false);

    if (!result.ok) {
      setError(result.error);
      return;
    }
    setError('');
  }

  return (
    <div className="auth-screen">
      <div className="auth-card">
        <div className="brand">
          <span className="brand-mark">PB</span>
          <span className="brand-name">ParaBank</span>
        </div>

        <h1 className="auth-title">Inicia sesión</h1>
        <p className="auth-subtitle">Accede a tu cuenta para ver tu saldo y movimientos.</p>

        <form onSubmit={handleSubmit} noValidate>
          <label className="field">
            <span>Usuario</span>
            <input
              type="text"
              value={username}
              onChange={(event) => setUsername(event.target.value)}
              autoComplete="username"
              placeholder="tu.usuario"
            />
          </label>

          <label className="field">
            <span>Contraseña</span>
            <input
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              autoComplete="current-password"
              placeholder="••••••••"
            />
          </label>

          {error && (
            <p className="form-error" role="alert">
              {error}
            </p>
          )}

          <button type="submit" className="btn-primary" disabled={submitting}>
            {submitting ? 'Iniciando sesión...' : 'Iniciar sesión'}
          </button>
        </form>

        <p className="auth-switch">
          ¿No tienes cuenta?{' '}
          <button type="button" className="link-button" onClick={onSwitchToRegister}>
            Regístrate
          </button>
        </p>
      </div>
    </div>
  );
}
