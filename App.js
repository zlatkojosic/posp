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
import Printer from "./src/printer/printer"
import RNHTMLtoPDF from 'react-native-html-to-pdf'
import {decode as atob} from "base-64"

var RNFS = require('react-native-fs');

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

const source = {uri: 'https://pos-system-templates.s3.amazonaws.com/test10.pdf', cache: true};

function Button({title, onPress}) {

    return <StyledButton onPress={onPress}>
        <ButtonTitle>{title}</ButtonTitle>
    </StyledButton>
}

const getPDF = async () => {

    console.log("calling async")
    let response = await fetch(
        `https://pos-system-templates.s3.amazonaws.com/test10.pdf`, {
            method: 'GET',

            responseType: 'base64'
        });

    //Create a Blob from the PDF Stream
    const file = new Blob(
        [response.data],
        {type: 'application/pdf'});
    console.log("getting file")
    console.log(file)

}


function App() {

    async function createPdf() {
        let options = {
            html: '<h1>PDF TEST</h1>',
            fileName: 'test',
        };

        let file = await RNHTMLtoPDF.convert(options)
        console.log(file.filePath);
        return file
    }

    function base64ToArray(base64) {
        let raw = atob(base64);
        let rawLength = raw.length;
        //let array = new Uint8Array(new ArrayBuffer(rawLength));
        let result = []
        for (let i = 0; i < rawLength; i++) {
            //array[i] = raw.charCodeAt(i);
            result.push(raw.charCodeAt(i))
        }

        return result
    }


    function onPress() {
        console.log("button pressed")
        let htmlFull = "<html><body><p>Hello World</p></body></html>"
        let html = "Hello World"
        createPdf()
            .then(response => {
                return RNFS.readFile(response.filePath, "base64")
            })
            .then(response => {
                let array = base64ToArray(response)
                //console.log("array", array)
                console.log("array.length", array.length)
                return Printer.printPdf(array)
            })
            .then(response => {
                console.log("response", response)
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
