
import { useDispatch, useSelector } from "react-redux"
import { getAllLoansForManager } from "../../redux/actions/get-loans-manager"
import { useNavigate } from "react-router-dom"
import Navbar from "../customer/navbar"
import { useEffect, useState } from "react"
import SidebarEmployee from "../employee/sidebar-Employee"
import Footer from "../footer"

const GetAllLoansForManager = () => {

    const dispatch = useDispatch()
    const navigate = useNavigate()
    const [page, setPage] = useState(0);
    const [size] = useState(10);
    const [totalPage, setTotalPages] = useState(undefined);

    const { loans } = useSelector(state => state.LoansForManagerReducer)




    useEffect(() => {

        const fetchData = async () => {
            const data = await dispatch(getAllLoansForManager(page, size));
            setTotalPages(data.totalPages);

        };

        fetchData();



    }, [dispatch, page, size]);


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
                    <div className="row g-4 mt-3 ms-4">
                        <div className="pagination-container mb-5">

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
                            loans && loans.map((l, index) => (

                                <div className="col-md-6" key={l.loanId}>

                                    <div className={`account-card ${l.status === "APPROVED" ? "active-card" : ""}`}>

                                        {/* TOP */}
                                        <div className="card-top">
                                            <span className="badge savings">{l.loanType}</span>
                                            <span className="emoji">💰</span>
                                        </div>

                                        {/* TITLE */}
                                        <h5 className="acc-name">Loan ID: {l.loanId}</h5>

                                        {/* AMOUNT */}
                                        <h3 className="acc-balance">₹{l.requestAmount}</h3>

                                        {/* STATUS */}
                                        <p className="acc-number">{l.status}</p>

                                        {/* META */}
                                        <div className="acc-meta">
                                            <div>
                                                <small>TYPE</small>
                                                <p>{l.loanType}</p>
                                            </div>

                                            <div>
                                                <small>STATUS</small>
                                                <p>{l.status}</p>
                                            </div>
                                        </div>

                                        {/* ACTION */}
                                        <div className="acc-actions">
                                            <button
                                                className="btn-outline"
                                                onClick={() => navigate(`/manager-loan/${l.loanId}`)}
                                            >
                                                View
                                            </button>
                                        </div>

                                    </div>

                                </div>

                            ))
                        }

                    </div>
                </div>
                <div className="col-1">

                </div>

            </div>
            <Footer/>

        </div>
    )
}

export default GetAllLoansForManager