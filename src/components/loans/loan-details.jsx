import { useEffect, useState } from "react";
import "../accounts/accounts-css/account-detail.css"; // reuse same CSS
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import "/Users/dhakshnamoorthy/Desktop/MavericksBankUi/mavericks-bank-ui/src/components/loans/loancss/loan-details.css"

const LoanDetails = () => {

  const { id } = useParams();
  const navigate = useNavigate();

  const api = "http://localhost:8081/api/loan/get-loans/";
  const loanAcceptanceApi = "http://localhost:8081/api/loan/loan-confirmation"
  const [loanConfirmation, setLoanConfirmation] = useState(null)
  const [loanDetails, setLoanDetails] = useState(undefined);
  const [errMesg, setErrMesg] = useState();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const loanstatus = useState(loanDetails?.loanStatus ?? "")

  useEffect(() => {

    const fetchLoanDetails = async () => {

      const config = {
        headers: {
          "Authorization": "Bearer " + localStorage.getItem("Token")
        }

      }

      try {
        const response = await axios.get(api + id, config);
        setLoanDetails(response.data[0]);
        console.log(response.data);
      } catch (err) {
        console.error(err);
        setErrMesg(err.message);
      }
    };

    fetchLoanDetails();



  }, [id]);



  const handleLoanConfirmation = async (value) => {
    const config = {
      headers: {
        "Authorization": "Bearer " + localStorage.getItem("Token")
      }

    }



    const respons = await axios.put(loanAcceptanceApi, {
      "loanId": loanDetails.loanId,
      "loanConfirmation": value
    }, config)

     setIsSubmitting(false); // allow


    // setLoanConfirmation(value)
    // console.log(loanConfirmation)
    // console.log(response.data)
  }

  return (
    <div className="row mt-4">

      <div className="col-md-12">
        <div className="account-info-card">

          {/* HEADER */}
          <div className="info-header">
            <h5>Loan Info</h5>
          </div>

          {/* STATUS */}
          <div className="info-row">
            <span>Status</span>
            <span
              className={`status ${loanDetails?.loanStatus === "APPROVED"
                ? "active"
                : "inactive"
                }`}
            >

              {loanDetails?.loanStatus ?? ""}
            </span>
          </div>

          {/* CUSTOMER */}
          <div className="info-row">
            <span>Loan Id</span>
            <span>{loanDetails?.loanId ?? ""}</span>
          </div>

          <div className="info-row">
            <span>Add collatral</span>
            <span><button className="btn-dark" disabled={
              loanDetails?.loanStatus !== "PENDING" || loanDetails?.loanType === "PERSONAL"
            } onClick={() => navigate(`/addCollatral/${loanDetails.loanId}`)} > add collatral </button></span>
          </div>


          {loanDetails?.loanStatus === "APPROVED" && (
            <div className="info-row">
              <span>Accept Loan</span>

              <button
                className="btn btn-success"
                disabled={isSubmitting}
                onClick={() => handleLoanConfirmation("ACCEPTED")}
              >
                Accept Loan
              </button>

              <button
                className="btn btn-danger"
                disabled={isSubmitting}
                onClick={() => handleLoanConfirmation("REJECTED")}
              >
                Reject Loan
              </button>
            </div>
          )}


          {/* LOAN TYPE */}
          <div className="info-row">
            <span>Loan Type</span>
            <span>

              {loanDetails?.loanType ?? ""}</span>
          </div>

          {/* REQUESTED AMOUNT */}
          <div className="info-row">
            <span>Requested Amount</span>
            <span>₹{loanDetails?.requestedLoanAmount ?? ""}</span>
          </div>

          {/* APPROVED AMOUNT */}
          <div className="info-row">
            <span>Approved Amount</span>
            <span className="balance">
              ₹{loanDetails?.approvedLoanAmount ?? ""}
            </span>
          </div>

          {/* INTEREST */}
          <div className="info-row">
            <span>Interest Rate</span>
            <span>{loanDetails?.intrestRate ?? ""}%</span>
          </div>

          {/* TENURE */}
          <div className="info-row">
            <span>Tenure</span>
            <span>{loanDetails?.months ?? ""} months</span>
          </div>

          {/* EMI */}
          <div className="info-row">
            <span>Monthly EMI</span>
            <span>₹{loanDetails?.emi ?? ""}</span>
          </div>

          {/* START DATE */}
          <div className="info-row">
            <span>Loan Started At</span>
            <span>{loanDetails?.LoanStatedAt ?? ""}</span>
          </div>

          {/* REMAINING */}
          <div className="info-row">
            <span>Remaining Balance</span>
            <span className="balance">
              ₹{loanDetails?.LoanBalance ?? ""}
            </span>
          </div>

        </div>
      </div>

      {/* ERROR */}
      {errMesg && (
        <div className="error-box mt-3">
          {errMesg}
        </div>
      )}

    </div>
  );
};

export default LoanDetails;