import { Ionicons } from "@expo/vector-icons";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useFocusEffect } from '@react-navigation/native';
import { useRouter } from "expo-router";
import { addDoc, collection, doc, getDoc, getDocs, serverTimestamp, updateDoc } from "firebase/firestore";
import { useCallback, useEffect, useState } from "react";
import { FlatList, Image, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import Toast from "react-native-toast-message";
import TabSwitch from '../components/TabSwitch';
import { db } from "../lib/firebaseConfig";

type CartItem = {
  id: string;
  name: string;
  price: number;
  quantity: number;
};
type notify ={
  id: string;
  
}

export default function AddAddressScreen(){
    const router = useRouter();
    const [fullName, setFullName] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [address, setAddress] = useState('');
    const [notify, setNotify] = useState('');
    const [activeTab, setActiveTab] = useState(0);
    const [cards, setCards] = useState<any[]>([]);
    const [selectedPayment, setSelectedPayment] = useState<{ type: string; cardId?: string } | null>(null);
    const [showCards, setShowCards] = useState(false);
    const [cartItems, setCartItems] = useState<CartItem[]>([]);


    const titles = ["Điền thông tin", "Phương thức thanh toán", "Xác nhận thông tin"];
    const nameRegex = /^[A-Za-zÀ-ỹ\s]+$/;
    const phoneRegex = /^0[0-9]{9}$/;

    // Lấy cart đã copy sang orders/orderId/cart
  useEffect(() => {
    const fetchCart = async () => {
      const userId = await AsyncStorage.getItem('userId');
      const orderId = await AsyncStorage.getItem('currentOrderId');
      if (!userId || !orderId) return;

      const cartRef = collection(db, 'orders', orderId, 'cart');
      const snapshot = await getDocs(cartRef);
      const items = snapshot.docs.map(cartDoc => ({
        id: cartDoc.id,
        name: cartDoc.data().name as string,
        price: cartDoc.data().price as number,
        quantity: cartDoc.data().quantity as number,
      }));
      setCartItems(items);
    };
    fetchCart();
  }, []);

    // Load dữ liệu tạm khi quay lại màn hình
    useEffect(() => {
      const loadTempData = async () => {
        const tempFullName = await AsyncStorage.getItem("temp_fullName");
        const tempPhone = await AsyncStorage.getItem("temp_phoneNumber");
        const tempAddress = await AsyncStorage.getItem("temp_address");
        const tempNotify = await AsyncStorage.getItem("temp_notify");

        if (tempFullName) setFullName(tempFullName);
        if (tempPhone) setPhoneNumber(tempPhone);
        if (tempAddress) setAddress(tempAddress);
        if (tempNotify) setNotify(tempNotify);
      };
      loadTempData();
    }, []);

    // Lấy danh sách thẻ từ Firestore
    useFocusEffect(
        useCallback(() => {
            const fetchCards = async () => {
                const userId = await AsyncStorage.getItem("userId");
                if (!userId) return;
                const snap = await getDocs(collection(db, "users", userId, "cards"));
                const data = snap.docs.map(cardDoc => ({ id: cardDoc.id, ...cardDoc.data() }));
                setCards(data);
            };
            if (activeTab === 1) fetchCards();
        }, [activeTab])
    );

    const handleTabChange = async (newIndex: number) => {
      if (newIndex > activeTab) { 
        if (activeTab === 0) {
          // Kiểm tra bước 1
          if (!fullName || !phoneNumber || !address) {
            Toast.show({
              type: 'error',
              text1: 'Vui lòng điền đầy đủ thông tin',
              position: 'top',
            });
            return;
          }
          if (!nameRegex.test(fullName.trim())) {
            Toast.show({
              type: 'error',
              text1: 'Họ tên chỉ được chứa chữ cái',
              position: 'top',
            });
            return;
          }
    
          // Kiểm tra số điện thoại
          if (!phoneRegex.test(phoneNumber.trim())) {
            Toast.show({
              type: 'error',
              text1: 'Số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0',
              position: 'top',
            });
            return;
          }
        }
    
        if (activeTab === 1) {
          // Kiểm tra bước 2
          if (!selectedPayment) {
            Toast.show({
              type: 'error',
              text1: 'Vui lòng chọn phương thức thanh toán',
              position: 'top',
            });
            return;
          }
        }
      }
      setActiveTab(newIndex);
    };
    


   const handleNext = async () => {
    if (activeTab === 0) {
      // Lưu dữ liệu tạm của bước 1
      await AsyncStorage.setItem("temp_fullName", fullName);
      await AsyncStorage.setItem("temp_phoneNumber", phoneNumber);
      await AsyncStorage.setItem("temp_address", address);
      await AsyncStorage.setItem("temp_notify", notify);

      if (!fullName || !phoneNumber || !address) {
        Toast.show({
          type: 'error',
          text1: 'Vui lòng điền đầy đủ thông tin',
          position: 'top',
          visibilityTime: 3000, // (ms)
        });
        return;
      }
      if (!nameRegex.test(fullName.trim())) {
        Toast.show({
          type: 'error',
          text1: 'Họ tên chỉ được chứa chữ cái',
          position: 'top',
        });
        return;
      }

      // Kiểm tra số điện thoại
      if (!phoneRegex.test(phoneNumber.trim())) {
        Toast.show({
          type: 'error',
          text1: 'Số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0',
          position: 'top',
        });
        return;
      }

      setActiveTab(1);
    } else if (activeTab === 1) {
      if (!selectedPayment) {
        Toast.show({
          type: 'error',
          text1: 'Vui lòng chọn phương thức thanh toán',
          position: 'top',
        });
        return;
      }else if(selectedPayment.type === "card" && !selectedPayment.cardId){
        Toast.show({
          type: 'error',
          text1: 'Vui lòng chọn thẻ',
          position: 'top',
        })
        return;
      }
      console.log("Phương thức thanh toán đã chọn:", selectedPayment);
      // Lưu dữ liệu tạm phương thức thanh toán
      await AsyncStorage.setItem("temp_payment", JSON.stringify(selectedPayment));
      setActiveTab(2);
    } else {
      try {
        const userId = await AsyncStorage.getItem("userId");
        if (!userId) {
          Toast.show({
            type: 'error',
            text1: 'Không tìm thấy người dùng',
            position: 'top',
          });
          return;
        }

        const paymentData = selectedPayment?.type === "cash"
          ? { type: "cash" }
          : { type: "card", cardId: selectedPayment?.cardId };

        // 1️⃣ Lấy toàn bộ cart
        const userRef = doc(db, 'users', userId);
        const userSnap = await getDoc(userRef);
        const userData = userSnap.data();

        if (!userData?.cart || userData.cart.length === 0) {
          console.log('Giỏ hàng trống');
          return;
        }
      
        const cartItems = userData.cart;

        const orderData = {
          userId,
          fullName,
          phoneNumber,
          address,
          notify,
          paymentMethod: paymentData,
          items: cartItems,
          createdAt: serverTimestamp(),
        };
    
        await addDoc(collection(db, 'orders'), orderData);

        // Xóa toàn bộ cart
        await updateDoc(doc(db, 'users', userId), {
          cart: []
        });
        Toast.show({
          type: 'success',
          text1: 'Đặt hàng thành công',
          position: 'top',
        });

        // Xóa dữ liệu tạm
        await AsyncStorage.multiRemove([
          "temp_fullName",
          "temp_phoneNumber",
          "temp_address",
          "temp_notify"
        ]);

        setCartItems([]);
        router.replace('/');
      } catch (error) {
        console.log(error);
        Toast.show({
          type: 'error',
          text1: 'Đặt hàng thất bại',
          position: 'top',
        });
      }
    }
  };     

  const updateQuantity = async (productId: string, newQuantity: number) => {
    if (newQuantity < 1) return; 
  
    const userId = await AsyncStorage.getItem('userId');
    if (!userId) return;
  
    const userRef = doc(db, 'users', userId);
    const userSnap = await getDoc(userRef);
    const cart = userSnap.exists() ? userSnap.data().cart || [] : [];
  
    const updatedCart = cart.map((item: any) =>
      item.productId === productId ? { ...item, quantity: newQuantity } : item
    );
  
    await updateDoc(userRef, { cart: updatedCart });
  
    // Cập nhật lại giỏ hàng sau khi thay đổi
    const updatedItems = await Promise.all(
      updatedCart.map(async (item: any) => {
        const productRef = doc(db, 'products', item.productId);
        const productSnap = await getDoc(productRef);
        return productSnap.exists()
          ? { ...productSnap.data(), quantity: item.quantity, id: item.productId }
          : null;
      })
    );
  
    setCartItems(updatedItems.filter(Boolean));
  };

  const removeFromCart = async (productId: string) => {
    const userId = await AsyncStorage.getItem('userId');
    if (!userId) return;
  
    const userRef = doc(db, 'users', userId);
    const userSnap = await getDoc(userRef);
    const cart = userSnap.exists() ? userSnap.data().cart || [] : [];
  
    const updatedCart = cart.filter((item: any) => item.productId !== productId);

    await updateDoc(userRef, { cart: updatedCart });
  
    setCartItems(prev => prev.filter((item) => item.id !== productId));
    Toast.show({
        type: 'success',
        text1: 'Xóa thành công',
        position: 'top', 
        visibilityTime: 3000, // (ms)
      });
  };

  useEffect(() => {
    const fetchCart = async () => {
      const userId = await AsyncStorage.getItem('userId');
      if (!userId) return;

      const userRef = doc(db, 'users', userId);
      const userSnap = await getDoc(userRef);
      const cart = userSnap.exists() ? userSnap.data().cart || [] : [];

      const productPromises = cart.map(async (item: any) => {

      const productRef = doc(db, 'products', item.productId);
      const productSnap = await getDoc(productRef);
      
      return productSnap.exists()
          ? { ...productSnap.data(), quantity: item.quantity, id: item.productId }
          : null;
      });

      const products = await Promise.all(productPromises);
      setCartItems(products.filter(Boolean));
    };
    fetchCart();
  }, []);

  const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const renderItem = ({ item }: any) => (
    <View style={styles.item}>
        <Image source={{ uri: item.image}} style={styles.image} />
      <View style={styles.infoCart}>
        <View style={styles.Row1}>
                <Text style={styles.name}>{item.name}</Text>
            <Ionicons name="close" size={25} style={styles.deleteicon} onPress={() => removeFromCart(item.id)}></Ionicons>
        </View>
        <View style={styles.qtyRow}>

            <Text style={styles.quantity}>Số lượng:</Text>

          <TouchableOpacity onPress={() => updateQuantity(item.id, item.quantity - 1)} style={styles.qtyButton}>
            <Text style={styles.qtySymbol}>-</Text>
          </TouchableOpacity>
            <Text>{item.quantity}</Text>
          <TouchableOpacity onPress={() => updateQuantity(item.id, item.quantity + 1)} style={styles.qtyButton}>
            <Text style={styles.qtySymbol}>+</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.Row2}>
            <Text>Thành tiền:</Text>
            <Text style={styles.price}>{(item.price * item.quantity).toFixed(2)}</Text>
        </View>
      </View>
    </View>
  );
     
    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <Ionicons name="arrow-back-outline" size={24} onPress={() => router.back()} />
                <Text style={styles.title}>{titles[activeTab]}</Text>
                <Text>      </Text>
            </View>

            <TabSwitch activeIndex={activeTab} onChange={handleTabChange} />
            <View style={styles.inputContainer}>

              {activeTab === 0 && (
            <>
              <TextInput style={styles.input} placeholder="Họ tên" value={fullName} onChangeText={setFullName} />
              <TextInput style={styles.input} placeholder="Số điện thoại" value={phoneNumber} onChangeText={setPhoneNumber} />
              <TextInput style={styles.input} placeholder="Địa chỉ" value={address} onChangeText={setAddress} />
              <TextInput style={styles.input} placeholder="Ghi chú" value={notify} onChangeText={setNotify} />
            </>
              )}

              {activeTab === 1 && (
                  <>
                  {/* Phương thức tiền mặt */}
                  <TouchableOpacity
                    style={[
                      styles.cardItem,
                      selectedPayment?.type === "cash" && styles.cardSelected
                    ]}
                    onPress={() => {setSelectedPayment({ type: "cash" });
                    setShowCards(false);
                  }}>

                    <Text style={styles.cardText}>Thanh toán tiền mặt</Text>
                  </TouchableOpacity>

                  <TouchableOpacity
                      style={[
                          styles.cardItem,
                          selectedPayment?.type === "card" && !selectedPayment?.cardId && styles.cardSelected
                      ]}
                      onPress={() => {
                          setShowCards(prev => !prev); // Mở/đóng danh sách thẻ
                          setSelectedPayment({ type: "card" }); // Chỉ định là thanh toán bằng thẻ nhưng chưa chọn thẻ
                      }}
                      >
                      <Text style={styles.cardText}>Thanh toán bằng thẻ</Text>
                  </TouchableOpacity>

                  
                  {/* Danh sách thẻ khi mở */}
                  {showCards && (
                  <FlatList
                      data={cards}
                      renderItem={({ item }) => (
                      <TouchableOpacity
                          style={[
                          styles.cardItem,
                          selectedPayment?.type === "card" &&
                          selectedPayment?.cardId === item.id &&
                          styles.cardSelected
                          ]}
                          onPress={() =>
                          setSelectedPayment({ type: "card", cardId: item.id })
                          }
                      >
                          <Text style={styles.cardText}>{item.cardName}</Text>
                          <Text style={styles.cardNumber}>
                          •••• {item.cardNumber.slice(-4)}
                          </Text>
                      </TouchableOpacity>
                      )}
                      keyExtractor={(item) => item.id}
                      ListFooterComponent={
                      cards.length < 5 ? (
                          <TouchableOpacity
                          style={styles.addCardBtn}
                          onPress={() => router.push("/addcard")}
                          >
                          <Text style={styles.addCardText}>+ Thêm thẻ</Text>
                          </TouchableOpacity>
                      ):null
                      }
                  />
                  )}
                </>
                
              )}

              {activeTab === 2 && (
                <View style={{ flex: 1 }}>
                  <FlatList
                    data={cartItems}
                    keyExtractor={(item) => item.id}
                    renderItem={renderItem}
                    contentContainerStyle={styles.list}
                    style={{ flex: 1 }}
                  />
                  <View style={styles.infoOrder}>
                    <View style={styles.infoConfirm}>
                      <Text style={styles.infoConfirm1}>Họ tên</Text>
                      <Text style={styles.infoConfirm2}>{fullName}</Text>
                    </View>
                    <View style={styles.infoConfirm}>
                      <Text style={styles.infoConfirm1}>SĐT</Text>
                      <Text style={styles.infoConfirm2}>{phoneNumber}</Text>
                    </View>
                    <View style={styles.infoConfirm}>
                      <Text style={styles.infoConfirm1}>Địa chỉ</Text>
                      <Text style={styles.infoConfirm2}>{address}</Text>
                    </View>
                    <View style={styles.infoConfirm}>
                      <Text style={styles.infoConfirm1}>Ghi chú</Text>
                      <Text style={styles.infoConfirm2}>{notify}</Text>
                    </View>
                    <View style={styles.infoConfirm}>
                      <Text style={styles.infoConfirm1}>Phương thức</Text>
                        <Text style={styles.infoConfirm2}>{" "}
                        {selectedPayment?.type === "cash"
                          ? "Tiền mặt"
                          : selectedPayment?.type === "card"
                            ? "Thẻ •••• " + (cards.find(c => c.id === selectedPayment?.cardId)?.cardNumber.slice(-4) || "")
                            : "Chưa chọn"}
                      </Text>
                    </View>
                  </View>
                
              
                  <TouchableOpacity style={styles.button} onPress={handleNext}>
                      <Text style={styles.buttonText}>
                          {activeTab < titles.length - 1 ? "Tiếp theo" : "Hoàn tất"}
                      </Text>
                  </TouchableOpacity>
                  </View>
              )}
            </View>
        </View>
    )
  }

