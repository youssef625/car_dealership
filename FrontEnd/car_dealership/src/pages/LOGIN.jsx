import React from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css';
import NAVBAR from '../componantes/NAVBAR';

const LOGIN = () => {
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
            style={{ width: '100%', maxWidth: '400px' }}
          >
            {/* Car icon */}
            <i
              className="bi bi-car-front-fill mb-4 d-block mx-auto"
              style={{ fontSize: '3rem', color: '#0d6efd' }}
            ></i>

            <h1 className="h4 mb-4 fw-bold text-center">Sign in to your account</h1>

            <div className="form-floating mb-3">
              <input
                type="email"
                className="form-control rounded-3"
                id="floatingInput"
                placeholder="name@example.com"
              />
              <label htmlFor="floatingInput">Email address</label>
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

            <div className="form-check text-start mb-4">
              <input
                className="form-check-input"
                type="checkbox"
                value="remember-me"
                id="checkDefault"
              />
              <label className="form-check-label" htmlFor="checkDefault">
                Remember me
              </label>
            </div>

            <button
              className="btn btn-primary w-100 py-2 rounded-3"
              type="submit"
              style={{ transition: '0.3s' }}
              onMouseOver={e => (e.currentTarget.style.backgroundColor = '#0b5ed7')}
              onMouseOut={e => (e.currentTarget.style.backgroundColor = '#0d6efd')}
            >
              Sign in
            </button>

            <p className="mt-5 mb-0 text-body-secondary text-center">
              © 2017–2025 Elite Cars Dealership
            </p>
          </form>
        </div>
      </main>
    </>
  );
};

export default LOGIN;
