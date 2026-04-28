import axios from "axios"
import { useState } from "react"
import { Link, Outlet } from "react-router-dom"
import Navbar from "../customer/navbar"
import Sidebar from "../customer/sidebar"
import Footer from "../footer"


const CreateAccount = () => {


    const [accountType, setAccountType] = useState(undefined)
    const [identityProof, setIdentityProof] = useState(undefined)
    const [addressProof, setAddressProof] = useState(undefined)
    const [panNo, setPanNo] = useState(undefined)
    const [photograph, setPhotograph] = useState(undefined)
    const [signature, setSignature] = useState(undefined)

    const [successMsg, setSuccessMsg] = useState(undefined)
    const [errMsg, setErrorMsg] = useState(undefined)

    const api = "http://localhost:8081/api/account/create"



    const createAccount = async (e) => {

        e.preventDefault()

         const config = {
                headers: {
                    "Authorization": 'Bearer ' + localStorage.getItem("Token")

                }
            }

        try {
            await axios.post(api,{

                "accountType": accountType,
                "identityProof":identityProof,
                "addressProof":addressProof,
                "panNo":panNo,
                "photograph":photograph,
                "signature":signature
            },config)
            

            setSuccessMsg("Account creation initated" )

        }catch(err){

            console.log(err.message)

            setErrorMsg(err.response.message)
        }

        }






    return (

        <div>

    <div>
            <div className="row">
                <Navbar />
            </div>

            <div className="row">
                <div className="col-2 bg-black min-vh-100">
                    <Sidebar />
                </div>

                <div className="col-9" >

                    <Outlet/>
                   </div>
                    <div className="col-1">
                </div>
                
                  
               
            </div>
             <Footer/>
        </div>

</div>
      
      

    )
}

export default CreateAccount

{/* //  <div className="card">
//                         <div className="card-header">
//                            Create Account
//                         </div>
//                         <div className="card-body"> */}
{/* //                             { */}
{/* //                                 errMsg == undefined ? "" :
//                                     <div className="alert alert-danger mt-4">
//                                         {errMsg}
//                                     </div>
//                             }
//                             { */}
{/* //                                 successMsg == undefined ? "" :
//                                     <div className="alert alert-primary mt-4">
//                                         {successMsg}
//                                     </div>
//                             }
//                             <form onSubmit={(e) => createAccount(e)}>
//                                 <div className="mt-4">
//                                     <label>Account Type: </label>
//                                     <select */}
{/* //                                         className="form-control"
//                                         required
//                                         onChange={(e) => setAccountType(e.target.value)}
//                                     >
//                                         <option value="">-- Select Account Type --</option>
//                                         <option value="SAVINGS">SAVINGS</option>
//                                         <option value="CURRENT">CURRENT</option>

//                                     </select> */}
{/* //                           

//                                 <div className="mt-4">
//                                     <label>Identity Proof: </label>
//                                     <input type="text" className="form-control" required="required"
//                                         onChange={(e) => setIdentityProof(e.target.value)}
//                                     />
//                                 </div>

//                                 <div className="mt-4">
//                                     <label>Address Proof: </label>
//                                     <input type="text" className="form-control" required="required"
//                                         onChange={(e) => setAddressProof(e.target.value)}
//                                     />
//                                 </div>


//                                 <div className="mt-4">
//                                     <label>Pan No: </label>
//                                     <input type="text" className="form-control" required="required"
//                                         onChange={(e) => setPanNo(e.target.value)}
//                                     />
//                                 </div>



//                                 <div className="mt-4">
//                                     <label>Photograph: </label>
//                                     <input type="text" className="form-control" required="required"
//                                         onChange={(e) => setPhotograph(e.target.value)}
//                                     />
//                                 </div>


//                                 <div className="mt-4">
//                                     <label>Signature : </label>
//                                     <input type="text" className="form-control" required="required"
//                                         onChange={(e) => setSignature(e.target.value)}
//                                     />
//                                 </div>




//                                 <div className="mt-4 mb-4">
//                                     <input type="submit" className="btn btn-primary" value="Create Account" />
//                                 </div>


//                             </form>

//                         </div>
//                     </div> */}