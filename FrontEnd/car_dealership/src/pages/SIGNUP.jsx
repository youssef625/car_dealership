import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap-icons/font/bootstrap-icons.css';
import NAVBAR from '../componantes/NAVBAR';

const SIGNUP = () => {
  return (
    <>
      <NAVBAR />

      <main
        className="d-flex justify-content-center align-items-center"
        style={{
          width: '100vw',
          marginLeft: 'calc(-50vw + 50%)',
          minHeight: '100vh',
          backgroundColor: '#f1f3f5',
        }}
      >
        <div
          className="container"
          style={{
            maxWidth: '1280px',
            margin: '0 auto',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '100%',
          }}
        >
          <form
            className="p-5 bg-white rounded-4 shadow-lg"
            style={{ width: '100%', maxWidth: '450px' }}
          >
            {/* Car icon */}
            <i
              className="bi bi-car-front-fill mb-4 d-block mx-auto"
              style={{ fontSize: '3rem', color: '#0d6efd' }}
            ></i>

            <h1 className="h4 mb-4 fw-bold text-center">Create your account</h1>

            <div className="form-floating mb-3">
              <input
                type="text"
                className="form-control rounded-3"
                id="floatingName"
                placeholder="Full Name"
              />
              <label htmlFor="floatingName">Full Name</label>
            </div>

            <div className="form-floating mb-3">
              <input
                type="email"
                className="form-control rounded-3"
                id="floatingEmail"
                placeholder="name@example.com"
              />
              <label htmlFor="floatingEmail">Email address</label>
            </div>

            <div className="form-floating mb-3">
              <input
                type="password"
                className="form-control rounded-3"
                id="floatingPassword"
                placeholder="Password"
              />
              <label htmlFor="floatingPassword">Password</label>
            </div>

            <div className="form-floating mb-4">
              <input
                type="password"
                className="form-control rounded-3"
                id="floatingConfirmPassword"
                placeholder="Confirm Password"
              />
              <label htmlFor="floatingConfirmPassword">Confirm Password</label>
            </div>

            <button
              type="submit"
              className="btn btn-primary w-100 py-2 rounded-3 mb-3"
              style={{ transition: '0.3s' }}
              onMouseOver={e => (e.currentTarget.style.backgroundColor = '#0b5ed7')}
              onMouseOut={e => (e.currentTarget.style.backgroundColor = '#0d6efd')}
            >
              Sign Up
            </button>

            <p className="text-center text-body-secondary">
              Already have an account? <Link to="/login">Sign in</Link>
            </p>

            <p className="mt-4 mb-0 text-body-secondary text-center">
              © 2017–2025 Elite Cars Dealership
            </p>
          </form>
        </div>
      </main>
    </>
  );
};

export default SIGNUP;
