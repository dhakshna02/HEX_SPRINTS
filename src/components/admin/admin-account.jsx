import { useEffect, useState } from "react"
import Navbar from "../customer/navbar"
import SidebarAdmin from "./side-bar-admin"
import axios from "axios"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/admin/admin-css/admin-account.css"
import { useNavigate } from "react-router-dom"
import Footer from "../footer"

const AdminAssignAccounts = () => {

    const [page, setPage] = useState(0)
    const [size, setSize] = useState(10)
    const [totalPage, setTotalPage] = useState(undefined)
    const [totalElements, setTotalElements] = useState(undefined)
    const assingApi = "http://localhost:8081/api/account/assign-emp-acct/admin/"
    const acctApi = `http://localhost:8081/api/account/get-all-unverified-account?page=${page}&size=${size}`
    const api = "http://localhost:8081/api/admin/get-managers"

    const [errMesg, setErrMesg] = useState(undefined)
    const [managers, setManagers] = useState([])
    const [accts, setAccts] = useState([])

    const [selectedManager, setSelectedManager] = useState("")
    const navigate = useNavigate();

    useEffect(() => {

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        const fetchManager = async () => {
            const response = await axios.get(api, config)
            setManagers(response.data)

        }

        const fetchAllUnverifiedAccounts = async () => {
            const response = await axios.get(acctApi, config)
            setAccts(response.data.accounts)
            setTotalPage(response.data.totalPages)
            setTotalElements(response.data.totalElements)
        }

        fetchManager()
        fetchAllUnverifiedAccounts()

    }, [])

    const AssignAccount = async (id1, id2) => {

        if (!id1 || !id2) {
            alert("Missing values")
            return
        }

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        await axios.put(assingApi + id1 + "/" + id2, {}, config)
        navigate("/admin")

    }

    return (
        <div>
            <div className="row">
                <Navbar />

                <div className="row ">
                    <div className="col-2 min-vh-100 bg-black">
                        <SidebarAdmin />
                    </div>

                    <div className="col-md-9 ">
                        <div className="pagination-container mt-4 mb-4">

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

                        <div className="assign-container mt-4 ms-5 ">

                            {
                                accts.map((a, index) => (

                                    <div className="assign-card" key={index}>

                                        <div className="assign-header">
                                            <span className="assign-type">{a.accountType}</span>
                                            <span className="assign-icon">{a.accountOpeningStatus}</span>
                                        </div>

                                        <h2 className="assign-name">{a.customerName}</h2>
                                        <h4 className="assign-balance">{a.acccountOpeningDate}</h4>
                                        <p className="assign-number">Account No: {a.acccountNumber}</p>

                                        <div className="assign-action">

                                            <select
                                                className="assign-select"
                                                onChange={(e) => setSelectedManager(e.target.value)}
                                            >
                                                <option value="">Select Manager</option>

                                                {
                                                    managers.map((d, index) => (
                                                        <option key={index} value={d.id}>
                                                            {d.id} --- {d.name}
                                                        </option>
                                                    ))
                                                }
                                            </select>

                                            <button
                                                className="assign-btn"
                                                onClick={() => AssignAccount(a.acccountNumber, selectedManager)}
                                            >
                                                Assign
                                            </button>

                                        </div>

                                    </div>

                                ))
                            }
                        </div>

                    </div>

                    <div className="col-1"></div>
                </div>
                <Footer/>
            </div>
        </div>
    )
}

export default AdminAssignAccounts