import { Link, Outlet } from "react-router-dom"
import Navbar from "../customer/navbar"
import Sidebar from "../customer/sidebar"
import AccountsGrid from "./accounts-grid"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/customer/sidebar.css"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/accounts/accounts-css/acct-dashboard.css"
import Footer from "../footer"
const AccountsDashboard = () => {


    return (
        <div>
            <div className="row">
                <Navbar />
            </div>

            <div className="row ">
                <div className="col-2 bg-black min-vh-100 ">
                    <Sidebar />
                </div>

                <div className="col-md-9 mt-3 ms-4 mb-4">

                    {/* TOP RIGHT BUTTON */}


                    <div className="d-flex justify-content-end p-3">

                        
                    </div>

                    <div>
                        <AccountsGrid />
                    </div>

                    {/* PAGE CONTENT */}
                    <div className="container">
                        <div className="row ">
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

export default AccountsDashboard
{/* TOP RIGHT BUTTON
            <div className="d-flex justify-content-end p-3">
                <Link to="/accounts-dashboard/create-account" className="btn btn-primary">
                    Create Account
                </Link>
            </div>

            PAGE CONTENT
            <div className="container">
                <div className="row mt-4">
                    <div className="col-lg-12">
                        <Outlet />
                    </div>
                </div>
            </div> */}