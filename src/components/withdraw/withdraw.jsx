import { useEffect, useState } from "react"
import Navbar from "../customer/navbar"
import Sidebar from "../customer/sidebar"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/Deposit/depositcss/deposit.css"
import axios from "axios"
import { useNavigate } from "react-router-dom"
import Footer from "../footer"




const Withdraw = () => {


    const api = "http://localhost:8081/api/account/get-all-accounts"

    const saveWithdraw = "http://localhost:8081/api/transcation/withdraw"

    const [withdraw, setWithdraw] = useState([])

    const [errMesg, setErrMesg] = useState(undefined)

    const [accountId, setAccountId] = useState(undefined)
    const [value, setValue] = useState(undefined)
    const [remarks, setRemarks] = useState(undefined)
    const [amount, setAmount] = useState(undefined)

    const navigate = useNavigate();



    useEffect(() => {

        const congfig = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        const fetchWithdraw = async () => {





            try {
                const response = await axios.get(api, congfig)
                setWithdraw(response.data)
                console.log(response.data)
            } catch (err) {
                setErrMesg(err.message)
            }



        }
        fetchWithdraw();




    }, [])


    const SaveWithdraws = async () => {
        if (!accountId) {
            setErrMesg("Please select account");
            return;
        }

        if (!value) {
            setErrMesg("Enter amount");
            return;
        }

        if (value <= 0) {
            setErrMesg("Amount must be greater than 0");
            return;
        }


        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }


        try {
            const response = await axios.post(saveWithdraw, {
                "accountId": accountId,
                "withdrawValue": value,
                "remarks": remarks

            }, config)


            navigate("/customer")


        } catch (err) {
            setErrMesg(err.message)
        }

    }







  return (
    <div >

        <div className="row">
            <Navbar />
        </div>

        <div className="row ">

            <div className="col-2 bg-black min-vh-100 ">
                <Sidebar />
            </div>

            <div className="col-9 mt-4 mb-4">
                <div className="deposit-container ms-5">

                    <div className="deposit-header">
                        <h4>Withdraw Funds</h4>
                        <p>Withdraw money securely to your account</p>
                    </div>

                    <div className="deposit-grid">
                        <div className="deposit-card">
                            {errMesg && <div className="error-box">{errMesg}</div>}

                            <div className="deposit-group">
                                <label>Select Account</label>
                                <select className="deposit-input" onChange={(e) => setAccountId(e.target.value)}>
                                    <option>Select your account by Account Number</option>
                                    {withdraw.map((d, index) => (
                                        <option key={index} value={d.accountNo}>{d.accountNo}</option>
                                    ))}
                                </select>
                            </div>

                            <div className="deposit-group">
                                <label>Amount</label>
                                <input type="number" className="deposit-input" placeholder="Enter amount"
                                    value={value ?? ""} onChange={(e) => setValue(e.target.value)} />
                            </div>

                            <div className="deposit-quick">
                                <button onClick={() => setValue(500)}>₹500</button>
                                <button onClick={() => setValue(1000)}>₹1,000</button>
                                <button onClick={() => setValue(5000)}>₹5,000</button>
                                <button onClick={() => setValue(10000)}>₹10,000</button>
                            </div>

                            <div className="deposit-group">
                                <label>Remarks</label>
                                <input type="text" className="deposit-input" placeholder="Optional note"
                                    onChange={(e) => setRemarks(e.target.value)} />
                            </div>

                            <button className="deposit-btn" onClick={SaveWithdraws}>
                                Withdraw Now
                            </button>
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

export default Withdraw