import styled, {css} from "styled-components/native"

export const View = styled.View`
    flex:1;
    align-items: center;
    justify-content: space-around;    
    font-size: 24px;
    align-items:${props => {
    switch (props.align) {
        case "start":
            return "flex-start"
        case "center":
            return "center";
        case "end":
            return "flex-end";
        default: 
            return "flex-start";
    }
    }}; 
    justify-content: ${props => {
    switch (props.justify) {
        case "start":
            return "flex-start"
        case "center":
            return "center";
        case "between":
            return "space-between";
        case "around":
            return "space-around";
        case "evenly":
            return "space-evenly";
        default:
            return "flex-start"
    }
}} ;        
`
