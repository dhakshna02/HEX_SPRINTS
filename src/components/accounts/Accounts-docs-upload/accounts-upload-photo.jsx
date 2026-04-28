import axios from "axios"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/accounts/accounts-css/identityProof.css"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/accounts/accounts-css/photo.css"

const PhotoGraph = () => {

    const api = "http://localhost:8081/api/document/upload"
    const PhotoApi = "http://localhost:8081/api/document/upload-photo"

    const [file, setFile] = useState(undefined)
    const [preview, setPreview] = useState(undefined)
    const [error, setError] = useState(undefined)
    const [loading, setLoading] = useState(false)
    const [proceed, setProceed] = useState(false)

    const navigate = useNavigate()

    const handleFileChange = (e) => {
        const selected = e.target.files[0]
        setFile(selected)

        if (selected) {
            setPreview(URL.createObjectURL(selected))
        }
    }

    const UploadFile = async () => {

        // if (!proceed) {
        //     setError("Please click the proceed box first")
        //     return
        // }

        if (!file) {
            setError("Please upload photo")
            return
        }

        const formData = new FormData()
        formData.append("file", file)

        try {
            setLoading(true)

            const response = await axios.post(api, formData, {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            })

            console.log(response.data)

            let path = "src/assets/uploads/"
            const filePath = path + response.data.documentNamee

            await axios.post(PhotoApi, {
                photo: filePath
            }, {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            })



        } catch (err) {
            setError("Upload failed")
        } finally {
            setLoading(false)
        }
    }

    return (

        <div className="deposit-container mt-10">

            <div className="deposit-header">
                <h4>Photo Upload</h4>
                <p>Step 4 of 5 — Upload your Photo</p>
            </div>

            <div className="deposit-main">
                <div className="deposit-card">

                    {/* STEPPER */}
                    <div className="stepper">
                        <div className="step-dot"></div>
                        <div className="step-dot"></div>
                        <div className="step-dot"></div>
                        <div className="step-dot active"></div>
                        <div className="step-dot"></div>
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
                                <label>Upload Photo</label>
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
                                {loading ? "Uploading..." : "Uploaded successfully"}
                            </button>

                            {/* CLICKABLE BOX */}

                           

                            </div>

                            

                            {/* RIGHT */}
                            <div className="deposit-info">

                                <h6>Preview</h6>

                                {
                                    preview === undefined ?
                                        <p className="deposit-note">
                                            Uploaded Photo will appear here
                                        </p>
                                        :
                                        <div className="preview-box">
                                            <img src={preview} className="preview-img"></img>
                                        </div>
                                }

                            </div>

                        </div>

                         <div className="row">
                                <div className="col-md-6">
                                    <div
                                        className={`action-box ${proceed ? "active-box" : ""}`}
                                        onClick={() => setProceed(true)}>
                                        <div className="action-box-content">


                                            <button onClick={() => navigate("/create-account/sign")}  > If already account is initated click me
                                            </button>


                                        </div>

                                    </div>

                                    </div>

                                    <div className="col-md-6">
                                    <div
                                        className={`action-box ${proceed ? "active-box" : ""}`}
                                        onClick={() => setProceed(true)}
                                    >
                                        <div className="action-box-content">


                                            <button onClick={() => navigate("/create-account/signature")}  > If  account is  not initated click me
                                            </button>

                                            <br />
                                        </div>

                                    </div>

                                    </div>

                                </div>

                    </div>
                </div>

            </div>

            )
}

            export default PhotoGraph