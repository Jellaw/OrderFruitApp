import { getImageFromName } from '@/utils/helpers';
import { Ionicons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useFocusEffect } from '@react-navigation/native';
import { useRouter } from 'expo-router';
import { doc, getDoc, updateDoc } from 'firebase/firestore';
import React, { useCallback, useState } from 'react';
import { ActivityIndicator, FlatList, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import Toast from 'react-native-toast-message';
import BottomNavigation from '../components/BottomNavigation';
import { db } from '../lib/firebaseConfig';

export default function CartScreen() {
    const [cartItems, setCartItems] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);
    const router = useRouter();

    const updateQuantity = async (productId: string, newQuantity: number) => {
      if (newQuantity < 1) return; 
    
      const userId = await AsyncStorage.getItem('userId');
      if (!userId) return;
    
      const userRef = doc(db, 'users', userId);
      const userSnap = await getDoc(userRef);
      const cart = userSnap.exists() ? userSnap.data().cart || [] : [];
    
      const updatedCart = cart.map((item: any) =>
        item.productId === productId ? { ...item, quantity: newQuantity } : item
      );
    
      await updateDoc(userRef, { cart: updatedCart });
    
      // Cập nhật lại giỏ hàng sau khi thay đổi
      const updatedItems = await Promise.all(
        updatedCart.map(async (item: any) => {
          const productRef = doc(db, 'products', item.productId);
          const productSnap = await getDoc(productRef);
          return productSnap.exists()
            ? { ...productSnap.data(), quantity: item.quantity, id: item.productId }
            : null;
        })
      );
    
      setCartItems(updatedItems.filter(Boolean));
    };

    const removeFromCart = async (productId: string) => {
      const userId = await AsyncStorage.getItem('userId');
      if (!userId) return;
    
      const userRef = doc(db, 'users', userId);
      const userSnap = await getDoc(userRef);
      const cart = userSnap.exists() ? userSnap.data().cart || [] : [];
    
      const updatedCart = cart.filter((item: any) => item.productId !== productId);

      await updateDoc(userRef, { cart: updatedCart });
    
      setCartItems(prev => prev.filter((item) => item.id !== productId));
      Toast.show({
          type: 'success',
          text1: 'Xóa thành công',
          position: 'top', 
          visibilityTime: 3000, // (ms)
        });
    };

    const handleCheckout = async () => {
      const userId = await AsyncStorage.getItem('userId');
      if (!userId) return;
    
      // Lấy cart từ users
      const userRef = doc(db, 'users', userId);
      const userSnap = await getDoc(userRef);
      const userData = userSnap.data();
    
      if (!userData?.cart || userData.cart.length === 0) {
        console.log('Giỏ hàng trống');
        return;
      }
    
      const cartItems = userData.cart;


      // // Lưu từng sản phẩm vào users/{userId}/cart
      // const cartRef = collection(db, 'users', userId, 'cart');
      // for (const item of cartItems) {
      //   await setDoc(doc(cartRef, item.productId), item);
      // }
    
      // Chuyển sang màn hình order-add
      router.push({
        pathname: '/order-add',
        params: { userId: userRef.id },
      });
      console.log('Đơn hàng mới tạo:', userRef.id, cartItems);
    };
    useFocusEffect(
        useCallback(() => {
        const fetchCart = async () => {
            const userId = await AsyncStorage.getItem('userId');
            if (!userId) return;
    
            const userRef = doc(db, 'users', userId);
            const userSnap = await getDoc(userRef);
            const cart = userSnap.exists() ? userSnap.data().cart || [] : [];
    
            const productPromises = cart.map(async (item: any) => {

            const productRef = doc(db, 'products', item.productId);
            const productSnap = await getDoc(productRef);
            
            return productSnap.exists()
                ? { ...productSnap.data(), quantity: item.quantity, id: item.productId }
                : null;
            });
    
            const products = await Promise.all(productPromises);
            setCartItems(products.filter(Boolean));
            setLoading(false);
        };
    
        fetchCart();
        }, [])
    );

  if (loading) {
    return <ActivityIndicator size="large" style={{ flex: 1 }} />;
  }

  const renderItem = ({ item }: any) => (
    <View style={styles.item}>
        <TouchableOpacity onPress={() => router.push({ pathname: '/product-info', params: { productId: item.id } })}>
            <Image    source={getImageFromName(item.image)} style={styles.image}  />
        </TouchableOpacity>
      <View style={styles.info}>
        <View style={styles.Row1}>
            <TouchableOpacity onPress={() => router.push({ pathname: '/product-info', params: { productId: item.id } })}>
                <Text style={styles.name}>{item.name}</Text>
            </TouchableOpacity>
            <Ionicons name="close" size={25} style={styles.deleteicon} onPress={() => removeFromCart(item.id)}></Ionicons>
        </View>
        <View style={styles.qtyRow}>

            <Text style={styles.quantity}>Số lượng:</Text>

          <TouchableOpacity onPress={() => updateQuantity(item.id, item.quantity - 1)} style={styles.qtyButton}>
            <Text style={styles.qtySymbol}>-</Text>
          </TouchableOpacity>
            <Text>{item.quantity}</Text>
          <TouchableOpacity onPress={() => updateQuantity(item.id, item.quantity + 1)} style={styles.qtyButton}>
            <Text style={styles.qtySymbol}>+</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.Row2}>
            <Text>Thành tiền:</Text>
            <Text style={styles.price}>{(item.price * item.quantity).toFixed(2)}</Text>
        </View>
      </View>
    </View>
  );
  

  const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);


  return (
    <View style={styles.container}>
        <View style={{flex:1}}>
            <Text style={styles.header}>Giỏ hàng của bạn</Text>
            {cartItems.length === 0 ? (
                <Text style={styles.empty}>Giỏ hàng trống</Text>
            ) : (
                <FlatList
                data={cartItems}
                keyExtractor={(item) => item.id}
                renderItem={renderItem}
                contentContainerStyle={styles.list}
                />
            )}
            <View style={styles.total}>
              <Text style={styles.totalText}>Tổng tiền </Text>
              <Text style={styles.totalPrice}>{totalPrice.toFixed(2)}</Text>
            </View>
            <TouchableOpacity style={styles.button} onPress={() => {if (cartItems.length === 0){
              Toast.show({
                type: 'error',
                text1: 'Giỏ hàng trống, vui lòng thêm sản phẩm',
                position: 'top', 
                visibilityTime: 3000, // (ms)
              });
            }else{
              handleCheckout();
            }
            }}>
              <Text style={styles.buttonText}>MUA HÀNG</Text>
            </TouchableOpacity>
      </View>
      <BottomNavigation />
    </View>
  );
}
const styles = StyleSheet.create({
    container: {
      flex: 1,
      paddingTop: 50,
      backgroundColor: '#EEEEEE',
      paddingHorizontal: 20,
    },
    header: {
      fontSize: 22,
      fontWeight: 'bold',
      marginBottom: 20,
      textAlign: 'center',
    },
    list: {
      paddingBottom: 100,
    },
    item: {
      flexDirection: 'row',
      backgroundColor: '#fff',
      marginBottom: 12,
      borderRadius: 10,
      padding: 15,
    },
    image: {
      width: 90,
      height: 80,
      marginRight: 17,
      borderRadius: 7,
    },
    info: {
      flex: 1,
      justifyContent: 'center',
    },
    name: {
      fontSize: 18,
      fontWeight: '600',

    },
    quantity: {
      fontSize: 14,
      color: '#555',
    },
    price: {
      fontSize: 16,
      color: '#228B22',
      marginTop: 4,
      marginRight:17,
    },
    total: {
      justifyContent: 'space-between', // đẩy 2 bên
      flexDirection:'row',
      alignItems: 'center',
      paddingHorizontal: 16,
      paddingTop:15,
    },
    totalText:{
      fontSize: 17,
      fontWeight: 'bold',
    },
    totalPrice:{
      color:'#228B22',
      fontSize:17,
    },
    empty: {
      textAlign: 'center',
      fontSize: 16,
      marginTop: 50,
      color: '#666',
      flex:1,
    },
    qtyRow: {
        justifyContent:'space-between',
        flexDirection: 'row',
        alignItems: 'center',
        marginTop: 6,
        marginBottom: 4,
    },
    qtyButton: {
        backgroundColor: '#eee',
        paddingHorizontal: 10,
        paddingVertical: 4,
        borderRadius: 6,
        marginHorizontal: 8,
    },
        qtySymbol: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    deleteicon:{
        alignSelf: 'flex-start',
        paddingBottom:10,
    },
    Row1:{
        flexDirection:'row',
        justifyContent:'space-between',
        alignItems: 'center',
    },
    Row2:{
        flexDirection:'row',
        justifyContent:'space-between',
        alignItems: 'center',
    },
    button:{
      marginTop:20,
      alignItems:'center',
      backgroundColor:'#FDB813',
      borderRadius:30,
      padding:17,
    },
    buttonText:{
      fontWeight:'bold',
    }
  });
  