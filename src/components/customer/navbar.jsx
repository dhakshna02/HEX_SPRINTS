import { Link, NavLink, useNavigate } from "react-router-dom"

import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/customer/navbar.css"
import { useEffect, useState } from "react";
import axios from "axios";

const Navbar = () => {

const [name, setname] = useState(undefined)
const[errMesg,seterrMesg] = useState(undefined)



  const api = "http://localhost:8081/api/customer/name"
  const navigate = useNavigate();


    useEffect(()=>{

        const config={
          headers:{
            "Authorization":"Bearer "+ localStorage.getItem("Token")
          }
        }



        const fetchName = async ()=>{


          try{
          const response = await axios.get(api,config)
          setname(response.data?.name || "User")


          console.log(response.data)
          }
          catch(err){
            seterrMesg(err.message)
          }
        }

        fetchName()

    },[])




  const logout = () => {

    localStorage.clear()
    navigate("/login")

  }
  return (
    <div className="topbar d-flex align-items-center px-4">

      {/* LEFT - LOGO */}
      <div className="sb-logo">
        <div className="sb-icon">M</div>
        <div className="sb-namek">Mavericks</div>
      </div>

      {/* CENTER - SEARCH */}
      {/* <div className="search-container">
        <div className="search-bar d-flex align-items-center">
          <span className="search-icon">🔍</span>
          <input
            type="text"
            placeholder="Search transactions, accounts..."
          />
        </div>
      </div> */}

      {/* RIGHT - USER */}
      <div className="ms-auto d-flex align-items-center gap-3">

        <button className="btn btn-outline-success" onClick={logout}>Logout</button>

        {/* Bell */}
        <div className="bell">
          <NavLink
                        to="/remarks"
                       
                       
                    >
                        🔔<span className="bell-dot"></span>
                    </NavLink>
          
        </div>

        {/* User */}
        <div className="user-area d-flex align-items-center">
          <div className="text-end me-2">
            <div className="user-name">
              {name || "User"}
            </div>

          </div>
          <div className="user-avatar"> {name ? name.charAt(0).toUpperCase() : "U"}</div>
        </div>

      </div>

    </div>
  )
}


export default Navbar