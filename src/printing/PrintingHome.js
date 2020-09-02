import React from "react"

import Printer from "../printer/printer"
import RNHTMLtoPDF from 'react-native-html-to-pdf'
import {decode as atob} from "base-64"
import {View} from "../component/View";
import {Button} from "../component/Button";

var RNFS = require('react-native-fs');


const source = {uri: 'https://pos-system-templates.s3.amazonaws.com/test10.pdf', cache: true};

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


export function PrintingHome() {

    async function createPdf() {
        let options = {
            html: `<head><style>p{font-size:15px;height:12px font-weight:500}</style></head><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p><p>Granatapfel Eistee 15€ Granatapfel Eisteesds</p>`,
            fileName: 'test',
            width: 300,
            height: 1700,
            fonts: ['/fonts/TimesNewRoman.ttf', '/fonts/Verdana.ttf']
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

    function onPrintColl() {
        let coll = ["Hello World 20€sad"]
        Printer.printColl(coll)
            .then(response => {
                console.log("response", response)
            })
            .catch(error => {
                console.error(error)
            })
    }

    return <View>
        <Button title={"Print PDF"} color={"#CC0000"} onPress={onPress}/>
        <Button title={"Print Collection"} color={"#0000CC"} onPress={onPrintColl}/>
    </View>

}