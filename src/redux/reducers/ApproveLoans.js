
import { APPROVE_LOANS } from "../actions/approve-loans" 

const initalState = {
    loans : []
}

const ApproveLoansByManager=(state = initalState,action)=>{

     switch(action.type){
        case APPROVE_LOANS:
         return {
            ...state,
            loanDetails : action.payload

         }   

       

        default :
            return state  

    }


    }



export default ApproveLoansByManager