import axios from "axios"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import Navbar from "../customer/navbar"
import SidebarEmployee from "../employee/sidebar-Employee"
import Footer from "../footer"

const AssetVerifier =()=>{
    const [errMsg,setErrMsg] = useState(undefined)
    const navigate = useNavigate()
    const [Loans,setLoanss] = useState([])
     const [secLoans, setSecLoans] = useState([]);
    const [page,setPage] = useState(0)
    const [size,setSize] = useState(10)
    const [totalPage,setTotalPages] = useState(undefined)
    const [totalElements,setTotalElements] = useState(undefined)
    const api = `http://localhost:8081/api/loan/get-all-by-Assest?page=${page}&size=${size}`


    useEffect(()=>{



        const fetchLoans = async()=>{


            const config ={
                headers:{
                    "Authorization":"Bearer "+localStorage.getItem("Token")
                }
            }


            const response = await axios.get(api,config)

            setLoanss(response.data.loans)
            setSecLoans(response.data.loans)
            setTotalPages(response.data.totalPages)
            setTotalElements(response.data.totalElements)
            console.log(response.data)

        }

        fetchLoans();


    },[])



    return(
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
                <div className="col-2 min-vh-100 bg-black">
                    <SidebarEmployee />
                </div>

                <div className="col-md-9 ">
                    <div className="row g-4 mt-3">
                          <div className="pagination-container mb-4 ms-5 mt-4 ">
                            

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
                        Loans.map((l, index) => (

                            <div className="col-md-6" key={l.id}>

                                <div className="account-card active-card">

                                    {/* TOP */}
                                    <div className="card-top">
                                        <span className="badge savings">{l.loanType}</span>
                                        <span className="emoji">💰</span>
                                    </div>

                                    {/* TITLE */}
                                    <h5 className="acc-name">Loan ID: {l.id}</h5>

                                    {/* AMOUNT */}
                                    <h3 className="acc-balance">₹{l.requestLoanAmount}</h3>

                                   

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
                                        <button className="btn-outline" onClick={(e)=>navigate(`/verify-assests/${l.id}`)}>
                                            View
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

export default AssetVerifier