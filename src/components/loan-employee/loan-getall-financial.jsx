import { useEffect, useState } from "react"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/accounts/accounts-css/account-grid.css"
import axios from "axios"
import SidebarEmployee from "../employee/sidebar-Employee"
import Navbar from "../customer/navbar"
import { useNavigate } from "react-router-dom"
import Footer from "../footer"

const GetAllFinancialAnalystLoans = () => {

    

    const [loans, setLoans] = useState([])
     
    const [errMsg, setErrMsg] = useState(undefined)
    const navigate = useNavigate();
    const [secLoans, setSecLoans] = useState([]);
    const [page,setPage] = useState(0)
    const [size,setSize] = useState(10)
    const [totalPage,setTotalPages] = useState(undefined)
    const [totalElements,setTotalElements] = useState(undefined)

    const api = `http://localhost:8081/api/loan/get-all-by-fin?page=${page}&size=${size}`

    useEffect(() => {

        const fetchLoans = async () => {

            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            }

            try {
                const response = await axios.get(api, config)
                setLoans(response.data.loans)
                setSecLoans(response.data.loans)
                setTotalPages(response.data.totalPages)
                setTotalElements(response.data.totalElements)
                console.log(response.data.loans)
            } catch (err) {
                setErrMsg(err.message)
            }
        }

        fetchLoans()

    }, [])

    return (
        <div>

            {
                errMsg && (
                    <div className="alert alert-danger">
                        {errMsg}
                    </div>
                )
            }



            <div className="row">
                <Navbar />
                <div className="col-2 bg-black min-vh-100">
                    <SidebarEmployee />
                </div>

                <div className="col-md-9"><div className="row g-4 mt-3">
                      <div className="pagination-container mb-4 ms-5 mt-4">

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

                    {
                        loans.map((l, index) => (

                            <div className="col-md-6" key={l.id}>

                                <div className="account-card active-card ms-4">

                                    {/* TOP */}
                                    <div className="card-top">
                                        <span className="badge savings">{l.loanType}</span>
                                        <span className="emoji">💰</span>
                                    </div>

                                    {/* TITLE */}
                                    <h5 className="acc-name">Loan ID: {l.id}</h5>

                                    {/* AMOUNT */}
                                    <h3 className="acc-balance">₹{l.requestLoanAmount}</h3>

                                    {/* STATUS */}
                                    <p className="acc-number">{l.loanStatus}</p>

                                    {/* META */}
                                    <div className="acc-meta">
                                        <div>
                                            <small>TYPE</small>
                                            <p>{l.loanType}</p>
                                        </div>

                                        <div>
                                            <small>STATUS</small>
                                            <p>{l.loanStatus}</p>
                                        </div>
                                    </div>

                                    {/* ACTIONS */}
                                    <div className="acc-actions">
                                        <button className="btn-outline" onClick={(e)=>navigate(`/other-loans/${l.id}`)}>
                                            View
                                        </button>

                                        <button className="btn-dark">
                                            Approve
                                        </button>
                                    </div>

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
    )
}

export default GetAllFinancialAnalystLoans