import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom"

const ENavbar = () => {

 


    const navigate = useNavigate();
    const logout =()=> {

        localStorage.clear()
        navigate("/login")

    }
    return (
        <nav className="navbar navbar-expand-lg bg-body-tertiary">
  <div className="container-fluid">
    <Link className="navbar-brand" to="/employee">Mavericks Bank</Link>
    
    <div className="collapse navbar-collapse">
      <ul className="navbar-nav me-auto mb-2 mb-lg-0">
        <li className="nav-item">
          <Link className="nav-link active" to="/accounts-verification">Accounts </Link>
        </li>
         <li className="nav-item">
          <Link className="nav-link active" to="/loan">Loan </Link>
        </li>
       
        <li className="nav-item">
          <Link className="nav-link" to="/profile">profile</Link>
        </li>
         
      </ul>
      <form className="d-flex">
         
        <button className="btn btn-outline-success" onClick={logout}>Logout</button>
      </form>
    </div>
  </div>
</nav>
  )
}


export default ENavbar