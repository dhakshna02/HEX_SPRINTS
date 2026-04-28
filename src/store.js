import { applyMiddleware, combineReducers,createStore } from "redux"
import { thunk } from "redux-thunk"
import LoansForManagerReducer from "./redux/reducers/LoansForManagerReducer"
import ApproveLoansByManager from "./redux/reducers/ApproveLoans"


const reducers = combineReducers(
  {
   LoansForManagerReducer : LoansForManagerReducer,
   ApproveLoansByManager :ApproveLoansByManager
  }
)


export const store = createStore(reducers,applyMiddleware(thunk))