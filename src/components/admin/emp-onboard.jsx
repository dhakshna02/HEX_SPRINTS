import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../customer/navbar";
import Sidebar from "../customer/sidebar";
import SidebarAdmin from "./side-bar-admin";
import Footer from "../footer";

const EmployeeSignUp = () => {

    const navigate = useNavigate();

    const [name, setName] = useState(undefined)
    const [email, setEmail] = useState(undefined)
    const [mobNo, setMobNo] = useState(undefined)
    const [designation, setDesignation] = useState(undefined)
    const [userName, setUsername] = useState(undefined)
    const [password, setPassword] = useState(undefined)

    const [errMesg, setErrMesg] = useState(undefined)
    const [successMsg, setSuccessMsg] = useState(undefined)

    const api = "http://localhost:8081/api/employee/create"

    const createEmployee = async () => {

         if (!name) {
        setErrMesg("Name is required");
        return;
    }

    if (!email) {
        setErrMesg("Email is required");
        return;
    }

    if (!mobNo) {
        setErrMesg("Mobile number is required");
        return;
    }

    if (!designation) {
        setErrMesg("Select designation");
        return;
    }

    if (!userName) {
        setErrMesg("Username is required");
        return;
    }

    if (!password) {
        setErrMesg("Password is required");
        return;
    }

    // ✅ clear error
    setErrMesg(null);


        try {
            await axios.post(api, {
                "name": name,
                "email": email,
                "mobNo": mobNo,
                "designation": designation,
                "userName": userName,
                "password": password
            })

            setSuccessMsg("Employee Created Successfully")

            // ✅ redirect
            setTimeout(() => {
                navigate("/admin")
            }, 1000)

        } catch (err) {
            setErrMesg(err.message)
        }
    }

    return (
        <div>

            {/* NAVBAR */}
            <div className="row">
                <Navbar />
            </div>

            <div className="row">

                {/* SIDEBAR */}
                <div className="col-2 bg-black min-vh-100">
                    <SidebarAdmin />
                </div>

                {/* MAIN */}
                <div className="col-md-9 mt-4">

                    <div className="transfer-card mb-4">

                        <h3 className="card-title">Employee Creation</h3>

                        {errMesg && (
                            <div className="error-box">
                                {errMesg}
                            </div>
                        )}

                        {successMsg && (
                            <div className="alert alert-success">
                                {successMsg}
                            </div>
                        )}

                        <div className="input-group">
                            <label>Name</label>
                            <input type="text" required="required"onChange={(e) => setName(e.target.value)} />
                        </div>

                        <div className="input-group">
                            <label>Email</label>
                            <input type="email" onChange={(e) => setEmail(e.target.value)} />
                        </div>

                        <div className="input-group">
                            <label>Mobile No</label>
                            <input type="text" onChange={(e) => setMobNo(e.target.value)} />
                        </div>

                        <div className="input-group">
                            <label>Designation</label>
                            <select
                                className="transfer-input"
                                onChange={(e) => setDesignation(e.target.value)}
                            >
                                <option value="">Select Designation</option>
                                <option value="MANAGER">Manager</option>
                                <option value="ASSET_VERIFIER">Assest verifier</option>
                                <option value="FINACIAL_ANALYST">Financial Analyst</option>
                            </select>
                        </div>

                        <div className="input-group">
                            <label>Username</label>
                            <input type="text" onChange={(e) => setUsername(e.target.value)} />
                        </div>

                        <div className="input-group">
                            <label>Password</label>
                            <input type="password" onChange={(e) => setPassword(e.target.value)} />
                        </div>

                        <button
                            className="transfer-submit"
                            onClick={() => createEmployee()}
                        >
                            Create Employee
                        </button>

                    </div>

                </div>
                <div className="col-1"></div>
            </div>
            <Footer/>
        </div>
    )
}

export default EmployeeSignUp