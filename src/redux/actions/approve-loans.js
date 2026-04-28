



import axios from "axios"



export const APPROVE_LOANS = "APPROVE_LOANS"

export const ApproveLoans = (id) => {

    return async (dispatch) => {

        const response = await axios.get(`http://localhost:8081/api/loan/decision/${id}`, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        })

       

        dispatch({

            type: APPROVE_LOANS,
            payload: response.data

        })
    }
}