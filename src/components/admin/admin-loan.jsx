import { useEffect, useState } from "react"
import Navbar from "../customer/navbar"
import SidebarAdmin from "./side-bar-admin"
import axios from "axios"
import { useNavigate } from "react-router-dom"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/admin/admin-css/admin-loan.css"
import Footer from "../footer"

const AdminAssignLoans = () => {

    const empApi = "http://localhost:8081/api/admin/get-employees"
    const assignApi = "http://localhost:8081/api/loan/assign-loan"

    const [loans, setLoans] = useState([])
    const [secLoans, setSecLoans] = useState([]);
    const [employees, setEmployees] = useState({})
    const [selectedData, setSelectedData] = useState({})
    const [page, setPage] = useState(0)
    const [size, setSize] = useState(10)
    const [totalPage, setTotalPage] = useState(undefined)
    const [totalElements, setTotalElements] = useState(undefined)
    const loanApi = `http://localhost:8081/api/loan/get-all-loan-pending?page=${page}&size=${size}`
    const [filterLoanType, setFilterLoanType] = useState("");
    const [assetVErifierId, setassetVErifierId] = useState(0)
    const [finaincialAnalyst, setfinaincialAnalyst] = useState(undefined)
    const [manager, setmanager] = useState(undefined)

    const navigate = useNavigate()

    useEffect(() => {

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        const fetchLoans = async () => {
            const res = await axios.get(loanApi, config)
            setTotalPage(res.data.totalPages)
            setTotalElements(res.data.totalElements)
            setLoans(res.data.loans)
            setSecLoans(res.data.loans)
            console.log("Loans:", res.data)
        }

        const fetchEmployees = async () => {
            const res = await axios.get(empApi, config)
            setEmployees(res.data)
            console.log("Employees:", res.data)
        }

        fetchLoans()
        fetchEmployees()

    }, [page])

   
    const assignLoan = async (loanId) => {

        const data = selectedData[loanId]
        if (!finaincialAnalyst) {
        alert("Select Financial Analyst");
        return;
    }

    if (!assetVErifierId) {
        alert("Select Asset Verifier");
        return;
    }

    if (!manager) {
        alert("Select Loan Verifier");
        return;
    }


        
        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        

        
        await axios.put(assignApi, {
            loanId: loanId,
            loanVerifier: manager,
            assestVerifier: assetVErifierId,
            finaincialAnalyst: finaincialAnalyst
        }, config)

        navigate("/admin")
    }

    const FilterLoanType = () => {

        console.log(filterLoanType);

        let SearchInData = loans.filter((s) =>
            s.loanType.toString().includes(filterLoanType)
        );

        setLoans(SearchInData);
        console.log(SearchInData);
    };

    return (
        <div>
            <div className="row">
                <Navbar />
            </div>
            <div className="row ">
                <div className="col-2 bg-black min-vh-100">
                    <SidebarAdmin />
                </div>

                <div className="col-md-9 mt-4">
                    <div className="mb-3 d-flex gap-2 ms-4">

                        <select
                            onChange={(e) => setFilterLoanType(e.target.value)}
                            className="form-control"
                        >
                            <option value="">Select Loan Type</option>
                            <option value="PERSONAL">PERSONAL</option>
                            <option value="VEHICLE">VEHICLE</option>
                            <option value="BUSINESS">BUSINESS</option>
                            <option value="HOME">HOME</option>
                            <option value="GOLD">GOLD</option>
                        </select>

                        <button className="btn btn-secondary" onClick={FilterLoanType}>
                            Filter
                        </button>

                    </div>
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

                    <div className="assign-container ms-5">



                        {
                            loans.map((l, index) => (

                                <div className="assign-card" key={index}>

                                    <h3>{l.loanType}</h3>
                                    <p> Customer Id :{l.customerId}</p>
                                    <p>Status: {l.loanStatus}</p>
                                    <h4>₹ {l.LoanAmount}</h4>
                                    <p>Loan ID: {l.Loanid}</p>
                                    <div className="assign-action-vertical">

                                        {/* Financial Analyst */}
                                        <div className="assign-field">
                                            <select
                                                onChange={(e) =>
                                                    setfinaincialAnalyst(e.target.value)
                                                }
                                            >
                                                <option>Select Financial Analyst</option>
                                                {
                                                    employees.financialAnalystId?.map(emp => (
                                                        <option key={emp.id} value={emp.id}>
                                                            {emp.name}
                                                        </option>
                                                    ))
                                                }
                                            </select>
                                        </div>

                                        {/* Asset Verifier */}
                                        <div className="assign-field">
                                            <select
                                                disabled={l.loanType === "PERSONAL"}
                                            
                                                onChange={(e) => setassetVErifierId(e.target.value)}
                                            >


                                                <option>Select Asset Verifier</option>
                                                {
                                                    employees.assetVErifierId?.map(emp => (
                                                        <option key={emp.id} value={emp.id}>
                                                            {emp.name}
                                                        </option>
                                                    ))
                                                }
                                            </select>
                                        </div>

                                        {/* Loan Verifier */}
                                        <div className="assign-field">
                                            <select
                                                onChange={(e) =>
                                                    setmanager(e.target.value)
                                                }
                                            >
                                                <option>Select Loan Verifier</option>
                                                {
                                                    employees.loanVerifierId?.map(emp => (
                                                        <option key={emp.id} value={emp.id}>
                                                            {emp.name}
                                                        </option>
                                                    ))
                                                }
                                            </select>
                                        </div>

                                        <button
                                            className="assign-btn mt-2"
                                            onClick={() => assignLoan(l.Loanid)}
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
            <Footer />
        </div>

    )
}

export default AdminAssignLoans