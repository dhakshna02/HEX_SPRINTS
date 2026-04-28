import axios from "axios";
import { useState, useRef, useEffect } from "react";
import Navbar from "../customer/navbar";
import Sidebar from "../customer/sidebar";
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/Benificairaes/beneficary.css";
import { Toast } from "primereact/toast";
import Footer from "../footer";

const Benificiers = () => {

    const [accountNumber, setAccountNumber] = useState("");
    const [payeeName, setPayeeName] = useState("");
    const [ifsc, setIfsc] = useState("");
    const [type, setType] = useState("INTERNAL");

    const [beneficiaries, setBeneficiaries] = useState([]);

    const [page, setPage] = useState(0);
    const [size] = useState(5);
    const [totalPage, setTotalPage] = useState(0);
    const [errMsg,setErrMsg] = useState(undefined)

    const toast = useRef(null);

    const addApi = "http://localhost:8081/api/benificary/add";
    const getApi = "http://localhost:8081/api/benificary/get";

    // 🔹 FETCH BENEFICIARIES WITH PAGINATION
    const fetchBeneficiaries = async () => {

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        };

        try {
            const res = await axios.get(
                `${getApi}?page=${page}&size=${size}`,
                config
            );

            setBeneficiaries(res.data.benifiacries);
            setTotalPage(res.data.totalPages);

        } catch (err) {
            setErrMsg(message)
        }
    };

    useEffect(() => {
        fetchBeneficiaries();
    }, [page]);

    // 🔹 ADD BENEFICIARY WITH VALIDATION
    const AddBeneficiary = async () => {

        // ❌ DO NOTHING IF EMPTY (no toast)
        if (!accountNumber || !payeeName || (type === "EXTERNAL" && !ifsc)) {
            return;
        }

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        };

        try {

            const finalIfsc = type === "INTERNAL" ? "IDBI" : ifsc;

            await axios.post(addApi, {
                accountNumber: Number(accountNumber),
                PayeeName: payeeName,
                ifsc: finalIfsc
            }, config);

            // ✅ SUCCESS TOAST
            toast.current.show({
                severity: "success",
                summary: "Success",
                detail: "Beneficiary Added Successfully",
                life: 3000
            });

            // CLEAR FORM
            setAccountNumber("");
            setPayeeName("");
            setIfsc("");

            // REFRESH DATA
            fetchBeneficiaries();

        } catch (err) {
            toast.current.show({
                severity: "error",
                summary: "Error",
                detail: "invalid account number",
                life: 3000
            });
        }
    };

    return (
        <div>
            <div className="row">
                <Navbar />

            </div>
            <div className="row  ">

                {/* SIDEBAR */}
                <div className="col-2 bg-black min-vh-100">
                    <Sidebar />
                </div>


                {/* CONTENT */}
                <div className="col-md-9 ">

                    {/* 🔹 FORM */}
                    <div className="transfer-card mt-4 ms-7  ">

                        <Toast ref={toast} />

                        <h3 className="card-title">Add Beneficiary</h3>

                        <div className="input-group">
                            <label>Transfer Type</label>
                            <select
                                className="transfer-input"
                                onChange={(e) => setType(e.target.value)}
                            >
                                <option value="INTERNAL">Within Bank</option>
                                <option value="EXTERNAL">Other Bank</option>
                            </select>
                        </div>

                        <div className="input-group">
                            <label>Account Number</label>
                            <input
                                type="text"
                                value={accountNumber}
                                onChange={(e) => setAccountNumber(e.target.value)}
                            />
                        </div>

                        <div className="input-group">
                            <label>Payee Name</label>
                            <input
                                type="text"
                                value={payeeName}
                                onChange={(e) => setPayeeName(e.target.value)}
                            />
                        </div>

                        {
                            type === "EXTERNAL" && (
                                <div className="input-group">
                                    <label>IFSC Code</label>
                                    <input
                                        type="text"
                                        value={ifsc}
                                        onChange={(e) => setIfsc(e.target.value)}
                                    />
                                </div>
                            )
                        }

                        <div className="btn-container">
                            <button
                                className="transfer-submit"
                                onClick={AddBeneficiary}
                            >
                                Add Beneficiary
                            </button>
                        </div>    <div className="mt-4">


                            <h4 style={{ color: "#00d4ff", marginBottom: "15px" }}>
                                My Beneficiaries
                            </h4>
                            <div className="pagination-container mb-4">

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
                                beneficiaries.length === 0 ? (
                                    <p style={{ color: "#888" }}>
                                        No beneficiaries added yet
                                    </p>
                                ) : (
                                    <table className="table table-dark table-hover">

                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Name</th>
                                                <th>Account Number</th>
                                                <th>IFSC</th>
                                                <th>Type</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            {
                                                beneficiaries.map((b, index) => (
                                                    <tr key={b.id}>
                                                        <td>{index + 1}</td>
                                                        <td>{b.payeeName}</td>
                                                        <td>{b.accountNumber}</td>
                                                        <td>{b.ifsc}</td>
                                                        <td>
                                                            {b.ifsc === "IDBI" ? "INTERNAL" : "EXTERNAL"}
                                                        </td>
                                                    </tr>
                                                ))
                                            }
                                        </tbody>

                                    </table>
                                )
                            }



                        </div>

                    </div>

                    {/* 🔹 TABLE */}


                </div>
            </div>
            <Footer />
        </div>
    );
};

export default Benificiers;