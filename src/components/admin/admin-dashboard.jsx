import { useEffect, useState } from "react";
import axios from "axios";
import Navbar from "../customer/navbar";
import SidebarAdmin from "./side-bar-admin";
import { Chart } from "primereact/chart";
import { useNavigate } from "react-router-dom";
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/admin/admin-css/admin-dashboard.css"
import Footer from "../footer";
const AdminDashBoard = () => {

    const navigate = useNavigate();

    const [data, setData] = useState(null);
    const [chartData, setChartData] = useState({});
    const [chartOptions, setChartOptions] = useState({});

    useEffect(() => {

        const fetchStats = async () => {
            try {
                const res = await axios.get(
                    "http://localhost:8081/api/employee/stats/manager",
                    {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem("Token")
                        }
                    }
                );

                const apiData = res.data;
                setData(apiData);

                const documentStyle = getComputedStyle(document.documentElement);

                // 🔹 YOUR BAR CHART (same style, theme colors)
                const chart = {
                    labels: ['Ongoing Loans', 'Pending Loans', 'Active Accounts', 'Pending Accounts'],
                    datasets: [
                        {
                            label: 'Manager Stats',
                            data: [
                                apiData.NoloanOngoing,
                                apiData.NoLoanPending,
                                apiData.NoOfAcctActive,
                                apiData.NoOfAccPending
                            ],
                            backgroundColor: [
                                documentStyle.getPropertyValue('--green-500'),
                                documentStyle.getPropertyValue('--yellow-500'),
                                documentStyle.getPropertyValue('--blue-500'),
                                documentStyle.getPropertyValue('--red-500')
                            ],
                            borderWidth: 1
                        }
                    ]
                };

                const options = {
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                };

                setChartData(chart);
                setChartOptions(options);

            } catch (err) {
                console.error(err);
            }
        };

        fetchStats();

    }, []);

    return (
        <div>
            <div className="row">
                <Navbar />
            </div>
                <div className="row ">

                    {/* 🔹 SIDEBAR */}
                    <div className="col-2 bg-black min-vh-100">
                        <SidebarAdmin />
                    </div>
                  

                    {/* 🔹 MAIN CONTENT */}
                    <div className="col-md-9 mt-4 ms-4">

                        {/* 🔹 YOUR ORIGINAL CARDS (UNCHANGED STYLE) */}
                        <div className="row ">

                            <div className="col-md-6 mt-4">
                                <div className="stat-card"
                                    onClick={() => navigate("/accounts-admin")}
                                    style={{ cursor: "pointer" }}>
                                    <p className="stat-label">Total Active Accounts</p>
                                    <h4 className="stat-value text-cyan">
                                        {data?.NoOfAcctActive || 0}
                                    </h4>
                                </div>
                            </div>

                            <div className="col-md-6 mt-4">
                                <div className="stat-card" onClick={() => navigate("/accounts-admin")}>
                                    <p className="stat-label">Pending Accounts</p>
                                    <h4 className="stat-value text-cyan">
                                        {data?.NoOfAccPending || 0}
                                    </h4>
                                </div>
                            </div>

 
                            <div className="col-md-4 mt-4">
                                <div className="stat-card"
                                onClick={() => navigate("/loan-admin")}>
                                    <p className="stat-label">Ongoing Loans</p>
                                    <h4 className="stat-value text-cyan">
                                        {data?.NoloanOngoing || 0}
                                    </h4>
                                </div>
                            </div>

                            <div className="col-md-4 mt-4">
                                <div className="stat-card"
                                onClick={() => navigate("/loan-admin")}>
                                    <p className="stat-label">Pending Loans</p>
                                    <h4 className="stat-value text-cyan">
                                        {data?.NoLoanPending || 0}
                                    </h4>
                                </div>
                            </div>

                            <div className="col-md-4 mt-4">
                                <div className="stat-card"
                                onClick={() => navigate("/loan-admin")}>
                                    <p className="stat-label">Total Loans</p>
                                    <h4 className="stat-value text-cyan">
                                        {(data?.NoloanOngoing || 0) + (data?.NoLoanPending || 0)}
                                    </h4>
                                </div>
                            </div>

                        </div>

                        {/* 🔹 BAR CHART (ONLY ADDITION) */}
                        <div className="row mt-4">
                            <div className="col-md-12 mb-4">
                                <div className="card">
                                    <div className="card-header">
                                        <h5>Manager Stats</h5>
                                    </div>
                                    <div className="card-body" style={{ height: "300px" }}>
                                        <Chart type="bar" data={chartData} options={chartOptions} />
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>
                 <Footer/>
            </div>
        
    );
};

export default AdminDashBoard;