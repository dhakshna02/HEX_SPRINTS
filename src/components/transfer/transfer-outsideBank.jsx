import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const TransferToOtheBankAccount = () => {

    const [errMesg, setErrMesg] = useState(undefined)
    const [acctdetails, setAccountDetails] = useState([])
    const [beneficiaries, setBeneficiaries] = useState([])

    const navigate = useNavigate();

    const [sourceAccount, setSourceAccount] = useState(undefined)
    const [destinationAccount, setdestinationAccount] = useState("")
    const [amount, setAmount] = useState(undefined)
    const [remarks, Setremarks] = useState(undefined)
    const [ifsc, setIfsc] = useState("")

    const api = "http://localhost:8081/api/account/get-all-accounts"
    const benApi = "http://localhost:8081/api/benificary/get"
    const outsideApi = "http://localhost:8081/api/transcation/transfer/other-bank"

    // 🔹 FETCH ACCOUNTS + EXTERNAL BENEFICIARIES
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

                // 🔥 FILTER EXTERNAL ONLY
                const external = res.data.benifiacries.filter(
                    (b) => b.ifsc !== "IDBI"
                )

                setBeneficiaries(external)

            } catch (err) {
                console.log(err)
            }
        }

        FetchAccount();
        FetchBeneficiaries();

    }, [])

    // 🔹 AUTO FILL
    const handleBeneficiarySelect = (e) => {

        const selectedId = e.target.value;

        const selected = beneficiaries.find(
            (b) => b.id.toString() === selectedId
        )

        if (selected) {
            setdestinationAccount(selected.accountNumber)
            setIfsc(selected.ifsc)
        }
    }

    // 🔹 TRANSFER
    const Transferoutside = async () => {
        if (!sourceAccount) {
        setErrMesg("Please select source account");
        return;
    }

    if (!destinationAccount) {
        setErrMesg("Enter destination account");
        return;
    } if (!amount || amount <= 0) {
        setErrMesg("Enter valid amount");
        return;
    }
     if (!ifsc) {
        setErrMesg("IFSC is required");
        return;
    }
     if (!remarks) {
        setErrMesg("remarks is required");
        return;
    }

        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        try {
            await axios.post(outsideApi, {
                sourceAccount: sourceAccount,
                destinationAccount: destinationAccount,
                amount: amount,
                remarks: remarks,
                ifsc: ifsc
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

                {/* 🔹 DESTINATION ACCOUNT */}
                <div className="input-group">
                    <label>Account Number</label>
                    <input
                        type="text"
                        value={destinationAccount}
                        onChange={(e) => setdestinationAccount(e.target.value)}
                    />
                </div>

                <div className="input-group">
                    <label>Re-enter Account Number</label>
                    <input
                        type="text"
                        value={destinationAccount}
                        onChange={(e) => setdestinationAccount(e.target.value)}
                    />
                </div>

                {/* 🔹 AMOUNT */}
                <div className="input-group">
                    <label>Amount</label>
                    <input
                        type="number"
                        onChange={(e) => setAmount(e.target.value)}
                    />
                </div>

                {/* 🔹 IFSC AUTO FILLED */}
                <div className="input-group">
                    <label>IFSC</label>
                    <input
                        type="text"
                        value={ifsc}
                        onChange={(e) => setIfsc(e.target.value)}
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
                        onChange={(e) => Setremarks(e.target.value)}
                    />
                </div>

                <button
                    className="transfer-submit"
                    onClick={Transferoutside}
                >
                    Transfer Now
                </button>

            </div>
        </div>
    )
}

export default TransferToOtheBankAccount;