import SidebarAdmin from "../admin/side-bar-admin"
import Navbar from "../customer/navbar"
import Sidebar from "../customer/sidebar"
import SidebarEmployee from "./sidebar-Employee"


const EmployeeDashBoard=()=>{

    return (
        <div>
           
           <div className="row">
            <Navbar/>
            </div>
            
      

        <div className="row">
        <div className="col-2 bg-black min-vh-100">
            <SidebarEmployee />
        </div>

        </div>
          </div>
    )
}


export default EmployeeDashBoard