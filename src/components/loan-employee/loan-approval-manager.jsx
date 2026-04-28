import { useParams, useNavigate } from "react-router-dom"
import { useDispatch, useSelector } from "react-redux"
import { ApproveLoans } from "../../redux/actions/approve-loans"
import { useEffect, useState } from "react"
import SidebarEmployee from "../employee/sidebar-Employee"
import Navbar from "../customer/navbar"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/loan-employee/loan-employee/loan-approval.css"
import axios from "axios"
import Footer from "../footer"

const LoanApprove = () => {

    const { id } = useParams()
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const [remarks, setRemarks] = useState(undefined)
    const [collatralRequirement, setcollatralRequirement] = useState(undefined)
    const remarksApi = "http://localhost:8081/api/remarks/remarks/loan"

    const [approvedLoanAmount, setApprovedLoanAmount] = useState("")
    const [emi, setEmi] = useState("")
    const [intrestRate, setIntrestRate] = useState("")
    const [monthsOfEmi, setMonthsOfEmi] = useState("")
    const { loanDetails } = useSelector(state => state.ApproveLoansByManager)



    useEffect(() => {

        dispatch(ApproveLoans(id))
    }, [dispatch])


    const UploadRemraks = async () => {



        const response = await axios.post(remarksApi, {
            "remarks": remarks,
            "loanId": loanDetails?.loanId ?? "",
            "CollatralStatus": collatralRequirement

        }, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        })

        // console.log(loanDetails?.loanId??"")
        console.log("done")
    }


    const verifyLoan = async () => {
          if (!approvedLoanAmount) {
        alert("Enter approved amount");
        return;
    }

    if (!emi) {
        alert("Enter EMI");
        return;
    }

    if (!intrestRate) {
        alert("Enter interest rate");
        return;
    }

    if (!monthsOfEmi) {
        alert("Enter months");
        return;
    }

    if (!loanDetails?.loanId) {
        alert("Invalid loan");
        return;
    }

        try {
            await axios.put(
                "http://localhost:8081/api/loan/verify-loan",
                {
                    approvedLoanAmount,
                    emi,
                    intrestRate,
                    monthsOfEmi,
                    loanStatus: "APPROVED",
                    loanId: loanDetails?.loanId
                },
                {
                    headers: {
                        "Authorization": "Bearer " + localStorage.getItem("Token")
                    }
                }
            )

            
            alert("Successful");
            navigate("/")

        } catch (err) {
            console.error(err)
        }
    }




    //console.log(loanDetails)
    return (
        <div>
            <div className="row">

                <Navbar />
                </div>

                <div className="row">

                <div className="col-2 bg-black min-vh-100">

                    <SidebarEmployee />

                    </div>

                    <div className="col-md-9 ms-4 mt-4">

                        <h2 className="loan-title-dark ">Loan Verification</h2>

                        {/* LOAN + CUSTOMER */}
                        <div className="loan-section-dark ">

                            <div className="grid-2">

                                <div>
                                    <h4>Loan Details</h4>
                                    <p><b>ID:</b> {loanDetails?.loanId}</p>
                                    <p><b>Status:</b> {loanDetails?.loanStatus}</p>
                                    <p><b>Amount:</b> ₹{loanDetails?.requestedLoanAmount}</p>
                                    <p><b>Type:</b> {loanDetails?.loanType}</p>
                                </div>

                                <div>
                                    <h4>Customer</h4>
                                    <p><b>Name:</b> {loanDetails?.customerName}</p>
                                    <p><b>Gender:</b> {loanDetails?.gender}</p>
                                    <p><b>Occupation:</b> {loanDetails?.occupation}</p>
                                    <p><b>Address:</b> {loanDetails?.address}</p>
                                </div>

                            </div>

                        </div>

                        {/* IMPORTANT SECTION */}
                        <div className="loan-section-dark highlight-dark">

                            <h3>Decision Factors</h3>

                            <div className="grid-2">

                                <div className="important-box-dark">
                                    <label>Risk Rating</label>
                                    <p className="highlight-text-dark">
                                        {loanDetails?.riskRate}
                                    </p>
                                </div>

                                <div className="important-box-dark">
                                    <label>Total Collateral Value</label>
                                    <p className="highlight-text-dark">
                                        ₹{loanDetails?.collatrals?.reduce((a, c) => a + c.value, 0)}
                                    </p>
                                </div>

                            </div>

                        </div>

                        {/* COLLATERALS */}
                        <div className="loan-section-dark">

                            <h3>Collaterals</h3>

                            <table className="loan-table-dark">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Type</th>
                                        <th>Value</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        loanDetails?.collatrals?.map((c, i) => (
                                            <tr key={i}>
                                                <td>{c.collatralName}</td>
                                                <td>{c.collatralType}</td>
                                                <td>₹{c.value}</td>
                                            </tr>
                                        ))
                                    }
                                </tbody>
                            </table>

                        </div>


                        <div className="loan-section-dark">

                            <h3>Remarks</h3>

                            <div className="remarks-group">

                                <label>Remarks</label>
                                <input
                                    type="text"
                                    className="remarks-input"
                                    placeholder="Add remarks"
                                    onChange={(e) => setRemarks(e.target.value)}
                                />
                                <br />

                                <label className="mt-3">Additional Collateral Required</label>
                                <select className="remarks-select" onChange={(e) => setcollatralRequirement(e.target.value)}>
                                    <option value="">Select</option>
                                    <option value="SUFFICIENT">SUFFICIENT</option>
                                    <option value="IN_SUFFICENT">IN_SUFFICENT</option>
                                </select>
                                <br />

                                <button className="btn btn-primary" onClick={UploadRemraks}>
                                    Add Remarks
                                </button>

                            </div>

                        </div>

                        {/* IMAGE */}
                        <div className="loan-section-dark">
                            <h3>Income Certificate</h3>

                            <img
                                src={loanDetails?.IncomeCertificate ?? ""}
                                alt="doc"
                                className="loan-image-dark"
                            />
                        </div>
                        <div className="loan-section-dark">

                            <h3>Approve Loan Details</h3>

                            <div className="approve-grid">

                                <div className="input-group-dark">
                                    <label>Approved Amount</label>
                                    <input type="number" onChange={(e) => setApprovedLoanAmount(e.target.value)} />
                                </div>

                                <div className="input-group-dark">
                                    <label>EMI</label>
                                    <input type="number" onChange={(e) => setEmi(e.target.value)} />
                                </div>

                                <div className="input-group-dark">
                                    <label>Interest Rate (%)</label>
                                    <input type="number" onChange={(e) => setIntrestRate(e.target.value)} />
                                </div>

                                <div className="input-group-dark">
                                    <label>Months</label>
                                    <input type="number" onChange={(e) => setMonthsOfEmi(e.target.value)} />
                                </div>

                            </div>

                        </div>

                        {/* ACTION */}
                        <div className="action-bar-dark">

                            <button className="transfer-btn active" onClick={verifyLoan} >
                                Approve
                            </button>

                            <button className="transfer-btn">
                                Reject
                            </button>

                        </div>

                    </div>
                    <div className="col-1">

                    </div>
                </div>
                <Footer/>
            </div>
       
    )
}

export default LoanApprove