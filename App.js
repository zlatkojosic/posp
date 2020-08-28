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

const StyledButton = styled.TouchableOpacity`
    background-color: #CC0000;   
    padding: 1px 15px 5px 15px;
    
    border-radius: 15px;   
`

const ButtonTitle = styled.Text`
    color: white;    
    font-size: 28px;
`

function Button({title,onPress}){

    return <StyledButton onPress={onPress}>
        <ButtonTitle>{title}</ButtonTitle>
    </StyledButton>
}

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
