import BottomNavigation from '@/components/BottomNavigation';
import { useColorScheme } from '@/hooks/useColorScheme';
import { DarkTheme, DefaultTheme, ThemeProvider, useFocusEffect } from '@react-navigation/native';
import { useFonts } from 'expo-font';
import { Stack, usePathname } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import React from 'react';
import { Alert, BackHandler } from 'react-native';
import { StackAnimationTypes } from 'react-native-screens';

export default function RootLayout() {
  const colorScheme = useColorScheme();
  const pathname = usePathname();
  const tabRoutes = ['home', 'favorite', 'cart', 'notifi', 'setting'];
  const [loaded] = useFonts({
    SpaceMono: require('../assets/fonts/SpaceMono-Regular.ttf'),
  });

  useFocusEffect(
    React.useCallback(() => {
      const onBackPress = () => {
        // Nếu đang ở tab home thì thoát app
        if (pathname === '/home') {
          Alert.alert(
            'Thoát ứng dụng',
            'Bạn có chắc muốn thoát?',
            [
              { text: 'Hủy', style: 'cancel' },
              { text: 'Thoát', onPress: () => BackHandler.exitApp() },
            ]
          );
          return true; // chặn hành vi mặc định
        }

        // Nếu ở tab khác -> chặn back về tab trước
        return true; // chặn hoàn toàn
      };

      const backHandler = BackHandler.addEventListener(
        'hardwareBackPress',
        onBackPress
      );
      return () => backHandler.remove();
    }, [pathname])
  );

  if (!loaded) {
    return null; 
  }

  return (
    <ThemeProvider value={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
      <Stack
        screenOptions={({ route }: any) => {
          const animation =
            tabRoutes.includes(route.name) && route.params?.animation
              ? (route.params.animation as StackAnimationTypes)
              : 'none';
      
          return {
            headerShown: false,
            animation,
          };
        }}
      >
        <Stack.Screen name="home" />
        <Stack.Screen name="favorite" />
        <Stack.Screen name="cart" />
        <Stack.Screen name="notifi" />
        <Stack.Screen name="setting" />
    </Stack>
    {tabRoutes.includes(pathname.replace('/', '')) && <BottomNavigation />}
      <StatusBar style="auto" />
    </ThemeProvider>
  );
}
