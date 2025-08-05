import { useLocalSearchParams, useRouter } from 'expo-router';
import { doc, getDoc } from 'firebase/firestore';
import { db } from '../lib/firebaseConfig';
import React, { useEffect, useState } from 'react';
import { View, Text, ActivityIndicator, Image, StyleSheet, TouchableOpacity } from 'react-native';
import { getImageFromName } from '@/utils/helpers';
import { Ionicons } from '@expo/vector-icons';
import BottomNavigation from '../components/BottomNavigation';
import { ScrollView } from 'react-native';

export default function ProductInfoScreen() {
  const { name } = useLocalSearchParams();
  const [product, setProduct] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [quantity, setQuantity] = useState(1);
  const router = useRouter();

  const handleIncrement = () => setQuantity(prev => prev + 1);
  const handleDecrement = () => {
    if (quantity > 1) setQuantity(prev => prev - 1);
  };

  useEffect(() => {
    if (!name) return;

    const fetchProduct = async () => {
      const docRef = doc(db, 'products', String(name));
      const docSnap = await getDoc(docRef);
      if (docSnap.exists()) {
        setProduct(docSnap.data());
      }
      setLoading(false);
    };

    fetchProduct();
  }, [name]);

  if (loading) {
    return <ActivityIndicator size="large" style={{ flex: 1 }} />;
  }

  if (!product) {
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

        <TouchableOpacity style={styles.addToCartBtn}>
          <Ionicons name="cart" size={20} color="#fff" />
          <Text style={styles.addToCartText}>ADD TO CART</Text>
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
    marginTop: 70,
  },
  addToCartText: {
    color: '#fff',
    fontWeight: '700',
    marginLeft: 10,
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
