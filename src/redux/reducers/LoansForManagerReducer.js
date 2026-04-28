
import { GET_ALL_LOANS_MANAGER } from "../actions/get-loans-manager"

const initalState = {
    loans : []
}

const LoansForManagerReducer=(state = initalState,action)=>{

     switch(action.type){
        case GET_ALL_LOANS_MANAGER:
         return {
            ...state,
            loans : action.payload

         }   

       

        default :
            return state  

    }


    }



export default LoansForManagerReducer