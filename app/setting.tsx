import { Ionicons, MaterialIcons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useRouter } from 'expo-router';
import React, { useEffect, useState } from 'react';
import { Alert, FlatList, Switch, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import BottomNavigation from '../components/BottomNavigation';
import Toast from 'react-native-toast-message';
import { signOut } from 'firebase/auth';
import { auth } from '../lib/firebaseConfig';

export default function SettingsScreen() {
    const router = useRouter();
    const [biometricEnabled, setBiometricEnabled] = useState(false);
    
    useEffect(() => {
      const loadSetting = async () => {
        const saved = await AsyncStorage.getItem('biometricEnabled');
        setBiometricEnabled(saved === 'true');
      };
      loadSetting();
    }, []);

    const toggleBiometric = async (value: boolean) => {
      setBiometricEnabled(value);
      await AsyncStorage.setItem('biometricEnabled', value.toString());
    };

    const renderItem = ({ item }: any) => (
      <TouchableOpacity
        style={styles.item}
        onPress={item.onPress}
        disabled={item.switch}
      >
        <View style={styles.iconContainer}>{item.icon}</View>
        <Text style={styles.itemText}>{item.title}</Text>
    
        {item.switch ? (
          <Switch value={biometricEnabled} onValueChange={toggleBiometric} />
        ) : (
          <Ionicons name="chevron-forward" size={20} color="#C4C4C4" />
        )}
      </TouchableOpacity>
    );
    const handleLogout = async () => {
      try {
        await signOut(auth); // đăng xuất khỏi Firebase
        await AsyncStorage.removeItem('isLoggedIn');
        await AsyncStorage.removeItem('userId');
        Toast.show({
          type: 'success',
          text1: 'Đăng xuất thành công',
          position: 'bottom',
          visibilityTime: 3000,
        });
        router.replace('/signin');
      } catch (err: any) {
        Alert.alert('Lỗi', err.message);
      }
    };
    const settingsData = [
    {
        id: '1',
        title: 'Thông tin cá nhân',
        icon: <Ionicons name="person-outline" size={20} color="#8B8B8B" />,
        onPress: () => console.log('Navigate to Account'),
    },
    {
        id: '2',
        title: 'Lịch sử mua hàng',
        icon: <Ionicons name="list-outline" size={20} color="#8B8B8B" />,
        onPress: () => router.replace('/history-order'),
    },
    {
      id: '3',
      title: 'Sinh trắc học',
      icon: <Ionicons name="options-outline" size={20} color="#8B8B8B"/>,
       switch: true,
    },
    {
        id: '4',
        title: 'Đổi mật khẩu',
        icon: <Ionicons name="key-outline" size={20} color="#8B8B8B" />,
        onPress: () => router.replace('/changepass'),
    },
    {
        id: '5',
        title: 'Đăng xuất',
        icon: <Ionicons name="log-out-outline" size={20} color="#8B8B8B"/>,
        onPress: handleLogout,
    },
    {
      id: '6',
      title: '',
      icon: <Ionicons name="log-out" size={20} color="#8B8B8B"/>,
      onPress: () => router.replace('/test'),
    },
    ];

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <Text style={styles.headerTitle}>Cài Đặt</Text>
      </View>

      {/* List */}
      <FlatList
        data={settingsData}     
        renderItem={renderItem}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
      />

    </View>
  );
}

const styles = StyleSheet.create({
  container: { 
    flex: 1, 
    backgroundColor: '#EEEEEE',
    paddingTop: 50,
},
  header: {
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingBottom: 30,
  },
  headerTitle: {
    fontSize: 22,
    fontWeight: 'bold',
  },
  list: {
    paddingHorizontal: 20,
  },
  item: {
    height:70,
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#FAF9F8',
    padding: 17,
    borderRadius: 12,
    marginBottom: 12,
  },
  iconContainer: {
    width: 30,
    alignItems: 'center',
    marginRight: 12,
  },
  itemText: {
    flex: 1,
    fontSize: 17,
    color: '#333',
  },
});
