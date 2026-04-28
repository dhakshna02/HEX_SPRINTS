import { useEffect, useState } from "react";
import axios from "axios";
import Navbar from "../customer/navbar";
import FinanceDonut from "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/analytics/CustomerDonut.jsx";
import Sidebar from "../customer/sidebar";
import RecentTranscations from "../customer/recent-transcations";
import MonthlyComparisonChart from "./customerBarChat";
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/analytics/analytics.css";
import Footer from "../footer";

const FinanceDashboard = () => {

    const [data, setData] = useState(null);
    const [err, setErr] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            const config = {
                headers: { Authorization: "Bearer " + localStorage.getItem("Token") },
            };
            try {
                const res = await axios.get(
                    "http://localhost:8081/api/transcation/inflow-outflow",
                    config
                );
                setData(res.data);
            } catch (e) {
                setErr("Failed to load data");
            }
        };
        fetchData();
    }, []);

    return (
        <div>
           

            <div className="row ">
 <Navbar />
                {/* SIDEBAR */}
                <div className="col-2 bg-black min-vh-100">
                    <Sidebar />
                </div>

                {/* CONTENT */}
                <div className="col-sm-9 mt-4">

                    {/* CHART ROW */}
                    <div className="row ms-5">

                        <div className="col-md-4 ">
                            <div className="card ms-4">
                                <div className="card-header">
                                    <h5>Finance Overview</h5>
                                </div>
                                <div className="card-body">
                                    <FinanceDonut />
                                </div>
                            </div>
                        </div>

                        <div className="col-md-7 mt-4">
                            <div className="card">
                                <div className="card-header">
                                    <h5>Monthly Overview</h5>
                                </div>
                                <div className="card-body ms-4">
                                    <MonthlyComparisonChart />
                                </div>
                            </div>


                        </div>
                    </div>

                    {/* TRANSACTIONS */}
                    <div className="row">
                        <div className="col-md-12 mt-4">
                            <RecentTranscations />
                        </div>
                    </div>

                </div>
                <div className="col-1">

                </div>

            </div>
            <Footer/>
        </div>
    );
};

export default FinanceDashboard;