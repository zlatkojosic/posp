import React, {useEffect} from "react"
import {View} from "../component/View";
import {Text} from "../component/Text";

import PaymentService from "./PaymentService"

export function PaymentHome() {

    useEffect(() => {
        console.log("calling payment service")
        PaymentService.payment(3)
        console.log("after calling payment service")
    }, [])


    return <View align={"center"} justify={"center"}>
        <Text>Coca Cola 1.00 EUR</Text>
        <Text>Chocolate 2.00 EUR</Text>
        <Text>==================</Text>
        <Text>Total 3.00 EUR</Text>
        <Text></Text>
        <Text fontSize={"32px"}>Checkout</Text>
    </View>

}