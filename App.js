/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react'
import {PermissionsAndroid} from "react-native";
import styled from "styled-components/native"
import {NavigationContainer} from "@react-navigation/native";
import {createStackNavigator} from '@react-navigation/stack';
import {createDrawerNavigator} from '@react-navigation/drawer';
import {PaymentHome} from "./src/payment/PaymentHome";
import {PrintingHome} from "./src/printing/PrintingHome";
import {SnContainer} from "./src/navigation/SnContainer";


const Drawer = createDrawerNavigator();

function App() {


    function homeComponent(navigation) {
        return <SnContainer navigation={navigation} name={"PaymentHome"} title={"Payment"} component={PaymentHome}/>
    }

    function printComponent(navigation) {
        return <SnContainer navigation={navigation} name={"PrintingHome"} title={"Printing"} component={PrintingHome}/>
    }


    return <NavigationContainer>
        <Drawer.Navigator initialRouteName={"home"}>
            <Drawer.Screen name={"home"}
                           component={homeComponent}
                           options={{drawerLabel: "Card reader"}}/>
            <Drawer.Screen name={"printing"}
                           component={printComponent}
                           options={{drawerLabel: "Printing"}}/>
        </Drawer.Navigator>
    </NavigationContainer>


}


export default App;
