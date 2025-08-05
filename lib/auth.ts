import { createUserWithEmailAndPassword, sendPasswordResetEmail, signInWithEmailAndPassword, updatePassword, EmailAuthProvider, reauthenticateWithCredential  } from 'firebase/auth';
import { auth } from './firebaseConfig';
import { signOut } from 'firebase/auth';
import { router } from 'expo-router';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Toast from 'react-native-toast-message';


// Đăng ký tài khoản mới
export async function signUp(email: string, password: string) {
  try {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);
    return { user: userCredential.user };
  } catch (error: any) {
    throw formatFirebaseError(error);
  }
}

// Đăng nhập tài khoản đã có
export async function signIn(email: string, password: string) {
  try {
    const userCredential = await signInWithEmailAndPassword(auth, email, password);
    return { user: userCredential.user };
  } catch (error: any) {
    throw formatFirebaseError(error);
  }
}

// Gửi email đặt lại mật khẩu
export async function sendPass(email: string) {
  try {
    await sendPasswordResetEmail(auth, email);
    return { success: true };
  } catch (error: any) {
    throw formatFirebaseError(error);
  }
}

// Đổi pass
export async function changePassword(oldPassword: string, newPassword: string) {
  const user = auth.currentUser;
  if (!user || !user.email) {
    await signOut(auth); 
    await AsyncStorage.removeItem('isLoggedIn');
    Toast.show({
      type: 'error',
      text1: 'Vui lòng đăng nhập lại để đổi mật khẩu',
      position: 'top', 
      visibilityTime: 3000, // (ms)
    });
    router.replace('/signin');
    return;
  }

  // Tạo credential từ email và mật khẩu cũ
  const credential = EmailAuthProvider.credential(user.email, oldPassword);

  try {
    // Xác thực lại người dùng
    await reauthenticateWithCredential(user, credential);

    // Đổi mật khẩu nếu xác thực thành công
    await updatePassword(user, newPassword);
    return 'Đổi mật khẩu thành công';
  } catch (error: any) {
     if (error.code === 'auth/invalid-credential') {
      throw new Error('Mật khẩu cũ không đúng.');
    } else if (error.code === 'auth/requires-recent-login') {
      throw new Error('Vui lòng đăng nhập lại để đổi mật khẩu');
    } else {
      throw new Error('Đổi mật khẩu thất bại: ' + error.message);
    }
  }
}

// Hàm format lỗi Firebase cho dễ hiểu
function formatFirebaseError(error: any): Error {
  let message = 'Có lỗi xảy ra';
  switch (error.code) {
    case 'auth/email-already-in-use':
      message = 'Email đã được sử dụng';
      break;
    case 'auth/invalid-email':
      message = 'Email không hợp lệ';
      break;
    case 'auth/weak-password':
      message = 'Mật khẩu quá yếu (ít nhất 6 ký tự)';
      break;
    case 'auth/user-not-found':
      message = 'Không tìm thấy tài khoản';
      break;
    case 'auth/wrong-password':
      message = 'Sai mật khẩu';
      break;
    case 'auth/too-many-requests':
      message = 'Tài khoản bị tạm khóa vì đăng nhập sai quá nhiều';
      break;
  }
  return new Error(message);
}