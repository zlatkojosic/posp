import React from "react";
import styled from "styled-components/native"

const StyledButton = styled.TouchableOpacity`
    background-color: ${props => props.color};   
    padding: 1px 15px 5px 15px;    
    border-radius: 15px;   
`

const ButtonTitle = styled.Text`
    color: white;    
    font-size: 28px;
`

export function Button({title, color, onPress}) {

    return <StyledButton color={color} onPress={onPress}>
        <ButtonTitle>{title}</ButtonTitle>
    </StyledButton>
}
