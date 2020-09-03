import React, {useEffect} from "react"
import {View} from "../component/View";
import {Text} from "../component/Text";

import PaymentService from "./PaymentService"

export function PaymentHome() {


    function onSuccessCard(card) {
        console.log("card data", card)
        PaymentService.payment(3,
            paymentSuccess => {
                console.log("paymentSuccess", paymentSuccess)
            },
            paymentError => {
                console.log("paymentError", paymentError)
            },
            error => {
                console.log("error in payment", error)
            },
            output => {
                console.log("payment terminal output", output)
            },
            empty => {

            },
            empty => {

            },
            empty => {

            }
        )

    }



    useEffect(() => {
        PaymentService.readCard(onSuccessCard,
            error => {
                console.log("card error", error)
            },
            state => {
                console.log("card failure state", state)
            },
            output => {
                console.log("card terminal output", output)
            }
        );
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