const styles = StyleSheet.create({
    container:{
        paddingTop:50,
        flex:1,
        backgroundColor: '#EEEEEE',
        paddingHorizontal: 20,
    },
    header:{
        justifyContent:'space-between',
        flexDirection:'row',
        alignItems:'center',
        marginBottom:15,
    },
    title:{
        fontSize:17,
        fontWeight:'bold',
    },
    inputContainer:{
        flex:1,
        marginTop:-20,
    },
    input:{
        backgroundColor:'#F0F8FF',
        padding:25,
        borderRadius:30,
        fontSize:16,
        marginBottom: 17,
        paddingHorizontal:30,
        width:'85%',
        alignSelf: 'center',
    },
    button:{
        backgroundColor:'#FDB813',
        borderRadius:30,
        padding:17,
        alignItems:'center',
        width:'85%',
        alignSelf: 'center',
        marginBottom:30,

    },
    buttonText:{
        fontWeight:'bold',
    },
    cardItem: { padding: 20, borderRadius: 12, borderWidth: 1, borderColor: '#ccc', marginBottom: 10, backgroundColor: '#fff' },
    cardSelected: { borderColor: '#FDB813', backgroundColor: '#FFF8E5' },
    cardText: { fontSize: 16, fontWeight: 'bold' },
    cardId:{},
    cardNumber: { fontSize: 14, color: '#555' },
    addCardBtn: { borderWidth: 1, borderColor: '#FDB813', borderRadius: 12, padding: 15, alignItems: 'center' },
    addCardText: { color: '#FDB813', fontWeight: 'bold' },
    item: {
      flexDirection: 'row',
      backgroundColor: '#fff',
      marginBottom: 8,
      borderRadius: 10,
      padding: 10,
    },
    infoCart: {
      flex: 1,
      justifyContent: 'center',
    },
    name: {
      fontSize: 16,
      fontWeight: '600',
    },
    image: {
      width: 70,
      height: 60,
      marginRight: 17,
      borderRadius: 7,
    },
    quantity: {
      fontSize: 14,
    },
    price: {
      fontSize: 14,
      color: '#228B22',
      marginTop: 4,
      marginRight:17,
    },
    Row1:{
      flexDirection:'row',
      justifyContent:'space-between',
      alignItems: 'center',
    },
    Row2:{
      flexDirection:'row',
      justifyContent:'space-between',
      alignItems: 'center',
    },
    qtyRow: {
      justifyContent:'space-between',
      flexDirection: 'row',
      alignItems: 'center',
      marginTop: 6,
      marginBottom: 4,
  },
  qtyButton: {
    backgroundColor: '#eee',
    paddingHorizontal: 10,
    paddingVertical: 4,
    borderRadius: 6,
    marginHorizontal: 8,
  },
    qtySymbol: {
    fontSize: 14,
    fontWeight: 'bold',
  },
  deleteicon:{
    alignSelf: 'flex-start',
    paddingBottom:10,
  },
  list: {
    paddingBottom: 100,
  },
  infoConfirm:{
    justifyContent:'space-between',
    flexDirection: 'row',
    alignItems:'center',
    paddingHorizontal: 16,
    paddingTop: 4,
  },
  infoConfirm1:{

  },
  infoConfirm2:{

  },
  infoOrder:{
    borderTopWidth: 1,
    borderColor: '#ddd',
    paddingVertical: 8,
  }
})
