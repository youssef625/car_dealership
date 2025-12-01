import React from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css';

const FEATURES = () => {
  return (
    <section
      className="position-relative"
      style={{
        width: '100vw',
        marginLeft: 'calc(-50vw + 50%)',
        backgroundColor: '#f8f9fa', // light background, can be changed
        padding: '5rem 0',
      }}
    >
      {/* Inner container to constrain content */}
      <div
        className="position-relative container"
        style={{ maxWidth: '1280px', margin: '0 auto', zIndex: 2 }}
      >
        <div className="row row-cols-1 row-cols-md-2 align-items-md-center g-5 py-5">

          {/* Left column: text */}
          <div className="col d-flex flex-column align-items-start gap-3">
            <h2 className="fw-bold text-body-emphasis">
              Why Choose Our Dealership?
            </h2>
            <p className="text-body-secondary">
              We provide the best cars at unbeatable prices, with exceptional service, easy financing, and a smooth buying experience. Explore our premium collection and drive away with confidence.
            </p>
            <a href="#contact" className="btn btn-primary btn-lg">
              Contact Us
            </a>
          </div>

          {/* Right column: features grid */}
          <div className="col">
            <div className="row row-cols-1 row-cols-sm-2 g-4">

              {/* Feature 1 */}
              <div className="col d-flex flex-column gap-3">
                <div className="d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient rounded-3" style={{ width: '70px', height: '70px', fontSize: '32px' }}>
                  <i className="bi bi-speedometer2"></i>
                </div>
                <h4 className="fw-semibold mb-0 text-body-emphasis">Performance Cars</h4>
                <p className="text-body-secondary">High-performance vehicles for those who love speed and precision.</p>
              </div>

              {/* Feature 2 */}
              <div className="col d-flex flex-column gap-3">
                <div className="d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient rounded-3" style={{ width: '70px', height: '70px', fontSize: '32px' }}>
                  <i className="bi bi-car-front-fill"></i>
                </div>
                <h4 className="fw-semibold mb-0 text-body-emphasis">Wide Selection</h4>
                <p className="text-body-secondary">Choose from a diverse range of cars to match your style and needs.</p>
              </div>

              {/* Feature 3 */}
              <div className="col d-flex flex-column gap-3">
                <div className="d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient rounded-3" style={{ width: '70px', height: '70px', fontSize: '32px' }}>
                  <i className="bi bi-wallet2"></i>
                </div>
                <h4 className="fw-semibold mb-0 text-body-emphasis">Affordable Financing</h4>
                <p className="text-body-secondary">Flexible payment plans and financing options for all budgets.</p>
              </div>

              {/* Feature 4 */}
              <div className="col d-flex flex-column gap-3">
                <div className="d-inline-flex align-items-center justify-content-center text-bg-primary bg-gradient rounded-3" style={{ width: '70px', height: '70px', fontSize: '32px' }}>
                  <i className="bi bi-tools"></i>
                </div>
                <h4 className="fw-semibold mb-0 text-body-emphasis">Reliable Service</h4>
                <p className="text-body-secondary">Professional maintenance and support to keep your car running smoothly.</p>
              </div>

            </div>
          </div>

        </div>
      </div>
    </section>
  );
};

export default FEATURES;
