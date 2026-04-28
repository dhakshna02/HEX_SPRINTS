import { useEffect, useState } from "react"
import axios from "axios"
import { useParams } from "react-router-dom"
import SidebarEmployee from "../employee/sidebar-Employee"
import Navbar from "../customer/navbar"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/loan-employee/loan-employee/other-loans.css"
import Footer from "../footer"

const AssetValue = () => {

    const { id } = useParams()

    // 👉 API (replace 10 with dynamic id if needed)
    const api = `http://localhost:8081/api/collatral/collatrals/${id}`
    const uploadValueApi = "http://localhost:8081/api/collatral/collatral-value"

    const [collatral, setCollatral] = useState([])
    const [value, setValue] = useState("")
    const [err, setErr] = useState(undefined)
    const config = {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("Token")
        }
    }

    useEffect(() => {

        const fetchCollatral = async () => {



            try {
                const res = await axios.get(api, config)
                setCollatral(res.data)
                console.log(res.data)
            } catch (e) {
                setErr("Failed to load collatral")
            }
        }

        fetchCollatral()

    }, [id])



    const uploadValue = async () => {


      if (!value) {
        setErr("Enter collateral value");
        return;
    }

    if (value <= 0) {
        setErr("Enter valid amount");
        return;
    }

        const respons = await axios.put(uploadValueApi, {
            "collatralId": collatral.collatralId,
            "collatralValue": value
        }, config)

        alert("Successful");
       Navigate("/")


    }

    return (
        <div >


            <div className="row">
                <Navbar />

            </div>

            <div className="row">

            <div className="col-2 bg-black min-vh-100">

                <SidebarEmployee />

            </div>
            <div className="col-md-9">

                {err && <div className="alert alert-danger">{err}</div>}

                {
                    (
                        <div className="row g-4 ms-5 mb-4">

                            {/* DETAILS */}
                            <div className="col-lg-10 ">
                                <div className="loan-card mt-4">

                                    <h5>Collateral Details</h5>
                                    <p><b>Loan Id:</b> {collatral.LoanId}</p>
                                    <p><b>Id:</b> {collatral.collatralId}</p>
                                    <p><b>Name:</b> {collatral.collatralName}</p>
                                    <p><b>Type:</b> {collatral.collatralTypes}</p>
                                    <p><b>Address:</b> {collatral.collatralAddress}</p>

                                    {/* INPUT VALUE */}
                                    <div className="loan-group">
                                        <label>Collateral Value</label>

                                        <input
                                            type="number"
                                            className="loan-input"
                                            placeholder="Enter value"
                                            value={value}
                                            onChange={(e) => setValue(e.target.value)}
                                        />

                                        <button className="btn btn-primary" onClick={uploadValue} >
                                            Upload Value
                                        </button>
                                    </div>

                                    <p><b>Entered:</b> ₹{value}</p>

                                </div>
                            </div>

                            {/* IMAGE */}
                            <div className="col-md-10 mb-4 ">
                                <div className="loan-card mt-4 mb-4">

                                    <h6>Collateral Document</h6>

                                    <img
                                        src={collatral?.collatralDocument ?? ""}
                                        alt="collatral"
                                        className="loan-img"
                                    />

                                </div>
                            </div>

                        </div>
                    )
                }

            </div>

            <div className="col-1">

            </div>
        </div>
        <Footer/>
        </div >


    )
}

export default AssetValue