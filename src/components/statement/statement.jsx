import axios from "axios"
import Navbar from "../customer/navbar"
import Sidebar from "../customer/sidebar"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/statement/statement.css"
import { useEffect, useState } from "react"
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import Footer from "../footer"

const Statement = () => {

    const api = "http://localhost:8081/api/transcation/get-all-Transction"

    const [data, setData] = useState([]);
    const [secData, setSecData] = useState([])

    const [totalRecords, setTotalRecords] = useState(0);
    const [selectedTxn, setSelectedTxn] = useState(null);
    const [errMesg, setErrMesg] = useState(undefined);
    const [filterAcct, setfilterAcct] = useState(undefined)

    const [search, setSearch] = useState("");
    const [txnIdFilter, setTxnIdFilter] = useState("");
    const [typeFilter, setTypeFilter] = useState("ALL");
    const [fromDate, setFromDate] = useState("");
    const [toDate, setToDate] = useState("");
    const [sortOrder, setSortOrder] = useState("DESC");
    const [page, setPage] = useState(0)
    const [size, setsize] = useState(10)
    const [totalPage,setTotalPage] = useState(undefined)
    const [totalElements ,setTotalElements] = useState(undefined)

    const [filteredData, setFilteredData] = useState([]);

    useEffect(() => {
        fetchTranscation();
    }, [page]);




    const fetchTranscation = async () => {

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        try {


            let url;

            if (fromDate && toDate) {
                url = `?page=${page}&size=${size}&fromDate=${fromDate}&toDate=${toDate}`;
            } else {
                url = `?page=${page}&size=${size}`;
            }
            console.log(page)
            const response = await axios.get(api + url, config)

            // ✅ DEFAULT SORT BY REF ID DESC


            setData(response.data.transcations);
            setSecData(response.data.transcations)
            setTotalRecords(response.data.totalElements);
            setTotalPage(response.data.totalPages)

            console.log(search)





        } catch (err) {
            setErrMesg(err.message)
        }
    }


    const Search = () => {

        console.log(search)
        let SearchInData = secData.filter((s) =>
            s.amount.toString().includes(search) ||
            s.date.toString().includes(search) ||
            s.type.toString().toLowerCase().includes(search) ||
            s.accountId.toString().includes(search) ||
            s.transcationId.toString().includes(search) ||
            s.description?.toLowerCase().includes(search.toLowerCase()) ||
            s.TreanscationFlow.toLowerCase().includes(search.toLowerCase())


        )
        
        setData(SearchInData)


    }

    const FilterAccount = () => {


        console.log(filterAcct)

        let SearcFilterInData = secData.filter((s) => s.accountId.toString().includes(filterAcct)

        )
        setData(SearcFilterInData)
        console.log(SearcFilterInData)

    }


    const downloadPDF = () => {

        const doc = new jsPDF();

        // 🔹 Title
        doc.setFontSize(16);
        doc.text("Transaction Report", 14, 15);

        doc.setFontSize(10);
        doc.text(`Generated on: ${new Date().toLocaleString()}`, 14, 22);

        // 🔹 Format function
        const formatAmount = (amt) =>
            Number(amt).toLocaleString("en-IN", {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            });

        // 🔹 Headers
        const tableColumn = [
            "Ref ID",
            "Account ID",
            "Date",
            "Type",
            "Description",
            "Amount in Rs",
            "Balance in Rs"
        ];

        // 🔹 Clean rows (IMPORTANT)
        const tableRows = (filteredData.length ? filteredData : data).map(t => [
            t.transcationId,
            t.accountId,
            new Date(t.date).toLocaleString(),
            t.type,
            t.description,
            `${t.TreanscationFlow === "DEBIT" ? "-" : "+"} ${formatAmount(t.amount)}`,
            formatAmount(t.balanceAfterTranscation)
        ]);

        // 🔹 Build table
        autoTable(doc, {
            head: [tableColumn],
            body: tableRows,
            startY: 30,
            theme: "grid",
            styles: { fontSize: 9 },
            columnStyles: {
                5: { halign: "right" },
                6: { halign: "right" }
            },
            headStyles: {
                fillColor: [41, 128, 185]
            }
        });

        doc.save("transactions.pdf");
    };


    return (
        <div>
            <div>
                <div className="row">
                    <Navbar />
                </div>

                <div className="row">
                    <div className="col-2 bg-black min-vh-100">
                        <Sidebar />
                    </div>

                    <div className="col-md-8 ms-3" >
                        <div className="transaction-wrapper">

                            <div className="txn-header">
                                <h4>Transactions</h4>
                                <p>Track all your account activities</p>
                            </div>

                            {/* FILTER BAR */}
                            <div className="txn-filter-bar">

                                <input
                                    type="text"
                                    placeholder="Search..."
                                    onChange={(e) => setSearch(e.target.value)}
                                    className="search-input"
                                />

                                <button className="btn btn-outline-secondary rounded-circle" onClick={Search}>
                                    <i className="pi pi-search"></i>
                                </button>

                                <input
                                    type="number"
                                    placeholder="Filter By Account number"
                                    onChange={(e) => setfilterAcct(e.target.value)}
                                    className="search-input"
                                />

                                <button className="btn btn-outline-secondary " onClick={FilterAccount}>
                                    <i className="pi pi-search"></i>
                                </button>

                                <div className="w-100"></div>

                                <h5>from Date:</h5>
                                <input
                                    type="date"
                                    value={fromDate}
                                    onChange={(e) => setFromDate(e.target.value)}
                                    className="date-input"
                                />

                                <h5>to Date:</h5>
                                <input
                                    type="date"
                                    value={toDate}
                                    onChange={(e) => setToDate(e.target.value)}
                                    className="date-input"

                                />


                                <button className="btn btn-secondary" onClick={fetchTranscation} > Retrive data</button>


                                <button className="btn btn-danger" onClick={downloadPDF}>
                                    Download PDF
                                </button>

                                <div className="pagination-container">

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

                            </div>

                            {/* TABLE */}
                            <div className="txn-table-container">
                                <table id="txnTable" className="txn-table">

                                    <thead>
                                        <tr>
                                            <th>Ref ID</th>
                                            <th>Account Id:</th>
                                            <th>Date</th>
                                            <th>Type</th>
                                            <th>Description</th>
                                            <th>Amount</th>
                                            <th>Balance</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        {(filteredData.length ? filteredData : data).map((t, index) => (
                                            <tr key={index}>
                                                <td>
                                                    <span
                                                        className="txn-ref"
                                                        onClick={() => setSelectedTxn(t)}
                                                    >
                                                        {t.transcationId}
                                                    </span>
                                                </td>
                                                <td>{t.accountId}</td>
                                                <td>{new Date(t.date).toLocaleString()}</td>
                                                <td>{t.type}</td>
                                                <td>{t.description}</td>

                                                <td>
                                                    <div className="txn-amount">
                                                        <span className={t.TreanscationFlow === "DEBIT" ? "arrow debit" : "arrow credit"}>
                                                            {t.TreanscationFlow === "DEBIT" ? "↑" : "↓"}
                                                        </span>
                                                        ₹{t.amount}
                                                    </div>
                                                </td>

                                                <td>₹{t.balanceAfterTranscation}</td>
                                            </tr>
                                        ))}
                                    </tbody>

                                </table>

                                {/* PAGINATION */}


                            </div>

                        </div>
                    </div>
                    <div className="col-1"></div>
                </div>

                 <Footer/>
            </div>
        </div>
    )
}

export default Statement