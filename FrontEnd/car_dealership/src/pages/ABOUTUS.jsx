import React from 'react';
import NAVBAR from '../componantes/NAVBAR'; // Make sure the path is correct

const ABOUTUS = () => {
  return (
    <>
      {/* Navbar */}
      <NAVBAR />

      {/* About Us Section */}
      <section className="py-5">
        <div className="container">
          {/* Centered Heading */}
          <div className="text-center">
            <span className="text-muted">Our Story</span>
            <h2 className="display-5 fw-bold mb-3">About Our Dealership</h2>
          </div>

          {/* Two Columns */}
          <div className="row gx-5">
            <div className="col-md-6">
              <div className="lead text-center text-md-start">
                <p>
                  At Car Dealership, we are passionate about bringing you the best vehicles with exceptional service. 
                  From brand-new cars to certified pre-owned models, we ensure quality, reliability, and style for every customer.
                </p>
              </div>
            </div>
            <div className="col-md-6">
              <div className="lead text-center text-md-start">
                <p>
                  Our team of automotive experts is dedicated to helping you find the perfect car that fits your lifestyle and budget. 
                  We believe in transparency, trust, and customer satisfaction at every step of your car buying journey.
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default ABOUTUS;
