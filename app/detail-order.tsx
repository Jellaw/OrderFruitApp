import { Ionicons } from "@expo/vector-icons";
import { useRouter, useLocalSearchParams } from "expo-router";
import { useEffect } from "react";
import { StyleSheet, Text, View } from "react-native";

export default function DetailOrderScreen(){
    const router = useRouter();
    const {orderId} = useLocalSearchParams();

    useEffect(() => {
        console.log('orderId:',orderId )
    })

    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <Ionicons name="arrow-back-outline" size={24} onPress={() => router.back()}></Ionicons>
                <Text style={styles.title}>Thông tin đơn hàng</Text>
                <Text>      </Text>
            </View>
            <View></View>
        </View>
    );
}

const styles = StyleSheet.create({
    container:{
        paddingTop:50,
        backgroundColor:'#EEEEEE',
        flex:1,
    },
    header:{
        flexDirection:'row',
        alignItems:'center',
        justifyContent:'space-between',
        paddingHorizontal:20,
        paddingBottom:30,
    },
    title:{
        fontSize:18,
        fontWeight:'600',
    }
})