import { useEffect, useState } from "react"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/accounts/accounts-css/account-detail.css"
import axios from "axios"
import { useParams } from "react-router-dom"

const AccountDetails = () => {

    const { id } = useParams()   

    const api = "http://localhost:8081/api/account/account-details/"


    const [accountDetails, setAccountDetails] = useState(undefined)
    const [errMesg, setErrMesg] = useState(undefined)
    const [loading, setLoading] = useState(true)

    useEffect(() => {

        const fetchAccountDetails = async () => {

            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            }

            try {
                const response = await axios.get(api + id, config)
                setAccountDetails(response.data)
            } catch (err) {
                setErrMesg("Failed to load account details")
            } finally {
                setLoading(false)
            }

        }

        fetchAccountDetails()

    }, [id])

    const CloseAccount = async(id)=>{

        console.log(id)
      
        const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            }

        const response = await axios.put(`http://localhost:8081/api/account/close?id=${id}&status=INACTIVE`,{},config)

        console.log(response.data)
    }

    return (
        <div className="container mt-4">

            {
                loading && (
                    <div className="alert alert-info">
                        Loading account details...
                    </div>
                )
            }

            {
                errMesg && (
                    <div className="alert alert-danger">
                        {errMesg}
                    </div>
                )
            }

            {
                accountDetails && (

                    <div className="row">

                        <div className="col-md-12">
                            <div className="account-info-card">

                                {/* HEADER */}
                                <div className="info-header">
                                    <h5>Account Info</h5>
                                </div>

                                {/* STATUS */}
                                <div className="info-row">
                                    <span>Status</span>
                                    <span
                                        className={`status ${
                                            accountDetails.accountCurrentStatus === "ACTIVE"
                                                ? "active"
                                                : "inactive"
                                        }`}
                                    >
                                        {accountDetails.accountCurrentStatus}
                                    </span>
                                </div>

                                <div className="info-row">
                                    <span>Account Holder</span>
                                    <span>{accountDetails.customerName}</span>
                                </div>

                                <div className="info-row">
                                    <span>Opened On</span>
                                    <span>{accountDetails.openDate}</span>
                                </div>

                                <div className="info-row">
                                    <span>Account Type</span>
                                    <span>{accountDetails.accountType}</span>
                                </div>

                                <div className="info-row">
                                    <span>IFSC Code</span>
                                    <span>{accountDetails.IFSC}</span>
                                </div>

                                <div className="info-row">
                                    <span>Branch</span>
                                    <span>{accountDetails.branchName}</span>
                                </div>

                                <div className="info-row">
                                    <span>Account Number</span>
                                    <span>{accountDetails.AccountNumber}</span>
                                </div>

                                <div className="info-row">
                                    <span>Balance</span>
                                    <span className="balance">₹{accountDetails.balance}</span>
                                </div>

                                 <div className="info-row">
                                   <button className="btn btn-danger" disabled={accountDetails.accountCurrentStatus==="INACTIVE"} onClick={()=>CloseAccount(accountDetails.AccountNumber)} > close Account</button>
                                </div>

                            </div>
                        </div>

                    </div>

                )
            }

        </div>
    )
}

export default AccountDetails