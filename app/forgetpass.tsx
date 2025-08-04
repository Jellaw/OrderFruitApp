import { useRouter } from 'expo-router';
import React, { useState } from 'react';
import { Alert, SafeAreaView, StyleSheet, Text, TouchableOpacity } from 'react-native';
import InputField from '../components/InputField';
import { sendPass } from '../lib/auth';


export default function ForgetPassScreen() {
  const router = useRouter();
  const [email, setEmail] = useState('');

  const handleReset = async () => {
    if (!email || !email.includes('@')) {
      Alert.alert('Vui lòng nhập email hợp lệ');
      return;
    }
    try {
      await sendPass(email);
      Alert.alert('Vui lòng kiểm tra email để đặt lại mật khẩu');
      router.replace('/signin');
    } catch (error: any) {
      let message = 'Lỗi gửi email';
      if (error.code === 'auth/user-not-found') {
        message = 'Tài khoản không tồn tại';
      }
      Alert.alert('Gửi mật khẩu thất bại', message);
    }
  };
  

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.title}>Quên mật khẩu</Text>

      <InputField icon="mail" placeholder="Email" value={email} onChangeText={setEmail} />

      <TouchableOpacity style={styles.button} onPress={handleReset}>
        <Text style={styles.buttonText}>Reset mật khẩu</Text>
      </TouchableOpacity>

      <TouchableOpacity onPress={() => router.push('/signin')}>
        <Text style={styles.linkText}>Quay lại đăng nhập</Text>
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
