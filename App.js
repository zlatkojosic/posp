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

const source = { uri: 'https://pos-system-templates.s3.amazonaws.com/test10.pdf', cache: true };

function Button({ title, onPress }) {

    return <StyledButton onPress={onPress}>
        <ButtonTitle>{title}</ButtonTitle>
    </StyledButton>
}

const getPDF = async () => {

    let response = await fetch(
        `https://pos-system-templates.s3.amazonaws.com/test10.pdf`, {
        method: 'GET',

        responseType: 'base64'
    });

    //Create a Blob from the PDF Stream
    const file = new Blob(
        [response.data],
        { type: 'application/pdf' });
    console.log(file)

}


function App() {

    function onPress() {
        console.log("button pressed")
        let htmlFull = "<html><body><p>Hello World</p></body></html>"
        let html = "Hello World"
        getPDF()


        // Printer.printPdf()
        //     .then(response => {
        //         console.log("response", response)
        //     })
        //     .catch(error => {
        //         console.error(error)
        //     })
    }

    return <View>
        <Button title={"Click me"} onPress={onPress} />
    </View>
}




export default App;
