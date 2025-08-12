import { getImageFromName } from '@/utils/helpers';
import { Ionicons } from '@expo/vector-icons';
import { useLocalSearchParams, useRouter } from 'expo-router';
import { doc, getDoc, updateDoc, arrayUnion, arrayRemove } from 'firebase/firestore';
import React, { useEffect, useState } from 'react';
import { ActivityIndicator, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import BottomNavigation from '../components/BottomNavigation';
import { db } from '../lib/firebaseConfig';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Toast from 'react-native-toast-message';

export default function ProductInfoScreen() {
  const {productId} = useLocalSearchParams();
  const [product, setProduct] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [quantity, setQuantity] = useState(1);
  const [isFavorite, setIsFavorite] = useState(false);
  const [userId, setUserId] = useState<string | null>(null);
  const router = useRouter();
  

  const handleIncrement = () => setQuantity(prev => prev + 1);
  const handleDecrement = () => {
    if (quantity > 1) setQuantity(prev => prev - 1);
  };

  useEffect(() => {

    const fetchProduct = async () => {
      const userId = await AsyncStorage.getItem('userId');
      setUserId(userId);
      console.log('userid', userId);

      if (!productId) return;
      console.log('productID',productId)

      // Lấy thông tin sản phẩm
      const productRef = doc(db, 'products', String(productId));
      const productSnap = await getDoc(productRef);
      if (productSnap.exists()) {
        setProduct(productSnap.data());
      }


      // Kiểm tra sản phẩm đã được yêu thích chưa
      const userRef = doc(db, 'users', String(userId));
      const userSnap = await getDoc(userRef);
      if (userSnap.exists()) {
        const favorites = userSnap.data().favorites || [];
        setIsFavorite(favorites.includes(productId));
      }

      setLoading(false);
    };

    
    fetchProduct();
  }, [productId]);

  const handleToggleFavorite = async () => {
    if (!userId || !productId) return;
  
    const userRef = doc(db, 'users', userId);
    const productRef = doc(db, 'products', String(productId));
    const userSnap = await getDoc(userRef);
    const favorites = userSnap.exists() ? userSnap.data().favorites || [] : [];
  
    if (isFavorite) {
      // Bỏ yêu thích
      const newFavorites = favorites.filter((id: string) => id !== productId);

      await Promise.all([
      updateDoc(userRef, { favorites: newFavorites }),
      updateDoc(productRef, { likedBy: arrayRemove(userId) }),
    ]);
    
      setIsFavorite(false);
    } else {
      // Thêm vào yêu thích
      await Promise.all([
        updateDoc(userRef, { favorites: arrayUnion(productId) }),
        updateDoc(productRef, { likedBy: arrayUnion(userId) }),
      ]);
      setIsFavorite(true);
    }
  };

  const handleAddToCart = async () => {
    if (!userId || !productId) return;
  
    const userRef = doc(db, 'users', userId);
    const userSnap = await getDoc(userRef);
  
    let cart = userSnap.exists() ? userSnap.data().cart || [] : [];
  
    const existingItemIndex = cart.findIndex((item: any) => item.productId === productId);
  
    if (existingItemIndex !== -1) {
      // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
      cart[existingItemIndex].quantity += quantity;
    } else {
      // Nếu chưa có, thêm sản phẩm mới
      cart.push({ productId, quantity });
    }
  
    await updateDoc(userRef, { cart });
  
    Toast.show({
      type: 'success',
      text1: 'Thêm vào giỏ hàng thành công',
      position: 'top',
      visibilityTime: 3000, // (ms)
    });
  };

  
  if (loading) {
    return <ActivityIndicator size="large" style={{ flex: 1 }} />;
  }

  if (!productId) {
    return (
      <View style={styles.centered}>
        <Text>Không tìm thấy sản phẩm</Text>
      </View>
    );
  }
  

  return (
    
    <View style={styles.container}>
      <View style={styles.header}>
          <Ionicons name="arrow-back-outline" size={24} onPress={() => router.back()} />
          <Text style={styles.title}>Thông tin sản phẩm</Text>
          <Text>      </Text>
      </View>
      <View style={styles.body}>
        <Image source={getImageFromName(product.image)} style={styles.image} />
        
        <Text style={styles.name}>{product.name}</Text>
        <Text style={styles.price}>${(product.price * quantity).toFixed(2)}</Text>

        <View style={styles.quantitySelector}>
          <TouchableOpacity onPress={handleDecrement} style={styles.qtyButton}>
            <Text style={styles.qtySymbol}>-</Text>
          </TouchableOpacity>
          <Text style={styles.qtyText}>{quantity}</Text>
          <TouchableOpacity onPress={handleIncrement} style={styles.qtyButton}>
            <Text style={styles.qtySymbol}>+</Text>
          </TouchableOpacity>
        </View>

        <ScrollView style={styles.descriptionBox}>
          <Text style={styles.description}>{product.description || 'Không có mô tả.'}</Text>
        </ScrollView>

        <TouchableOpacity onPress={handleToggleFavorite} style={styles.addToFavoriteBtn}>
          <Ionicons name={isFavorite ? 'heart' : 'heart-outline'} size={20} color="#fff" />
          <Text style={styles.addToCartText}>
            {isFavorite ? 'Bỏ yêu thích' : 'Thêm vào yêu thích'}
          </Text>
        </TouchableOpacity>


        <TouchableOpacity onPress={handleAddToCart} style={styles.addToCartBtn}>
          <Ionicons name="cart" size={20} color="#fff" />
          <Text style={styles.addToCartText}>Thêm vào giỏ hàng</Text>
        </TouchableOpacity>


      </View>

      <BottomNavigation></BottomNavigation>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { 
    flex: 1, 
    paddingTop: 50,
    backgroundColor:'#EEEEEE',
  },
  centered: { 
    flex: 1, 
    justifyContent: 'center', 
    alignItems: 'center' 
  },
  image: { 
    alignSelf: 'center',
    width: 220, 
    height: 180, 
    marginBottom: 20 
  },
  name: { 
    textAlign: 'center',
    fontSize: 24, 
    fontWeight: 'bold', 
    marginBottom: 10 
  },
  price: { 
    textAlign: 'center',
    fontSize: 20, 
    color: '#888', 
    marginBottom: 10 
  },
  description: { 
    fontSize: 17, 
    textAlign: 'center', 
    color: '#444' 
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 20,
    justifyContent: 'space-between',
    marginBottom: 90,
  },
  title: {
    fontSize: 18,
    fontWeight: '600',
  },
  addToCartBtn: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#FFC107',
    paddingVertical: 14,
    paddingHorizontal: 30,
    borderRadius: 30,
    marginTop: 20,
  },
  addToCartText: {
    color: '#fff',
    fontWeight: '700',
    marginLeft: 10,
  },
  addToFavoriteBtn:{
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#FF00FF',
    paddingVertical: 14,
    paddingHorizontal: 30,
    borderRadius: 30,
    marginTop: 50,
  },
  quantitySelector: {
    alignSelf: 'center',
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 10,
  },
  qtyButton: {
    backgroundColor: '#eee',
    borderRadius: 20,
    paddingHorizontal: 12,
    paddingVertical: 6,
    marginHorizontal: 10,
  },
  qtySymbol: {
    fontSize: 20,
    color: '#333',
  },
  qtyText: {
    fontSize: 16,
    fontWeight: '500',
  },
  body: {
    flex:1,
    paddingHorizontal: 20,
  },
  descriptionBox: {
    maxHeight: 150,
    marginBottom: 0,
  },
});
