import { useEffect, useState } from "react"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/customer/css-files/recent-transcations.css"
import axios from "axios"
import { useNavigate } from "react-router-dom"


const RecentTranscations = () => {



    const navigate = useNavigate();
    const api = "http://localhost:8081/api/transcation/get-all-Transction"

    const [transca, setTransca] = useState(undefined)
    const [errMesg, setErrMesg] = useState(undefined)




    useEffect(() => {


        const transction = async () => {


            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            }

            try {
                const response = await axios.get(api, config)
                setTransca(response.data.transcations)
                console.log(response.data)

            } catch (err) {
                err.setErrMesg
            }

        }
        transction()

    }, [])



    return (

        <div>
            <div className="stat-card mt-4 mb-4">

                {/* <!-- HEADER --> */}
                <div className="rt-header" onClick={() => navigate("/statement")}
                    >
                    <h6>Recent Transactions</h6>
                    <span className="view-all">View all →</span>
                </div>

                {/* <!-- ITEM --> */}


                {transca?.map((t, index) => (
                    <div key={index}>

                        <div className="rt-item">
                            <div className="rt-left">
                                
                                <div className="rt-title">{t?.transcationId??""}</div>
                                <div className="rt-title">{t?.type??""}</div>
                            
                                <div className="rt-title">{t?.description ?? ""}</div>
                                <div className="rt-date">  {new Date(t.date).toLocaleString()}
                                </div>
                            </div>
                            <div className={`rt-amount ${t?.TreanscationFlow === "CREDIT" ? "credit" : "debit"}`}>
                                {t?.TreanscationFlow === "CREDIT" ? "+" : "-"}₹{t?.amount}
                            </div>
                        </div>
                    </div>
                ))}


            </div >
        </div>

    )
}
export default RecentTranscations