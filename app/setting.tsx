import { Ionicons, MaterialIcons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useRouter } from 'expo-router';
import React from 'react';
import { FlatList, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import BottomNavigation from '../components/BottomNavigation';
import Toast from 'react-native-toast-message';

export default function SettingsScreen() {
    const router = useRouter();
    const renderItem = ({ item }: any) => (
        <TouchableOpacity style={styles.item} onPress={item.onPress}>
        <View style={styles.iconContainer}>{item.icon}</View>
        <Text style={styles.itemText}>{item.title}</Text>
        <Ionicons name="chevron-forward" size={20} color="#C4C4C4" />
        </TouchableOpacity>
    );
    const handleLogout = async () => {
        await AsyncStorage.removeItem('isLoggedIn');
        Toast.show({
          type: 'success',
          text1: 'Đăng xuất thành công',
          position: 'bottom', // hoặc 'bottom'
          visibilityTime: 3000, // (ms)
        });
        router.replace('/signin');
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
        icon: <MaterialIcons name="list-alt" size={20} color="#8B8B8B" />,
        onPress: () => console.log('Navigate to Orders'),
    },
    {
        id: '3',
        title: 'Đổi mật khẩu',
        icon: <MaterialIcons name="list-alt" size={20} color="#8B8B8B" />,
        onPress: () => router.replace('/changepass'),
    },
    {
        id: '4',
        title: 'Đăng xuất',
        icon: <Ionicons name="log-out-outline" size={20} color="#8B8B8B"/>,
        onPress: handleLogout,
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

      <BottomNavigation></BottomNavigation>
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
    fontSize: 20,
    fontWeight: '600',
    color: '#333',
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
    fontSize: 16,
    color: '#333',
  },
});
