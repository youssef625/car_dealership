import React from "react";
import { Link } from "react-router-dom";
import 'bootstrap-icons/font/bootstrap-icons.css'; 

const NAVBAR = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light shadow-sm fixed-top">
      <div className="container-fluid">

        {/* Left: Car icon + Brand */}
        <Link className="navbar-brand d-flex align-items-center" to="/">
          <i className="bi bi-car-front-fill me-2" style={{ fontSize: '1.5rem' }}></i>
          Car Dealership
        </Link>

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
              <Link className="nav-link" to="/">Home</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/products">Our Products</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/contact">Contact Us</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/about">About Us</Link>
            </li>
          </ul>
        </div>

        {/* Right: Login / Signup */}
        <div className="d-flex ms-auto">
          <Link to="/login" className="btn btn-outline-primary me-2">Login</Link>
          <Link to="/signup" className="btn btn-primary">Signup</Link>
        </div>

      </div>
    </nav>
  );
};

export default NAVBAR;
