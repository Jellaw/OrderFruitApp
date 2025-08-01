import { useEffect } from 'react';
import { useRouter } from 'expo-router';

export default function RedirectHome() {
  const router = useRouter();

  useEffect(() => {
    router.replace('/signin');
  }, []);

  return null;
}
