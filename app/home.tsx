import { getCategoryFromName, getImageFromName } from '@/utils/helpers';
import { Ionicons } from '@expo/vector-icons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useFocusEffect } from '@react-navigation/native';
import { useRouter } from 'expo-router';
import {
  onAuthStateChanged
} from 'firebase/auth';
import { collection, getDocs } from 'firebase/firestore';
import React, { useCallback, useEffect, useState } from 'react';
import { Dimensions, FlatList, Image, Pressable, StyleSheet, Text, TextInput, TouchableOpacity, View, } from 'react-native';
import { auth, db } from '../lib/firebaseConfig';
  const { width } = Dimensions.get('window');

  export default function HomeScreen() {
    const router = useRouter();
    const [categories, setCategories] = useState<any[]>([]);
    const [searchText, setSearchText] = useState("");

    useFocusEffect(useCallback(() => {
      const fetchProducts = async () => {
        const snapshot = await getDocs(collection(db, 'products'));
        let products = snapshot.docs.map(doc => ({ productId: doc.id, ...doc.data() as any}));

        // Nếu có từ khóa thì lọc theo tên
        if (searchText.trim() !== "") {
          const keyword = searchText.trim().toLowerCase();
          products = products.filter(p => p.name?.toLowerCase().includes(keyword));
        }
        
        // Nhóm sản phẩm theo category
        const grouped = products.reduce((acc: any, product: any) => {
          const cat = product.category || 'Khác';
          if (!acc[cat]) acc[cat] = [];
          acc[cat].push(product);
          return acc;
        }, {});

        const groupedArray = Object.keys(grouped).map(key => ({
          title: key,
          data: grouped[key],
        }));

        setCategories(groupedArray);
      };

      fetchProducts();
    }, [categories, searchText]));


    useEffect(() => {
      const getUser = async () => {
        const id = await AsyncStorage.getItem('userId');
        console.log(" userId:", id);
      };
      getUser();
    }, []);
    
    useEffect(() => {
      const unsubscribe = onAuthStateChanged(auth, async (user) => {
        const isLoggedIn = await AsyncStorage.getItem('isLoggedIn');
    
        if (user || isLoggedIn === 'true') {
          
        } else {
          router.replace('/signin');
        }
      });
    
      return () => unsubscribe();
    }, []);
    

    
    return (
      <View style={styles.container}>
        <View style={styles.header}>
          <View style={styles.headerLeft}>
          <Image source={require('../assets/images/logo_app.png')} style={styles.logoApp} />
            <Text style={styles.logoText}>MaLuyFood</Text>
          </View>
          <Ionicons name="person-circle-outline" size={30} color="#999" />
        </View>

        <View style={styles.searchContainer}>
          <Ionicons name="search" size={20} color="#aaa" />
          <TextInput placeholder="Tìm kiếm" style={styles.searchInput} value={searchText} onChangeText={setSearchText} />
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
                keyExtractor={(product) => product.productId}
                renderItem={({ item: product }) => (
                  <Pressable
                    onPress={() => router.push({pathname:'/product-info', params: {productId : product.productId}})} 
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

        

      </View>
    );
  }

  const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor:'#EEEEEE',
      paddingTop: 50,
    },
    header: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      paddingHorizontal: 20,
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
      marginHorizontal: 20,
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
      paddingHorizontal: 20,
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

  });
