import axios from "axios"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/accounts/accounts-css/identityProof.css"

const Signaturee = () => {

    const api = "http://localhost:8081/api/document/upload"
    const signApi = "http://localhost:8081/api/document/signature-upload"

    const [file, setFile] = useState(undefined)
    const [preview, setPreview] = useState(undefined)
    const [error, setError] = useState(undefined)
    const [loading, setLoading] = useState(false)

    const navigate = useNavigate()

    const handleFileChange = (e) => {
        const selected = e.target.files[0]
        setFile(selected)

        if (selected) {
            setPreview(URL.createObjectURL(selected))
        }
    }

    const UploadFile = async () => {

        if (!file) {
            setError("Please upload signature")
            return
        }

        const formData = new FormData()
        formData.append("file", file)

        try {
            setLoading(true)

            // 🔹 Step 1: Upload file
            const response = await axios.post(api, formData, {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            })

            console.log(response.data)

            let path = "src/assets/uploads/"
            const filePath = path + response.data.documentNamee

            // 🔹 Step 2: Send signature to backend
            await axios.post(signApi, {
                signature: filePath
            }, {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            })

            // 🔹 Step 3: Navigate to customer page
            navigate("/customer")

        } catch (err) {
            setError("Upload failed")
        } finally {
            setLoading(false)
        }
    }

    return (

        <div className="deposit-container mt-10">

            {/* HEADER */}
            <div className="deposit-header">
                <h4>Signature Upload</h4>
                <p>Step 5 of 5 — Upload your Signature</p>
            </div>

            <div className="deposit-main">
                <div className="deposit-card">

                    {/* STEPPER */}
                    <div className="stepper">
                        <div className="step-dot"></div>
                        <div className="step-dot"></div>
                        <div className="step-dot"></div>
                        <div className="step-dot"></div>
                        <div className="step-dot active"></div>
                    </div>

                    <div className="deposit-row-grid">

                        {/* LEFT */}
                        <div className="deposit-form">

                            {
                                error === undefined ? "" :
                                <div className="alert alert-danger">
                                    {error}
                                </div>
                            }

                            <div className="deposit-group">
                                <label>Upload Signature</label>
                                <input
                                    type="file"
                                    className="deposit-input"
                                    onChange={(e) => handleFileChange(e)}
                                />
                            </div>

                            <button
                                className="deposit-btn"
                                onClick={() => UploadFile()}
                            >
                                {loading ? "Uploading..." : "Upload & Finish"}
                            </button>

                        </div>

                        {/* RIGHT */}
                        <div className="deposit-info">

                            <h6>Preview</h6>

                            {
                                preview === undefined ?
                                <p className="deposit-note">
                                    Uploaded signature will appear here
                                </p>
                                :
                                <div className="preview-box">
                                    <img src={preview} className="preview-img"></img>
                                </div>
                            }

                        </div>

                    </div>

                </div>
            </div>

        </div>
    )
}

export default Signaturee