import { useEffect, useState } from "react"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/accounts/accounts-css/account-grid.css"
import axios from "axios"
import { Link, useNavigate } from "react-router-dom"


const AccountsGrid = () => {


    const navigate = useNavigate();

    const api = "http://localhost:8081/api/account/get-by-userName"

    const [accountDetails, setAccountDetails] = useState([])
    const [errmesg, setErrMesg] = useState(undefined)




    useEffect(() => {


        const fetchAccountDEtails = async () => {


            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            }



            try {
                const response = await axios.get(api, config)
                setAccountDetails(response.data)
                console.log(response.data)


            } catch (err) {
                setErrMesg(err.message)
            }



        }

        fetchAccountDEtails();
    }, [])

    const hasPendingAccount = accountDetails.some(

        (l) => l.accountOpeningStatus === "PENDING"
    );





    return (
        <div>
            <div className="row  g-4 mt-4">

                {/* <!-- CARD 1 --> */}






                <button
                    className="btn-apply-loan"
                    onClick={() => navigate("/create-account/identity-proof")}
                    disabled={hasPendingAccount}
                > Apply New Account</button>

                {hasPendingAccount && (
                    <div style={{ color: "red", marginTop: "8px" }}>
                        Account already initiated. Cannot apply again.
                    </div>
                )}

                {
                    accountDetails.map((a, index) => (


                        <div className="col-md-6" key={index}>
                            <div className="account-card active-card">

                                <div className="card-top">
                                    <span className="badge savings">{a.accountType}</span>
                                    <span className="emoji">🐷</span>
                                </div>

                                <h5 className="acc-name">{a.customerName}</h5>
                                <h3 className="acc-balance">₹{a.balance}</h3>

                                <p className="acc-number">{a.AccountNumber}</p>

                                <div className="acc-meta">
                                    <div>
                                        <small>IFSC</small>
                                        <p>{a.IFSC}</p>
                                    </div>
                                    <div>
                                        <small>BRANCH</small>
                                        <p>{a.branchName}</p>
                                    </div>
                                    <div>
                                        <small>AccountStatus</small>
                                        <p>{a.accountCurrentStatus}</p>
                                    </div>
                                </div>

                                <div className="acc-actions">
                                    <button className="btn-outline"
                                        onClick={() => navigate(`/accounts-dashboard/details/${a.AccountNumber}`)}
                                    >Details</button>
                                    <button className="btn-dark">Transfer</button>
                                </div>

                            </div>



                        </div>
                    ))
                }






            </div>
        </div>
    )
}
export default AccountsGrid