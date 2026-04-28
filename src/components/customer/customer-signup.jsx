import axios from "axios"
import { use, useState } from "react"
import { Link } from "react-router-dom"

const CustomerSignUp = () => {

    const [name, setName] = useState(undefined)
    const [mobNo, setMobNo] = useState(undefined)
    const [mail, setEmail] = useState(undefined)
    const [address, setAddress] = useState(undefined)
    const [dob, setDob] = useState(undefined)
    const [gender, setGender] = useState(undefined)
    const [occupation, setOccupation] = useState(undefined)
    const [annualIncome, setAnnualIncome] = useState(undefined)
    const [userName, setUsername] = useState(undefined)
    const [password, setPassword] = useState(undefined)

    const [successMsg, setSuccessMsg] = useState(undefined)
    const [errMsg, setErrorMsg] = useState(undefined)

    const signUpApi = "http://localhost:8081/api/customer/signup"


    const processSignup = async (e) => {
        e.preventDefault();

        try {
            await axios.post(signUpApi, {
                "name": name,
                "mobNumber": mobNo,
                "mailId": mail,
                "address": address,
                "DOB": dob,
                "gender": gender,
                "occupation": occupation,
                "annualIncome": annualIncome,
                "userName": userName,
                "password": password

            })

            setSuccessMsg("Successfully registerd")


        } catch (err) {
            setErrorMsg(err.message)
        }




    }



    return (



        <div className="container">
            
            <div className="row mt-4">
                <div className="col-sm-3" >

                </div>
                <div className="col-md-6" >
                    <div className="card">
                        <div className="card-header">
                            Customer Sign Up
                        </div>
                        <div className="card-body">
                            {
                                errMsg == undefined ? "" :
                                    <div className="alert alert-danger mt-4">
                                        {errMsg}
                                    </div>
                            }
                            {
                                successMsg == undefined ? "" :
                                    <div className="alert alert-primary mt-4">
                                        {successMsg}
                                    </div>
                            }
                            <form onSubmit={(e) => processSignup(e)}>
                                <div className="mt-4">
                                    <label>Name: </label>
                                    <input type="text" className="form-control" required="required"
                                        onChange={(e) => setName(e.target.value)}
                                    />
                                </div>

                                <div className="mt-4">
                                    <label>Mob No: </label>
                                    <input type="text" className="form-control" required="required"
                                        onChange={(e) => setMobNo(e.target.value)}
                                    />
                                </div>

                                <div className="mt-4">
                                    <label>Email: </label>
                                    <input type="email" className="form-control" required="required"
                                        onChange={(e) => setEmail(e.target.value)}
                                    />
                                </div>


                                <div className="mt-4">
                                    <label>Address: </label>
                                    <input type="text" className="form-control" required="required"
                                        onChange={(e) => setAddress(e.target.value)}
                                    />
                                </div>



                                <div className="mt-4">
                                    <label>DOB: </label>
                                    <input
                                        type="date"
                                        className="form-control"
                                        required
                                        onChange={(e) => setDob(e.target.value)}
                                    />
                                </div>


                                <div className="mt-4">
                                    <label>Gender: </label>
                                    <select
                                        className="form-control"
                                        required
                                        onChange={(e) => setGender(e.target.value)}
                                    >
                                        <option value="">-- Select Gender --</option>
                                        <option value="MALE">Male</option>
                                        <option value="FEMALE">Female</option>
                                        <option value="OTHER">Other</option>
                                    </select>
                                </div>

                                <div className="mt-4">
                                    <label>Occupation: </label>
                                    <input type="text" className="form-control" required="required"
                                        onChange={(e) => setOccupation(e.target.value)}
                                    />
                                </div>


                                <div className="mt-4">
                                    <label>Annual Income: </label>
                                    <input type="text" className="form-control" required="required"
                                        onChange={(e) => setAnnualIncome(e.target.value)}
                                    />
                                </div>



                                <hr />
                                <div className="mt-4">
                                    <label>Username: </label>
                                    <input type="text" className="form-control" required="required"
                                        onChange={(e) => setUsername(e.target.value)}
                                    />
                                </div>
                                <div className="mt-4">
                                    <label>Password: </label>
                                    <input type="password" className="form-control" required="required"
                                        onChange={(e) => setPassword(e.target.value)}
                                    />
                                </div>
                                <div className="mt-4 mb-4">
                                    <input type="submit" className="btn btn-primary" value="Sign Up" />
                                </div>

                                <div className="mt-4">
                                    Have an account already?
                                    &nbsp;&nbsp;
                                    <Link to="/login">Login</Link>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div className="col-sm-3" >

                </div>
            </div>
            
        </div>

    )
}

export default CustomerSignUp