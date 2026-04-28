import { useEffect, useState } from "react"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/transfer/csss/within-bank.css"
import axios from "axios"
import { useNavigate } from "react-router-dom"

const TransferWithinBank = () => {

    const api = "http://localhost:8081/api/account/get-all-accounts"
    const benApi = "http://localhost:8081/api/benificary/get?page=0&size=100"
    const insidebank = "http://localhost:8081/api/transcation/transfer/inside-bank"

    const [errMesg, setErrMesg] = useState(undefined)
    const [acctdetails, setAccountDetails] = useState([])
    const [beneficiaries, setBeneficiaries] = useState([])

    const navigate = useNavigate();

    const [sourceAccount, setSourceAccount] = useState(undefined)
    const [destinationAccount, setdestinationAccount] = useState("")
    const [amount, setAmount] = useState(undefined)
    const [remarks, Setremarks] = useState(undefined)

    // 🔹 FETCH ACCOUNTS + BENEFICIARIES
    useEffect(() => {

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        const FetchAccount = async () => {
            try {
                const response = await axios.get(api, config)
                setAccountDetails(response.data)
            } catch (err) {
                setErrMesg(err.response.data.message)
            }
        }

        const FetchBeneficiaries = async () => {
            try {
                const res = await axios.get(benApi, config)

                // 🔥 FILTER INTERNAL ONLY
                const internal = res.data.benifiacries.filter(
                    (b) => b.ifsc === "IDBI"
                )

                setBeneficiaries(internal)

            } catch (err) {
                console.log(err)
            }
        }

        FetchAccount();
        FetchBeneficiaries();

    }, [])

    // 🔹 AUTO FILL WHEN SELECT BENEFICIARY
    const handleBeneficiarySelect = (e) => {

        const selectedId = e.target.value;

        const selected = beneficiaries.find(
            (b) => b.id.toString() === selectedId
        )

        if (selected) {
            setdestinationAccount(selected.accountNumber)
        }
    }

    // 🔹 TRANSFER
    const Transferinside = async (e) => {
        e.preventDefault();
        if (!sourceAccount) {
            setErrMesg("Please select source account");
            return;
        }

        if (!destinationAccount) {
            setErrMesg("Enter destination account");
            return;
        }

        if (!amount || amount <= 0) {
            setErrMesg("Enter valid amount");
            return;
        }

        // optional
        if (sourceAccount === destinationAccount) {
            setErrMesg("Source and destination cannot be same");
            return;
        }

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        try {
            await axios.post(insidebank, {
                sourceAccount: sourceAccount,
                destinationAccount: destinationAccount,
                amount: amount,
                remarks: remarks
            }, config)

            navigate("/customer")

        } catch (err) {
            setErrMesg(err.response.data.message)
        }
    }

    return (
        <div>
            <div className="transfer-card">

                <h3 className="card-title">Bank Transfer</h3>

                {errMesg && (
                    <div className="error-box">
                        {errMesg}
                    </div>
                )}

                {/* 🔹 BENEFICIARY DROPDOWN */}
                <div className="input-group">
                    <label>Select Beneficiary</label>
                    <select
                        className="transfer-input"
                        onChange={handleBeneficiarySelect}
                    >
                        <option>Select beneficiary</option>
                        {
                            beneficiaries.map((b) => (
                                <option key={b.id} value={b.id}>
                                    {b.payeeName} - {b.accountNumber}
                                </option>
                            ))
                        }
                    </select>
                </div>

                <form onSubmit={Transferinside} >
                    {/* 🔹 DESTINATION ACCOUNT */}
                    <div className="input-group">
                        <label>Account Number</label>
                        <input
                            type="text"
                            value={destinationAccount}
                            placeholder="Enter account number"
                            required="required"
                            onChange={(e) => setdestinationAccount(e.target.value)}
                        />
                    </div>

                    <div className="input-group">
                        <label>Re-enter Account Number</label>
                        <input
                            type="text"
                            value={destinationAccount}
                            required="required"
                            onChange={(e) => setdestinationAccount(e.target.value)}
                        />
                    </div>

                    <div className="input-group">
                        <label>Amount</label>
                        <input
                            type="number"
                            placeholder="Enter amount"
                            required="required"
                            onChange={(e) => setAmount(e.target.value)}
                        />
                    </div>

                    {/* 🔹 SOURCE ACCOUNT */}
                    <div className="input-group">
                        <label>Select Account</label>

                        <select
                            className="transfer-input"
                            onChange={(e) => setSourceAccount(e.target.value)}
                           
                        >
                            <option>Select your account</option>
                            {
                                acctdetails.map((d, index) => (
                                    <option key={index} value={d.accountNo}>
                                        {d.accountNo}
                                    </option>
                                ))
                            }
                        </select>
                    </div>

                    <div className="input-group">
                        <label>Remarks</label>
                        <input
                            type="text"
                            placeholder="enter remarks"
                            required="required"
                            onChange={(e) => Setremarks(e.target.value)}
                        />
                    </div>

                    <button
                        className="transfer-submit"
                        type="submit"
                    >
                        Transfer Now
                    </button>

                </form>

            </div>
        </div>
    )
}

export default TransferWithinBank