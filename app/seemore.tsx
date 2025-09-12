import { Ionicons } from '@expo/vector-icons';
import { useLocalSearchParams, useRouter } from 'expo-router';
import { collection, getDocs } from 'firebase/firestore';
import React, { useEffect, useState } from 'react';
import { Dimensions, FlatList, Image, Pressable, StyleSheet, Text, View } from 'react-native';
import BottomNavigation from '../components/BottomNavigation';
import { db } from '../lib/firebaseConfig';
import { getCategoryFromName } from '@/utils/helpers';

const { width } = Dimensions.get('window');
const cardWidth = (width - 60) / 2; // 2 cột, 20px padding mỗi bên và 20px giữa

export default function SeeMoreScreen() {
  const { category } = useLocalSearchParams();
  const [products, setProducts] = useState<any[]>([]);
  const router = useRouter();

  const [sortOption, setSortOption] = useState('popular');
  const [allProducts, setAllProducts] = useState<any[]>([]);

  const categoryParam = Array.isArray(category) ? category[0] : category;

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
    console.log(sortOption)
    setProducts(sortProducts(sortOption, allProducts));
  }, [sortOption]); 

  return (
    <View style={styles.container}>
      {/* Header */}
        <View style={styles.header}>
            <Ionicons name="arrow-back-outline" size={24} onPress={() => router.back()} />
            <Text style={styles.title}>{getCategoryFromName(categoryParam)}</Text>
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
                onPress={() => {setSortOption(key)}}

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
            <Image source={{ uri: item.image}} style={styles.image} resizeMode="cover"/>
            <Text style={styles.name}>{item.name}</Text>
            <Text style={styles.price}>{item.price}</Text>
          </Pressable>
        )}
      />
      <BottomNavigation></BottomNavigation>
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
    borderRadius: 14,
    alignItems: 'center',
    width: cardWidth,
    elevation: 3,
    shadowColor: '#fefefe',
    shadowOpacity: 0.1,
    shadowOffset: { width: 0, height: 1 },
    shadowRadius: 4,
    overflow: 'hidden', // quan trọng để ảnh bo tròn theo card
    paddingBottom: 10,
  },
  image: {
    width: '100%',
    aspectRatio: 1.2, // tùy chỉnh tỷ lệ (1 = vuông, >1 = ngang, <1 = dọc)
    marginBottom: 7
  },
  name: {
    fontWeight: '600',
    fontSize: 14,
  },
  price: {
    color: '#888',
    fontSize: 13,
  },

});
