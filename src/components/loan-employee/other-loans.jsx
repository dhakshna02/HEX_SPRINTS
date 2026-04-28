import { useEffect, useState } from "react"
import axios from "axios"
import { useNavigate, useParams } from "react-router-dom"
import SidebarEmployee from "../employee/sidebar-Employee"
import Navbar from "../customer/navbar"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/loan-employee/loan-employee/other-loans.css"
import Footer from "../footer"

const OtherLoans = () => {

    const { id } = useParams()
    const api = `http://localhost:8081/api/loan/get-other-loan/${id}`
    const updateApi = "http://localhost:8081/api/loan/verify-and-riskRates"

    const [loan, setLoan] = useState(undefined)
    const [risk, setRisk] = useState(undefined)
    const [err, setErr] = useState(undefined)
    const [incomeCertificate, setIncomeCertificate] = useState(undefined)
    const navigate = useNavigate()

    useEffect(() => {

        const fetchLoan = async () => {

            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            }

            try {
                const res = await axios.get(api, config)
                setLoan(res.data)
                console.log(res.data.incomeCertificate)
                setIncomeCertificate(res.data.incomeCertificate)
            } catch (e) {
                setErr("Failed to load loan")
            }
        }

        fetchLoan()

    }, [])


    const updateRiskRate = async () => {
        if (!risk) {
            setErr("Select risk rating");
            return;
        }

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        const response = await axios.put(updateApi, {
            "loanId": id,
            "riskRating": risk
        }, config)

        console.log(response.data)
        alert("Successful");
        navigate("/financial-analyst-loans")

    }

    return (

        <div>
            <div className="row">

                <Navbar />
            </div>

            <div className="row">

                <div className="col-2 bg-black min-vh-100">

                    <SidebarEmployee />
                </div>

                <div className="col-md-9">

                    {err && <div className="alert alert-danger mt-4">{err}</div>}

                    {
                        loan && (
                            <div className="row g-4 ms-4 mt-4">

                                {/* MAIN CARD */}
                                <div className="col-md-6">
                                    <div className="loan-card">

                                        <h5>Loan Details</h5>

                                        <p><b>ID:</b> {loan.loanId}</p>
                                        <p><b>Type:</b> {loan.loanType}</p>
                                        <p><b>Amount:</b> ₹{loan.requestLoanAmount}</p>

                                        <div className="loan-group">
                                            <label>Risk Rating</label>

                                            <select
                                                className="loan-input"
                                                value={risk}
                                                onChange={(e) => setRisk(e.target.value)}
                                            >
                                                <option value="">Select Risk</option>
                                                <option>VERY_LOW_RISK</option>
                                                <option>LOW_RISK</option>
                                                <option>MODERATE_RISK</option>
                                                <option>HIGH_RISK</option>
                                                <option>VERY_HIGH_RISK</option>
                                            </select>

                                            <button className="btn btn-primary" onClick={updateRiskRate} > upload risk </button>
                                        </div>

                                        <p><b>Selected:</b> {risk}</p>

                                    </div>
                                </div>

                                {/* IMAGE */}
                                <div className="col-md-6">
                                    <div className="loan-card">

                                        <h6>Income Certificate</h6>

                                        <img
                                            src={loan.incomeCertificate}
                                            alt="certificate"
                                            className="loan-img"
                                        />

                                    </div>
                                </div>

                                {/* OTHER LOANS */}
                                <div className="col-md-12">
                                    <div className="loan-card">

                                        <h5>Other Loans</h5>

                                        <div className="row mb-5">

                                            {
                                                loan.otherLoans.map((o) => (

                                                    <div className="col-md-4 mt-3" key={o.loanId}>

                                                        <div className="loan-subcard">

                                                            <p><b>ID:</b> {o.loanId}</p>
                                                            <p><b>Type:</b> {o.loanType}</p>
                                                            <p><b>Approved:</b> ₹{o.approvedLoanAmount}</p>
                                                            <p><b>Status:</b> {o.loanStatus}</p>
                                                            <p><b>Balance:</b> ₹{o.loanBalance}</p>
                                                            <p><b>EMI:</b> ₹{o.emi}</p>
                                                            <p><b>Date:</b> {o.createdAt ?? "N/A"}</p>

                                                        </div>

                                                    </div>

                                                ))
                                            }

                                        </div>

                                    </div>
                                </div>

                            </div>
                        )
                    }

                </div>
                <div className="col-1">

                </div>
            </div>
            <Footer />


        </div>

    )
}

export default OtherLoans