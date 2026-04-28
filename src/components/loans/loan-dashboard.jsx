import { Outlet, useNavigate } from "react-router-dom"
import Navbar from "../customer/navbar"
import Sidebar from "../customer/sidebar"
import LoansGrid from "./loand-grid"
import LoanDetails from "./loan-details"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/loans/loancss/loan-dashboard.css"
import Footer from "../footer"

const LoanDashboard = () => {


    const navigate = useNavigate();

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


                    <LoansGrid />



                    <Outlet />


                </div>
            </div>
            <Footer/>
        </div>
    )

}

export default LoanDashboard
{/* <div className="container">
                            <div className="row mt-4">
                                <div className="col-lg-12">
                                    <Outlet />
                                </div>
                            </div>
                        </div> */}