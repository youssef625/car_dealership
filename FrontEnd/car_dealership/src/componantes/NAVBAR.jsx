import React from "react";
import 'bootstrap-icons/font/bootstrap-icons.css'; 

const NAVBAR = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm fixed-top">
      <div className="container-fluid">

        {/* Left: Car icon + Brand */}
        <a className="navbar-brand d-flex align-items-center" href="#">
          <i className="bi bi-car-front-fill me-2" style={{ fontSize: '1.5rem' }}></i>
          Car Dealership
        </a>

        {/* Toggler for mobile */}
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        {/* Navbar Links (center) */}
        <div className="collapse navbar-collapse justify-content-center" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item">
              <a className="nav-link active" href="#">Home</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#">Our Products</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#">Contact Us</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#">About Us</a>
            </li>
          </ul>
        </div>

        {/* Right: Login / Signup */}
        <div className="d-flex ms-auto">
          <button className="btn btn-outline-primary me-2">Login</button>
          <button className="btn btn-primary">Signup</button>
        </div>

      </div>
    </nav>
  );
};

export default NAVBAR;
