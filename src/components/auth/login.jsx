import axios from "axios"
import { useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/auth/login.css"


const Login = () =>{


    const [userName, setUserName ] = useState(undefined)
    const [password,setPassword] = useState(undefined)
    const [token, setToken] = useState(undefined)
    const [errmesg , setErrMesg] = useState(undefined)
    const navigate = useNavigate()

    const loginApi = "http://localhost:8081/api/auth/login"
    const userApi ="http://localhost:8081/api/auth/user-Details"

const processLogin = async (e) =>{


    e.preventDefault()

    try{

        let encoding = window.btoa(userName+":"+password)

        const config = {
            headers :{
                "Authorization" : "Basic "+ encoding
            }

        }

        const response = await axios.get(loginApi,config)

        console.log(response)
        setToken(response.data.token )
        
        localStorage.setItem("Token" , response.data.token)
        

        const detailsHeader = {
            headers :{
                "Authorization ": 'Bearer '+  localStorage.getItem("Token")
            }
        }



        const respon = await axios.get(userApi,detailsHeader)
        console.log(respon.data.Role)

        switch(respon.data.Role){
            case "CUSTOMER":
            navigate("/customer")
            break

            case "ADMIN":
            navigate("/admin")
            break;


            case "EMPLOYEE":
            navigate("/accounts-verification")    
            break;
        }



    }catch(err){
        setErrMesg(err.message)
    }

}



    // return (
    //     <div className="container">
            
    //         <div className="row mt-4 mb-4">
    //             <div className="col-lg-4" >

    //             </div>
    //             <div className="col-lg-4 mt-5" >
    //                 <div className="card">
    //                     <div className="card-header">
    //                         Login
    //                     </div>
    //                     <div className="card-body">
    //                         <form onSubmit={(e) => processLogin(e)}>
    //                               {
    //                                 errmesg == undefined? "" :
    //                                     <div className="alert alert-danger mt-4">
    //                                         {errmesg}
    //                                     </div>
    //                             }
    //                             <div className="mt-4">
    //                                 <label>Username: </label>
    //                                 <input type="text" className="form-control" required="required"
    //                                     onChange={(e) => setUserName(e.target.value)} />
    //                             </div>
    //                             <div className="mt-4">
    //                                 <label>Password: </label>
    //                                 <input type="password" className="form-control" required="required"
    //                                     onChange={(e) => setPassword(e.target.value)} />
    //                             </div>
    //                             <div className="mt-4">
    //                                 <input type="submit" className="btn btn-primary" value="Login" />
    //                             </div>
    //                             <div className="mt-4">
    //                                 Dont have an account
    //                                 <Link to= "/customer-signUp">signup</Link>
    //                             </div>
    //                         </form>
    //                     </div>
    //                 </div>
    //             </div>
    //             <div className="col-lg-4" >

    //             </div>
    //         </div>
            
    //     </div>
    // )

return (
  <div className="login-v2">

    {/* LEFT SIDE */}
    <div className="login-v2-left">
      <div className="login-v2-brand">

        <h2>Mavericks<br />Bank</h2>
        <p>Secure digital banking<br />experience built for the modern era.</p>

        <div className="login-v2-pills">
          <div className="login-v2-pill">
            <div className="login-v2-pill-dot" />
            256-bit SSL Encryption
          </div>
          <div className="login-v2-pill">
            <div className="login-v2-pill-dot" />
            24/7 Fraud Monitoring
          </div>
          <div className="login-v2-pill">
            <div className="login-v2-pill-dot" />
            Instant Transfers
          </div>
        </div>

      </div>
    </div>

    {/* RIGHT SIDE */}
    <div className="login-v2-right">
      <div className="login-v2-card">

        <div className="login-v2-badge">
          <div className="login-v2-badge-dot" />
          Secure Sign-in
        </div>

        <h4 className="login-v2-title">Welcome Back</h4>
        <p className="login-v2-sub">Sign in to your Mavericks account</p>

        <div className="login-v2-divider" />

        {errmesg && (
          <div className="login-v2-error">
            <svg className="login-v2-error-icon" viewBox="0 0 16 16" fill="none">
              <circle cx="8" cy="8" r="7" stroke="#f87171" strokeWidth="1.3" />
              <path d="M8 4.5v4M8 10.5v1" stroke="#f87171" strokeWidth="1.5" strokeLinecap="round" />
            </svg>
            {errmesg}
          </div>
        )}

        <form onSubmit={(e) => processLogin(e)}>

          <div className="login-v2-group">
            <label className="login-v2-label">Username</label>
            <div className="login-v2-input-wrap">
              <input
                type="text"
                className="login-v2-input"
                placeholder="Enter your username"
                required
                onChange={(e) => setUserName(e.target.value)}
              />
              <svg className="login-v2-input-icon" viewBox="0 0 16 16" fill="none">
                <circle cx="8" cy="5.5" r="2.5" stroke="currentColor" strokeWidth="1.2" />
                <path d="M2.5 13c0-2.485 2.462-4.5 5.5-4.5s5.5 2.015 5.5 4.5"
                  stroke="currentColor" strokeWidth="1.2" strokeLinecap="round" />
              </svg>
            </div>
          </div>

          <div className="login-v2-group">
            <label className="login-v2-label">Password</label>
            <div className="login-v2-input-wrap">
              <input
                type="password"
                className="login-v2-input"
                placeholder="••••••••••"
                required
                onChange={(e) => setPassword(e.target.value)}
              />
              <svg className="login-v2-input-icon" viewBox="0 0 16 16" fill="none">
                <rect x="3" y="7" width="10" height="7" rx="1.5"
                  stroke="currentColor" strokeWidth="1.2" />
                <path d="M5 7V5a3 3 0 016 0v2"
                  stroke="currentColor" strokeWidth="1.2" strokeLinecap="round" />
              </svg>
            </div>
          </div>

          <div className="login-v2-row">
            <label className="login-v2-remember">
              <input type="checkbox" defaultChecked />
              Remember me
            </label>
            <Link to="/forgot-password" className="login-v2-forgot">
              Forgot password?
            </Link>
          </div>

          <button type="submit" className="login-v2-btn">
            Sign in to account
          </button>

        </form>

        <p className="login-v2-footer">
          Don't have an account? <Link to="/customer-signUp">Create one free</Link>
        </p>

      </div>
    </div>

  </div>
);

}

export default Login