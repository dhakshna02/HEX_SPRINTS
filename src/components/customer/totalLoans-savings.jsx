import axios from "axios"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"

const TotalLoansAndSavingsWidget=()=>{


    const navigate = useNavigate();

    const api = "http://localhost:8081/api/loan/active-loan-amount"

    const savingApi = "http://localhost:8081/api/account/saving-balance"

    const [loanData,setLoanDAta] = useState(undefined)
    const[errMesg,setErrMesg] = useState(undefined)
    const [savingWidget,setSavingWidger] = useState(undefined)


    useEffect(()=>{

        const loan = async() =>{

            const config ={
                headers:{
                    "Authorization":"Bearer "+ localStorage.getItem("Token")

                }
            }


            try{
            const response = await axios.get(api,config)
            setLoanDAta(response.data)
            console.log(response.data)



            const response1 = await axios.get(savingApi,config)
            setSavingWidger(response1.data)
            console.log(response1.data)

            }
            catch(err){
                    setErrMesg(err.response.message)
            }
        }

        loan()


    },[])



    return (

        <div>
             {/*  left box */}
                    <div className="row g-3 mt-2">


                        <div className="col-md-6 d-flex flex-column gap-3">

                            {/* TOTAL LOANS */}
                            <div className="stat-card"
                            onClick={() => navigate("/loan")}
                        style={{ cursor: "pointer" }}>
                                <div className="stat-label">TOTAL LOANS</div>
                                <div className="stat-value text-warning">₹{(loanData?.loanValue ?? "").toLocaleString("en-IN")}</div>
                                <div className="stat-change text-secondary">
                                    {loanData?.no_of_Loans ?? "0"} Active loans
                                </div>
                            </div>



                        </div>

                        <div className="col-md-6"
                        onClick={() => navigate("/accounts-dashboard")}
                        style={{ cursor: "pointer" }}>
                            <div className="stat-card h-100">
                                <div className="stat-label">SAVINGS</div>
                                <div className="stat-value text-beige">₹{(savingWidget?.savings ?? "0.00").toLocaleString("en-IN")}</div>
                                <div className="stat-change text-green">
                                      {savingWidget?.accounts ?? "0"} Active Accounts
                                </div>
                            </div>
                        </div>


                        






                    </div>

        </div>
    )
}


export default TotalLoansAndSavingsWidget