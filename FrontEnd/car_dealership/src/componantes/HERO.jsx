import React from 'react';
import heroImage from '../assets/Model3Standard_84.jpg';

const HERO = () => {
  return (
    <section
      className="position-relative"
      style={{
        width: '100vw', 
        height: 'calc(100vh - 70px)', 
        minHeight: '500px',
        marginLeft: 'calc(-50vw + 50%)', 
      }}
    >
      {/* Background image */}
      <div
        className="position-absolute top-0 start-0 w-100 h-100"
        style={{
          backgroundImage: `url(${heroImage})`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          filter: 'brightness(0.7)',
          zIndex: 1,
        }}
      />

      {/* Overlay content */}
      <div
        className="position-relative container h-100 d-flex align-items-center"
        style={{ zIndex: 2 }}
      >
        <div className="row w-100 m-0">
          {/* Text content */}
          <div className="col-12 col-lg-6 text-center text-lg-start">
            <h1 className="display-3 fw-bold text-white mb-4">
              Find Your Dream Car
            </h1>
            <p className="lead text-white mb-4">
              Premium models, unbeatable deals, and a smooth buying experience await you at Elite Cars Dealership.
            </p>
            <div className="d-flex justify-content-center justify-content-lg-start gap-3">
              <button className="btn btn-outline-light btn-lg px-4">Login</button>
              <button className="btn btn-primary btn-lg px-4">Sign Up</button>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default HERO;
