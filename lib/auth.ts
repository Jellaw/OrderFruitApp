import { createUserWithEmailAndPassword, sendPasswordResetEmail, signInWithEmailAndPassword } from 'firebase/auth';
import { auth } from './firebaseConfig';

// Đăng ký tài khoản mới
export async function signUp(email: string, password: string) {
  return await createUserWithEmailAndPassword(auth, email, password);
}

// Đăng nhập tài khoản đã có
export async function signIn(email: string, password: string) {
  return await signInWithEmailAndPassword(auth, email, password);
}

// Reset pass
export async function sendPass(email:string) {
  return await sendPasswordResetEmail(auth, email);
}
