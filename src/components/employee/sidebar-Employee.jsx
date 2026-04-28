
import { useNavigate } from "react-router-dom"
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/customer/sidebar.css"
import { NavLink } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/employee/sidebar-emp.css"

const SidebarEmployee = () => {

    const api = "http://localhost:8081/api/employee/designation"
    const [designation, setDesignation] = useState(undefined)
    const [loanRoute, setLoanRoute] = useState(undefined)

    

    const navigate = useNavigate()





    useEffect(() => {



        const fetchDesgination = async () => {

            const config = {
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("Token")
                }
            }


            const response = await axios.get(api, config)
            setDesignation(response.data.designation)
            const role = response.data.designation

            if (role === "FINACIAL_ANALYST") {
                setLoanRoute("/financial-analyst-loans")
            } else if (role === "ASSET_VERIFIER") {
                setLoanRoute("/asset-verifier")
            } else if (role === "MANAGER") {
                setLoanRoute("/manager")
            }


        }

        fetchDesgination()




    }, [])



  

  
  return (
    <div className="row bg-black" >
        <div className="col-12 ">
           <div className="mt-4">

            <NavLink
                to="/accounts-verification"
                end={false}
                className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
            >
                <span className="sb-ni-ic">💳</span> Accounts
            </NavLink>

            <NavLink
                to={loanRoute}
                className={({ isActive }) => `sb-ni ${isActive ? "active" : ""}`}
            >
                <span className="sb-ni-ic">⬙</span> Loans
            </NavLink>
            </div>
        </div>
    </div>
);

}

export default SidebarEmployee