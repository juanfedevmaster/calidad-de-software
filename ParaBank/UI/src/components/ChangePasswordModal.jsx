import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';

export default function ChangePasswordModal({ onClose }) {
  const { changePassword } = useAuth();
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);

  function handleSubmit(event) {
    event.preventDefault();

    if (!currentPassword || !newPassword || !confirmPassword) {
      setError('Completa todos los campos.');
      return;
    }
    if (newPassword !== confirmPassword) {
      setError('La nueva contraseña no coincide.');
      return;
    }
    if (newPassword.length < 4) {
      setError('La nueva contraseña debe tener al menos 4 caracteres.');
      return;
    }

    const result = changePassword({ currentPassword, newPassword });
    if (!result.ok) {
      setError(result.error);
      return;
    }

    setError('');
    setSuccess(true);
  }

  return (
    <div className="modal-overlay" role="dialog" aria-modal="true">
      <div className="modal-card">
        <button type="button" className="modal-close" onClick={onClose} aria-label="Cerrar">
          ×
        </button>

        <h2>Cambiar contraseña</h2>

        {success ? (
          <>
            <p className="form-success">Tu contraseña se actualizó correctamente.</p>
            <button type="button" className="btn-primary" onClick={onClose}>
              Listo
            </button>
          </>
        ) : (
          <form onSubmit={handleSubmit} noValidate>
            <label className="field">
              <span>Contraseña actual</span>
              <input
                type="password"
                value={currentPassword}
                onChange={(event) => setCurrentPassword(event.target.value)}
                autoComplete="current-password"
              />
            </label>

            <label className="field">
              <span>Nueva contraseña</span>
              <input
                type="password"
                value={newPassword}
                onChange={(event) => setNewPassword(event.target.value)}
                autoComplete="new-password"
              />
            </label>

            <label className="field">
              <span>Confirmar nueva contraseña</span>
              <input
                type="password"
                value={confirmPassword}
                onChange={(event) => setConfirmPassword(event.target.value)}
                autoComplete="new-password"
              />
            </label>

            {error && (
              <p className="form-error" role="alert">
                {error}
              </p>
            )}

            <div className="modal-actions">
              <button type="button" className="btn-ghost" onClick={onClose}>
                Cancelar
              </button>
              <button type="submit" className="btn-primary">
                Guardar
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
}
