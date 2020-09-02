import React from "react";
import {createStackNavigator} from "@react-navigation/stack";
import {NdContainer} from "./NdContainer";

const Stack = createStackNavigator();


export function SnContainer({navigation, name, title, component}) {
    return <Stack.Navigator initialRouteName={name}>
        <Stack.Screen
            name={name}
            component={component}
            options={{
                title: title, //Set Header Title
                headerLeft: () => <NdContainer navigation={navigation}/>,
                headerStyle: {
                    backgroundColor: "#d6e7fc",
                },
                headerTintColor: "#3284ff",
                headerTitleStyle: {
                    fontWeight: "bold",
                },
            }}
        />
    </Stack.Navigator>
}