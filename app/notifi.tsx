import { db } from "@/lib/firebaseConfig";
import { Ionicons } from "@expo/vector-icons";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useRouter } from "expo-router";
import { collection, deleteDoc, doc, onSnapshot, orderBy, query, updateDoc } from "firebase/firestore";
import { useEffect, useState } from "react";
import { FlatList, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import Toast from "react-native-toast-message";


export default function NotifyScreen(){
    const router = useRouter();
    const [notifys, setNotifys] = useState<any[]>([]);


    // Lắng nghe thay đổi realtime Firestore
    useEffect(() => {
        const loadUser = async () => {
          const userId = await AsyncStorage.getItem("userId"); // lấy userId đã lưu
          if (!userId) return;
      
          const q = query(
            collection(db, "users", userId, "notify"),
            orderBy("createdAt", "desc")
          );
      
          const unsub = onSnapshot(q, (snapshot) => {
            const data = snapshot.docs.map((doc) => ({
              id: doc.id,
              ...doc.data(),
            }));
            setNotifys(data);
          });
      
          return () => unsub();
        };
      
        loadUser();
    }, []);

    // Đọc tất cả
    const handleReadAll = async () => {
        const userId = await AsyncStorage.getItem("userId");
        if (!userId) return;
        notifys.forEach(async (item) => {
        const ref = doc(db, "users", userId, "notify", item.id);
        await updateDoc(ref, { read: true });
        });
        Toast.show({
          type: 'success',
          text1: 'Đã đọc tất cả thông báo',
          position: 'top',
        });
    };

    // Xóa tất cả
    const handleDeleteAll = async () => {
        const userId = await AsyncStorage.getItem("userId");
        if (!userId) return;
        notifys.forEach(async (item) => {
        await deleteDoc(doc(db, "users", userId, "notify", item.id));
        });

        Toast.show({
          type: 'success',
          text1: 'Xóa thông báo thành công',
          position: 'top',
        });

    };
    
    // Khi nhấn 1 thông báo
    const handlePressNotify = async (item: any) => {
        const userId = await AsyncStorage.getItem("userId");
        if (!userId) return;
        if (!item.read) {
        const ref = doc(db, "users", userId, "notify", item.id);
        await updateDoc(ref, { read: true });
        }
        // router.push(`/order/${item.orderId}`);
    };

    const renderNotifi = ({ item }: { item: any }) => (
        <TouchableOpacity
          style={[styles.notifyItem, item.read && { opacity: 0.6 }]}
          onPress={() => handlePressNotify(item)}
        >
          <Ionicons
            name="notifications-outline"
            size={24}
            color={item.read ? "#999" : "#555"}
          />
          <Text style={styles.notifyText}>{item.title}</Text>
          {!item.read && <View style={styles.dot} />}
        </TouchableOpacity>
      );

    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.headerTitle}>Thông báo</Text>
            </View>
            <View style={styles.option}>
                <TouchableOpacity style={styles.btn} onPress={handleReadAll}>
                    <Text style={styles.btnTextRead}>Đọc tất cả</Text>
                    <Ionicons name="checkmark-done-outline" size={18} color="blue" />
                </TouchableOpacity>
                <TouchableOpacity style={styles.btn} onPress={handleDeleteAll}>
                    <Text style={styles.btnTextDelete}>Xóa tất cả</Text>
                    <Ionicons name="trash-outline" size={18} color="red" />
                </TouchableOpacity>
            </View>
            <View style={styles.line}>

            </View>
            
            <FlatList
            data={notifys}
            renderItem={renderNotifi}
            keyExtractor={(item) => item.id}
            contentContainerStyle={{ paddingVertical: 20 }}
            />

        </View>
    );
}
const styles = StyleSheet.create({
    container:{
        flex:1,
        paddingTop:50,
        backgroundColor:'#EEEEEE',
    },
    header:{
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingHorizontal: 20,
        paddingBottom: 20,
    },
    headerTitle: {
        fontSize: 22,
        fontWeight: 'bold',
        color: '#333',
    },
    option:{
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingHorizontal: 35,
    },
    btn: {
        flexDirection: "row",
        alignItems: "center",
        borderWidth: 1,
        borderColor: "#999",
        borderRadius: 20,
        paddingVertical: 6,
        paddingHorizontal: 15,
        backgroundColor: "#fff",
        gap: 6, // hoặc dùng marginRight cho icon
      },
      btnTextRead: {
        color: "blue",
        fontSize: 14,
        fontWeight: "500",
      },
      btnTextDelete: {
        color: "red",
        fontSize: 14,
        fontWeight: "500",
      },
      notifyItem: {
        flexDirection: "row",
        alignItems: "center",
        backgroundColor: "#fff",
        marginHorizontal: 20,
        marginBottom: 10,
        padding: 15,
        borderRadius: 12,
        shadowColor: "#000",
        shadowOpacity: 0.05,
        shadowOffset: { width: 0, height: 2 },
        shadowRadius: 4,
        elevation: 2,
      },
      notifyText: {
        marginLeft: 10,
        fontSize: 15,
        color: "#333",
        flexShrink: 1,
      },
      dot: {
        width: 10,
        height: 10,
        borderRadius: 5,
        backgroundColor: "blue",
        marginLeft: "auto", // đẩy dấu chấm sang bên phải
      },
      line:{
        marginTop: 20,
        borderBottomWidth: 0.5,
        borderBottomColor: 'gray'
      }
})