import { Ionicons } from '@expo/vector-icons';
import { useRouter } from 'expo-router';
import React from 'react';
import { Dimensions, Pressable, FlatList, Image, StyleSheet, Text, TextInput, TouchableOpacity, View, } from 'react-native';
import { useEffect, useState } from 'react';
import { collection, getDocs } from 'firebase/firestore';
import { db } from '../lib/firebaseConfig';
import AsyncStorage from '@react-native-async-storage/async-storage';

const { width } = Dimensions.get('window');


export default function HomeScreen() {
  const router = useRouter();
  const [categories, setCategories] = useState<any[]>([]);
  useEffect(() => {
    const fetchProducts = async () => {
      const snapshot = await getDocs(collection(db, 'products'));
      const products = snapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));

      // Nhóm sản phẩm theo category
      const grouped = products.reduce((acc: any, product: any) => {
        const cat = product.category || 'Khác';
        if (!acc[cat]) acc[cat] = [];
        acc[cat].push(product);
        return acc;
      }, {});

      // Biến thành array có title và data
      const groupedArray = Object.keys(grouped).map(key => ({
        title: key,
        data: grouped[key],
      }));

      setCategories(groupedArray);
    };

    fetchProducts();
  }, []);
  useEffect(() => {
    const checkLoginStatus = async () => {
      const isLoggedIn = await AsyncStorage.getItem('isLoggedIn');
      if (!isLoggedIn) {
        router.replace('/signin');
      }
    };
  
    checkLoginStatus();
  }, []);

  const getImageFromName = (name: string) => {
    switch (name) {
      case 'banhmi':
        return require('../assets/images/image_coffee.png');
      case 'phobo':
        return require('../assets/images/image_coffee.png');
      case 'trasua':
        return require('../assets/images/image_coffee.png');
      case 'khobo':
        return require('../assets/images/image_coffee.png');
      // ... các ảnh khác
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
        <View style={styles.headerLeft}>
        <Image source={require('../assets/images/logo_app.png')} style={styles.logoApp} />
          <Text style={styles.logoText}>MaLuyFood</Text>
        </View>
        <Ionicons name="person-circle-outline" size={30} color="#999" />
      </View>

      {/* Search */}
      <View style={styles.searchContainer}>
        <Ionicons name="search" size={20} color="#aaa" />
        <TextInput placeholder="Tìm kiếm" style={styles.searchInput} />
      </View>

      {}
      <FlatList
        data={categories}
        keyExtractor={(item) => item.title}
        renderItem={({ item }) => (
          <View style={styles.section}>
            <View style={styles.sectionHeader}>
              <Text style={styles.sectionTitle}>{getCategoryFromName(item.title)}</Text>
              <TouchableOpacity onPress={() => router.push({ pathname: '/seemore', params: { category: item.title } })}>
                <Text style={styles.moreText}>Xem thêm →</Text>
              </TouchableOpacity>

            </View>
            <FlatList
              data={item.data}
              horizontal
              showsHorizontalScrollIndicator={false}
              keyExtractor={(product) => product.id}
              renderItem={({ item: product }) => (
                <Pressable
                  onPress={() => console.log('Pressed:', product.name)}
                  style={({ pressed }) => [
                    styles.card,
                    { opacity: pressed ? 0.4 : 1 },
                  ]}
                >
                  <Image source={getImageFromName(product.image)} style={styles.productImage} />
                  <Text style={styles.productName}>{product.name}</Text>
                  <Text style={styles.productPrice}>{product.price}</Text>
                </Pressable>
              )}
            />
          </View>
        )}
      />

      {/* Bottom Navigation */}
      <View style={styles.bottomNav}>
        <Ionicons name="home" size={24} color="#FDB813" />
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
    flex: 1,
    backgroundColor:'#EEEEEE',
    paddingTop: 50,
    paddingHorizontal: 20,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  headerLeft: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  logoApp:{
    width :40,
    height :40,
    marginRight: 4,
  },
  logoText: {
    fontSize: 20,
    fontWeight: '700',
  },
  searchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#f2f2f2',
    padding: 10,
    borderRadius: 12,
    marginVertical: 16,
  },
  searchInput: {
    marginLeft: 10,
    fontSize: 16,
    flex: 1,
  },
  section: {
    marginBottom: 30,
  },
  sectionHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 10,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: '600',
  },
  moreText: {
    color: '#FDB813',
    fontSize: 14,
  },
  card: {
    backgroundColor: '#fefefe',
    padding: 7,
    borderRadius: 14,
    marginRight: 17,
    alignItems: 'center',
    width: 167,
    elevation: 3,
    shadowColor: '#fefefe',
    shadowOpacity: 0.1,
    shadowOffset: { width: 0, height: 1 },
    shadowRadius: 4,
  },
  productImage: {
    width: 80,
    height: 70,
    marginBottom: 10,
  },
  productName: {
    fontWeight: '600',
    fontSize: 14,
  },
  productPrice: {
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
