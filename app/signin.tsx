import { useRouter } from 'expo-router';
import React, { useState } from 'react';
import { useEffect } from 'react';
import { Alert, SafeAreaView, StyleSheet, Text, TouchableOpacity } from 'react-native';
import InputField from '../components/InputField';
import { signIn } from '../lib/auth';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Toast from 'react-native-toast-message';
import { signOut } from 'firebase/auth';
import { auth } from '../lib/firebaseConfig';
import { onAuthStateChanged } from 'firebase/auth';

export default function SignInScreen() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  useEffect(() => {
    const checkLogin = async () => {
      const isLoggedIn = await AsyncStorage.getItem('isLoggedIn');
      if (isLoggedIn === 'true') {
        router.replace('/home'); 
        const unsubscribe = onAuthStateChanged(auth, async (user) => {
          if (user) {
            await AsyncStorage.setItem('isLoggedIn', 'true');
            await AsyncStorage.setItem('userId', user.uid);
          }
        });  
        return () => unsubscribe();   
      }
    };
    checkLogin();
  }, []);

    const handleSignIn = async () => {
      try {
        const userCredential = await signIn(email, password);
        const user = userCredential;
        await AsyncStorage.setItem('isLoggedIn', 'true'); //lưu trạng thái
        await AsyncStorage.setItem('userId', user.uid);
        Toast.show({
          type: 'success',
          text1: 'Đăng nhập thành công',
          position: 'bottom', // hoặc 'bottom'
          visibilityTime: 3000, // (ms)
        });
        router.replace('/intro');
      } catch (error: any) {
        await AsyncStorage.removeItem('isLoggedIn');
        await AsyncStorage.removeItem('userId');
          Alert.alert('Lỗi', error.message);
      }
    };

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.title}>Đăng Nhập</Text>

      <InputField icon="mail" placeholder="Email" value={email} onChangeText={setEmail} />
      <InputField icon="lock" placeholder="Mật Khẩu" secureTextEntry value={password} onChangeText={setPassword} />

      <TouchableOpacity onPress={() => router.replace('/forgetpass')}>
        <Text style={styles.forgot}>Quên mật khẩu?</Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.button} onPress={handleSignIn}>
        <Text style={styles.buttonText}>ĐĂNG NHẬP</Text>
      </TouchableOpacity>

      <TouchableOpacity onPress={() => router.replace('/signup')}>
        <Text style={styles.linkText}>TẠO TÀI KHOẢN</Text>
      </TouchableOpacity>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingHorizontal: 24,
    justifyContent: 'center',
    backgroundColor: '#fff',
  },
  title: {
    fontSize: 22,
    fontWeight: '600',
    textAlign: 'center',
    marginBottom: 40,
    color: '#593C1F',
  },
  forgot: {
    textAlign: 'right',
    marginVertical: 8,
    color: '#888',
  },
  button: {
    backgroundColor: '#FDB813',
    padding: 15,
    borderRadius: 30,
    alignItems: 'center',
    marginTop: 20,
    shadowColor: '#FDB813',
    shadowOpacity: 0.3,
    shadowOffset: { width: 0, height: 10 },
    shadowRadius: 10,
  },
  buttonText: {
    color: '#fff',
    fontWeight: 'bold',
  },
  linkText: {
    textAlign: 'center',
    marginTop: 30,
    color: '#888',
  },
});
