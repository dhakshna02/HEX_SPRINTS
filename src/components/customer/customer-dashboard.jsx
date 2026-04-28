import CustomerSignUp from "./customer-signup"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/customer/customer-dashboard.css"
import Navbar from "./navbar"
import Sidebar from "./sidebar"
import { useEffect, useState } from "react"
import Stat from "./stats"
import TotalLoansAndSavingsWidget from "./totalLoans-savings"
import { useSearchParams } from "react-router-dom"
import RecentTranscations from "./recent-transcations"
import Footer from "../footer"

const CustomerDashboard = () => {



    const api = "http://localhost:8081/api/transcation/inflow-outflow"

    const [name, setName] = useState(undefined)



   







    return (

        <div>


            <div className="row ">
                <Navbar name={name} />
            </div>

            <div className="row">


                <div className="col-2 bg-black min-vh-100  ">
                    <Sidebar />
                </div>


                <div className="col-sm-9 mt-4 ms-4">
                    <Stat setName={setName} />
                     <TotalLoansAndSavingsWidget />
                    <RecentTranscations/>
                </div>
                <div className="col-sm-1"></div>

                <div>

                </div>
            </div>
            <Footer/>

        </div>
    )
}


export default CustomerDashboard