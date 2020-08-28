/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react'
import styled from "styled-components/native"
import Printer from "./src/printer/printer"


const View = styled.View`
    flex:1;
    align-items: center;
    justify-content: center;    
    font-size: 24px;        
`

const Button = styled.Button`  
`

function App() {

    function onPress() {
        console.log("button pressed")
        let html = "Hello world"
        Printer.printHtml(html)
            .then(response=> {
                console.log("response",response)
            })
            .catch(error => {
                console.error(error)
            })
    }

    return <View>
        <Button title={"Click me"} onPress={onPress}/>
    </View>
}


export default App;
