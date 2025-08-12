import { useRouter, useLocalSearchParams } from "expo-router";
import { useEffect } from "react";

export default function DetailOrderScreen(){
    const router = useRouter();
    const {orderId} = useLocalSearchParams();

    useEffect(() => {
        console.log('orderId:',orderId )
    })
}