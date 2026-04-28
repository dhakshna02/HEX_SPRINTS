import { useEffect, useState } from "react"
import Navbar from "../customer/navbar"
import Sidebar from "../customer/sidebar"
import axios from "axios"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/Deposit/depositcss/deposit.css"
import { useNavigate } from "react-router-dom"
import Footer from "../footer"

const Remarks = () => {

    const accountRemarksApi = "http://localhost:8081/api/remarks/viewing-remarks"
    const loanRemarksApi = "http://localhost:8081/api/remarks/viewing-loan-remakrs"
    const updateRemarksApi = "http://localhost:8081/api/remarks/update-remarks"

    const [accountRemarks, setAccountRemarks] = useState([])
    const [loanRemarks, setLoanRemarks] = useState([])
    const [errMsg, setErrMsg] = useState()
    const [loadingId, setLoadingId] = useState(null)
    const navigate = useNavigate();

    const config = {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("Token")
        }
    }

    // 🔹 Fetch BOTH remarks
    useEffect(() => {

        const fetchRemarks = async () => {
            try {

                const accRes = await axios.get(accountRemarksApi, config)
                const loanRes = await axios.get(loanRemarksApi, config)

                setAccountRemarks(accRes.data)
                setLoanRemarks(loanRes.data)

            } catch (err) {
                setErrMsg(err.message)
            }
        }

        fetchRemarks()

    }, [])

    // 🔹 Update remark
    const updateStatus = async (id, type) => {

        try {
            setLoadingId(id)

            await axios.put(
                `${updateRemarksApi}/INACTIVE/${id}`,
                {},
                config
            )

            // remove from correct list
            if (type === "ACCOUNT") {
                setAccountRemarks(prev => prev.filter(r => r.id !== id))
            } else {
                setLoanRemarks(prev => prev.filter(r => r.id !== id))
            }

        } catch (err) {
            setErrMsg(err.message)
        } finally {
            setLoadingId(null)
        }
    }

    return (
        <div>

            <div className="row">
                <Navbar />
            </div>

            <div className="row">

                <div className="col-2 bg-black min-vh-100">
                    <Sidebar />
                </div>

                <div className="col-sm-9 mt-4">

                    <div className="deposit-container">

                        <div className="deposit-header">
                            <h4>Remarks</h4>
                            <p>Manage account and loan remarks</p>
                        </div>

                        {errMsg && <div className="alert alert-danger">{errMsg}</div>}

                        <div className="deposit-grid">

                            {/* 🔹 ACCOUNT REMARKS */}
                            <h5>Account Remarks</h5>

                            {
                                accountRemarks.length === 0 ?
                                    <p className="deposit-note">No account remarks</p>
                                    :
                                    accountRemarks.map(r => (

                                        <div className="deposit-card mb-3" key={r.id}>

                                            <div className="deposit-info-row">
                                                <span>ID</span>
                                                <span>{r.id}</span>
                                            </div>

                                            <div className="deposit-info-row">
                                                <span>Remark</span>
                                                <span>{r.remarks}</span>
                                            </div>

                                            <button
                                                className="deposit-btn mt-2"
                                                onClick={() => { updateStatus(r.id, "ACCOUNT") ;
                                            navigate("/create-account/identity-proof")}
}
                                            disabled={loadingId === r.id}

                                        >
                                            {loadingId === r.id ? "Processing..." : "Resolve"}
                                        </button>

                                    </div>
                        ))
                            }

                        {/* 🔹 LOAN REMARKS */}
                        <h5 className="mt-4">Loan Remarks</h5>

                        {
                            loanRemarks.length === 0 ?
                                <p className="deposit-note">No loan remarks</p>
                                :
                                loanRemarks.map(r => (

                                    <div className="deposit-card mb-3" key={r.id}>

                                        <div className="deposit-info-row">
                                            <span>ID</span>
                                            <span>{r.id}</span>
                                        </div>

                                        <div className="deposit-info-row">
                                            <span>Remark</span>
                                            <span>{r.remarks}</span>
                                        </div>

                                        <button
                                            className="deposit-btn mt-2"
                                            onClick={() => {
                                                updateStatus(r.id, "LOAN");
                                                navigate("/loan");
                                            }
                                            }
                                            disabled={loadingId === r.id}
                                        >
                                            {loadingId === r.id ? "Processing..." : "Resolve"}
                                        </button>

                                    </div>
                                ))
                        }

                    </div>

                </div>

            </div>
            <div className="col-1">

            </div>

        </div>

<Footer/>
        </div >
    )
}

export default Remarks