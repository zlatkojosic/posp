import React, {useEffect} from "react"
import {View} from "../component/View";
import {Text} from "../component/Text";

import PaymentService from "./PaymentService"


export function PaymentHome() {


    useEffect(() => {
        PaymentService.readCard()
            .then(card => {
                console.log("card", card);
                return Promise.payment(3)
            })
            .then(payment => {
                console.log("payment", payment);
                return payment;
            })
            .catch(error => {
                console.log("error",error);
            })
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