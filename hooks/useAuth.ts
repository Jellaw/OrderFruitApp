import { useEffect } from 'react';
import { useRouter } from 'expo-router';
import { onAuthStateChanged } from 'firebase/auth';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { auth } from '../lib/firebaseConfig';

export default function useAuthRedirect(protectedRoute: string = '/home', signinRoute: string = '/signin') {
  const router = useRouter();

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, async (user) => {
      if (user) {
        // Đăng nhập thành công
        await AsyncStorage.setItem('isLoggedIn', 'true');
        await AsyncStorage.setItem('userId', user.uid);
        router.replace(protectedRoute as any);
      } else {
        // Chưa đăng nhập
        await AsyncStorage.removeItem('isLoggedIn');
        await AsyncStorage.removeItem('userId');
        router.replace(signinRoute as any);
      }
    });

    return () => unsubscribe();
  }, []);
}
