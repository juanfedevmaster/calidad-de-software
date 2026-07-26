import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';

const AuthContext = createContext(null);

const USERS_KEY = 'parabank_users';
const SESSION_KEY = 'parabank_session';

function loadUsers() {
  try {
    const raw = localStorage.getItem(USERS_KEY);
    return raw ? JSON.parse(raw) : {};
  } catch {
    return {};
  }
}

function saveUsers(users) {
  localStorage.setItem(USERS_KEY, JSON.stringify(users));
}

function randomBalance() {
  return Math.round((3000 + Math.random() * 15000) * 100) / 100;
}

function randomAccountNumber() {
  let value = '';
  for (let i = 0; i < 10; i += 1) {
    value += Math.floor(Math.random() * 10);
  }
  return value;
}

export function AuthProvider({ children }) {
  const [users, setUsers] = useState(loadUsers);
  const [currentUser, setCurrentUser] = useState(() => {
    const savedUsername = localStorage.getItem(SESSION_KEY);
    const allUsers = loadUsers();
    return savedUsername && allUsers[savedUsername] ? savedUsername : null;
  });

  useEffect(() => {
    saveUsers(users);
  }, [users]);

  const register = useCallback(
    ({ name, username, password }) => {
      const key = username.trim().toLowerCase();
      if (!key) {
        return { ok: false, error: 'El usuario es requerido.' };
      }
      if (users[key]) {
        return { ok: false, error: 'Ese usuario ya existe.' };
      }

      const newUser = {
        name: name.trim(),
        password,
        balance: randomBalance(),
        accountNumber: randomAccountNumber(),
      };

      setUsers((prev) => ({ ...prev, [key]: newUser }));
      localStorage.setItem(SESSION_KEY, key);
      setCurrentUser(key);
      return { ok: true };
    },
    [users],
  );

  const login = useCallback(
    ({ username, password }) => {
      const key = username.trim().toLowerCase();
      const user = users[key];
      if (!user || user.password !== password) {
        return { ok: false, error: 'Usuario o contraseña incorrectos.' };
      }
      localStorage.setItem(SESSION_KEY, key);
      setCurrentUser(key);
      return { ok: true };
    },
    [users],
  );

  const logout = useCallback(() => {
    localStorage.removeItem(SESSION_KEY);
    setCurrentUser(null);
  }, []);

  const changePassword = useCallback(
    ({ currentPassword, newPassword }) => {
      if (!currentUser) {
        return { ok: false, error: 'No hay sesión activa.' };
      }
      const user = users[currentUser];
      if (user.password !== currentPassword) {
        return { ok: false, error: 'La contraseña actual no es correcta.' };
      }
      setUsers((prev) => ({
        ...prev,
        [currentUser]: { ...prev[currentUser], password: newPassword },
      }));
      return { ok: true };
    },
    [currentUser, users],
  );

  const value = {
    user: currentUser ? { username: currentUser, ...users[currentUser] } : null,
    register,
    login,
    logout,
    changePassword,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error('useAuth debe usarse dentro de AuthProvider');
  }
  return ctx;
}
