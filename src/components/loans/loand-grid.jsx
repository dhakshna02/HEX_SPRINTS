import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../accounts/accounts-css/account-grid.css"; // reuse same CSS

const LoansGrid = () => {

  const navigate = useNavigate();

  const api = "http://localhost:8081/api/loan/get-loans"

  const [loans, setLoans] = useState([]);
  const [errmesg, setErrMesg] = useState();

  useEffect(() => {


    const fetchLoans = async () => {
      const config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("Token")
        }
      };

      try {

        const res = await axios.get(api, config);

        setLoans(res.data);
        console.log(res.data);
      } catch (err) {
        console.error(err);
        setErrMesg(err.message);
      }
    };

    fetchLoans();

  }, []);


  const hasPendingLoan = loans.some(
    (l) => l.loanStatus === "PENDING"
  );

  return (
    <div className="row  mt-4 ms-4 mb-4">

      <button
        className="btn-apply-loan"
        onClick={() => navigate("/loan-apply")}
        disabled={hasPendingLoan}
      >
        + Apply Loan
      </button>
      {hasPendingLoan && (
        <div style={{ color: "red", marginTop: "8px" }}>
          Loan already initiated. Cannot apply again.
        </div>
      )}

      {errmesg && <div className="error-box">{errmesg}</div>}

      {loans.map((l, index) => (

        <div className="col-md-6" key={index}>
          <div className="account-card active-card">

            <div className="card-top">
              <span className="badge savings">{l.loanType}</span>
              <span className="emoji">🏦</span>
            </div>

            <h5 className="acc-name">{l.customerName}</h5>

            <h3 className="acc-balance">₹ {Number(l?.approvedLoanAmount || 0).toLocaleString("en-IN")}</h3>
            <h6 className="acc-balance">
              Balance: ₹ {Number(l?.LoanBalance || 0).toLocaleString("en-IN")}
            </h6>

            <p className="acc-number">Loan ID: {l?.loanId ?? ""}</p>

            <div className="acc-meta">
              <div>
                <small>STATUS</small>
                <p>{l.loanStatus}</p>
              </div>

              <div>
                <small>DURATION</small>
                <p>{l.months} months</p>
              </div>

              <div>
                <small>EMi-Amount</small>
                <p>{l.emi}</p>
              </div>

              <div>
                <small>Loan Started At</small>
                <p>{l.LoanStatedAt} </p>
              </div>
            </div>

            <div className="acc-actions">
              <button
                className="btn-outline"
                onClick={() => navigate(`/loan/details/${l.loanId}`)}


              >

                Details
              </button>

              <button className="btn-dark" >
                Pay EMI
              </button>
            </div>

          </div>
        </div>

      ))}

    </div>
  );
};

export default LoansGrid;