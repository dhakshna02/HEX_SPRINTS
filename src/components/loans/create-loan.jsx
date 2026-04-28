import axios from "axios";
import { useState } from "react"
import { useNavigate } from "react-router-dom";
import Navbar from "../customer/navbar";
import Sidebar from "../customer/sidebar";
import Footer from "../footer";

const CreateLoan = () => {

    const [errMesg, setErrMesg] = useState(undefined)


    const api = "http://localhost:8081/api/document/upload";

    const createLoanApi = "http://localhost:8081/api/loan/create-loan"

    const [incomecerti, setincomecerti] = useState(undefined)
    const [file, setFile] = useState(undefined)
    const [loanType, setloanType] = useState(undefined)
    const [requestedLoanAmount, setrequestedLoanAmount] = useState(undefined)
    const [incomeCertificate, setincomeCertificate] = useState(undefined)
    const [isUploaded, setIsUploaded] = useState(false);

    const [collatralName, setCollatralName] = useState(undefined)
    const [collatralType, setCollatralType] = useState(undefined)
    const [collatralAddress, setcollatralAddress] = useState(undefined)
    const [collatralDocument, setcollatralDocument] = useState(undefined)
    const[collatralDocUrl,setcollatralDocUrl] = useState(undefined)
    const [collatralUploaded, setCollatralUploaded] = useState(undefined)


    const navigate = useNavigate();

    const UploadFile = async () => {

        const formData = new FormData()

        formData.append("file", file)





        const response = await axios.post(api, formData, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        })


        let path = "src/assets/uploads/"
        setincomecerti(path + response.data.documentNamee)
        console.log(response.data)
        setIsUploaded(true)
    }

    const createLoans = async () => {



    if (!requestedLoanAmount || requestedLoanAmount <= 0) {
        setErrMesg("Enter valid loan amount");
        return;
    }

   
    if (!loanType || loanType === "") {
        setErrMesg("Please select loan type");
        return;
    }

   
    if (!file) {
        setErrMesg("Select income certificate file");
        return;
    }

    if (!isUploaded) {
        setErrMesg("Upload income certificate first");
        return;
    }

  
    if (!collatralName) {
        setErrMesg("Enter collateral name");
        return;
    }

    if (!collatralType) {
        setErrMesg("Select collateral type");
        return;
    }

    if (!collatralAddress) {
        setErrMesg("Enter collateral address");
        return;
    }

    if (!collatralDocument) {
        setErrMesg("Select collateral document");
        return;
    }

    if (!collatralUploaded) {
        setErrMesg("Upload collateral document first");
        return;
    }

   
    setErrMesg(null);




        const config = {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        }

        try {
            const response = await axios.post(createLoanApi, {

                "loanType": loanType,
                "requestedLoanAmount": requestedLoanAmount,
                "incomeCertificate": incomecerti,
                "collatralName":collatralName,
                "collatralType":collatralType,
                "collatralAddress":collatralAddress,
                "collatralDocUrl":collatralDocUrl
            }, config)
            navigate("/customer")

        } catch (err) {
            setErrMesg(err.message)
        }






    }


    const uploaFile= async ()=>{

         const formData = new FormData()

        formData.append("file", collatralDocument)

        setCollatralUploaded




        const response = await axios.post(api, formData, {
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("Token")
            }
        })


        let path = "src/assets/uploads/"
        setcollatralDocUrl(path + response.data.documentNamee)
        console.log(response.data)
        setCollatralUploaded(true)
    }

    const isPersonalLoan = loanType === "PERSONAL";
      
    
    
    return (
        <div>

            <div className="row">
                <Navbar />

                <div className="col-2 min-vh-100 bg-black">
                    <Sidebar />
                </div>

                <div className="col-md-9">
                    <div className="transfer-card mt-4">

                        <h3 className="card-title">Apply For Loan</h3>

                        {errMesg && (
                            <div className="error-box">
                                {errMesg}
                            </div>
                        )}

                        <div className="input-group">
                            <label>Loan Required</label>
                            <input type="text" placeholder="Loan Amount"  onChange={(e) => setrequestedLoanAmount(e.target.value)} />
                        </div>

                        <div className="input-group">
                            <label>Loan Type</label>

                            <select
                                className="transfer-input"
                                onChange={(e) => setloanType(e.target.value)}
                                
                            >
                                <option value="">Select Loan Type</option>
                             
                                <option value="VEHICLE">Vehicle Loan</option>
                                <option value="BUSINESS">Business Loan</option>
                                <option valuze="HOME">Home Loan</option>
                                <option value="GOLD">Gold Loan</option>
                            </select>
                        </div>

                        <div className="input-group mt-3">
                            <label>Upload Income Certificate</label>

                            <input
                                type="file"
                                onChange={(e) => setFile(e.target.files[0])}
                            />

                            <button className="deposit-btn" onClick={UploadFile}>
                                Upload
                            </button>

                            {isUploaded && (
                                <span style={{ color: "green", marginLeft: "10px" }}>
                                    ✓ Uploaded
                                </span>
                            )}




                        </div>



                        <input
                            type="text"
                             disabled={isPersonalLoan}
                            onChange={(e) => setCollatralName(e.target.value)}
                            placeholder="collatralName"
                        />

                        <br />
                        <select
                            value={collatralType}
                             disabled={isPersonalLoan}
                            onChange={(e) => setCollatralType(e.target.value)}
                        >
                            <option value="">Select Collateral Type</option>
                            <option value="PROPERTY">PROPERTY</option>
                            <option value="GOLD">GOLD</option>
                            <option value="VEHICLE">VEHICLE</option>
                            <option value="FD">FD</option>
                            <option value="SHARES">SHARES</option>
                        </select>


                        <br />


                        <input
                            type="text"
                             disabled={isPersonalLoan}
                            onChange={(e) => setcollatralAddress(e.target.value)}
                            placeholder="Collatral Address"
                        />

                        <input
                            type="file"
                             disabled={isPersonalLoan}
                            onChange={(e) => setcollatralDocument(e.target.files[0])}
                        />

<div>
                        <button className="deposit-btn"
                         disabled={isPersonalLoan} onClick={uploaFile}>
                            Upload
                        </button>
                        {collatralUploaded && (
                                <span style={{ color: "green", marginLeft: "10px" }}>
                                    ✓ Uploaded
                                </span>
                            )}


</div>

                        <button className="transfer-submit"

                            onClick={() => createLoans()}

                        >Request Loan</button>


                    </div>

                </div>
            </div>
            <Footer/>

        </div>
    )
}

export default CreateLoan