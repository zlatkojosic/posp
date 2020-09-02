import React from "react";
import {View} from "../component/View";
import {TouchableOpacity} from "../component/TouchableOpacity";
import {Image} from "../component/Image";

export function NdContainer({navigation}) {
    function toggleDrawer() {
        console.log("navigation",navigation)
        navigation.navigation.toggleDrawer()
    }

    return <View>
        <TouchableOpacity onPress={() => toggleDrawer()}>
            {/*Donute Button Image */}
            <Image
                source={{uri: 'https://raw.githubusercontent.com/AboutReact/sampleresource/master/drawerWhite.png'}}
            />
        </TouchableOpacity>
    </View>
}