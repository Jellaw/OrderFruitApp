import { useRouter } from 'expo-router';
import React, { useState } from 'react';
import { Alert, SafeAreaView, StyleSheet, Text, TouchableOpacity } from 'react-native';
import InputField from '../components/InputField';
import { signIn } from '../lib/auth';


export default function SignInScreen() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');


  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.title}>Đăng Nhập</Text>

      <InputField icon="mail" placeholder="Email" value={email} onChangeText={setEmail} />

      <TouchableOpacity style={styles.button}>
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
