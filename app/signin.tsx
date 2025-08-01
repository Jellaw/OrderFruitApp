import { useRouter } from 'expo-router';
import React, { useState } from 'react';
import { Alert, SafeAreaView, StyleSheet, Text, TouchableOpacity } from 'react-native';
import InputField from '../components/InputField';
import { signIn } from '../lib/auth';


export default function SignInScreen() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSignIn = async () => {
    try {
      await signIn(email, password);
      Alert.alert('Đăng nhập thành công');
       router.replace('/home');
    } catch (error: any) {
        let message = 'Lỗi đăng nhập';
        if (error.code === 'auth/user-not-found') {
          message = 'Tài khoản không tồn tại';
        } else if (error.code === 'auth/wrong-password') {
          message = 'Mật khẩu không đúng';
        }
        Alert.alert('Đăng nhập thất bại', message);
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.title}>Đăng Nhập</Text>

      <InputField icon="mail" placeholder="Email" value={email} onChangeText={setEmail} />
      <InputField icon="lock" placeholder="Mật Khẩu" secureTextEntry value={password} onChangeText={setPassword} />

      <TouchableOpacity>
        <Text style={styles.forgot}>Quên mật khẩu?</Text>
      </TouchableOpacity>

      <TouchableOpacity style={styles.button} onPress={handleSignIn}>
        <Text style={styles.buttonText}>ĐĂNG NHẬP</Text>
      </TouchableOpacity>

      <TouchableOpacity onPress={() => router.push('/signup')}>
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
