import { Ionicons } from "@expo/vector-icons";
import axios from "axios";
import { useRouter } from "expo-router";
import { useEffect, useState } from "react";
import { ScrollView, StyleSheet, Text, View, Image, ActivityIndicator } from "react-native";

export default function TestScreen(){
    const router = useRouter();
    const [dogImage, setDogImage] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);


    const fetchDogImage = async () => {
        try {
          setLoading(true);
          const res = await axios.get("https://dog.ceo/api/breeds/image/random");
          setDogImage(res.data.message);
          setError(null);
        } catch (err) {
          console.error("Lỗi khi gọi API:", err);
          setError("Không thể tải ảnh");
        } finally {
          setLoading(false);
        }
      };

      useEffect(() => {
        fetchDogImage();
      }, []);
    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <Ionicons name="arrow-back-outline" size={24} onPress={() => router.replace('/setting')} />
                <Text style={styles.title}>Test</Text>
                <Text>      </Text>
            </View>
            <View style={styles.content}>
                {loading ? (
                <ActivityIndicator size="large" color="#333" />
                ) : error ? (
                <Text style={styles.error}>{error}</Text>
                ) : dogImage ? (
                <Image source={{ uri: dogImage }} style={styles.image} />
                ) : null}
                <Text style={styles.refresh} onPress={fetchDogImage}>
                Tải lại ảnh
                </Text>
            </View>
        </View>
    );
}
const styles = StyleSheet.create({
    container:{
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
    title:{
        fontSize: 22,
        fontWeight: '600',
    },
    content: { flex: 1, justifyContent: "center", alignItems: "center", padding: 20 },
    image: { width: 300, height: 300, borderRadius: 10, marginBottom: 15 },
    refresh: { fontSize: 18, color: "#0066CC", marginTop: 10 },
    error: { color: "red", fontSize: 16 },
})