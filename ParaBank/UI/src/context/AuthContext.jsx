import React, { createContext, useContext, useState, useEffect, useCallback } from 'react';
import {
  login as apiLogin,
  register as apiRegister,
  getBalance as apiGetBalance,
  changePassword as apiChangePassword,
} from '../api/authApi';

const AuthContext = createContext(null);

const TOKEN_KEY = 'parabank_token';
const USER_KEY = 'parabank_user';

function loadStoredUser() {
  try {
    const raw = localStorage.getItem(USER_KEY);
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

function persistSession(token, user) {
  localStorage.setItem(TOKEN_KEY, token);
  localStorage.setItem(USER_KEY, JSON.stringify(user));
}

function clearSession() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER_KEY);
}

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem(TOKEN_KEY));
  const [user, setUser] = useState(loadStoredUser);
  const [initializing, setInitializing] = useState(Boolean(localStorage.getItem(TOKEN_KEY)));

  const applyAuthResponse = useCallback((authResponse) => {
    const nextUser = {
      name: authResponse.name,
      username: authResponse.username,
      accountNumber: authResponse.accountNumber,
      balance: authResponse.balance,
    };
    persistSession(authResponse.token, nextUser);
    setToken(authResponse.token);
    setUser(nextUser);
  }, []);

  // On load, if there's a stored token, confirm it's still valid against the
  // backend and refresh the balance instead of trusting the cached copy.
  useEffect(() => {
    const storedToken = localStorage.getItem(TOKEN_KEY);
    if (!storedToken) {
      setInitializing(false);
      return;
    }

    apiGetBalance(storedToken)
      .then((balanceResponse) => {
        setUser((prev) => {
          const next = {
            ...(prev || {}),
            accountNumber: balanceResponse.accountNumber,
            balance: balanceResponse.balance,
          };
          localStorage.setItem(USER_KEY, JSON.stringify(next));
          return next;
        });
      })
      .catch(() => {
        clearSession();
        setToken(null);
        setUser(null);
      })
      .finally(() => setInitializing(false));
  }, []);

  const register = useCallback(
    async ({ name, username, password }) => {
      try {
        const response = await apiRegister({ name, username, password });
        applyAuthResponse(response);
        return { ok: true };
      } catch (err) {
        return { ok: false, error: err.message };
      }
    },
    [applyAuthResponse],
  );

  const login = useCallback(
    async ({ username, password }) => {
      try {
        const response = await apiLogin({ username, password });
        applyAuthResponse(response);
        return { ok: true };
      } catch (err) {
        return { ok: false, error: err.message };
      }
    },
    [applyAuthResponse],
  );

  const logout = useCallback(() => {
    clearSession();
    setToken(null);
    setUser(null);
  }, []);

  const changePassword = useCallback(
    async ({ currentPassword, newPassword }) => {
      try {
        await apiChangePassword(token, { currentPassword, newPassword });
        return { ok: true };
      } catch (err) {
        return { ok: false, error: err.message };
      }
    },
    [token],
  );

  const value = {
    user,
    token,
    initializing,
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
