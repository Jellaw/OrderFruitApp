import { useRouter } from 'expo-router';
import React, { useRef, useState } from 'react';
import { Dimensions, FlatList, Image, StyleSheet, Text, TouchableOpacity, View, } from 'react-native';

const { width } = Dimensions.get('window');
 
const fruitData = [
  {
    title: 'Bữa ăn dinh dưỡng',
    description: 'Chọn món nhanh chóng, ngon miệng và đầy đủ dưỡng chất.',
    image: require('../assets/images/image_sandwich.png'),
  },
  {
    title: 'Thức uống giải khát',
    description: 'Gọi món nhanh – uống mát lạnh, sảng khoái cả ngày.',
    image: require('../assets/images/image_coffee.png'),
  },
  {
    title: 'Hoa quả tươi ngon',
    description: 'Giao nhanh trái cây tươi, sạch và ngọt tự nhiên.',
    image: require('../assets/images/image_fruits.png'),
  },
];

export default function IntroScreen() {
  const flatListRef = useRef<FlatList>(null);
  const [pageIndex, setPageIndex] = useState(0);
  const router = useRouter();

  const handleScroll = (event: any) => {
    const index = Math.round(event.nativeEvent.contentOffset.x / width);
    setPageIndex(index);
  };

  const pagination = () =>
    fruitData.map((_, i) => (
      <Text key={i} style={i === pageIndex ? styles.dotActive : styles.dotInactive}>
        ●
      </Text>
    ));

  const goToLastPage = () => {
    flatListRef.current?.scrollToIndex({ index: fruitData.length - 1 });
  };

  return (
    <View style={styles.container}>

      <FlatList
        data={fruitData}
        keyExtractor={(_, i) => i.toString()}
        horizontal
        pagingEnabled
        showsHorizontalScrollIndicator={false}
        onScroll={handleScroll}
        scrollEventThrottle={16}
        ref={flatListRef}
        renderItem={({ item }) => (
          <View style={styles.slide}>
            <Image source={item.image} style={styles.image} resizeMode="contain" />
            <Text style={styles.title}>{item.title}</Text>
            <Text style={styles.description}>{item.description}</Text>
          </View>
        )}
      />

      <View style={styles.footer}>
        <View style={styles.dots}>{pagination()}</View>

        {pageIndex === fruitData.length - 1 ? (
          <TouchableOpacity style={styles.getStartedBtn} onPress={() => router.replace('/home')}>
            <Text style={styles.getStartedText}>Bắt đầu mua hàng</Text>
          </TouchableOpacity>
        ) : (
          <TouchableOpacity style={styles.skipBtn} onPress={goToLastPage}>
            <Text style={styles.skipText}>Bỏ qua</Text>
          </TouchableOpacity>
        )}
      </View>
    </View>
  );
}
const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#fff',
    },
    slide: {
      width: width,
      alignItems: 'center',
      justifyContent: 'center',
      paddingTop: 100,
    },
    image: {
      width: 220,
      height: 220,
      marginBottom: 30,
    },
    title: {
      fontSize: 24,
      fontWeight: '600',
      marginBottom: 10,
      color: '#2C2C2C',
    },
    description: {
      textAlign: 'center',
      color: '#888',
      paddingHorizontal: 20,
      fontSize: 14,
    },
    footer: {
      alignItems: 'center',
      paddingBottom: 50,
    },
    dots: {
      flexDirection: 'row',
      marginBottom: 20,
    },
    dotActive: {
      color: '#FDB813',
      fontSize: 16,
      marginHorizontal: 4,
    },
    dotInactive: {
      color: '#ccc',
      fontSize: 16,
      marginHorizontal: 4,
    },
    skipBtn: {
      marginTop: 10,
    },
    skipText: {
      color: '#888',
      fontWeight: '500',
      fontSize: 14,
    },
    getStartedBtn: {
      backgroundColor: '#FDB813',
      padding: 14,
      borderRadius: 30,
      paddingHorizontal: 40,
    },
    getStartedText: {
      color: '#fff',
      fontWeight: '600',
    },
    backBtn: {
      position: 'absolute',
      top: 60,
      left: 20,
      zIndex: 1,
    },
    backArrow: {
      fontSize: 24,
      color: '#aaa',
    },
  });
  