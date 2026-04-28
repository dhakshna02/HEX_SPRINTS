import axios from "axios"
import { useEffect, useState } from "react"
import ENavbar from "./emp-navbar"
import Sidebar from "../customer/sidebar"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/Deposit/depositcss/deposit.css"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/employee/approval.css"
import Navbar from "../customer/navbar"
import SidebarAdmin from "../admin/side-bar-admin"
import SidebarEmployee from "./sidebar-Employee"
import Footer from "../footer"

const AccountApproval = () => {

    const [account, setAccount] = useState([])
    const [acctId, setAcctId] = useState(undefined)
    const [remarks, setRemarks] = useState("")
    const [successMsg, setSuccessMesg] = useState(undefined)
    const [errMsg, setErrMsg] = useState(undefined)
    const [secAcct, setSecAcct] = useState([]);
    const [page, setPage] = useState(0)
    const [size, setSize] = useState(5)
    const [totalPage, setTotalPages] = useState(undefined)
    const [totalElements, setTotalElements] = useState(undefined)

    const api = `http://localhost:8081/api/account/get-all-unverified-account-with-username?page=${page}&size=${size}`
    const remarksApi = "http://localhost:8081/api/remarks/opening-remarks"
    const verifyApi = "http://localhost:8081/api/account/verification"

    const config = {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("Token")
        }
    }

    // 🔹 FETCH DATA
    useEffect(() => {

        const getAccounts = async () => {
            try {
                const response = await axios.get(api, config)




                setAccount(response.data.accounts)
                setSecAcct(response.data.accounts)
                setTotalPages(response.data.totalPages)
                setTotalElements(response.data.totalElements)


            } catch (err) {
                setErrMsg(err.message)
            }
        }

        getAccounts()

    }, [page])

    // 🔹 ADD REMARKS
    const CreateRemarks = async (accid) => {

        try {
            await axios.post(remarksApi, {
                remarks: remarks,
                accountId: accid
            }, config)

            setSuccessMesg("Remarks submitted successfully")

            // ✅ remove from UI after remarks
            setAccount(prev => prev.filter(a => a.acccountNumber !== accid))

        } catch (err) {
            setErrMsg(err.message)
        }
    }

    // 🔹 APPROVE ACCOUNT
    const approveAccount = async (e) => {

        const id = e.target.value

        try {
            console.log(id)
            await axios.put(verifyApi, {
                accountApprovedStatus: "APPROVED",
                accountid: id
            }, config)

            // ✅ remove after approval
            setAccount(prev => prev.filter(a => a.acccountNumber != id))

        } catch (err) {
            setErrMsg(err.message)
        }
    }

    return (
        <div>

            {/* NAVBAR */}
            <div className="row">
                <Navbar />
            </div>

            <div className="row">

                {/* SIDEBAR */}
                <div className="col-2 bg-black min-vh-100">
                    <SidebarEmployee />
                </div>

                {/* MAIN */}
                <div className="col-9 ">

                    <div className="deposit-container">
                        <div className="pagination-container mb-4 ms-4">

                            <button
                                onClick={() => setPage(page - 1)}
                                disabled={page === 0}
                                className="page-btn"
                            >
                                Prev
                            </button>

                            <span className="page-info">
                                Page {page + 1} of {totalPage}
                            </span>

                            <button
                                onClick={() => setPage(page + 1)}
                                disabled={page === totalPage - 1}
                                className="page-btn"
                            >
                                Next
                            </button>

                        </div>

                        <div className="deposit-header">
                            <h4>Account Approval</h4>
                            <p>Review documents and approve or raise remarks</p>
                        </div>

                        {
                            errMsg &&
                            <div className="alert alert-danger">{errMsg}</div>
                        }

                        <div className="deposit-main">

                            { account.length === 0 ? (
                                <div className="row min-vh-100 mt-4">
                                    <div className="card">
                                        <div className="card-body">
                                            <h2>Oops no pending work </h2>
                                            </div>
                                    </div>
                                    </div>
                            ) :
                             (
                                account.map((a, index) => (

                                    <div className="deposit-card mb-4" key={index}>

                                        {/* INFO */}
                                        <div className="deposit-info-row">
                                            <span>Account Number</span>
                                            <span>{a.acccountNumber}</span>
                                        </div>

                                        <div className="deposit-info-row">
                                            <span>Customer</span>
                                            <span>{a.customerName}</span>
                                        </div>

                                        <div className="deposit-info-row">
                                            <span>Account Type</span>
                                            <span>{a.accountType}</span>
                                        </div>

                                        <div className="deposit-info-row">
                                            <span>Status</span>
                                            <span className="status-success">
                                                {a.accountOpeningStatus}
                                            </span>
                                        </div>

                                        {/* DOCUMENTS */}
                                        <div className="deposit-row-grid mt-4">

                                            <div className="deposit-info">
                                                <h6>Identity Proof</h6>
                                                <div className="preview-box">
                                                    <img src={a.identityProof} className="preview-img" />
                                                </div>
                                            </div>

                                            <div className="deposit-info">
                                                <h6>Address Proof</h6>
                                                <div className="preview-box">
                                                    <img src={a.addressProof} className="preview-img" />
                                                </div>
                                            </div>

                                            <div className="deposit-info">
                                                <h6>PAN</h6>
                                                <div className="preview-box">
                                                    <img src={a.panNo} className="preview-img" />
                                                </div>
                                            </div>

                                        </div>

                                        {/* ACTIONS */}
                                        <div className="mt-4">

                                            <button
                                                className="deposit-btn me-2"
                                                onClick={() => setAcctId(a.acccountNumber)}
                                            >
                                                Add Remarks
                                            </button>

                                            <button
                                                className="btn-approve"
                                                value={a.acccountNumber}
                                                onClick={approveAccount}
                                            >
                                                Approve
                                            </button>

                                        </div>

                                        {/* REMARK BOX */}
                                        {
                                            acctId === a.acccountNumber && (

                                                <div className="mt-4">

                                                    {
                                                        successMsg &&
                                                        <div className="alert alert-primary">
                                                            {successMsg}
                                                        </div>
                                                    }

                                                    <input
                                                        type="text"
                                                        className="deposit-input"
                                                        placeholder="Enter remarks..."
                                                        value={remarks}
                                                        onChange={(e) => setRemarks(e.target.value)}
                                                    />

                                                    <div className="mt-2">
                                                        <button
                                                            className="deposit-btn me-2"
                                                            onClick={() => CreateRemarks(a.acccountNumber)}
                                                        >
                                                            Submit
                                                        </button>

                                                        <button
                                                            className="btn-reject"
                                                            onClick={(e) => setAcctId(e.target.value)}
                                                        >
                                                            Cancel
                                                        </button>
                                                    </div>

                                                </div>
                                            )
                                        }

                                    </div>

                                )))
                            }

                        </div>

                    </div>

                </div>
                <div className="col-1"></div>


            </div>
            <Footer/>

        </div>
    )
}

export default AccountApproval