import React, { useState } from 'react';
import { AuthProvider, useAuth } from './context/AuthContext';
import AnimatedBackground from './components/AnimatedBackground';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import './styles.css';

function Screens() {
  const { user, initializing } = useAuth();
  const [view, setView] = useState('login');

  if (initializing) {
    return (
      <div className="auth-screen">
        <p className="loading-text">Cargando...</p>
      </div>
    );
  }

  if (user) {
    return <Dashboard />;
  }

  return view === 'register' ? (
    <Register onSwitchToLogin={() => setView('login')} />
  ) : (
    <Login onSwitchToRegister={() => setView('register')} />
  );
}

function App() {
  return (
    <AuthProvider>
      <AnimatedBackground />
      <Screens />
    </AuthProvider>
  );
}

export default App;
