
import { useNavigate } from "react-router-dom"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/customer/sidebar.css"
import { NavLink } from "react-router-dom";


const Sidebar = () => {

    const navigate = useNavigate()

    return (

        <div className="row bg-black " >

            {/* // <!-- ══ CUSTOMER SIDEBAR ══ --> */}
            <div className="col-12  " >

                {/* <!-- Logo --> */}
                {/* <div className="sb-logo">
                <div className="sb-icon">M</div>
                <div className="sb-name">Maverick</div>
            </div> */}

                {/* <!-- Main nav --> */}
               <div className="mt-5 ms-2">
                    <NavLink
                        to="/customer"
                        className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
                    >
                        <span className="sb-ni-ic">⊞</span> Home
                    </NavLink>

                    <NavLink
                        to="/accounts-dashboard"
                        end={false}
                        className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
                    >
                        <span className="sb-ni-ic">💳</span> Accounts
                    </NavLink>


                     <NavLink
                        to="/statement"
                        className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
                    >
                        <span className="sb-ni-ic">📄</span> Statement
                    </NavLink>

                    <NavLink
                        to="/transfer/within-bank"
                        className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
                    >
                        <span className="sb-ni-ic">⇄</span> Transfers
                    </NavLink>


                    <NavLink
                        to="/deposit"
                        className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
                    >
                        <span className="sb-ni-ic">↑</span> Deposit
                    </NavLink>

                     <NavLink
                        to="/withdraw"
                        className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
                    >
                        <span className="sb-ni-ic">↓</span> Withdraw
                    </NavLink>


                    <NavLink
                        to="/analytics"
                        className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
                    >
                        <span className="sb-ni-ic">◑</span> Analytics
                    </NavLink>

                    <NavLink
                        to="/loan"
                        className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
                    >
                        <span className="sb-ni-ic">⬙</span> Loans
                     
                    </NavLink>

                    <NavLink
                        to="/benificery"
                        className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
                    >
                        <span className="sb-ni-ic">⬙</span> Benificary
                     
                    </NavLink>
                </div>
                </div>

                    </div>

           




    

    )


}

export default Sidebar