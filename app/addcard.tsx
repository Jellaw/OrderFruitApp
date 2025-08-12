import React, { useState } from "react";
import { View, Text, TextInput, TouchableOpacity, StyleSheet } from "react-native";
import { addDoc, collection, serverTimestamp } from "firebase/firestore";
import { db } from "../lib/firebaseConfig";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useRouter } from "expo-router";
import Toast from "react-native-toast-message";


export default function AddCardScreen() {
  const router = useRouter();
  const [cardName, setCardName] = useState("");
  const [cardNumber, setCardNumber] = useState("");
  const [expiryDate, setExpiryDate] = useState("");
  const [securityCode, setSecurityCode] = useState("");

    const saveCard = async () => {

        if (!cardName || !cardNumber || !expiryDate || !securityCode) {
            Toast.show({
                    type: 'error',
                    text1: 'Vui lòng nhập đầy đủ thông tin',
                    position: 'top', 
                    visibilityTime: 3000, // (ms)
                });
            return;
        }

        const userId = await AsyncStorage.getItem("userId");
        if (!userId) return;

        try {
            await addDoc(collection(db, "users", userId, "cards"), {
            cardName,
            cardNumber: cardNumber.replace(/\s+/g, ""),
            expiryDate,
            securityCode,
            createdAt: serverTimestamp()
            });
    
            Toast.show({
                    type: 'success',
                    text1: 'Thêm thẻ thành công',
                    position: 'top', 
                    visibilityTime: 3000, // (ms)
                });
        } catch (error) {
            Toast.show({
                type: 'error',
                text1: 'Lỗi", "Không thể lưu thẻ:'+(error as Error).message,
                position: 'top', 
                visibilityTime: 3000, // (ms)
            });
        }

        router.back();
    };

    const handleCardNumberChange = (text: string) => {
        let cleaned = text.replace(/\D/g, ""); // bỏ ký tự không phải số
        let formatted = cleaned.match(/.{1,4}/g)?.join(" ") || "";
        setCardNumber(formatted);
      };
      
    const handleExpiryCheck = (text: string) => {
        // Chỉ cho phép số
        let cleaned = text.replace(/[^0-9]/g, '');
      
        if (cleaned.length >= 3) {
          cleaned = cleaned.slice(0, 2) + '/' + cleaned.slice(2, 4);
        }
      
        // Giới hạn tối đa 5 ký tự (MM/YY)
        const formatted = cleaned.slice(0, 5);
        setExpiryDate(formatted);
      
        // Khi nhập đủ 5 ký tự mới kiểm tra
        if (formatted.length === 5) {
          const [monthStr, yearStr] = formatted.split('/');
          const month = parseInt(monthStr, 10);
          const year = parseInt(yearStr, 10) + 2000; // YY -> YYYY
      
          const now = new Date();
          const currentMonth = now.getMonth() + 1; // JS getMonth() trả 0–11
          const currentYear = now.getFullYear();
      
          if (
            month < 1 ||
            month > 12 ||
            year < currentYear ||
            (year === currentYear && month < currentMonth)
          ) {
            Toast.show({
                type: 'error',
                text1: 'Ngày hết hạn không hợp lệ',
                position: 'top', 
                visibilityTime: 3000, // (ms)
              });
              setExpiryDate('');
          }
        }
      };
    
      const handleSecurityCheck =(text: string) => {
        let cleaned = text.replace(/[^0-9]/g, '');
        const formatted = cleaned.slice(0, 4);
        setSecurityCode(formatted);
      };
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Thêm thẻ</Text>

      <TextInput style={styles.input} placeholder="Tên chủ thẻ" value={cardName} onChangeText={setCardName} />
      <TextInput style={styles.input} placeholder="Số thẻ" value={cardNumber} onChangeText={handleCardNumberChange} keyboardType="numeric" />
      <TextInput style={styles.input} placeholder="Ngày hết hạn (MM/YY)" value={expiryDate} onChangeText={handleExpiryCheck} keyboardType="numeric" />
      <TextInput style={styles.input} placeholder="Mã bảo mật" value={securityCode} onChangeText={handleSecurityCheck} keyboardType="numeric" secureTextEntry />

      <TouchableOpacity style={styles.continueBtn} onPress={saveCard}>
        <Text style={styles.continueText}>→ CONTINUE</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#fff", padding: 20 },
  title: { fontSize: 20, fontWeight: "bold", marginBottom: 20 },
  input: { borderWidth: 1, borderColor: "#ccc", borderRadius: 10, padding: 10, marginBottom: 15 },
  continueBtn: { backgroundColor: "#FFB800", padding: 15, borderRadius: 30, alignItems: "center", marginTop: 20 },
  continueText: { fontSize: 16, color: "#fff", fontWeight: "bold" }
});
