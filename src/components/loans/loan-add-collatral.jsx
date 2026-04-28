import { useState } from "react"
import Navbar from "../customer/navbar"
import Sidebar from "../customer/sidebar"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/Deposit/depositcss/deposit.css"
import axios from "axios"
import { useNavigate, useParams } from "react-router-dom"
import Footer from "../footer"

const AddCollatral = () => {

    const navigate = useNavigate()
    const { id } = useParams() // LoanId

    const uploadApi = "http://localhost:8081/api/document/upload"
    const addCollatralApi = "http://localhost:8081/api/collatral/addcollatral"

    const [collatralName, setCollatralName] = useState("")
    const [collatralType, setCollatralType] = useState("")
    const [collatralAddress, setCollatralAddress] = useState("")
    const [collatralDocument, setCollatralDocument] = useState(null)
    const [docUrl, setDocUrl] = useState("")
    const [uploaded, setUploaded] = useState(false)
    const [errMesg, setErrMesg] = useState()

    // ✅ Upload file first
    const uploadFile = async () => {
        const formData = new FormData()
        formData.append("file", collatralDocument)

        try {
            const response = await axios.post(uploadApi, formData, {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            })

            let path = "src/assets/uploads/"
            setDocUrl(path + response.data.documentNamee)
            setUploaded(true)

        } catch (err) {
            setErrMesg(err.message)
        }
    }

    const saveCollatral = async () => {

        if (!collatralType) {
        setErrMesg("Select collateral type");
        return;
    }

    if (!collatralAddress) {
        setErrMesg("Address required");
        return;
    }

    if (!collatralDocument) {
        setErrMesg("Select file");
        return;
    }

    if (!uploaded) {
        setErrMesg("Upload file first");
        return;
    }

    setErrMesg(null);
        try {
            await axios.post(addCollatralApi, {
                "LoanId": id,
                "collatralName": collatralName,
                "collatralTypes": collatralType,
                "collatralAddress": collatralAddress,
                "collatralDocument": docUrl
            }, {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            })

            navigate("/customer")

        } catch (err) {
            setErrMesg(err.message)
        }
    }

    return (
        <div>

            {/* HEADER */}
            <div className="row">
                <Navbar />
            </div>

            <div className="row">

                {/* SIDEBAR */}
                <div className="col-2 bg-black min-vh-100">
                    <Sidebar />
                </div>

                {/* MAIN */}
                <div className="col-9 mt-1">
                    <div className="deposit-container">

                        {/* HEADER */}
                        <div className="deposit-header">
                            <h4>Add Collateral</h4>
                            <p>Provide collateral details for your loan</p>
                        </div>

                        <div className="deposit-grid">

                            <div className="deposit-card">

                                {/* NAME */}
                                <div className="deposit-group">
                                    <label>Collateral Name</label>
                                    <input
                                        type="text"
                                        className="deposit-input"
                                        placeholder="Enter name"
                                        onChange={(e) => setCollatralName(e.target.value)}
                                    />
                                </div>

                                {/* TYPE */}
                                <div className="deposit-group">
                                    <label>Collateral Type</label>
                                    <select
                                        className="deposit-input"
                                        onChange={(e) => setCollatralType(e.target.value)}
                                    >
                                        <option value="">Select Type</option>
                                        <option value="PROPERTY">PROPERTY</option>
                                        <option value="GOLD">GOLD</option>
                                        <option value="FD">FD</option>
                                        <option value="VEHICLE">VEHICLE</option>
                                    </select>
                                </div>

                                {/* ADDRESS */}
                                <div className="deposit-group">
                                    <label>Address</label>
                                    <input
                                        type="text"
                                        className="deposit-input"
                                        placeholder="Enter address"
                                        onChange={(e) => setCollatralAddress(e.target.value)}
                                    />
                                </div>

                                {/* FILE */}
                                <div className="deposit-group">
                                    <label>Upload Document</label>
                                    <input
                                        type="file"
                                        className="deposit-input"
                                        onChange={(e) => setCollatralDocument(e.target.files[0])}
                                    />

                                    <button className="deposit-btn mt-2" onClick={uploadFile}>
                                        Upload File
                                    </button>

                                    {uploaded && (
                                        <span style={{ color: "green" }}>✓ Uploaded</span>
                                    )}
                                </div>

                                {/* SUBMIT */}
                                <button className="deposit-btn" onClick={saveCollatral}>
                                    Add Collateral
                                </button>

                                {/* ERROR */}
                                {errMesg && (
                                    <div className="error-box mt-2">
                                        {errMesg}
                                    </div>
                                )}

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <Footer/>
        </div>
    )
}

export default AddCollatral