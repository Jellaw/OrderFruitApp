import { Ionicons, MaterialIcons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useRouter } from 'expo-router';
import React from 'react';
import { FlatList, StyleSheet, Text, TouchableOpacity, View } from 'react-native';

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
        router.replace('/signin');
    };
    const settingsData = [
    {
        id: '1',
        title: 'Tài khoản cá nhân',
        icon: <Ionicons name="person-outline" size={20} color="#8B8B8B" />,
        onPress: () => console.log('Navigate to Account'),
    },
    {
        id: '2',
        title: 'Đơn hàng của bạn',
        icon: <MaterialIcons name="list-alt" size={20} color="#8B8B8B" />,
        onPress: () => console.log('Navigate to Orders'),
    },
    {
        id: '3',
        title: 'Đăng xuất',
        icon: <Ionicons name="log-out-outline" size={20} color="#8B8B8B"/>,
        onPress: handleLogout,
    },
    ];

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <Ionicons name="arrow-back-outline" size={24} color="#333" />
        <Text style={styles.headerTitle}>Cài Đặt</Text>
        <Text>      </Text>
      </View>

      {/* List */}
      <FlatList
        data={settingsData}     
        renderItem={renderItem}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
      />

      {/* Bottom Navigation */}
      <View style={styles.bottomNav}>
        <Ionicons name="home-outline" size={24} color="#aaa" onPress={() => router.replace('/home')}/>
        <Ionicons name="notifications-outline" size={24} color="#aaa" />
        <TouchableOpacity style={styles.addButton}>
          <Text style={styles.addText}>＋</Text>
        </TouchableOpacity>
        <Ionicons name="cart-outline" size={24} color="#aaa" />
        <Ionicons name="settings" size={24} color="#FDB813" />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { 
    flex: 1, 
    backgroundColor: '#EEEEEE',
},
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingTop: 50,
    paddingBottom: 16,
  },
  headerTitle: {
    fontSize: 18,
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
  bottomNav: {
    paddingHorizontal: 20,
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
    paddingVertical: 14,
    borderTopColor: '#eee',
    borderTopWidth: 1,
  },
  addButton: {
    backgroundColor: '#FDB813',
    width: 48,
    height: 48,
    borderRadius: 24,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 12,
  },
  addText: {
    color: '#fff',
    fontSize: 24,
    lineHeight: 28,
  },
});
