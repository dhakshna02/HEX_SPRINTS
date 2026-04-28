import { NavLink } from "react-router-dom";
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/admin/admin-css/addmin-sidebas.css"

const SidebarAdmin = () => {
    return (
        <div className="row bg-black">

            <div className="col-12 mt-5">

                <NavLink to="/admin" className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}>
                    <span className="sb-ni-ic">⊞</span> Home
                </NavLink>

                <NavLink to="/accounts-admin" className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}>
                    <span className="sb-ni-ic">💳</span> Accounts
                </NavLink>

                <NavLink to="/empl-onboard" className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}>
                    <span className="sb-ni-ic">㊦</span> Employee-Onboard
                </NavLink>

                <NavLink to="/loan-admin" className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}>
                    <span className="sb-ni-ic">⬙</span> Loans
                </NavLink>

            </div>
        </div>
    );
};

export default SidebarAdmin;