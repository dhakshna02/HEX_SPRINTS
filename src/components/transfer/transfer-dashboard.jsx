import { Outlet, useNavigate } from "react-router-dom"
import Navbar from "../customer/navbar"
import Sidebar from "../customer/sidebar"

import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/transfer/csss/transfer-dashboard.css"
import Footer from "../footer"

const TransferDashboard = () => {

    const navigate = useNavigate()

    const path = window.location.pathname; // ✅ DEFINE IT HERE



    return (
        <div>
            <div className="row">
                <Navbar />
            </div>

            <div className="row">


                <div className="col-2 bg-black min-vh-100">
                    <Sidebar />
                </div>

                <div className="col-md-9">

                    <div className="transfer-type mt-4 ms-4">
                        <button
                            className={`transfer-btn ${path === "/transfer/within-bank" ? "active" : ""}`}
                            onClick={() => navigate("/transfer/within-bank")}
                        >
                            Within Bank
                        </button>


                        <button
                            className={`transfer-btn ${path === "/transfer/other-banks" ? "active" : ""}`}
                            onClick={() => navigate("/transfer/other-banks")}
                        >
                            Outside Bank
                        </button>               
                          </div>


                    <div className="container">
                        <div className="row mt-4 mb-4">
                            <div className="col-lg-12">
                                <Outlet />
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-1">

                </div>

            </div>
             <Footer/>
        </div>
    )
}

export default TransferDashboard