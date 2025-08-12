import { createContext, useContext, useEffect, useState } from 'react';
import { onAuthStateChanged, getAuth, User } from 'firebase/auth';
import { app } from '../lib/firebaseConfig';

const AuthContext = createContext<{ userId: User | null; initializing: boolean }>({
    userId: null,
  initializing: true,
});

export const AuthProvider = ({ children }: any) => {
  const [userId, setUserId] = useState<User | null>(null);
  const [initializing, setInitializing] = useState(true);

  useEffect(() => {
    const auth = getAuth(app);
    const unsubscribe = onAuthStateChanged(auth, (userId) => {
        setUserId(userId);
      setInitializing(false); // ✅ Đợi xác định user xong mới render app
    });

    return () => unsubscribe();
  }, []);

  return (
    <AuthContext.Provider value={{ userId, initializing }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
