import { useRouter } from "expo-router";
import { useState } from "react";
import { StyleSheet, View, Text, Touchable, TouchableOpacity } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import BottomNavigation from "@/components/BottomNavigation";

export default function NotifyScreen(){
    const router = useRouter();
    const [notifys, setNotifys] = useState();

    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.headerTitle}>Thông báo</Text>
            </View>
            <View style={styles.option}>
                <TouchableOpacity>
                    <Text>Đọc tất cả</Text>
                </TouchableOpacity>
                <TouchableOpacity>
                    <Text>Xóa tất cả</Text>
                </TouchableOpacity>
            </View>
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
        paddingBottom: 30,
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
})