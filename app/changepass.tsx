import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, Alert, StyleSheet } from 'react-native';
import { changePassword } from '../lib/auth';
import { useRouter } from 'expo-router';
import { Ionicons } from '@expo/vector-icons';
import Toast from 'react-native-toast-message';
import AsyncStorage from '@react-native-async-storage/async-storage';

export default function ChangePasswordScreen() {
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [showOldPassword, setShowOldPassword] = useState(false);
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const router = useRouter();

  const handleChangePassword = async () => {

    if (!oldPassword || !newPassword || !confirmPassword) {
      Toast.show({
        type: 'error',
        text1: 'Vui lòng điền đầy đủ thông tin',
        position: 'top', // hoặc 'bottom'
        visibilityTime: 3000, // (ms)
      });
      return;
    }
    if (newPassword !== confirmPassword) {
      Toast.show({
        type: 'error',
        text1: 'Mật khẩu xác nhận không khớp',
        position: 'top', 
        visibilityTime: 3000, // (ms)
      });
      return;
    }

    try {
      await changePassword(oldPassword, newPassword);
      Toast.show({
        type: 'success',
        text1: 'Đổi mật khẩu thành công',
        position: 'top', 
        visibilityTime: 3000, // (ms)
      });
      router.replace('/setting');
    } catch (error: any) {
      if (error.message === 'Vui lòng đăng nhập lại để đổi mật khẩu' ||
          error.code === 'custom/requires-relogin') {
        Toast.show({
          type: 'success',
          text1: 'Vui lòng đăng nhập lại để đổi mật khẩu',
          position: 'top', 
          visibilityTime: 3000, // (ms)
        })
        await AsyncStorage.removeItem('isLoggedIn');
        router.replace('/signin');
        return;
      }

      Alert.alert('Lỗi', error.message);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
            <Ionicons name="arrow-back-outline" size={24} onPress={() => router.replace('/setting')} />
            <Text style={styles.title}>Đổi mật khẩu</Text>
            <Text>      </Text>
      </View>

      <View style={styles.inputContainer}>
        <TextInput style={styles.input}  placeholder="Mật khẩu cũ"  secureTextEntry={!showOldPassword}  value={oldPassword}  onChangeText={setOldPassword}/>
        <TouchableOpacity onPress={() => setShowOldPassword(!showOldPassword)}>
          <Ionicons
            name={showOldPassword ? 'eye-off' : 'eye'}
            size={22}
            color="#999"
          />
        </TouchableOpacity>
      </View>
      <View style={styles.inputContainer}>
      <TextInput style={styles.input}  placeholder="Mật khẩu mới"  secureTextEntry={!showNewPassword}  value={newPassword}  onChangeText={setNewPassword}/>
      <TouchableOpacity onPress={() => setShowNewPassword(!showNewPassword)}>
        <Ionicons
          name={showNewPassword ? 'eye-off' : 'eye'}
          size={22}
          color="#999"
        />
      </TouchableOpacity>
      </View>
      <View style={styles.inputContainer}>
      <TextInput style={styles.input}  placeholder="Xác nhận mật khẩu"  secureTextEntry={!showConfirmPassword}  value={confirmPassword}  onChangeText={setConfirmPassword}/>
      <TouchableOpacity onPress={() => setShowConfirmPassword(!showConfirmPassword)}>
        <Ionicons
          name={showConfirmPassword ? 'eye-off' : 'eye'}
          size={22}
          color="#999"
        />
      </TouchableOpacity>
      </View>
      

      <TouchableOpacity style={styles.button} onPress={handleChangePassword}>
        <Text style={styles.buttonText}>Đổi mật khẩu</Text>
      </TouchableOpacity>
    </View>
  );
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    paddingHorizontal: 20,
    paddingTop: 50,
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#f2f2f2',
    borderRadius: 8,
    paddingHorizontal: 12,
    marginVertical: 8,
    marginBottom:20,
  },
  header:{
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: 70,
  },
  title: {
    fontSize: 18,
    fontWeight: '600',
  },
  input: {
    backgroundColor: '#f2f2f2',
    padding: 20,
    borderRadius: 10,
    fontSize: 17,
    marginBottom: 1,
    flex: 1,
  },
  button: {
    backgroundColor: '#FDB813',
    padding: 14,
    borderRadius: 10,
    alignItems: 'center',
    marginTop: 70,
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: '600',
  },
});
