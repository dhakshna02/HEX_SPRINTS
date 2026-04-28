



import axios from "axios"



export const GET_ALL_LOANS_MANAGER = "GET_ALL_LOANS_MANAGER"

export const getAllLoansForManager = (page,size) => {

   

    return async (dispatch) => {
       
        const response = await axios.get(`http://localhost:8081/api/loan/all-loans?page=${page}&size=${size}`, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        })

        console.log(response.data)

        dispatch({

            type: GET_ALL_LOANS_MANAGER,
            payload:  response.data.loans

        })

        return response.data;
    }
}