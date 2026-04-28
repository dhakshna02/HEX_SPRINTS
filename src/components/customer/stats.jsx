import axios from "axios"
import { useEffect, useState } from "react"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/customer/stats.css"
import { useNavigate } from "react-router-dom"

const Stat = ({ setName }) => {

    const [statData, setStatData] = useState(undefined)
    const [errMesg, setErrMesg] = useState(undefined)

    const api = "http://localhost:8081/api/transcation/inflow-outflow"

    const navigate = useNavigate();
    const getPercentageChange = (current, previous) => {
        if (previous === 0) {
            if (current === 0) return 0
            return 100  // or return "New"
        }
        return ((current - previous) / previous) * 100
    }


    const inflowChange = getPercentageChange(
        statData?.inFlow,
        statData?.inflowLatMonth
    )

    const outflowChange = getPercentageChange(
        statData?.outFlow,
        statData?.outFlowLastMonth
    )


    useEffect(() => {

        const statss = async () => {

            const config = {

                headers: {
                    "Authorization ": "Bearer " + localStorage.getItem("Token")
                }

            }

            try {

                const response = await axios.get(api, config)
                setStatData(response.data)
                setName(response.data.name)
                console.log(response.data)

            } catch (err) {
               
               setErrMesg(err.response.data.message)
            }
        }
        statss()



    }, [])

    return (
        <div>

            <div className="mb-4">
                <h2 className="welcome-title">
                    Welcome back <span className="highlight-name">{statData?.name ?? ""}!!</span>
                </h2>
                <p className="welcome-subtitle">
                    Here’s your financial overview
                </p>
            </div>
            <div className="row g-3 mt-4">



                <div className="col-md-4">
                    <div className="stat-card"
                        onClick={() => navigate("/accounts-dashboard")}
                        style={{ cursor: "pointer" }}>
                        <p className="stat-label">TOTAL BALANCE</p>
                        <h4 className="stat-value text-cyan">₹ {(statData?.totalBalance ?? 0).toLocaleString("en-IN")}

                        </h4>
                        <span className="stat-change text-success">
                          All accounts balance
                        </span>
                    </div>
                </div>

                <div className="col-md-4">
                    <div className="stat-card"
                     onClick={() => navigate("/accounts-dashboard")}
                        style={{ cursor: "pointer" }}>
                        <p className="stat-label">INFLOW · MAR</p>
                        <h4 className="stat-value text-green">₹ {(statData?.inFlow ?? 0).toLocaleString("en-IN")}</h4>
                        <span className={`stat-change ${inflowChange >= 0 ? "text-success" : "text-danger"}`}>
                            {statData?.inflowLatMonth === 0 && statData?.inFlow > 0
                                ? "New 📈 "
                                : `${inflowChange >= 0 ? "+" : ""}${inflowChange.toFixed(2)}% last month `}

                        </span>
                    </div>
                </div>

                <div className="col-md-4">
                    <div className="stat-card"
                     onClick={() => navigate("/accounts-dashboard")}
                        style={{ cursor: "pointer" }}>
                        <p className="stat-label">OUTFLOW · MAR</p>
                        <h4 className="stat-value text-red">₹ {(statData?.outFlow ?? 0).toLocaleString("en-IN")}</h4>
                        <span className={`stat-change ${outflowChange >= 0 ? "text-danger" : "text-success"}`}>
                            {(statData?.outFlowLastMonth === 0 && statData?.outFlow > 0)
                                ? "New 📉 "
                                : `${outflowChange >= 0 ? "+" : ""}${outflowChange.toFixed(2)}% last month`}

                        </span>
                    </div>
                </div>
            </div>

        </div>
    )
}

export default Stat