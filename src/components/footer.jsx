const Footer = () => {
  return (
    <footer className="bg-black text-light ">
      <div className="container py-4">
        
        <div className="row h-100">
          
          {/* Left */}
          <div className="col-md-6">
            <h5>Mavericks Bank</h5>
            <p className="mb-0">
              Secure. Reliable. Fast banking for everyone.
            </p>
          </div>

          {/* Right */}
          <div className="col-md-6 text-md-end mt-3 mt-md-0">
            <p className="mb-1">© 2026 Mavericks Bank</p>
            <small>All rights reserved</small>
          </div>

        </div>

      </div>
    </footer>
  );
};

export default Footer;