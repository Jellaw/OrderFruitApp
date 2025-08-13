import { useRouter } from "expo-router";
import { useState } from "react";
import { StyleSheet, View, Text } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import BottomNavigation from "@/components/BottomNavigation";

export default function NotifyScreen(){
    const router = useRouter();
    const [notifys, setNotifys] = useState();

    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.headerTitle}>Cài Đặt</Text>
            </View>
            <BottomNavigation></BottomNavigation>
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
        fontSize: 20,
        fontWeight: '600',
        color: '#333',
    },
})