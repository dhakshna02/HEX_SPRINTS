import { createRoot } from 'react-dom/client'
import App from './App'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Login from './components/auth/login'
import CustomerDashboard from './components/customer/customer-dashboard'
import CustomerSignUp from './components/customer/customer-signup'
import AdminDashBoard from './components/admin/admin-dashboard'
import EmployeeDashBoard from './components/employee/employee-dashboard'
import AccountsDashboard from './components/accounts/account-dashboard'
import CreateAccount from './components/accounts/create-account'
import AccountApproval from './components/employee/employee-AccountApproval'
import AccountDetails from './components/accounts/account-details'
import Statement from './components/statement/statement'
import Deposit from './components/Deposit/deposit'
import Withdraw from './components/withdraw/withdraw'
import TransferDashboard from './components/transfer/transfer-dashboard'
import TransferToOtheBankAccount from './components/transfer/transfer-outsideBank'
import TransferWithinBank from './components/transfer/transfer-Within-bank'
import LoanDashboard from './components/loans/loan-dashboard'
import LoanDetails from './components/loans/loan-details'
import CreateLoan from './components/loans/create-loan'
import AdminAssignAccounts from './components/admin/admin-account'
import AdminAssignLoans from './components/admin/admin-loan'
import EmployeeSignUp from './components/admin/emp-onboard'
import IdentityProof from './components/accounts/Accounts-docs-upload/accounts-upload-identityproof'
import AddressProof from './components/accounts/Accounts-docs-upload/accounts-upload-addressproof'
import PanCard from './components/accounts/Accounts-docs-upload/accounts-upload-pan'
import PhotoGraph from './components/accounts/Accounts-docs-upload/accounts-upload-photo'
import Signature from './components/accounts/Accounts-docs-upload/accounts-upload-signature-account-initation'
import RemarksNotification from './components/remarks/remarks-notification'
import Remarks from './components/remarks/remarks-notification'
import Signaturee from './components/accounts/accounts-upload-signature'
import GetAllFinancialAnalystLoans from './components/loan-employee/loan-getall-financial'
import OtherLoans from './components/loan-employee/other-loans'
import AddCollatral from './components/loans/loan-add-collatral'
import AssetVerifier from './components/loan-employee/loan-getall-assestVerifier'
import AssetValue from './components/loan-employee/loan-assest-value'
import { Provider } from 'react-redux'
import { store } from './store'
import GetAllLoansForManager from './components/loan-employee/loan-getall-manager'
import LoanApprove from './components/loan-employee/loan-approval-manager'
import FinanceDonut from './components/analytics/analytics'
import Benificiers from './components/Benificairaes/add-beneficiary'
import "primereact/resources/themes/lara-dark-blue/theme.css"; 
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";


const routes = createBrowserRouter([
  {
    path: "",
    element: <App />
  },

  {
    path: "/login",
    element: <Login />
  },
  {
    path: "/customer",
    element: <CustomerDashboard />
  },

  {
    path: "/customer-signUp",
    element: <CustomerSignUp />
  },
  {
    path: "/loan-admin",
    element: <AdminAssignLoans />
  },
  {
    path: "/statement",
    element: <Statement />

  },

  {
    path: "/accounts-admin",
    element: <AdminAssignAccounts />


  },
  {

    path: "/empl-onboard",
    element: <EmployeeSignUp />
  },

  {
    path: "/withdraw",
    element: <Withdraw />
  },
  {
    path: "/transfer/",
    element: <TransferDashboard />,

    children: [{
      path: "other-banks",
      element: <TransferToOtheBankAccount />
    },
    {
      path: "within-bank",
      element: <TransferWithinBank />


    }
    ]
  },



  {

    path: "/loan",
    element: <LoanDashboard />,
    children: [

      {
        path: "details/:id",
        element: <LoanDetails />
      }
    ]
  },{

    path:"/loan-apply",
    element:<CreateLoan/>
  }



  , {
    path: "/admin",
    element: <AdminDashBoard />
  },
  {
    path: "/employee",
    element: <EmployeeDashBoard />
  },
  {
    path: "/deposit",
    element: <Deposit />
  },
  {
    path: "/accounts-dashboard/",
    element: <AccountsDashboard />,
    children: [

      {
        path: "details/:id",
        element: <AccountDetails />
      }, {

         
      }
    ]
  },

  {
    path: "/create-account/",
    element: <CreateAccount />,
    children: [
      {
        path: "identity-proof",
        element: <IdentityProof />
      },
      {
        path: "address-proof",
        element: <AddressProof />
      },
      {
        path: "pancard",
        element: <PanCard />

      }, {
        path: "photo",
        element: <PhotoGraph />
      },
      {
        path: "signature",
        element: <Signature />
      },
      {
        path: "sign",
        element: <Signaturee />
      }

    ]

  },
  {
    path: "accounts-verification",
    element: <AccountApproval />

  }, {
    path: "/remarks",
    element: <Remarks />
  },{
    path:"/financial-analyst-loans",
    element:<GetAllFinancialAnalystLoans />
  },{
    path:"/other-loans/:id",
    element:<OtherLoans/>
  },{
    path:"/addCollatral/:id",
    element:<AddCollatral/>
  },{
    path:"/asset-verifier",
    element:<AssetVerifier/>
  },{
    path:"/verify-assests/:id",
    element:<AssetValue/>
  },{
    path:"/manager",
    element:<GetAllLoansForManager/>
  }
  ,{
    path:"/manager-loan/:id",
    element :<LoanApprove />
  },{
    path:"/analytics",
    element:<FinanceDonut/>
  },{
    path:"/benificery",
    element:<Benificiers/>
  }




])

createRoot(document.getElementById('root')).render(
  <Provider store={store}>
  <RouterProvider router={routes} >
    <App />
  </RouterProvider>

</Provider>
)
