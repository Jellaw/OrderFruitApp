// components/BottomNavigation.tsx
import React from 'react';
import { View, TouchableOpacity, StyleSheet } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { useRouter, usePathname } from 'expo-router';

export default function BottomNavigation() {
  const router = useRouter();
  const pathname = usePathname();

  const isActive = (route: string) => pathname === route;

  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={() => router.replace('/')}>
        <Ionicons
          name="home-outline"
          size={25}
          color={isActive('/home') ? '#fff' : '#aaa'}
          style={isActive('/home') ? styles.activeIcon : undefined}
        />
      </TouchableOpacity>

      <TouchableOpacity onPress={() => router.replace('/favorite')}>
        <Ionicons
          name="heart-outline"
          size={25}
          color={isActive('/favorite') ? '#fff' : '#aaa'}
          style={isActive('/favorite') ? styles.activeIcon : undefined}
        />
      </TouchableOpacity>

      <TouchableOpacity onPress={() => router.replace('/cart')}>
      <Ionicons
          name="cart-outline"
          size={25}
          color={isActive('/cart') ? '#fff' : '#aaa'}
          style={isActive('/cart') ? styles.activeIcon : undefined}
        />
      </TouchableOpacity>

      <TouchableOpacity onPress={() => router.replace('/notifi')}>
        <Ionicons
          name="notifications-outline"
          size={25}
          color={isActive('/notifications') ? '#fff' : '#aaa'}
          style={isActive('/notifications') ? styles.activeIcon : undefined}
        />
      </TouchableOpacity>

      <TouchableOpacity onPress={() => router.replace('/setting')}>
        <Ionicons
          name="settings-outline"
          size={25}
          color={isActive('/setting') ? '#fff' : '#aaa'}
          style={isActive('/setting') ? styles.activeIcon : undefined}
        />
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
    paddingVertical: 14,
    borderTopColor: '#eee',
    borderTopWidth: 1,
    backgroundColor: '#EEEEEE',
  },
  activeIcon: {
    backgroundColor: '#FDB813',
    padding: 10,
    borderRadius: 25,
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
});
