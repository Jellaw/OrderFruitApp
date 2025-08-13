import { Ionicons } from "@expo/vector-icons";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useRouter } from 'expo-router';
import { collection, getDocs, query, where } from 'firebase/firestore';
import React, { useEffect, useState } from "react";
import { FlatList, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { db } from '../lib/firebaseConfig';

  
  type Order = {
    id: string;
    userId: string;
  };

export default function HistoryScreen(){
    const router = useRouter();
    const [orders, setOrders] = useState<Order[]>([]);

    useEffect(() => {
        const fetchOrder = async() =>{

        const userId = await AsyncStorage.getItem('userId');
        if (!userId) return;

        try {
            const ordersRef = collection(db, 'orders');
            const q = query(ordersRef, where('userId', '==', userId));
    
            const querySnapshot = await getDocs(q);
            const ordersData: Order[] = querySnapshot.docs.map(docOrders => ({
                id: docOrders.id,
                userId: docOrders.data().userId
              })) as Order[];
    
            console.log('Orders:', ordersData);
            setOrders(ordersData); 
          } catch (error) {
            console.error('Lỗi lấy orders:', error);
          }
        };
        fetchOrder();
    }, []);

    const renderItem = ({item} : any) =>(
        <TouchableOpacity style={styles.item} onPress={() => router.push( {pathname: '/detail-order', params:{orderId : item.id}})}>
            <View style ={{flexDirection:'row'}}>
            <Text style={styles.itemText}>Đơn hàng </Text>
            <Text style={styles.codeOrder}>#{item.id.slice(-5)}</Text>
            </View>
            <Ionicons name="chevron-forward" size={20} color="#C4C4C4" />
        </TouchableOpacity>
    );
  
    return(
        <View style ={styles.container}>
            <View style={styles.header}>
                <Ionicons name="arrow-back-outline" size={24} onPress={() => router.replace('/setting')} />
                <Text style={styles.title}>Danh sách đơn hàng</Text>
                <Text>      </Text>
            </View>
            {orders.length === 0? (
                <Text style={styles.empty}>Bạn chưa có đơn hàng nào</Text>
            ):(
                <FlatList 
                data={orders}
                keyExtractor={(item) => item.id}
                renderItem={renderItem}
                contentContainerStyle={styles.list}
                />
            )}
            
            
        </View>
    );
}
const styles = StyleSheet.create({
    container: { 
        flex: 1,
        backgroundColor: '#EEEEEE',
        paddingTop: 50,
    },
    header:{
        flexDirection: 'row',
        alignItems: 'center',
        paddingHorizontal: 20,
        justifyContent: 'space-between',
        paddingBottom:30,
    },
    title: {
        fontSize: 18,
        fontWeight: '600',
    },
    item:{
        height:70,
        justifyContent:'space-between',
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#FAF9F8',
        padding: 17,
        borderRadius: 12,
        marginBottom: 12,
    },
    itemText:{
        paddingLeft:30,
        fontSize:17,
    },
    list: {
        paddingHorizontal: 20,
    },
    infoOrder:{
        
    },
    codeOrder:{
        fontSize:17,
        fontWeight:'300',
        color:'#FF4500',
    },
    empty:{
        marginTop:40,
        fontSize:20,
        alignItems: 'center',
        textAlign:'center',

    }
})