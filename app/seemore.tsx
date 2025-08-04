import { useLocalSearchParams, useRouter } from 'expo-router';
import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, FlatList, Image, TouchableOpacity, Pressable, Dimensions } from 'react-native';
import { collection, getDocs } from 'firebase/firestore';
import { db } from '../lib/firebaseConfig';
import { Ionicons } from '@expo/vector-icons';

const { width } = Dimensions.get('window');
const cardWidth = (width - 60) / 2; // 2 cột, 20px padding mỗi bên và 20px giữa

export default function SeeMoreScreen() {
  const { category } = useLocalSearchParams();
  const [products, setProducts] = useState<any[]>([]);
  const router = useRouter();

  const [sortOption, setSortOption] = useState('popular');
  const [allProducts, setAllProducts] = useState<any[]>([]);

  const sortProducts = (option: string, products: any[]) => {
    switch (option) {
      case 'priceAsc':
        return [...products].sort((a, b) => parseFloat(a.price) - parseFloat(b.price));
      case 'priceDesc':
        return [...products].sort((a, b) => parseFloat(b.price) - parseFloat(a.price));
      case 'az':
        return [...products].sort((a, b) => a.name.localeCompare(b.name));
      default:
        return products; // popular
    }
  };

  useEffect(() => {
    const fetchProducts = async () => {
      const snapshot = await getDocs(collection(db, 'products'));
      const all = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
      const filtered = all.filter((item: any) => item.category === category);
      setAllProducts(filtered);
      setProducts(sortProducts(sortOption, filtered));
    };
    fetchProducts();
  }, []);

  useEffect(() => {
    setProducts(sortProducts(sortOption, allProducts));
  }, [sortOption]);
  
  const getImageFromName = (name: string) => {
    switch (name) {
      case 'banhmi':
      case 'phobo':
      case 'trasua':
      case 'khobo':
        return require('../assets/images/image_coffee.png');
      default:
        return require('../assets/images/icon.png');
    }
  };
  const getCategoryFromName = (name: string) => {
    switch (name){
      case 'foods':
        return 'Đồ ăn';
      case 'drinks':
        return 'Đồ uống';
      case 'fruits':
        return 'Hoa quả';
      case 'snacks':
        return 'Đồ ăn nhanh';
    default:
      return 'Khác';
    }
  }
  return (
    <View style={styles.container}>
      {/* Header */}
        <View style={styles.header}>
            <Ionicons name="arrow-back-outline" size={24} onPress={() => router.back()} />
            <Text style={styles.title}>Tất cả sản phẩm</Text>
            <Text>      </Text>
        </View>

        <View style={styles.filterContainer}>
            {[
                { key: 'popular', label: 'Popular' },
                { key: 'priceAsc', label: 'Price ↑' },
                { key: 'priceDesc', label: 'Price ↓' },
                { key: 'az', label: 'A → Z' },
            ].map(({ key, label }) => (
                <Pressable
                key={key}
                onPress={() => setSortOption(key)}
                style={[
                    styles.filterButton,
                    sortOption === key && styles.filterButtonActive,
                ]}
                >
                <Text
                    style={[
                    styles.filterText,
                    sortOption === key && styles.filterTextActive,
                    ]}
                >
                    {label}
                </Text>
                </Pressable>
            ))}
        </View>

      {/* Grid view */}
      <FlatList
        data={products}
        numColumns={2}
        keyExtractor={(item) => item.id}
        columnWrapperStyle={styles.row}
        renderItem={({ item }) => (
            <Pressable
            onPress={() => console.log('Pressed:', item.name)}
            style={({ pressed }) => [
              styles.card,
              { opacity: pressed ? 0.4 : 1 },
            ]}
          >
            <Image source={getImageFromName(item.image)} style={styles.image} />
            <Text style={styles.name}>{item.name}</Text>
            <Text style={styles.price}>{item.price}</Text>
          </Pressable>
        )}
      />
      {/* Bottom Navigation */}
      <View style={styles.bottomNav}>
        <Ionicons name="home-outline" size={24} color="#aaa" onPress={() => router.replace('/home')}/>
        <Ionicons name="notifications-outline" size={24} color="#aaa" />
        <TouchableOpacity style={styles.addButton}>
          <Text style={styles.addText}>＋</Text>
        </TouchableOpacity>
        <Ionicons name="notifications-outline" size={24} color="#aaa" />
        <Ionicons name="settings-outline" size={24} color="#aaa" onPress={() => router.replace('/setting')}/>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    paddingTop: 50,
    paddingHorizontal: 20,
    backgroundColor: '#EEEEEE',
    flex: 1,
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    marginBottom: 20,
  },
  title: {
    fontSize: 18,
    fontWeight: '600',
  },
  filterContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 12,
    flexWrap: 'wrap',
  },
  filterButton: {
    paddingHorizontal: 12,
    paddingVertical: 6,
    backgroundColor: '#f2f2f2',
    borderRadius: 20,
    marginRight: 8,
    marginBottom: 8,
  },
  filterButtonActive: {
    backgroundColor: '#FDB813',
  },
  filterText: {
    fontSize: 13,
    color: '#555',
  },
  filterTextActive: {
    color: '#fff',
    fontWeight: '600',
  },
  
  row: {
    justifyContent: 'space-between',
    marginBottom: 16,
  },
  card: {
    backgroundColor: '#fff',
    padding: 12,
    borderRadius: 12,
    alignItems: 'center',
    width: cardWidth,
  },
  image: {
    width: 80,
    height: 70,
    marginBottom: 10,
  },
  name: {
    fontWeight: '600',
    fontSize: 14,
  },
  price: {
    color: '#888',
    fontSize: 13,
  },
  bottomNav: {
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
