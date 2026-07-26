import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import ChangePasswordModal from './ChangePasswordModal';

const currencyFormatter = new Intl.NumberFormat('en-US', {
  style: 'currency',
  currency: 'USD',
  minimumFractionDigits: 2,
});

export default function Dashboard() {
  const { user, logout } = useAuth();
  const [showModal, setShowModal] = useState(false);

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div className="brand">
          <span className="brand-mark">PB</span>
          <span className="brand-name">ParaBank</span>
        </div>

        <div className="dashboard-user">
          <span className="dashboard-username">{user.name}</span>
          <button type="button" className="btn-ghost" onClick={logout}>
            Cerrar sesión
          </button>
        </div>
      </header>

      <main className="dashboard-main">
        <section className="balance-card">
          <span className="balance-label">Saldo disponible</span>
          <span className="balance-amount">{currencyFormatter.format(user.balance)}</span>
          <div className="balance-footer">
            <span>Cuenta •••• {user.accountNumber.slice(-4)}</span>
          </div>
        </section>

        <section className="account-panel">
          <h2>Tu perfil</h2>
          <dl className="account-details">
            <div>
              <dt>Nombre</dt>
              <dd>{user.name}</dd>
            </div>
            <div>
              <dt>Usuario</dt>
              <dd>{user.username}</dd>
            </div>
            <div>
              <dt>Número de cuenta</dt>
              <dd>{user.accountNumber}</dd>
            </div>
          </dl>

          <button type="button" className="btn-secondary" onClick={() => setShowModal(true)}>
            Cambiar contraseña
          </button>
        </section>
      </main>

      {showModal && <ChangePasswordModal onClose={() => setShowModal(false)} />}
    </div>
  );
}
